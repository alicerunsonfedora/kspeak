package kspeak

import kotlinx.serialization.*

/** A type that describes the structure of a dialogue block. */
typealias DialoguePart = Map<String, String>

/** A builder that generates dialogue blocks. */
@Serializable
class DialogueBuilder: ArrayList<DialoguePart>() {

    /** Add a line of dialogue from a character. */
    fun line(part: DialoguePart) {
        add(part)
    }

    /** Conveniently write multiple lines of dialogue from the same person. */
    fun monologue(builder: Monologue.() -> Unit) {
        val monologue = Monologue()
        builder.invoke(monologue)
        monologue.forEach{ add(it) }
    }

    /** Add a line of narration. */
    fun narrate(line: String) {
        line(Character("narrator").speak(line))
    }
}

class Monologue: ArrayList<DialoguePart>() {
    infix fun say(value: DialoguePart) {
        add(value)
    }
}