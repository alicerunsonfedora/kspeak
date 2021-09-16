/*
 * (C) 2021 Marquis Kurt.
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package kspeak

import kotlinx.serialization.Serializable

/** A data class that represents a part of a scene.
 *
 * Scene parts are blocks of data that can contain either dialogue or options. Each part has a descriptor for its type,
 * as well as any optional parameters attached to it.
 *
 * @property kind The kind of scene part block that the current block is.
 * @see ScenePart.Kind
 *
 * @property dialogue The dialogue for this part, if the part is a dialogue part. Defaults to null.
 * @property branch The list of options for the branch, if the part is a branch. Defaults to null.
 * @property options A map of options for this scene part.
 */
@Serializable
data class ScenePart(
    val kind: Kind,
    val dialogue: ArrayList<DialoguePart>? = null,
    val branch: ArrayList<Option>? = null,
    val imageChangeRequest: ImageChangeRequest? = null,
    val options: Map<String, String> = emptyMap()
) {

    /** An enumeration for the different kinds of parts a ScenePart can be. */
    enum class Kind {
        /** A block of dialogue. */
        DIALOGUE,

        /** A decision branch containing options. */
        BRANCH,

        /** A command to change either the background image or the sprite image. */
        IMAGE_CHANGE
    }
}

/** A builder class that generates scene parts.
 *
 * This is commonly used in `buildScene` to generate the list of parts that the scene will take.
 */
class ScenePartBuilder: ArrayList<ScenePart>() {

    /** Change the background image of a scene.
     *
     * @param backgroundPath The path to the image to use as the background.
     */
    fun background(backgroundPath: String) {
        add(
            ScenePart(
                ScenePart.Kind.IMAGE_CHANGE,
                imageChangeRequest = ImageChangeRequest(backgroundPath, "")
            )
        )
    }

    /** Generate a decision branch scene part.
     *
     * Example:
     * ```
     * branch(waitForAll = false) {
     *  option("Start now.") { ... }
     *  option("Wait until later.") { ... }
     * }
     * ```
     *
     * @param waitForAll Whether the player must click through all options before proceeding to the next scene part.
     * Defaults to false.
     *
     * @param builder A closure that will construct the list of options for this branch.
     *
     */
    fun branch(waitForAll: Boolean = false, builder: OptionBuilder.() -> Unit) {
        val options = OptionBuilder()
        builder.invoke(options)
        add(ScenePart(ScenePart.Kind.BRANCH, branch = options, options = mapOf("waitForAll" to "$waitForAll")))
    }

    /** Generate a dialogue scene part.
     *
     * Example:
     * ```
     * val angi = Character(...)
     * dialogue() {
     *  narrate("I slowly find myself unable to write the next line of text.")
     *  line(angi.speak("What's the matter? You're not able to finish?"))
     * }
     * ```
     *
     * @param builder A closure that will construct the dialogue for this part.
     */
    fun dialogue(builder: DialogueBuilder.() -> Unit) {
        val dialogueBuilder = DialogueBuilder()
        builder.invoke(dialogueBuilder)
        val dialogueUnit = ScenePart(ScenePart.Kind.DIALOGUE, dialogue = dialogueBuilder)
        add(dialogueUnit)
    }

    /** Change the sprite image on top of the background.
     *
     * @param spritePath The path to the sprite image to use.
     */
    fun sprite(spritePath: String) {
        add(
            ScenePart(
                ScenePart.Kind.IMAGE_CHANGE,
                imageChangeRequest = ImageChangeRequest("", spritePath)
            )
        )
    }

}