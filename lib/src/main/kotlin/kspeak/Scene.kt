package kspeak

import java.io.File
import kotlinx.serialization.*
import kotlinx.serialization.json.*

/** A class that represents a scriptable scene.
 *
 * A scriptable scene contains a list of parts that comprise of dialogue and branch blocks. These parts are built with
 * their own builders and are serializable to JSON.
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

    /** Set the version for the script. This is used to track compatibility.
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

    /** List the parts to make the scene. */
    fun parts(builder: ScenePartBuilder.() -> Unit) {
        val scenePartBuilder = ScenePartBuilder()
        builder.invoke(scenePartBuilder)
        parts = scenePartBuilder
    }

    /** Write the scene to a serialized JSON file. */
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

/** Build a scene from its parts. */
fun buildScene(builder: Scene.() -> Unit): Scene {
    val newScene = Scene()
    builder.invoke(newScene)
    newScene.write()
    return newScene
}