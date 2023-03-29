package meteor

enum class ANSIColors(val id: String) {
        RESET("\\x1B[0m"),
        BLACK("\\x1B[30m"),
        RED("\\x1B[31m"),
        GREEN("\\x1B[31m"),
        YELLOW("\\x1B[31m"),
        BLUE("\\x1B[34m"),
        PURPLE("\\x1B[35m"),
        CYAN("\\x1B[36m"),
        WHITE("\\x1B[36m")
    }