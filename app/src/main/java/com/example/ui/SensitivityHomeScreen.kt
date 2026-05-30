package com.example.ui

import androidx.compose.animation.*
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.data.*
import com.example.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SensitivityHomeScreen(
    viewModel: SensitivityViewModel,
    modifier: Modifier = Modifier
) {
    var activeTab by remember { mutableStateOf("DEVICES") }
    val savedProfiles by viewModel.savedProfiles.collectAsStateWithLifecycle()
    val aiState by viewModel.aiState.collectAsStateWithLifecycle()

    var showSaveDialog by remember { mutableStateOf(false) }
    
    // Temporary variables for manual saves
    var tempGeneral by remember { mutableStateOf(95) }
    var tempRedDot by remember { mutableStateOf(90) }
    var temp2x by remember { mutableStateOf(85) }
    var temp4x by remember { mutableStateOf(80) }
    var tempSniper by remember { mutableStateOf(50) }
    var tempFreeLook by remember { mutableStateOf(70) }
    var tempFireButtonSize by remember { mutableStateOf(50) }
    var tempDpi by remember { mutableStateOf(360) }
    var tempBrand by remember { mutableStateOf("SAMSUNG") }
    var tempModel by remember { mutableStateOf("Custom Build") }

    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = "FIRE",
                            fontWeight = FontWeight.Black,
                            fontFamily = FontFamily.Monospace,
                            fontSize = 24.sp,
                            color = RedPrimary
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = "SENSITIVITY",
                            fontWeight = FontWeight.Bold,
                            fontSize = 20.sp,
                            color = Color.White
                        )
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = SlateBackground
                )
            )
        },
        containerColor = SlateBackground
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            // Main Top Navigation Row for esports categories
            LazyRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp, horizontal = 12.dp)
                    .testTag("categories_navigation_row"),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                val menuItems = listOf(
                    "DEVICES" to "📱 Presets",
                    "HUD" to "🧩 Custom HUD",
                    "TIPS" to "💡 Hacks & Tips",
                    "PRO_PLAYERS" to "🏆 Pro Presets",
                    "AI_COACH" to "🤖 AI Optimizer",
                    "SAVES" to "💾 Saved Configs"
                )
                items(menuItems) { (tag, label) ->
                    val isActive = activeTab == tag
                    val bgBrush = if (isActive) {
                        Brush.horizontalGradient(listOf(RedPrimary, RedDark))
                    } else {
                        Brush.horizontalGradient(listOf(SlateSurface, SlateSurface))
                    }
                    val borderStroke = if (isActive) 0.dp else 1.dp

                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(20.dp))
                            .background(bgBrush)
                            .border(
                                width = borderStroke,
                                color = if (isActive) Color.Transparent else SlateCard,
                                shape = RoundedCornerShape(20.dp)
                            )
                            .clickable { activeTab = tag }
                            .padding(horizontal = 16.dp, vertical = 10.dp)
                            .testTag("nav_tab_$tag")
                    ) {
                        Text(
                            text = label,
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold,
                            color = if (isActive) Color.White else TextMuted
                        )
                    }
                }
            }

            // Central content depending on activeTab
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
            ) {
                when (activeTab) {
                    "DEVICES" -> DevicesPresetsView(
                        onSelectSens = { gen, red, s2x, s4x, snip, free, dpi, fSize, brand, model ->
                            tempGeneral = gen
                            tempRedDot = red
                            temp2x = s2x
                            temp4x = s4x
                            tempSniper = snip
                            tempFreeLook = free
                            tempDpi = dpi
                            tempFireButtonSize = fSize
                            tempBrand = brand
                            tempModel = model
                            showSaveDialog = true
                        }
                    )
                    "HUD" -> CustomHudView()
                    "TIPS" -> HeadshotTipsView()
                    "PRO_PLAYERS" -> ProPlayersView(
                        onApplyProSens = { gen, red, s2x, s4x, snip, free, dpi, fSize, name ->
                            tempGeneral = gen
                            tempRedDot = red
                            temp2x = s2x
                            temp4x = s4x
                            tempSniper = snip
                            tempFreeLook = free
                            tempDpi = dpi
                            tempFireButtonSize = fSize
                            tempBrand = "Pro Player"
                            tempModel = name
                            showSaveDialog = true
                        }
                    )
                    "AI_COACH" -> AiSensitivityView(
                        viewModel = viewModel,
                        aiState = aiState,
                        onSaveAiSens = { gen, red, s2x, s4x, snip, free, dpi, fSize, model ->
                            tempGeneral = gen
                            tempRedDot = red
                            temp2x = s2x
                            temp4x = s4x
                            tempSniper = snip
                            tempFreeLook = free
                            tempDpi = dpi
                            tempFireButtonSize = fSize
                            tempBrand = "Gemini AI"
                            tempModel = model
                            showSaveDialog = true
                        }
                    )
                    "SAVES" -> SavedConfigsView(
                        savedProfiles = savedProfiles,
                        onDelete = { id -> viewModel.deleteProfile(id) }
                    )
                }
            }
        }
    }

    // Interactive Dialog to save a target configuration to the DB!
    if (showSaveDialog) {
        var profileNameInput by remember { mutableStateOf("") }
        var clawTypeSelected by remember { mutableStateOf("2-Finger") }
        var notesInput by remember { mutableStateOf("") }

        AlertDialog(
            onDismissRequest = { showSaveDialog = false },
            title = {
                Text(
                    text = "Save Config to Device",
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                )
            },
            text = {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Text(
                        text = "Configuring layouts for $tempBrand $tempModel",
                        color = RedPrimary,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.SemiBold
                    )

                    OutlinedTextField(
                        value = profileNameInput,
                        onValueChange = { profileNameInput = it },
                        label = { Text("Profile Name (e.g. My Drag Setup)") },
                        singleLine = true,
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedTextColor = Color.White,
                            unfocusedTextColor = Color.LightGray,
                            focusedBorderColor = RedPrimary,
                            unfocusedBorderColor = SlateCard
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("input_save_profile_name")
                    )

                    // Dropdown simulation for claw layout selector
                    Text(
                        text = "Select Custom HUD Claw Type",
                        color = TextMuted,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        listOf("2-Finger", "3-Finger", "4-Finger").forEach { claw ->
                            val isClawSelected = clawTypeSelected == claw
                            Box(
                                modifier = Modifier
                                    .weight(1f)
                                    .clip(RoundedCornerShape(8.dp))
                                    .background(if (isClawSelected) RedPrimary else SlateSurface)
                                    .clickable { clawTypeSelected = claw }
                                    .padding(vertical = 8.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = claw,
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = if (isClawSelected) Color.White else TextMuted
                                )
                            }
                        }
                    }

                    OutlinedTextField(
                        value = notesInput,
                        onValueChange = { notesInput = it },
                        label = { Text("Custom Notes or DPI settings") },
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedTextColor = Color.White,
                            unfocusedTextColor = Color.LightGray,
                            focusedBorderColor = RedPrimary,
                            unfocusedBorderColor = SlateCard
                        ),
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        viewModel.saveConfig(
                            profileName = profileNameInput,
                            deviceName = "$tempBrand $tempModel",
                            general = tempGeneral,
                            redDot = tempRedDot,
                            scope2x = temp2x,
                            scope4x = temp4x,
                            sniper = tempSniper,
                            freeLook = tempFreeLook,
                            fireButtonSize = tempFireButtonSize,
                            dpi = tempDpi,
                            clawType = clawTypeSelected,
                            notes = notesInput
                        )
                        showSaveDialog = false
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = RedPrimary),
                    modifier = Modifier.testTag("confirm_save_profile_button")
                ) {
                    Text("Save Config", fontWeight = FontWeight.Bold)
                }
            },
            dismissButton = {
                TextButton(onClick = { showSaveDialog = false }) {
                    Text("Cancel", color = Color.White)
                }
            },
            containerColor = SlateSurface
        )
    }
}

