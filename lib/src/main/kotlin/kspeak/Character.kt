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

/** A class that represents a character.
 *
 * Characters are basic building blocks for presenting dialogue. They can speak a given line and optionally present a
 * different image.
 *
 * Additionally, characters do not get imported into the scripting data, since their dialogue will encode the
 * information necessary. It is recommended to declare characters in the `Scene.parts` closure:
 *
 * ```
 * buildScene {
 *  parts {
 *      val amy = Character("Amy")
 *      val john = Character("John")
 *
 *      ...
 *  }
 * }
 * ```
 *
 * @property name The name of the character. This can also be retrieved via `Character.toString()`.
 *
 */
class Character(private val name: String) {

    /** Retrieves the character's name.
     *
     * @see name
     */
    override fun toString(): String = name

    /** Have the character speak a line, with optional imagery.
     *
     * Example:
     * ```
     * val amy = Character("Amy")
     * dialogue {
     *   line(amy.speak("Hello, world!", imageName = "test"))
     * }
     * ```
     *
     * @param line The line of dialogue that the character will say.
     * @param imageName The name of the image to display as the line is being spoken. Defaults to an empty string.
     *
     * @return A JSON-like dialogue part structure that represents the line data.
     * @see DialoguePart
     *
     */
    fun speak(line: String, imageName: String = ""): DialoguePart {
        val map = mutableMapOf(name to line)
        if (imageName.isNotEmpty())
            map["image"] = imageName
        return map
    }
}