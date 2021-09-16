/*
 * (C) 2021 Marquis Kurt.
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package kspeak

import kotlinx.serialization.Serializable

/** A data class that represents a request to change images. */
@Serializable
data class ImageChangeRequest(val backgroundImage: String, val characterImage: String)