// 1. DEVICES PRESETS LAYOUT
@Composable
fun DevicesPresetsView(
    onSelectSens: (general: Int, redDot: Int, scope2x: Int, scope4x: Int, sniper: Int, freeLook: Int, dpi: Int, fSize: Int, brand: String, model: String) -> Unit
) {
    var selectedBrand by remember { mutableStateOf("ALL") }
    val brandsList = listOf("ALL", "SAMSUNG", "VIVO", "POCO", "REDMI / MI", "OTHER DEVICES")

    val filteredList = if (selectedBrand == "ALL") {
        Presets.devices
    } else {
        Presets.devices.filter { it.brand.equals(selectedBrand, ignoreCase = true) }
    }

    Column(modifier = Modifier.fillMaxSize()) {
        // Brands selector Horizontal list
        LazyRow(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp, vertical = 6.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(brandsList) { brand ->
                val isSelected = selectedBrand == brand
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(8.dp))
                        .background(if (isSelected) SlateCard else SlateSurface)
                        .border(
                            width = 1.dp,
                            color = if (isSelected) RedPrimary else Color.Transparent,
                            shape = RoundedCornerShape(8.dp)
                        )
                        .clickable { selectedBrand = brand }
                        .padding(horizontal = 14.dp, vertical = 8.dp)
                ) {
                    Text(
                        text = brand,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = if (isSelected) RedPrimary else TextMuted
                    )
                }
            }
        }

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 12.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp),
            contentPadding = PaddingValues(bottom = 16.dp)
        ) {
            items(filteredList) { device ->
                var isExpanded by remember { mutableStateOf(false) }

                Card(
                    onClick = { isExpanded = !isExpanded },
                    colors = CardDefaults.cardColors(containerColor = SlateSurface),
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .border(1.dp, SlateCard, RoundedCornerShape(12.dp))
                        .testTag("device_card_${device.id}")
                ) {
                    Column(modifier = Modifier.padding(14.dp)) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = device.brand,
                                    fontSize = 10.sp,
                                    color = RedPrimary,
                                    fontWeight = FontWeight.Bold,
                                    letterSpacing = 1.sp
                                )
                                Text(
                                    text = device.modelName,
                                    fontSize = 16.sp,
                                    color = Color.White,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                            Icon(
                                imageVector = if (isExpanded) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                                contentDescription = if (isExpanded) "Collapse" else "Expand",
                                tint = TextMuted
                            )
                        }

                        if (!isExpanded) {
                            // Quick view tags
                            Row(
                                modifier = Modifier
                                    .padding(top = 8.dp)
                                    .fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                QuickStatTag("General: ${device.general}")
                                QuickStatTag("Rec. DPI: ${device.recommendedDpi}")
                                QuickStatTag("Fire Button: ${device.recommendedFireButtonSize}%")
                            }
                        }

                        AnimatedVisibility(
                            visible = isExpanded,
                            enter = expandVertically() + fadeIn(),
                            exit = shrinkVertically() + fadeOut()
                        ) {
                            Column(
                                modifier = Modifier
                                    .padding(top = 12.dp)
                                    .fillMaxWidth()
                            ) {
                                Divider(color = SlateCard, thickness = 1.dp)
                                Spacer(modifier = Modifier.height(10.dp))

                                // Render sensitivity sliders
                                SensitivitySliderSpec(label = "General (सामान्य)", value = device.general)
                                SensitivitySliderSpec(label = "Red Dot (रेड डॉट)", value = device.redDot)
                                SensitivitySliderSpec(label = "2x Scope (2x स्कोप)", value = device.scope2x)
                                SensitivitySliderSpec(label = "4x Scope (4x स्कोप)", value = device.scope4x)
                                SensitivitySliderSpec(label = "Sniper Scope (स्नाइपर)", value = device.sniper)
                                SensitivitySliderSpec(label = "Free Look (फ्री लुक)", value = device.freeLook)

                                Spacer(modifier = Modifier.height(10.dp))

                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                                ) {
                                    Column(modifier = Modifier.weight(1f)) {
                                        Text("Recom. DPI Setting", color = TextMuted, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                                        Text("${device.recommendedDpi} DPI", color = CyanAccent, fontSize = 15.sp, fontWeight = FontWeight.ExtraBold)
                                    }
                                    Column(modifier = Modifier.weight(1f)) {
                                        Text("Best Fire Button Size", color = TextMuted, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                                        Text("${device.recommendedFireButtonSize}%", color = Color.White, fontSize = 15.sp, fontWeight = FontWeight.ExtraBold)
                                    }
                                }

                                Spacer(modifier = Modifier.height(10.dp))
                                Text("💡 Specific Tip (विशेष टिप्स):", color = TextLight, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                                Text(
                                    text = device.specificTips,
                                    color = TextMuted,
                                    fontSize = 11.sp,
                                    lineHeight = 16.sp,
                                    modifier = Modifier.padding(vertical = 4.dp)
                                )

                                Spacer(modifier = Modifier.height(12.dp))
                                Button(
                                    onClick = {
                                        onSelectSens(
                                            device.general,
                                            device.redDot,
                                            device.scope2x,
                                            device.scope4x,
                                            device.sniper,
                                            device.freeLook,
                                            device.recommendedDpi,
                                            device.recommendedFireButtonSize,
                                            device.brand,
                                            device.modelName
                                        )
                                    },
                                    colors = ButtonDefaults.buttonColors(containerColor = SlateCard),
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .border(1.dp, RedPrimary, RoundedCornerShape(8.dp))
                                        .testTag("apply_preset_${device.id}"),
                                    shape = RoundedCornerShape(8.dp)
                                ) {
                                    Icon(Icons.Default.Save, contentDescription = null, tint = RedPrimary)
                                    Spacer(modifier = Modifier.width(6.dp))
                                    Text("Apply & Customize Settings", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 12.sp)
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

// 2. CUSTOM HUD VIEW WITH VECTOR GRAPHIC DRAFT CANVAS
@Composable
fun CustomHudView() {
    var selectedHudIndex by remember { mutableStateOf(0) }
    val activeHud = Presets.huds[selectedHudIndex]

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 12.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        contentPadding = PaddingValues(bottom = 16.dp)
    ) {
        // Segmented selector row to pick claw setup
        item {
            Card(
                colors = CardDefaults.cardColors(containerColor = SlateSurface),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp),
                border = BorderStroke(1.dp, SlateCard)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(6.dp),
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Presets.huds.forEachIndexed { idx, hud ->
                        val isSel = selectedHudIndex == idx
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .clip(RoundedCornerShape(8.dp))
                                .background(if (isSel) RedPrimary else Color.Transparent)
                                .clickable { selectedHudIndex = idx }
                                .padding(vertical = 10.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = hud.clawType,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Black,
                                color = if (isSel) Color.White else TextMuted
                            )
                        }
                    }
                }
            }
        }

        // Custom Canvas drawing depicting standard layout of buttons
        item {
            Text(
                text = "Virtual Arena Screen HUD Mockup",
                color = TextLight,
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 0.5.sp,
                modifier = Modifier.padding(start = 2.dp, bottom = 4.dp)
            )

            // Canvas layout mock representing exact locations inside Free Fire landscape aspect ratio!
            val fireRadiusValue = activeHud.fireButtonSize.toFloat() * 0.5f + 15f
            val wallSizeValue = activeHud.glooWallButtonSize.toFloat() * 0.5f + 25f

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
            ) {
                Canvas(
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(RoundedCornerShape(12.dp))
                        .background(SlateSurface)
                        .border(1.dp, SlateCard, RoundedCornerShape(12.dp))
                        .testTag("gaming_hud_interactive_canvas")
                ) {
                    val w = size.width
                    val h = size.height

                    // Draw outer border bezel offset
                    drawRoundRect(
                        color = SlateCard,
                        topLeft = Offset(8f, 8f),
                        size = Size(w - 16f, h - 16f),
                        cornerRadius = CornerRadius(14f, 14f),
                        style = Stroke(width = 3f)
                    )

                    // Subdued landscape target guides
                    drawLine(
                        color = Color.LightGray.copy(alpha = 0.05f),
                        start = Offset(0f, h / 2f),
                        end = Offset(w, h / 2f),
                        strokeWidth = 2f
                    )
                    drawLine(
                        color = Color.LightGray.copy(alpha = 0.05f),
                        start = Offset(w / 2f, 0f),
                        end = Offset(w / 2f, h),
                        strokeWidth = 2f
                    )

                    // Draw Crosshair pointer
                    drawCircle(
                        color = Color(0xFFFF3030).copy(alpha = 0.4f),
                        radius = 6f,
                        center = Offset(w / 2f, h / 2f)
                    )
                    // Crosshair boundary lines
                    drawLine(
                        color = Color(0xFFFF3030).copy(alpha = 0.2f),
                        start = Offset(w/2f - 20f, h/2f),
                        end = Offset(w/2f + 20f, h/2f),
                        strokeWidth = 3f
                    )
                    drawLine(
                        color = Color(0xFFFF3030).copy(alpha = 0.2f),
                        start = Offset(w/2f, h/2f - 20f),
                        end = Offset(w/2f, h/2f + 20f),
                        strokeWidth = 3f
                    )

                    // Rendering configurations
                    when (activeHud.clawType) {
                        "2-Finger Layout" -> {
                            // Standard Thumb Joystick
                            drawCircle(
                                color = Color.White.copy(alpha = 0.15f),
                                radius = 35f,
                                center = Offset(110f, h - 80f)
                            )
                            drawCircle(
                                color = Color.White.copy(alpha = 0.4f),
                                radius = 12f,
                                center = Offset(110f, h - 80f)
                            )

                            // Gloo Wall left center
                            drawRoundRect(
                                color = CyanAccent.copy(alpha = 0.5f),
                                topLeft = Offset(40f, h/2f - 25f),
                                size = Size(65f, 50f),
                                cornerRadius = CornerRadius(10f, 10f)
                            )

                            // Fire button lower right (Thumb range)
                            drawCircle(
                                color = RedPrimary.copy(alpha = 0.65f),
                                radius = fireRadiusValue,
                                center = Offset(w - 120f, h - 80f)
                            )
                        }
                        "3-Finger Layout" -> {
                            // Joystick lower-left
                            drawCircle(
                                color = Color.White.copy(alpha = 0.15f),
                                radius = 30f,
                                center = Offset(110f, h - 70f)
                            )

                            // Fast Gloo wall - Top Left index finger
                            drawRoundRect(
                                color = CyanAccent.copy(alpha = 0.65f),
                                topLeft = Offset(45f, 25f),
                                size = Size(wallSizeValue + 15f, wallSizeValue),
                                cornerRadius = CornerRadius(12f, 12f)
                            )

                            // Fire button normal right
                            drawCircle(
                                color = RedPrimary.copy(alpha = 0.65f),
                                radius = fireRadiusValue - 2f,
                                center = Offset(w - 110f, h - 80f)
                            )

                            // Jump button offset
                            drawCircle(
                                color = Color.Yellow.copy(alpha = 0.5f),
                                radius = 25f,
                                center = Offset(w - 60f, h/2f - 30f)
                            )
                        }
                        "4-Finger Layout" -> {
                            // Joystick left
                            drawCircle(
                                color = Color.White.copy(alpha = 0.15f),
                                radius = 26f,
                                center = Offset(90f, h - 60f)
                            )

                            // Gloo wall upper-left
                            drawRoundRect(
                                color = CyanAccent.copy(alpha = 0.65f),
                                topLeft = Offset(40f, 25f),
                                size = Size(70f, 45f),
                                cornerRadius = CornerRadius(8f, 8f)
                            )

                            // Left Fire Button
                            drawCircle(
                                color = RedPrimary.copy(alpha = 0.45f),
                                radius = 28f,
                                center = Offset(w/2f - 140f, 40f)
                            )

                            // Right Fire Button
                            drawCircle(
                                color = RedPrimary.copy(alpha = 0.65f),
                                radius = fireRadiusValue - 4f,
                                center = Offset(w - 120f, h - 70f)
                            )

                            // Jump right-top corner index range
                            drawCircle(
                                color = Color.Yellow.copy(alpha = 0.5f),
                                radius = 28f,
                                center = Offset(w - 65f, 42f)
                            )
                        }
                    }
                }

                // Tech Overlay Tag
                Box(
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(10.dp)
                        .clip(RoundedCornerShape(6.dp))
                        .background(Color.Black.copy(alpha = 0.7f))
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                ) {
                    Text(
                        text = "Interactive Guide",
                        color = CyanAccent,
                        fontSize = 8.sp,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 0.5.sp
                    )
                }
            }
        }

        // Title and description details
        item {
            Card(
                colors = CardDefaults.cardColors(containerColor = SlateSurface),
                border = BorderStroke(1.dp, SlateCard),
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = activeHud.title,
                        color = Color.White,
                        fontSize = 17.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = activeHud.description,
                        color = TextMuted,
                        fontSize = 12.sp,
                        lineHeight = 18.sp
                    )

                    Spacer(modifier = Modifier.height(14.dp))
                    Text(
                        text = "⚙️ Key Settings Mapping Details:",
                        color = RedPrimary,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 0.5.sp
                    )

                    Spacer(modifier = Modifier.height(8.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        SpecKeyHudBox(tag = "Right Fire", sizeText = "${activeHud.fireButtonSize}%")
                        SpecKeyHudBox(tag = "Gloo Wall", sizeText = "${activeHud.glooWallButtonSize}%")
                        if (activeHud.leftFireButtonScale > 0) {
                            SpecKeyHudBox(tag = "Left Fire", sizeText = "${activeHud.leftFireButtonScale}%")
                        }
                        SpecKeyHudBox(tag = "Quick Switch", sizeText = "${activeHud.switchWeaponSize}%")
                    }
                }
            }
        }

        // Setup steps list
        item {
            Text(
                text = "🔨 Layout Setup Instructions (स्टेप-बाय-स्टेप सेटिंग्स)",
                color = Color.White,
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(vertical = 4.dp, horizontal = 2.dp)
            )
        }

        items(activeHud.setupSteps) { step ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 2.dp),
                verticalAlignment = Alignment.Top
            ) {
                Text(
                    text = "•",
                    color = RedPrimary,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.ExtraBold,
                    modifier = Modifier.padding(end = 8.dp)
                )
                Text(
                    text = step,
                    color = TextMuted,
                    fontSize = 12.sp,
                    lineHeight = 17.sp,
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }
}

// 3. HEADSHOT ACCURACY & MOVEMENT SPEED HACKS VIEW
@Composable
fun HeadshotTipsView() {
    var query by remember { mutableStateOf("") }
    var selectedCategoryFilter by remember { mutableStateOf("ALL") }

    val filteredTutorials = Presets.tutorials.filter {
        val matchesSearch = it.title.lowercase().contains(query.lowercase()) || it.description.lowercase().contains(query.lowercase())
        val matchesCategory = selectedCategoryFilter == "ALL" || it.category.equals(selectedCategoryFilter, ignoreCase = true)
        matchesSearch && matchesCategory
    }

    Column(modifier = Modifier.fillMaxSize()) {
        OutlinedTextField(
            value = query,
            onValueChange = { query = it },
            placeholder = { Text("Search Tip (e.g., Drag, Wall)", color = TextMuted) },
            leadingIcon = { Icon(Icons.Default.Search, contentDescription = null, tint = RedPrimary) },
            singleLine = true,
            colors = OutlinedTextFieldDefaults.colors(
                focusedTextColor = Color.White,
                unfocusedTextColor = Color.LightGray,
                focusedBorderColor = RedPrimary,
                unfocusedBorderColor = SlateCard
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp, vertical = 6.dp)
                .testTag("hacks_search_field")
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp, vertical = 4.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            listOf("ALL", "Headshot Accuracy", "Movement Speed", "Sniper Tricks").forEach { cat ->
                val isSel = selectedCategoryFilter == cat
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(8.dp))
                        .background(if (isSel) SlateCard else SlateSurface)
                        .border(1.dp, if (isSel) RedPrimary else Color.Transparent, RoundedCornerShape(8.dp))
                        .clickable { selectedCategoryFilter = cat }
                        .padding(horizontal = 12.dp, vertical = 6.dp)
                ) {
                    Text(
                        text = cat,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        color = if (isSel) RedPrimary else TextMuted
                    )
                }
            }
        }

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            contentPadding = PaddingValues(bottom = 16.dp)
        ) {
            if (filteredTutorials.isEmpty()) {
                item {
                    NoDataState(message = "No tips match your filter context. Try another search.")
                }
            } else {
                items(filteredTutorials) { tip ->
                    var isExpanded by remember { mutableStateOf(false) }

                    Card(
                        onClick = { isExpanded = !isExpanded },
                        colors = CardDefaults.cardColors(containerColor = SlateSurface),
                        border = BorderStroke(1.dp, SlateCard)
                    ) {
                        Column(modifier = Modifier.padding(14.dp)) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Column(modifier = Modifier.weight(1f)) {
                                    val difColor = when (tip.difficulty) {
                                        "Easy" -> Color(0xFF4CAF50)
                                        "Medium" -> Color(0xFFFF9800)
                                        else -> RedPrimary
                                    }

                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        Box(
                                            modifier = Modifier
                                                .clip(RoundedCornerShape(4.dp))
                                                .background(difColor.copy(alpha = 0.15f))
                                                .padding(horizontal = 6.dp, vertical = 2.dp)
                                        ) {
                                            Text(tip.difficulty, color = difColor, fontSize = 9.sp, fontWeight = FontWeight.Bold)
                                        }
                                        Spacer(modifier = Modifier.width(6.dp))
                                        Text(tip.category, color = TextMuted, fontSize = 10.sp, fontWeight = FontWeight.SemiBold)
                                    }

                                    Spacer(modifier = Modifier.height(4.dp))
                                    Text(tip.title, color = Color.White, fontSize = 15.sp, fontWeight = FontWeight.Bold)
                                }
                                Icon(
                                    imageVector = if (isExpanded) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                                    contentDescription = null,
                                    tint = TextMuted
                                )
                            }

                            Spacer(modifier = Modifier.height(6.dp))
                            Text(tip.description, color = TextLight, fontSize = 11.sp, lineHeight = 16.sp)

                            AnimatedVisibility(
                                visible = isExpanded,
                                enter = expandVertically() + fadeIn(),
                                exit = shrinkVertically() + fadeOut()
                            ) {
                                Column(modifier = Modifier.padding(top = 10.dp)) {
                                    Divider(color = SlateCard)
                                    Spacer(modifier = Modifier.height(8.dp))
                                    Text("🎯 Step-by-Step Drill Practice:", color = CyanAccent, fontSize = 11.sp, fontWeight = FontWeight.Bold)

                                    tip.stepByStep.forEachIndexed { idx, step ->
                                        Row(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .padding(vertical = 4.dp),
                                            verticalAlignment = Alignment.Top
                                        ) {
                                            Text(
                                                text = "${idx + 1}.",
                                                fontWeight = FontWeight.Bold,
                                                color = RedPrimary,
                                                fontSize = 11.sp,
                                                modifier = Modifier.padding(end = 6.dp)
                                            )
                                            Text(step, color = TextMuted, fontSize = 11.sp, lineHeight = 16.sp)
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

// 4. PRO PLAYERS PRESETS VIEW
@Composable
fun ProPlayersView(
    onApplyProSens: (general: Int, redDot: Int, scope2x: Int, scope4x: Int, sniper: Int, freeLook: Int, dpi: Int, fSize: Int, name: String) -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 12.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp),
        contentPadding = PaddingValues(bottom = 16.dp)
    ) {
        items(Presets.proPlayers) { pro ->
            var expanded by remember { mutableStateOf(false) }

            Card(
                onClick = { expanded = !expanded },
                colors = CardDefaults.cardColors(containerColor = SlateSurface),
                border = BorderStroke(1.dp, SlateCard),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        // Avatar Simulation
                        Box(
                            modifier = Modifier
                                .size(40.dp)
                                .clip(CircleShape)
                                .background(Brush.radialGradient(listOf(RedPrimary, RedDark))),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = pro.name.take(2).uppercase(),
                                fontWeight = FontWeight.Black,
                                fontSize = 14.sp,
                                color = Color.White
                            )
                        }

                        Spacer(modifier = Modifier.width(12.dp))
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = pro.name,
                                color = Color.White,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Black
                            )
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text(pro.country, color = TextMuted, fontSize = 11.sp)
                                Spacer(modifier = Modifier.width(6.dp))
                                Box(
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(3.dp))
                                        .background(SlateCard)
                                        .padding(horizontal = 4.dp, vertical = 1.dp)
                                ) {
                                    Text(pro.device, color = CyanAccent, fontSize = 9.sp, fontWeight = FontWeight.Bold)
                                }
                            }
                        }
                        Icon(
                            imageVector = if (expanded) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                            contentDescription = null,
                            tint = TextMuted
                        )
                    }

                    if (!expanded) {
                        Text(
                            text = pro.playstyle,
                            color = RedPrimary,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(top = 6.dp)
                        )
                    }

                    AnimatedVisibility(
                        visible = expanded,
                        enter = expandVertically() + fadeIn(),
                        exit = shrinkVertically() + fadeOut()
                    ) {
                        Column(modifier = Modifier.padding(top = 10.dp)) {
                            Divider(color = SlateCard)
                            Spacer(modifier = Modifier.height(10.dp))

                            SensitivitySliderSpec(label = "General (सामान्य)", value = pro.general)
                            SensitivitySliderSpec(label = "Red Dot (रेड डॉट)", value = pro.redDot)
                            SensitivitySliderSpec(label = "2x Scope", value = pro.scope2x)
                            SensitivitySliderSpec(label = "4x Scope", value = pro.scope4x)
                            SensitivitySliderSpec(label = "Sniper Scope", value = pro.sniper)
                            SensitivitySliderSpec(label = "Free Look", value = pro.freeLook)

                            Spacer(modifier = Modifier.height(8.dp))
                            Row(modifier = Modifier.fillMaxWidth()) {
                                Column(modifier = Modifier.weight(1f)) {
                                    Text("Fire Button", color = TextMuted, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                                    Text("${pro.fireButtonSize}% size", color = Color.White, fontSize = 14.sp, fontWeight = FontWeight.Bold)
                                }
                                Column(modifier = Modifier.weight(1f)) {
                                    Text("DPI Rate", color = TextMuted, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                                    Text("${pro.recommendedDpi} DPI", color = CyanAccent, fontSize = 14.sp, fontWeight = FontWeight.Bold)
                                }
                            }

                            Spacer(modifier = Modifier.height(10.dp))
                            Text("💬 Pro Quote:", color = TextLight, fontSize = 10.sp, fontWeight = FontWeight.Bold)
                            Text(
                                text = pro.quote,
                                color = TextMuted,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Medium,
                                modifier = Modifier.padding(vertical = 2.dp)
                            )

                            Spacer(modifier = Modifier.height(12.dp))
                            Button(
                                onClick = {
                                    onApplyProSens(
                                        pro.general,
                                        pro.redDot,
                                        pro.scope2x,
                                        pro.scope4x,
                                        pro.sniper,
                                        pro.freeLook,
                                        pro.recommendedDpi,
                                        pro.fireButtonSize,
                                        pro.name
                                    )
                                },
                                colors = ButtonDefaults.buttonColors(containerColor = SlateCard),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .border(1.dp, RedPrimary, RoundedCornerShape(8.dp)),
                                shape = RoundedCornerShape(8.dp)
                            ) {
                                Icon(Icons.Default.Favorite, contentDescription = null, tint = RedPrimary)
                                Spacer(modifier = Modifier.width(6.dp))
                                Text("Load Pro Configuration", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 12.sp)
                            }
                        }
                    }
                }
            }
        }
    }
}

