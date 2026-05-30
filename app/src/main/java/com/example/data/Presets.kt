package com.example.data

data class DevicePreset(
    val id: String,
    val brand: String,
    val modelName: String,
    val general: Int,
    val redDot: Int,
    val scope2x: Int,
    val scope4x: Int,
    val sniper: Int,
    val freeLook: Int,
    val recommendedDpi: Int,
    val recommendedFireButtonSize: Int,
    val specificTips: String
)

data class HudPreset(
    val title: String,
    val clawType: String,
    val description: String,
    val fireButtonSize: Int,
    val glooWallButtonSize: Int,
    val leftFireButtonScale: Int,
    val switchWeaponSize: Int,
    val visualMap: String, // String representation or layout style hint
    val setupSteps: List<String>
)

data class TutorialTip(
    val title: String,
    val category: String, // "Movement" or "Headshot" or "Sniper"
    val description: String,
    val stepByStep: List<String>,
    val difficulty: String // "Easy", "Medium", "Pro"
)

data class ProPlayerPreset(
    val name: String,
    val country: String,
    val device: String,
    val general: Int,
    val redDot: Int,
    val scope2x: Int,
    val scope4x: Int,
    val sniper: Int,
    val freeLook: Int,
    val recommendedDpi: Int,
    val fireButtonSize: Int,
    val playstyle: String,
    val quote: String
)

