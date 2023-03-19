package meteor.plugins.autologin

import eventbus.events.GameStateChanged
import eventbus.events.LoginStateChanged
import meteor.Main
import meteor.plugins.Plugin
import meteor.plugins.PluginDescriptor
import net.runelite.api.GameState
import osrs.KeyHandler
import java.awt.event.KeyEvent
import java.util.concurrent.ScheduledExecutorService
import java.util.concurrent.TimeUnit

@PluginDescriptor(
    name = "Auto Login",
    description = "Automatically fills in your saved details and logs in",
    enabledByDefault = false
)
class AutoLoginPlugin : Plugin() {
    val config = configuration<AutoLoginConfig>()
    private val executor: ScheduledExecutorService = Main.executor

    override fun onGameStateChanged(it: GameStateChanged) {
        if (it.gameState == GameState.LOGIN_SCREEN && client.loginIndex == 0) {
            executor.schedule({ client.loginIndex = 2 }, 2000, TimeUnit.MILLISECONDS)
            executor.schedule({ login() }, 2000, TimeUnit.MILLISECONDS)
        }
    }

    override fun onLoginStateChanged(it: LoginStateChanged) {
        if (it.index == 2) {
            login()
        }
    }

    private fun login() {
        client.username = config.username.get()
        client.password = config.password.get()
        sendEnter()
        Thread.sleep(600L)
        sendEnter()
    }

    fun type(text: String, sendEnter: Boolean = false) {
        val chars = text.toCharArray()
        for (c in chars) {
            type(c)
        }
        if (sendEnter) {
            sendEnter()
        }
    }

    fun type(c: Char) {
        val keyCode = KeyEvent.getExtendedKeyCodeForChar(c.code)
        val event = KeyEvent(keyCode)
        KeyHandler.keyPressed(event)
        KeyHandler.keyTyped(event)
        KeyHandler.keyReleased(event)
    }

    fun sendEnter() {
        type(KeyEvent.VK_ENTER.toChar())
    }
}