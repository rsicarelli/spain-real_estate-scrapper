package utils

import java.net.URI

fun String.extracthAuthority(): String {
    return URI(this).authority
}