// 5. INTERACTIVE GEMINI-API AI COACH SENSITIVITY VIEW
@Composable
fun AiSensitivityView(
    viewModel: SensitivityViewModel,
    aiState: AiGenerationState,
    onSaveAiSens: (general: Int, redDot: Int, scope2x: Int, scope4x: Int, sniper: Int, freeLook: Int, dpi: Int, fSize: Int, model: String) -> Unit
) {
    var deviceNameInput by remember { mutableStateOf("") }
    var selectedRamIndex by remember { mutableStateOf(2) }
    var selectedStyleIndex by remember { mutableStateOf(0) }

    val ramsList = listOf("3GB RAM", "4GB RAM", "6GB RAM", "8GB RAM", "12GB RAM+")
    val playstyleList = listOf("One-Tap Headshots", "High Speed Movement", "Double Sniper Fast Switch", "All-Round Rusher")

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 12.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        contentPadding = PaddingValues(bottom = 16.dp)
    ) {
        item {
            Card(
                colors = CardDefaults.cardColors(containerColor = SlateSurface),
                border = BorderStroke(1.dp, SlateCard)
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Text(
                        text = "🤖 Gemini eSports AI Coach Optimizer",
                        color = CyanAccent,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Black
                    )
                    Text(
                        text = "Apne device parameters input karein taaki humara AI model aapke hardware aur playstyle ke hisaab se customized eSports profile aur warmup drills create kar sake.",
                        color = TextMuted,
                        fontSize = 11.sp,
                        lineHeight = 16.sp,
                        modifier = Modifier.padding(top = 4.dp, bottom = 12.dp)
                    )

                    OutlinedTextField(
                        value = deviceNameInput,
                        onValueChange = { deviceNameInput = it },
                        placeholder = { Text("Device Name (e.g., Vivo V29, Poco X5 Pro)", color = TextMuted) },
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedTextColor = Color.White,
                            unfocusedTextColor = Color.LightGray,
                            focusedBorderColor = CyanAccent,
                            unfocusedBorderColor = SlateCard
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("ai_device_name_field")
                    )

                    Spacer(modifier = Modifier.height(10.dp))
                    Text("Select Device RAM Level", color = TextLight, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                    LazyRow(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp),
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        items(ramsList.size) { idx ->
                            val isSel = selectedRamIndex == idx
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(6.dp))
                                    .background(if (isSel) CyanAccent else SlateCard)
                                    .clickable { selectedRamIndex = idx }
                                    .padding(horizontal = 10.dp, vertical = 6.dp)
                            ) {
                                Text(
                                    text = ramsList[idx],
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = if (isSel) Color.Black else TextMuted
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))
                    Text("Select Gaming Target Strategy", color = TextLight, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp),
                        verticalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        playstyleList.forEachIndexed { idx, style ->
                            val isSel = selectedStyleIndex == idx
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clip(RoundedCornerShape(8.dp))
                                    .background(if (isSel) CyanAccent.copy(alpha = 0.15f) else SlateCard)
                                    .border(1.dp, if (isSel) CyanAccent else Color.Transparent, RoundedCornerShape(8.dp))
                                    .clickable { selectedStyleIndex = idx }
                                    .padding(12.dp)
                            ) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    RadioButton(
                                        selected = isSel,
                                        onClick = { selectedStyleIndex = idx },
                                        colors = RadioButtonDefaults.colors(selectedColor = CyanAccent)
                                    )
                                    Spacer(modifier = Modifier.width(6.dp))
                                    Text(
                                        text = style,
                                        fontSize = 12.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = if (isSel) Color.White else TextMuted
                                    )
                                }
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(14.dp))
                    Button(
                        onClick = {
                            viewModel.generateAiSensitivity(
                                deviceName = deviceNameInput.ifBlank { "Standard Mobile" },
                                ramSize = ramsList[selectedRamIndex],
                                playstyle = playstyleList[selectedStyleIndex]
                            )
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = CyanAccent),
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("ai_trigger_optimize_button")
                    ) {
                        Icon(Icons.Default.Refresh, contentDescription = null, tint = Color.Black)
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "OPTIMIZE WITH GEMINI AI",
                            color = Color.Black,
                            fontWeight = FontWeight.ExtraBold,
                            fontSize = 12.sp
                        )
                    }
                }
            }
        }

        // Output Display Area based on AI States
        item {
            when (aiState) {
                is AiGenerationState.Idle -> {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .border(1.dp, SlateCard, RoundedCornerShape(12.dp))
                            .padding(24.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Icon(Icons.Default.CheckCircle, contentDescription = null, tint = TextMuted, modifier = Modifier.size(28.dp))
                            Spacer(modifier = Modifier.height(6.dp))
                            Text("Ready to Optimise parameters. Click build.", color = TextMuted, fontSize = 11.sp, textAlign = TextAlign.Center)
                        }
                    }
                }
                is AiGenerationState.Loading -> {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .border(1.dp, CyanAccent, RoundedCornerShape(12.dp))
                            .padding(32.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            CircularProgressIndicator(color = CyanAccent)
                            Spacer(modifier = Modifier.height(12.dp))
                            Text("Gemini AI matching hardware density settings...", color = Color.White, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                            Text("This may take 1-3 seconds", color = TextMuted, fontSize = 10.sp)
                        }
                    }
                }
                is AiGenerationState.Success -> {
                    Card(
                        colors = CardDefaults.cardColors(containerColor = SlateSurface),
                        border = BorderStroke(1.dp, CyanAccent)
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text("💥 Personalized AI Preset Model", color = CyanAccent, fontSize = 13.sp, fontWeight = FontWeight.Bold)
                                Box(
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(4.dp))
                                        .background(Color.White.copy(alpha = 0.12f))
                                        .clickable { viewModel.resetAiState() }
                                        .padding(horizontal = 6.dp, vertical = 2.dp)
                                ) {
                                    Text("Reset Form", color = Color.White, fontSize = 9.sp, fontWeight = FontWeight.Bold)
                                }
                            }

                            Spacer(modifier = Modifier.height(10.dp))
                            SensitivitySliderSpec(label = "General Sensitivity", value = aiState.general)
                            SensitivitySliderSpec(label = "Red Dot Sensitivity", value = aiState.redDot)
                            SensitivitySliderSpec(label = "2x Scope Scale", value = aiState.scope2x)
                            SensitivitySliderSpec(label = "4x Scope Scale", value = aiState.scope4x)
                            SensitivitySliderSpec(label = "AWM Sniper Zoom", value = aiState.sniper)
                            SensitivitySliderSpec(label = "Free Look Action", value = aiState.freeLook)

                            Spacer(modifier = Modifier.height(12.dp))
                            Row(modifier = Modifier.fillMaxWidth()) {
                                Column(modifier = Modifier.weight(1f)) {
                                    Text("Recommended DPI Setting", color = TextMuted, fontSize = 10.sp, fontWeight = FontWeight.Bold)
                                    Text("${aiState.recommendedDpi} DPI", color = CyanAccent, fontSize = 14.sp, fontWeight = FontWeight.Black)
                                }
                                Column(modifier = Modifier.weight(1f)) {
                                    Text("Best Trigger Fire Size", color = TextMuted, fontSize = 10.sp, fontWeight = FontWeight.Bold)
                                    Text("${aiState.recommendedFireSize}%", color = Color.White, fontSize = 14.sp, fontWeight = FontWeight.Black)
                                }
                            }

                            Spacer(modifier = Modifier.height(12.dp))
                            Text("🧠 AI Strategic Coaching Drills ( Urdu / Hindi ):", color = Color.White, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                            Text(
                                text = aiState.aiTips,
                                color = TextMuted,
                                fontSize = 11.sp,
                                lineHeight = 16.sp,
                                modifier = Modifier.padding(vertical = 4.dp)
                            )

                            Spacer(modifier = Modifier.height(14.dp))
                            Button(
                                onClick = {
                                    onSaveAiSens(
                                        aiState.general,
                                        aiState.redDot,
                                        aiState.scope2x,
                                        aiState.scope4x,
                                        aiState.sniper,
                                        aiState.freeLook,
                                        aiState.recommendedDpi,
                                        aiState.recommendedFireSize,
                                        deviceNameInput.ifBlank { "Standard Mobile" }
                                    )
                                },
                                colors = ButtonDefaults.buttonColors(containerColor = CyanAccent),
                                shape = RoundedCornerShape(8.dp),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .testTag("save_ai_preset_to_local_button")
                            ) {
                                Icon(Icons.Default.Bookmark, contentDescription = null, tint = Color.Black)
                                Spacer(modifier = Modifier.width(6.dp))
                                Text("Backup AI Layout to Profiles", color = Color.Black, fontWeight = FontWeight.Bold, fontSize = 12.sp)
                            }
                        }
                    }
                }
                is AiGenerationState.Error -> {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .border(1.dp, RedPrimary, RoundedCornerShape(12.dp))
                            .padding(16.dp)
                    ) {
                        Column {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(Icons.Default.Warning, contentDescription = null, tint = RedPrimary)
                                Spacer(modifier = Modifier.width(6.dp))
                                Text("Optimization Offline Failed", color = RedPrimary, fontWeight = FontWeight.Bold, fontSize = 13.sp)
                            }
                            Spacer(modifier = Modifier.height(6.dp))
                            Text(aiState.errorMessage, color = TextMuted, fontSize = 11.sp)
                        }
                    }
                }
            }
        }
    }
}

