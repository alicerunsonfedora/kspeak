package kspeak

import kotlin.test.*

class SceneTests {

    @Test
    fun testSceneGenerates() {
        val emptyScene = buildScene {
            version(1)
        }

        assertNotNull(emptyScene.toJSONString())
    }

}