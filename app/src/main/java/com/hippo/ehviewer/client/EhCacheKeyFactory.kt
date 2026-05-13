/*
 * Copyright 2016 Hippo Seven
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.hippo.ehviewer.client

import com.hippo.ehviewer.client.data.AbstractGalleryInfo

const val THUMB_PROXY = "https://proxy.moonchan.xyz/"

private val NormalPreviewKeyRegex = Regex("/(c[12m])/[^/]+/(\\d+-\\d+)")

fun getImageKey(gid: Long, index: Int) = "image:$gid:$index"

fun getThumbKey(gid: Long): String = "preview:large:$gid:0"

fun getLargePreviewKey(gid: Long, index: Int) = "preview:large:$gid:$index"

fun getNormalPreviewKey(url: String) = NormalPreviewKeyRegex.find(url)?.let { "preview:normal:${it.groupValues[1]}:${it.groupValues[2]}" } ?: url

val String.isNormalPreviewKey
    get() = startsWith("preview:normal:")

val String.thumbUrl: String
    get() {
        val url = this
        if (url.startsWith("http://") || url.startsWith("https://")) {
            val afterProto = url.substringAfter("://")
            var host = afterProto.substringBefore("/")
            val path = afterProto.substringAfter("/", "")
            if (host == "s.exhentai.org") {
                host = "ehgt.org"
            }
            return "${THUMB_PROXY}$path?proxy_host=$host"
        }
        return THUMB_PROXY + url.trimStart('/')
    }

val AbstractGalleryInfo.thumbUrl: String?
    get() = thumb?.thumbUrl
