package bisq.client

actual class Platform actual constructor() {
    actual val platform: String = getOsName()
}

fun getOsName(): String {
    val os = System.getProperty("os.name")?.lowercase()
    return when {
        os?.contains("win") == true -> {
            "Windows"
        }
        os?.contains("nix") == true || os?.contains("nux") == true || os?.contains("aix") == true -> {
            "Linux"
        }
        os?.contains("mac") == true -> {
            "macOS"
        }
        else -> "Unknown"
    }
}
