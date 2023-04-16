package meteor

import java.lang.Exception
import java.lang.StringBuilder
import java.util.*

class Logger(var name: String = "") {
    var plugin: String? = null
    var format = "%-35s%s%n"
    fun info(message: Any, vararg replacers: Any) {
        printColorMessageReplacers(ANSIColors.WHITE.id, message, *replacers)
    }

    fun warn(message: Any, vararg replacers: Any) {
        printColorMessageReplacers(ANSIColors.YELLOW.id, message, *replacers)
    }

    fun debug(message: Any, vararg replacers: Any) {
        if (!isDebugEnabled) {
            return
        }
        printColorMessageReplacers(ANSIColors.GREEN.id, message, *replacers)
    }

    fun error(message: Any, vararg replacers: Any) {
        printColorMessageReplacers(ANSIColors.RED.id, message, *replacers)
    }

    private fun printColorMessage(ansiColor: String, message: Any) {
        val tempName: String = if (plugin != null) {
            plugin.toString()
        } else {
            name
        }
        val header = Message.newMessage()
                .add("", "[$tempName] ")
                .build()
        System.out.format(format, header, message)
    }

    private fun printColorMessageReplacers(ansiColor: String, message: Any, vararg replacers: Any) {
        val sRef: String = try {
            message as String
        } catch (e: Exception) {
            printColorMessage(ansiColor, message)
            return
        }
        if (!sRef.contains("{}")) {
            printColorMessage(ansiColor, sRef)
            return
        }
        val finalMessage = StringBuilder()
        val replacersArray = Arrays.stream(replacers).toArray()
        for ((i, s) in sRef.split("{}").toTypedArray().withIndex()) {
            if (i != replacersArray.size) finalMessage.append(s).append(replacersArray[i]) else finalMessage.append(s)
        }
        printColorMessage(ansiColor, finalMessage)
    }

    companion object {
        var DEFAULT_CONTROLLER_COLOR = ANSIColors.CYAN.id
        var isDebugEnabled = true
        fun getLogger(loggedClass: Class<*>): Logger {
            val newLogger = Logger(loggedClass.name)
            val split = loggedClass.toString().split(".")
            newLogger.name = split[split.size - 1]
            return newLogger
        }

        fun generateError(s: String): String {
            if (s.length < 5) return ""
            val lines = s.split(" at ").toTypedArray()
            val output = StringBuilder()
            if (lines.isNotEmpty()) {
                for (line in lines) {
                    if (line.length < 10) continue
                    output.append(line.replace("\n", "")).append("\n")
                }
            }
            return ANSIColors.RED.id + output + ANSIColors.RESET.id
        }
    }
}