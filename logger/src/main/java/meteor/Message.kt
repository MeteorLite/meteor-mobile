package meteor

class Message internal constructor(var message: String) {
    fun add(ansiColor: String?, text: String): Message {
        return if (ansiColor == null || ansiColor == "") addDefault(text) else Message(message + ansiColor + text)
    }

    fun addDefault(text: String): Message {
        return Message(message + ANSIColors.GREEN.id + text)
    }

    fun build(): String {
        return message + ANSIColors.RESET.id
    }

    companion object {
        fun newMessage(): Message {
            return Message("")
        }
    }
}