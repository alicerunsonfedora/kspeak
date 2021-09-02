/*
 * (C) 2021 Marquis Kurt.
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package kspeak

import kotlin.test.Test
import kotlin.test.assertNotNull

class SceneTests {

    @Test
    fun testSceneGenerates() {
        val emptyScene = buildScene {
            version(1)
        }

        assertNotNull(emptyScene.toJSONString())
    }

}