object Presets {
    val devices = listOf(
        // Samsung
        DevicePreset(
            id = "samsung_flagship",
            brand = "SAMSUNG",
            modelName = "Galaxy S21/S22/S23/S24/Ultra",
            general = 98,
            redDot = 92,
            scope2x = 90,
            scope4x = 88,
            sniper = 45,
            freeLook = 75,
            recommendedDpi = 450,
            recommendedFireButtonSize = 48,
            specificTips = "High-end processor hone ki wajah se screen response bohot fast hai. Custom DPI apply na karein to behtar hai, normal sensitivity par hi full drag headshot lagte hain. Fire button horizontal drag zoom apply karein."
        ),
        DevicePreset(
            id = "samsung_a_series",
            brand = "SAMSUNG",
            modelName = "Galaxy A34/A54/A73 (Midrange)",
            general = 100,
            redDot = 95,
            scope2x = 92,
            scope4x = 90,
            sniper = 50,
            freeLook = 80,
            recommendedDpi = 410,
            recommendedFireButtonSize = 52,
            specificTips = "In devices mein Touch Response rate average hota hai. Recommended DPI 410 configure karne se headshot easy drag honge. Rotation drag technique use karein."
        ),
        DevicePreset(
            id = "samsung_low_end",
            brand = "SAMSUNG",
            modelName = "Galaxy M & F Series (Budget)",
            general = 100,
            redDot = 98,
            scope2x = 95,
            scope4x = 95,
            sniper = 55,
            freeLook = 85,
            recommendedDpi = 440,
            recommendedFireButtonSize = 55,
            specificTips = "Budget devices mein sensitivity kam rehti hai. General sensitivity ko strictly 100 par rakhein. Drag hamesha straight direction mein speed se karein."
        ),

        // Vivo
        DevicePreset(
            id = "vivo_v_series",
            brand = "VIVO",
            modelName = "V23 / V25 / V27 / V29 / V30",
            general = 96,
            redDot = 90,
            scope2x = 88,
            scope4x = 85,
            sniper = 40,
            freeLook = 70,
            recommendedDpi = 400,
            recommendedFireButtonSize = 46,
            specificTips = "Vivo ki screens bohot smooth hain. Light drag (halka sa pull) karne se hi direct headshot lagta hai. Ammo size chota rakhein taaki air drag kam ho."
        ),
        DevicePreset(
            id = "vivo_y_series",
            brand = "VIVO",
            modelName = "Y21 / Y35 / Y50 / Y100 (Budget)",
            general = 100,
            redDot = 96,
            scope2x = 94,
            scope4x = 92,
            sniper = 50,
            freeLook = 80,
            recommendedDpi = 420,
            recommendedFireButtonSize = 50,
            specificTips = "Y-series ke liye general sensitivity humesha full rakhein. Ammo drag boost karne ke liye rapid rotation technique best kaam karegi."
        ),

        // Poco
        DevicePreset(
            id = "poco_x_series",
            brand = "POCO",
            modelName = "Poco X3 Pro / X5 / X6 Pro (eSports)",
            general = 95,
            redDot = 88,
            scope2x = 85,
            scope4x = 82,
            sniper = 35,
            freeLook = 65,
            recommendedDpi = 390,
            recommendedFireButtonSize = 44,
            specificTips = "Ultimate gaming device! 120Hz/140Hz screen refresh rate ke sath default headshot accuracy bohot high hai. DPI bohot kam badhaein warna drag aim choke ho jayega (crosshair air mein chala jayega)."
        ),
        DevicePreset(
            id = "poco_m_series",
            brand = "POCO",
            modelName = "Poco M4 / M5 / M6 Pro",
            general = 98,
            redDot = 92,
            scope2x = 90,
            scope4x = 88,
            sniper = 44,
            freeLook = 75,
            recommendedDpi = 410,
            recommendedFireButtonSize = 48,
            specificTips = "M-series budget beasts hain. Inke liye sensitivity balance rakhni parti hai taaki short range aur long range dono me equal accuracy mile."
        ),

        // Mi / Redmi
        DevicePreset(
            id = "redmi_note_series",
            brand = "REDMI / MI",
            modelName = "Redmi Note 10 / 11 / 12 / 13 Pro",
            general = 97,
            redDot = 91,
            scope2x = 89,
            scope4x = 86,
            sniper = 40,
            freeLook = 72,
            recommendedDpi = 400,
            recommendedFireButtonSize = 46,
            specificTips = "Super AMOLED display ke sath responsive feedback milta hai. Rotation drag technique semi-circular shape me swipe karne se full red-numbers target hote hain."
        ),
        DevicePreset(
            id = "redmi_number_series",
            brand = "REDMI / MI",
            modelName = "Redmi 10 / 12 / 13C / A3 (Budget)",
            general = 100,
            redDot = 98,
            scope2x = 96,
            scope4x = 94,
            sniper = 52,
            freeLook = 85,
            recommendedDpi = 430,
            recommendedFireButtonSize = 54,
            specificTips = "Is phone ki sensitivity slow hoti hai. DPI up karke use karein. Gloo-wall custom scale large rakhein taaki click miss na ho."
        ),

        // Redmi K Series / Mi Flagships
        DevicePreset(
            id = "mi_premium",
            brand = "REDMI / MI",
            modelName = "Mi 11 Ultra / Xiaomi 12 / 13 / 14 / K50 / K60",
            general = 94,
            redDot = 86,
            scope2x = 84,
            scope4x = 80,
            sniper = 32,
            freeLook = 60,
            recommendedDpi = 380,
            recommendedFireButtonSize = 42,
            specificTips = "Snapdragon Flagship processor aur premium touch sampling rate hai. 94-96 is absolute perfect sensitivity range. Recoil tight karne ke liye stock attachment zarur lagaen."
        ),

        // Others (Realme, OnePlus, IQOO, Asus, Infinix)
        DevicePreset(
            id = "realme_all",
            brand = "OTHER DEVICES",
            modelName = "Realme 9 / 10 / 11 / GT Series",
            general = 98,
            redDot = 91,
            scope2x = 89,
            scope4x = 86,
            sniper = 45,
            freeLook = 75,
            recommendedDpi = 415,
            recommendedFireButtonSize = 47,
            specificTips = "Realme devices stable sensitivity standard follow karte hain. Inme horizontal drag best kaam karta hai, short range shotgun headshot ke liye 47 size fire button adjust karein."
        ),
        DevicePreset(
            id = "oneplus_all",
            brand = "OTHER DEVICES",
            modelName = "OnePlus Nord / Pro / 11 / 12",
            general = 93,
            redDot = 85,
            scope2x = 82,
            scope4x = 80,
            sniper = 30,
            freeLook = 60,
            recommendedDpi = 380,
            recommendedFireButtonSize = 40,
            specificTips = "Extremely premium fluid panels. Sensitiviy 95+ rakhne se control kharab ho sakta hai. One-tap short range ke liye perfect drag speed zaruri hai, drag speed normal rakhein."
        ),
        DevicePreset(
            id = "infinix_tecno",
            brand = "OTHER DEVICES",
            modelName = "Infinix Hot / Note & Tecno Pova",
            general = 100,
            redDot = 100,
            scope2x = 98,
            scope4x = 98,
            sniper = 55,
            freeLook = 90,
            recommendedDpi = 450,
            recommendedFireButtonSize = 55,
            specificTips = "Low-touch response screens. Custom DPI change karne se system performance slow ho sakti hai, isliye general aur red-dot sensitivity humesha 100 par switch karke rakhein."
        )
    )

