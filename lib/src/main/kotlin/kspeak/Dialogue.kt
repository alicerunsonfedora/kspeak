/*
 * (C) 2021 Marquis Kurt.
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

/*
 * (C) 2021 Marquis Kurt.
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package kspeak

import kotlinx.serialization.Serializable

/** A type that describes the structure of a dialogue block.
 *
 * Dialogue blocks make up the foundation of dialogue sequences. They are the equivalent of JSON objects with strings.
 *
 * For example, `Character.speak()` returns a dialogue part that shows as the equivalent in JSON:
 * ```
 * {"Amy": "Hello, world!", "imageName": "test"}
 * ```
 */
typealias DialoguePart = Map<String, String>

/** A builder that generates dialogue blocks.
 *
 * This builder is commonly used inside of `buildScene` to generate dialogue blocks that the DSL uses to construct the
 * JSON values for.
 */
@Serializable
class DialogueBuilder: ArrayList<DialoguePart>() {

    /** Add a line of dialogue from a character.
     *
     * Example:
     * ```
     * dialogue {
     *  line(amy.speak("Hello, world!"))
     * }
     * ```
     *
     * @param part The dialogue part that will be included in this dialogue block.
     * @see DialoguePart
     */
    fun line(part: DialoguePart) {
        add(part)
    }

    /** Conveniently write multiple lines of dialogue from the same person.
     *
     * Example:
     * monologue {
     *  this say player.speak("Hello.")
     *  this say player.speak("I am Tom.")
     * }
     *
     * @param builder A closure that generates a list of dialogue parts.
     * @see Monologue
     */
    fun monologue(builder: Monologue.() -> Unit) {
        val monologue = Monologue()
        builder.invoke(monologue)
        monologue.forEach{ add(it) }
    }

    /** Add a line of narration.
     *
     * This is the equivalent of calling `line` with the Character being named "narrator".
     *
     * Example:
     * ```
     * dialogue {
     *  narrate("Amy paces in the room.")
     * }
     * ```
     *
     * @param line The line of dialogue that will be narrated.
     */
    fun narrate(line: String) {
        line(Character("narrator").speak(line))
    }
}

class Monologue: ArrayList<DialoguePart>() {
    infix fun say(value: DialoguePart) {
        add(value)
    }
}