package com.haroon.kmpconveyor

class JVMPlatform: Platform {
    override val name: String = getOSName()
    
    private fun getOSName(): String {
        val osName = System.getProperty("os.name", "Unknown")
        
        return when {
            osName.lowercase().contains("linux") -> {
                try {
                    val process = Runtime.getRuntime().exec("cat /etc/os-release")
                    val reader = process.inputStream.bufferedReader()
                    val lines = reader.readText()
                    reader.close()
                    
                    val nameLine = lines.lines().find { it.startsWith("NAME=") }
                    if (nameLine != null) {
                        nameLine.substringAfter("NAME=").trim('"').split(" ")[0]
                    } else {
                        "Linux"
                    }
                } catch (e: Exception) {
                    "Linux"
                }
            }
            osName.lowercase().contains("windows") -> "Windows"
            osName.lowercase().contains("mac") -> "macOS"
            else -> osName
        }
    }
}

actual fun getPlatform(): Platform = JVMPlatform()