    val huds = listOf(
        HudPreset(
            title = "Aesthetic Double-Thumb Auto Headshot Setup",
            clawType = "2-Finger Layout",
            description = "Best layout un players ke liye jo basic performance aur accuracy pe focus karte hain. Isme thumb movement fast milti hai air drag aim lock bohot perfect hota hai.",
            fireButtonSize = 48,
            glooWallButtonSize = 90,
            leftFireButtonScale = 0, // Disabled or default
            switchWeaponSize = 50,
            visualMap = "THUMB_LAYOUT",
            setupSteps = listOf(
                "Right Fire Button size 45-50% ke beech rakhein aur isko bottom margin se thoda ooper space deir, taaki dragging space bohot acha mile.",
                "Left index finger free rahegi movement ke liye aur sirf thumb controllers use honge.",
                "Gloo Wall button left corner par 90-100% scale pe adjust karein taaki instantly click ho sake.",
                "Quick Weapon Switch option strictly select karein aur use left hand corner ya direct bottom center me 70% opacity pe rakhein."
            )
        ),
        HudPreset(
            title = "Fast Gloo Wall & Aim Lock Competitive Setup",
            clawType = "3-Finger Layout",
            description = "In-game movement, jump shot aur sit-up fast gloo wall lagane ke liye sabse recommended layout hai. Sabse zyada semi-pro aur pro players is technique ka prayog karte hain.",
            fireButtonSize = 45,
            glooWallButtonSize = 100,
            leftFireButtonScale = 85,
            switchWeaponSize = 80,
            visualMap = "CLAW_THREE",
            setupSteps = listOf(
                "Gloo Wall button ko right bottom side se shift karke upper left corner me slide karein aur size scale ko 100% full kar dein.",
                "Use the left index finger exclusively to tap the Gloo Wall and Jump buttons.",
                "Right Fire button bottom right line par rahega (Size: 45) rotation headshot lagane ke liye.",
                "Left Fire button enable karein aur isse upper corner me place karein quick seat shoot clicks ke liye.",
                "Jump button ko movement button ke exact upar lagayein quick jump shot apply karne ke liye."
            )
        ),
        HudPreset(
            title = "Insane Movement eSports Fast Switch Setup",
            clawType = "4-Finger Layout",
            description = "Ye competitive tournament aur high eSports lobbies ke liye banaya gaya ultimate control HUD hai. Isme weapon double-tapping, quick-scopes aur ultra-fast movement speeds possible hain.",
            fireButtonSize = 42,
            glooWallButtonSize = 95,
            leftFireButtonScale = 90,
            switchWeaponSize = 90,
            visualMap = "CLAW_FOUR",
            setupSteps = listOf(
                "Jump button strictly top-right space pe assign karein setup me.",
                "Crouch/Sit button standard right hand inner line par 75% size horizontal par lagayein.",
                "Gloo Wall button top left center boundary me place karein jisse third finger (Left Index) se access kiya ja sake.",
                "Right Fire Button size strictly 40-42% pe lock karein precise drag headshot stability ke liye.",
                "Quick Weapon switch button upar bottom left slot se direct right side margin par lagayein run button ke pass, weapon change aur run instantly click hoga."
            )
        )
    )

