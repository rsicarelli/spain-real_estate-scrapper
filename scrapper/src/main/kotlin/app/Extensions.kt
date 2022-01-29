package app

import it.skrape.selects.CssSelectable
import it.skrape.selects.CssSelector
import it.skrape.selects.html5.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import java.time.ZoneId
import java.time.ZonedDateTime
import kotlin.time.Duration
import kotlin.time.ExperimentalTime

fun String.convertToDouble() = this.replace(Regex("[^0-9]"), "").toDouble()
fun String.convertToInt() = this.replace(Regex("[^0-9]"), "").toInt()

inline fun <T> runCatchingOrDefault(defaultValue: T, action: () -> T): T {
    return try {
        action()
    } catch (e: Exception) {
        defaultValue
    }
}

@OptIn(ExperimentalTime::class)
fun CoroutineScope.launchPeriodicAsync(
    duration: Duration,
    action: suspend () -> Unit
) = this.async {
    val millis = duration.inWholeMilliseconds
    if (millis > 0) {
        while (isActive) {
            action()
            delay(millis)
        }
    } else {
        action()
    }
}

fun <T> CssSelectable.divWithClass(className: String, action: CssSelector.() -> T): T {
    return div {
        withClass = className
        action(this)
    }
}

fun <T> CssSelectable.spanWithClass(className: String, action: CssSelector.() -> T): T {
    return span {
        withClass = className
        action(this)
    }
}

fun <T> CssSelectable.h3WithClass(className: String, action: CssSelector.() -> T): T {
    return h3 {
        withClass = className
        action(this)
    }
}

fun <T> CssSelectable.liWithClass(className: String, action: CssSelector.() -> T): T {
    return li {
        withClass = className
        action(this)
    }
}

fun <T> CssSelectable.sectionWithClass(className: String, action: CssSelector.() -> T): T {
    return section {
        withClass = className
        action(this)
    }
}

fun <T> CssSelectable.aWithClass(className: String, action: CssSelector.() -> T): T {
    return a {
        withClass = className
        action(this)
    }
}

fun <T> CssSelectable.h1WithClass(className: String, action: CssSelector.() -> T): T {
    return h1 {
        withClass = className
        action(this)
    }
}

fun <T> CssSelectable.pWithClass(className: String, action: CssSelector.() -> T): T {
    return p {
        withClass = className
        action(this)
    }
}

fun <T> CssSelectable.ulWithClass(className: String, action: CssSelector.() -> T): T {
    return ul {
        withClass = className
        action(this)
    }
}

fun currentTimeAtUTC(): String {
    return ZonedDateTime.now(ZoneId.of("UTC")).toLocalDateTime().toString()
}
