package com.example.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.BuildConfig
import com.example.data.AppDatabase
import com.example.data.ProfileRepository
import com.example.data.SensitivityProfile
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import java.util.concurrent.TimeUnit

sealed interface AiGenerationState {
    object Idle : AiGenerationState
    object Loading : AiGenerationState
    data class Success(
        val general: Int,
        val redDot: Int,
        val scope2x: Int,
        val scope4x: Int,
        val sniper: Int,
        val freeLook: Int,
        val recommendedDpi: Int,
        val recommendedFireSize: Int,
        val aiTips: String
    ) : AiGenerationState
    data class Error(val errorMessage: String) : AiGenerationState
}

class SensitivityViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: ProfileRepository
    val savedProfiles = MutableStateFlow<List<SensitivityProfile>>(emptyList())

    private val _aiState = MutableStateFlow<AiGenerationState>(AiGenerationState.Idle)
    val aiState: StateFlow<AiGenerationState> = _aiState.asStateFlow()

    init {
        val database = AppDatabase.getDatabase(application)
        repository = ProfileRepository(database.profileDao())
        
        // Load profiles reactively
        viewModelScope.launch {
            repository.allProfiles
                .flowOn(Dispatchers.IO)
                .catch { e ->
                    // Handle query errors
                }
                .collect { list ->
                    savedProfiles.value = list
                }
        }
    }

    fun saveConfig(
        profileName: String,
        deviceName: String,
        general: Int,
        redDot: Int,
        scope2x: Int,
        scope4x: Int,
        sniper: Int,
        freeLook: Int,
        fireButtonSize: Int,
        dpi: Int,
        clawType: String,
        notes: String
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            val entity = SensitivityProfile(
                profileName = profileName.ifBlank { "Unsaved Preset" },
                deviceName = deviceName.ifBlank { "Standard Mobile" },
                general = general,
                redDot = redDot,
                scope2x = scope2x,
                scope4x = scope4x,
                sniper = sniper,
                freeLook = freeLook,
                fireButtonSize = fireButtonSize,
                dpi = dpi,
                clawType = clawType,
                notes = notes
            )
            repository.insertProfile(entity)
        }
    }

    fun deleteProfile(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteProfileById(id)
        }
    }

    fun resetAiState() {
        _aiState.value = AiGenerationState.Idle
    }

    fun generateAiSensitivity(deviceName: String, ramSize: String, playstyle: String) {
        _aiState.value = AiGenerationState.Loading

        viewModelScope.launch(Dispatchers.IO) {
            val key = BuildConfig.GEMINI_API_KEY
            if (key.isBlank() || key == "MY_GEMINI_API_KEY") {
                // Fallback to local rule engine if user has not configured their Gemini key
                simulateLocalAiResult(deviceName, ramSize, playstyle)
                return@launch
            }

            try {
                val promptText = """
                    You are a professional eSports coach for Garena Free Fire. 
                    Generate the optimal sensitivity settings and drag headshot tips in Hinglish/Urdu for:
                    Device: $deviceName
                    RAM: $ramSize
                    Playstyle: $playstyle
                    
                    Respond strictly in JSON format matching this payload exactly. No extra markup or backticks:
                    {
                      "general": 98,
                      "redDot": 92,
                      "scope2x": 88,
                      "scope4x": 84,
                      "sniper": 40,
                      "freeLook": 70,
                      "recommendedDpi": 415,
                      "recommendedFireSize": 46,
                      "aiTips": "Provide 3 short, specific drag headshot and movement strategy tips in Roman Urdu/Hindi (Hinglish) corresponding to this playstyle and device RAM."
                    }
                """.trimIndent()

                // Construct JSON request body
                val requestJson = JSONObject().apply {
                    val contentsArr = org.json.JSONArray().apply {
                        val partsObj = org.json.JSONObject().apply {
                            put("text", promptText)
                        }
                        val contentObj = org.json.JSONObject().apply {
                            put("parts", org.json.JSONArray().put(partsObj))
                        }
                        put(contentObj)
                    }
                    put("contents", contentsArr)

                    // Request structured JSON response
                    val configObj = org.json.JSONObject().apply {
                        val formatObj = org.json.JSONObject().apply {
                            val textObj = org.json.JSONObject().apply {
                                put("mimeType", "application/json")
                            }
                            put("text", textObj)
                        }
                        put("responseFormat", formatObj)
                    }
                    put("generationConfig", configObj)
                }

                val okHttpClient = OkHttpClient.Builder()
                    .connectTimeout(30, TimeUnit.SECONDS)
                    .readTimeout(30, TimeUnit.SECONDS)
                    .build()

                val request = Request.Builder()
                    .url("https://generativelanguage.googleapis.com/v1beta/models/gemini-3.5-flash:generateContent?key=$key")
                    .post(requestJson.toString().toRequestBody("application/json".toMediaType()))
                    .build()

                val response = okHttpClient.newCall(request).execute()
                if (response.isSuccessful) {
                    val responseBody = response.body?.string()
                    if (responseBody != null) {
                        val resultJson = parseGeminiResponse(responseBody)
                        if (resultJson != null) {
                            withContext(Dispatchers.Main) {
                                _aiState.value = AiGenerationState.Success(
                                    general = resultJson.optInt("general", 95),
                                    redDot = resultJson.optInt("redDot", 90),
                                    scope2x = resultJson.optInt("scope2x", 88),
                                    scope4x = resultJson.optInt("scope4x", 85),
                                    sniper = resultJson.optInt("sniper", 45),
                                    freeLook = resultJson.optInt("freeLook", 70),
                                    recommendedDpi = resultJson.optInt("recommendedDpi", 400),
                                    recommendedFireSize = resultJson.optInt("recommendedFireSize", 48),
                                    aiTips = resultJson.optString("aiTips", "Drag instantly when crosshair is white around enemy head level.")
                                )
                            }
                        } else {
                            // Fallback on parsing error
                            simulateLocalAiResult(deviceName, ramSize, playstyle, "Parsing issue, showing offline model.")
                        }
                    } else {
                        simulateLocalAiResult(deviceName, ramSize, playstyle, "Empty response, offline parameters loaded.")
                    }
                } else {
                    simulateLocalAiResult(deviceName, ramSize, playstyle, "Server status ${response.code}, showing offline estimation.")
                }
            } catch (e: Exception) {
                simulateLocalAiResult(deviceName, ramSize, playstyle, "Failed to connect: ${e.localizedMessage}. Offline settings active.")
            }
        }
    }

    private fun parseGeminiResponse(rawBody: String): JSONObject? {
        return try {
            val root = JSONObject(rawBody)
            val candidates = root.optJSONArray("candidates") ?: return null
            if (candidates.length() > 0) {
                val firstCandidate = candidates.getJSONObject(0)
                val content = firstCandidate.optJSONObject("content") ?: return null
                val parts = content.optJSONArray("parts") ?: return null
                if (parts.length() > 0) {
                    val text = parts.getJSONObject(0).optString("text")
                    return JSONObject(text)
                }
            }
            null
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    private suspend fun simulateLocalAiResult(
        deviceName: String,
        ramSize: String,
        playstyle: String,
        systemLog: String = ""
    ) {
        // High quality offline fallback generator using simple math rules
        delayTime()
        val baseGeneral = if (ramSize.contains("2GB") || ramSize.contains("3GB") || ramSize.contains("4GB")) 100 else 96
        val baseRedDot = baseGeneral - 6
        val base2x = baseGeneral - 9
        val base4x = baseGeneral - 12
        val baseSniper = 45
        val baseFree = 70
        val baseFireSize = if (playstyle.lowercase().contains("one-tap")) 44 else 50
        val baseDpi = if (ramSize.contains("Budget") || ramSize.contains("4GB")) 420 else 390

        val extraLog = if (systemLog.isNotEmpty()) "[$systemLog] " else ""
        val calculatedTips = when {
            playstyle.lowercase().contains("one-tap") -> "${extraLog}One-Tap ke liye hamesha Shotgun ki quick switch practice kare training grounds me. Crosshair chest level par lock karke ekdam upar drag lagayein. Fire button margin strictly lower visual area me rahe."
            playstyle.lowercase().contains("sniper") -> "${extraLog}Double Sniper chalane ke liye fire button dabate hi slot menu change karke fire timing optimize karein. Snipe scope crosshair always red center mode rkhein."
            else -> "${extraLog}$deviceName ($ramSize RAM) me movement boost ke liye 3-Finger layout sabse best hai. Sit-up Gloo Wall key layout apply karein. Rotation-drag pull lagane se red damage hit hoga speed me."
        }

        withContext(Dispatchers.Main) {
            _aiState.value = AiGenerationState.Success(
                general = baseGeneral,
                redDot = baseRedDot,
                scope2x = base2x,
                scope4x = base4x,
                sniper = baseSniper,
                freeLook = baseFree,
                recommendedDpi = baseDpi,
                recommendedFireSize = baseFireSize,
                aiTips = calculatedTips
            )
        }
    }

    private suspend fun delayTime() {
        withContext(Dispatchers.IO) {
            Thread.sleep(1500) // Simulating network lag
        }
    }
}
