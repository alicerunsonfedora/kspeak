package kspeak

/** A class that represents a character. */
class Character(val name: String) {

    override fun toString(): String = name

    /** Have the character speak a line, with optional imagery. */
    fun speak(line: String, imageName: String = ""): DialoguePart {
        val map = mutableMapOf(name to line)
        if (imageName.isNotEmpty())
            map["image"] = imageName
        return map
    }
}