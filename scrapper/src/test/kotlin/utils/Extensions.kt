package utils

import java.net.URI

fun String.extractAuthority(): String {
    return URI(this).authority
}

inline fun <reified T> T.loadResource(path: String) = this!!::class.java.getResource(path)!!.readText()
