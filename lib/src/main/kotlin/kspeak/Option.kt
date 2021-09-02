package kspeak

import kotlinx.serialization.*

/** A data class that represents a choice the player can make in a decision branch.
 *
 * Options make up decision branches in scripting. An option often contains dialogue inside of it.
 *
 * @property name The option's text as it appears on screen.
 * @property dialogue The dialogue that will be displayed on screen when the player clicks this option.
 */
@Serializable
data class Option(val name: String, val dialogue: ArrayList<DialoguePart>)

/** A builder that generates decision branches.
 *
 * This is commonly used inside of `buildScene` to generate decision branches on the fly.
 */
@Serializable
class OptionBuilder: ArrayList<Option>() {

    /** Adds an option to the decision branch, as well as any associated dialogue.
     *
     * Example:
     * ```
     * option("Go to the shop.") {
     *  narrate("Maybe it's better if I head to the shop first...")
     * }
     * ```
     *
     * @param name The option's text as it will appear on screen.
     * @param builder A closure that builds dialogue for this option.
     * @see Option
     */
    fun option(name: String, builder: DialogueBuilder.() -> Unit) {
        val dialogueBuilder = DialogueBuilder()
        builder.invoke(dialogueBuilder)
        add(Option(name, dialogueBuilder))
    }

}