    val tutorials = listOf(
        TutorialTip(
            title = "Perfect One-Tap Headshots (One-Tap Technique)",
            category = "Headshot Accuracy",
            description = "Free Fire mein one-tap headshot marna sabse zyada popular skill hai. Iske liye Drag aur Crosshair ki position sabsay important hoti hai.",
            stepByStep = listOf(
                "Crosshair Position: Crosshair ko hamesha dushman ke shoulder ya face level ke side par rakhein. Direct body par lock hone se headshot nahi lagega.",
                "Straight Drag (Mid/Long Range): Agar dushman seedha khada hai ya dur hai, to right fire button ko halka sa seedha upar ki taraf pull karein.",
                "Rotation Drag (Short Range): Agar dushman close range mein circular movement kar rha hai, to fire button ko circular swipe karte huay dushman ke head direction ki taraf pull karein.",
                "Weapon Quick Switch: Fire tap karte hi instantly Quick Weapon Switch button tap karein aur run button daba kar position relocate karein."
            ),
            difficulty = "Medium"
        ),
        TutorialTip(
            title = "Zero Recoil Shot Technique",
            category = "Headshot Accuracy",
            description = "Bohot saare players ka spray headshot ke bad hawa mein nikal jata hai ya goli choke ho jati hai. Zero recoil apply karne ke tips:",
            stepByStep = listOf(
                "Don't Hold: Ek sath 4-5 se zyada bullet fire na karein. Continuous sprays se gun heavily recoil karne lagti hai.",
                "Burst Fire Formula: Humesha burst patterns (2-3 seconds drag tap) repeat kar ke fire karein.",
                "Analog Release: Fire karte waqt movement Joystick/Analog ko touch bilkul na karein. Isse bullet bypass choke hat jata hai aur straight bullet hit karti hain."
            ),
            difficulty = "Easy"
        ),
        TutorialTip(
            title = "Sit-Up Gloo Wall (Sec-Fast Shield)",
            category = "Movement Speed",
            description = "Apne damage control ko improve karne aur quick defense build karne ke sabse solid tricks.",
            stepByStep = listOf(
                "Fire: Sabse pehle right action button se drag headshot try karein.",
                "Sit (Crouch): Fire button ko niche late hi instantly direct crouch (baithne wala button) press karein.",
                "Hold Wall: Uske turant baad Gloo Wall icon pe tap karein jo left high index boundary screen me hai.",
                "Place Shield: Left fire button click karein ya direct manual right wall trigger press kar ke 360 shield create karein."
            ),
            difficulty = "Pro"
        ),
        TutorialTip(
            title = "Double Snipe Rapid Scope Technique",
            category = "Sniper Tricks",
            description = "AWM aur M82B ko bina reload reload sound ke extreme speed me switch karne ki competitive tips.",
            stepByStep = listOf(
                "Aim target: Pehle dushman ke body orientation point ko lock karein.",
                "Scope Open + Fire: Zoom tap karein aur instantly right lock trigger press karein gun release blast ke liye.",
                "Tap Switch Hand: Fire trigger click hote hi milliseconds me left status bar ya quick slot change weapon select karein.",
                "Tap Zoom again: Agli bullet slide line me aate hi zoom scope click karke bina lag ke target double trace karein."
            ),
            difficulty = "Pro"
        )
    )

    val proPlayers = listOf(
        ProPlayerPreset(
            name = "Ruok FF",
            country = "Thailand",
            device = "iPhone 15 Pro Max",
            general = 94,
            redDot = 82,
            scope2x = 80,
            scope4x = 78,
            sniper = 25,
            freeLook = 50,
            recommendedDpi = 380, // or default iOS equivalent
            fireButtonSize = 38,
            playstyle = "One-Tap Headshot / Drag Master",
            quote = "Perfect crosshair positioning is the only secret to consistent double-tap headshots!"
        ),
        ProPlayerPreset(
            name = "Raistar",
            country = "India",
            device = "ROG Phone 8",
            general = 100,
            redDot = 98,
            scope2x = 95,
            scope4x = 92,
            sniper = 40,
            freeLook = 80,
            recommendedDpi = 480,
            fireButtonSize = 45,
            playstyle = "Insane Movement Speed / Sit-up Gloo Wall",
            quote = "Speed wins fights. Keep your general sensitivity at 100 and learn to rotate drag."
        ),
        ProPlayerPreset(
            name = "White444",
            country = "Brazil",
            device = "Xiaomi 13 Ultra",
            general = 95,
            redDot = 86,
            scope2x = 84,
            scope4x = 81,
            sniper = 35,
            freeLook = 60,
            recommendedDpi = 410,
            fireButtonSize = 42,
            playstyle = "Precision Headshot / Sniper Burst",
            quote = "Don't increase your DPI blindly. Maintain bullet density with steady drag rates."
        ),
        ProPlayerPreset(
            name = "Badge 99",
            country = "India",
            device = "POCO F5",
            general = 98,
            redDot = 92,
            scope2x = 90,
            scope4x = 88,
            sniper = 45,
            freeLook = 75,
            recommendedDpi = 420,
            fireButtonSize = 47,
            playstyle = "Short-Range Rusher with Shotguns",
            quote = "In short range, keep your fire button low on your screen and use 3-Finger layout."
        )
    )
}
