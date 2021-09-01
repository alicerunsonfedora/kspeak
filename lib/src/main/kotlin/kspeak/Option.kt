package kspeak

import kotlinx.serialization.*

/** A data class that represents a choice the player can make in a decision branch. */
@Serializable
data class Option(val name: String, val dialogue: ArrayList<DialoguePart>)

/** A builder that generates decision branches. */
@Serializable
class OptionBuilder: ArrayList<Option>() {

    /** Adds an option to the decision branch., as well as any associated dialogue. */
    fun option(name: String, builder: DialogueBuilder.() -> Unit) {
        val dialogueBuilder = DialogueBuilder()
        builder.invoke(dialogueBuilder)
        add(Option(name, dialogueBuilder))
    }

}