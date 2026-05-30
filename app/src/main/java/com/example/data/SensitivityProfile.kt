package com.example.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "sensitivity_profiles")
data class SensitivityProfile(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val profileName: String,
    val deviceName: String,
    val general: Int = 95,
    val redDot: Int = 90,
    val scope2x: Int = 85,
    val scope4x: Int = 80,
    val sniper: Int = 50,
    val freeLook: Int = 70,
    val fireButtonSize: Int = 50,
    val dpi: Int = 360,
    val clawType: String = "2-Finger",
    val notes: String = "",
    val timestamp: Long = System.currentTimeMillis()
)
