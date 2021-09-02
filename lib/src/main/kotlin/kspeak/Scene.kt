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

import kotlinx.serialization.*
import kotlinx.serialization.json.Json
import java.io.File

/** A class that represents a scriptable scene.
 *
 * A scriptable scene contains a list of parts that comprise of dialogue and branch blocks. These parts are built with
 * their own builders and are serializable to JSON.
 *
 * @see buildScene
 *
 * @property version The version of the script the scene is. Currently unused.
 * @property parts The list of parts that comprise of the scene.
 * @property outputPath The path to the JSON file that will be written to.
 */
@Serializable
@OptIn(ExperimentalSerializationApi::class)
class Scene(
    @Required private var version: String = "1",
    @Required private var parts: ArrayList<ScenePart> = ArrayList(),
    @Transient private var outputPath: String = ""
) {

    override fun toString(): String {
        var start = "Scene(version = $version"
        if (outputPath.isNotEmpty())
            start += ", outputPath = $outputPath"
        if (parts.isNotEmpty())
            start += ", parts = $parts"
        return "$start)"
    }

    /** Returns a JSON string of this scene. */
    @OptIn(ExperimentalSerializationApi::class)
    fun toJSONString(): String {
        return Json.encodeToString(this)
    }

    /** Set the version for the script.
     *
     * This is used to track compatibility.
     *
     * @param newVersion The version of the script schema that is being used.
     */
    fun version(newVersion: Int) {
        version = "$newVersion"
    }

    /** Sets the path of the output file.
     *
     * When a path is set for a scene, it will automatically make a serialized JSON file that the game can read.
     *
     * @param path The path corresponding to the output file to write to.
     */
    fun outputToFile(path: String) {
        outputPath = path
    }

    /** List the parts to make the scene.
     *
     * @param builder A closure that will generate the list of parts that the scene will comprise of.
     */
    fun parts(builder: ScenePartBuilder.() -> Unit) {
        val scenePartBuilder = ScenePartBuilder()
        builder.invoke(scenePartBuilder)
        parts = scenePartBuilder
    }

    /** Write the scene to a serialized JSON file, if possible. */
    @OptIn(ExperimentalSerializationApi::class)
    fun write() {
        if (outputPath.isEmpty()) return
        val scene = this
        val format = Json { prettyPrint = true }

        with(File(outputPath)) {
            writeText(format.encodeToString(scene))
        }
    }

}

/** Build a scene from its parts. This is the common entry point for creating a scene by hand.
 *
 * @see Scene
 * @param builder A closure that will build the scene
 */
fun buildScene(builder: Scene.() -> Unit): Scene {
    val newScene = Scene()
    builder.invoke(newScene)
    newScene.write()
    return newScene
}