// 6. SAVED CONFIGS BACKUP VIEW LISTING ROOM ENTITIES
@Composable
fun SavedConfigsView(
    savedProfiles: List<SensitivityProfile>,
    onDelete: (id: Int) -> Unit
) {
    if (savedProfiles.isEmpty()) {
        NoDataState(message = "Bhai, aapka koi custom profile saved nahi hai! Presets me jaake 'Apply & Customize' karein ya AI Coach se dynamic config banayein.")
    } else {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 12.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp),
            contentPadding = PaddingValues(bottom = 16.dp)
        ) {
            items(savedProfiles, key = { it.id }) { item ->
                var expanded by remember { mutableStateOf(false) }

                Card(
                    onClick = { expanded = !expanded },
                    colors = CardDefaults.cardColors(containerColor = SlateSurface),
                    border = BorderStroke(1.dp, SlateCard),
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("saved_profile_card_${item.id}")
                ) {
                    Column(modifier = Modifier.padding(14.dp)) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = item.profileName,
                                    fontSize = 16.sp,
                                    color = Color.White,
                                    fontWeight = FontWeight.Bold
                                )
                                Text(
                                    text = "${item.deviceName} • Layout: ${item.clawType}",
                                    fontSize = 11.sp,
                                    color = RedPrimary,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                            IconButton(
                                onClick = { onDelete(item.id) },
                                modifier = Modifier.testTag("delete_profile_${item.id}")
                            ) {
                                Icon(Icons.Default.Delete, contentDescription = "Delete", tint = Color.Gray)
                            }
                        }

                        if (!expanded) {
                            Row(
                                modifier = Modifier
                                    .padding(top = 10.dp)
                                    .fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(6.dp)
                            ) {
                                QuickStatTag("General: ${item.general}")
                                QuickStatTag("Fire Size: ${item.fireButtonSize}%")
                                QuickStatTag("DPI Flag: ${item.dpi}")
                            }
                        }

                        AnimatedVisibility(
                            visible = expanded,
                            enter = expandVertically() + fadeIn(),
                            exit = shrinkVertically() + fadeOut()
                        ) {
                            Column(modifier = Modifier.padding(top = 12.dp)) {
                                Divider(color = SlateCard)
                                Spacer(modifier = Modifier.height(10.dp))

                                SensitivitySliderSpec(label = "General Calibration", value = item.general)
                                SensitivitySliderSpec(label = "Red Dot Match", value = item.redDot)
                                SensitivitySliderSpec(label = "2x Scope scale", value = item.scope2x)
                                SensitivitySliderSpec(label = "4x Scope scale", value = item.scope4x)
                                SensitivitySliderSpec(label = "AWM Sniper Zoom", value = item.sniper)
                                SensitivitySliderSpec(label = "Free Look Action", value = item.freeLook)

                                Spacer(modifier = Modifier.height(8.dp))
                                Row(modifier = Modifier.fillMaxWidth()) {
                                    Column(modifier = Modifier.weight(1f)) {
                                        Text("Config DPI Setting", color = TextMuted, fontSize = 10.sp, fontWeight = FontWeight.Bold)
                                        Text("${item.dpi} DPI", color = CyanAccent, fontSize = 14.sp, fontWeight = FontWeight.Black)
                                    }
                                    Column(modifier = Modifier.weight(1f)) {
                                        Text("Best Fire Button", color = TextMuted, fontSize = 10.sp, fontWeight = FontWeight.Bold)
                                        Text("${item.fireButtonSize}% size", color = Color.White, fontSize = 14.sp, fontWeight = FontWeight.Black)
                                    }
                                }

                                if (item.notes.isNotBlank()) {
                                    Spacer(modifier = Modifier.height(8.dp))
                                    Text("📝 Saved Notes / Guide:", color = Color.White, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                                    Text(
                                        text = item.notes,
                                        color = TextMuted,
                                        fontSize = 11.sp,
                                        lineHeight = 16.sp
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

// SHARED UTILITY COMPOSABLES
@Composable
fun QuickStatTag(text: String) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(4.dp))
            .background(SlateCard)
            .padding(horizontal = 8.dp, vertical = 4.dp)
    ) {
        Text(
            text = text,
            fontSize = 9.sp,
            color = TextLight,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
fun SpecKeyHudBox(tag: String, sizeText: String) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(6.dp))
            .background(SlateCard)
            .border(1.dp, SlateCard)
            .padding(8.dp)
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(tag, color = TextMuted, fontSize = 9.sp, fontWeight = FontWeight.SemiBold)
            Text(sizeText, color = Color.White, fontSize = 12.sp, fontWeight = FontWeight.Black)
        }
    }
}

@Composable
fun SensitivitySliderSpec(label: String, value: Int) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = label, color = TextLight, fontSize = 11.sp, fontWeight = FontWeight.Bold)
            Text(text = "$value", color = RedPrimary, fontSize = 11.sp, fontWeight = FontWeight.ExtraBold)
        }
        Slider(
            value = value.toFloat(),
            onValueChange = {},
            valueRange = 0f..100f,
            enabled = false,
            colors = SliderDefaults.colors(
                disabledThumbColor = RedPrimary,
                disabledActiveTrackColor = RedPrimary,
                disabledInactiveTrackColor = SlateCard
            ),
            modifier = Modifier
                .height(24.dp)
                .fillMaxWidth()
        )
    }
}

@Composable
fun NoDataState(message: String) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(
                imageVector = Icons.Default.Info,
                contentDescription = null,
                tint = RedPrimary.copy(alpha = 0.5f),
                modifier = Modifier.size(52.dp)
            )
            Spacer(modifier = Modifier.height(14.dp))
            Text(
                text = message,
                color = TextLight,
                fontSize = 13.sp,
                textAlign = TextAlign.Center,
                lineHeight = 18.sp,
                fontWeight = FontWeight.Medium
            )
        }
    }
}
