package kspeak

import kotlinx.serialization.*

/** A data class that represents a part of a scene. */
@Serializable
data class ScenePart(
    val kind: Kind,
    val data: ArrayList<DialoguePart>? = null,
    val branch: ArrayList<Option>? = null,
    val options: Map<String, String> = emptyMap()) {
    enum class Kind {
        DIALOGUE,
        BRANCH
    }
}

/** A builder class that generates scene parts. */
class ScenePartBuilder: ArrayList<ScenePart>() {

    /** Generate a dialogue scene part. */
    fun dialogue(builder: DialogueBuilder.() -> Unit) {
        val dialogueBuilder = DialogueBuilder()
        builder.invoke(dialogueBuilder)
        val dialogueUnit = ScenePart(ScenePart.Kind.DIALOGUE, data = dialogueBuilder)
        add(dialogueUnit)
    }

    /** Generate a decision branch scene part. */
    fun branch(waitForAll: Boolean = false, builder: OptionBuilder.() -> Unit) {
        val options = OptionBuilder()
        builder.invoke(options)
        add(ScenePart(ScenePart.Kind.BRANCH, branch = options, options = mapOf("waitForAll" to "$waitForAll")))
    }

}