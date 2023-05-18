package ru.sejapoe.cellural

import com.badlogic.gdx.Graphics.DisplayMode
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration

// Please note that on macOS your application needs to be started with the -XstartOnFirstThread JVM argument
object DesktopLauncher {
    @JvmStatic
    fun main(arg: Array<String>) {
        val config = Lwjgl3ApplicationConfiguration()
        config.setForegroundFPS(60)
        config.setTitle("Cellural")
        config.setWindowedMode(1920, 1080)
        Lwjgl3Application(GameOfLife(), config)
    }
}
