package app

import domain.entity.Location
import it.skrape.selects.CssSelectable
import it.skrape.selects.CssSelector
import it.skrape.selects.html5.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import java.time.LocalTime
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


fun Map<String, Any?>.asLocation(token: String): Location {
    val locationMap = this[token] as HashMap<String, Any?>
    return Location(
        name = locationMap.asString("name"),
        lat = locationMap.asDouble("lat"),
        lng = locationMap.asDouble("lng"),
        isApproximated = locationMap.asBoolean("approximated"),
        isUnknown = locationMap.asBoolean("unknown"),
    )
}

fun Map<String, Any?>.asString(token: String) = this[token] as String
fun Map<String, Any?>.asNullableString(token: String, default: String? = null) =
    (this[token] as String?) ?: default

fun Map<String, Any?>.asDouble(token: String) = this[token] as Double
fun Map<String, Any?>.asInt(token: String) = (this[token] as Long).toInt()
fun Map<String, Any?>.asBoolean(token: String) = this[token] as Boolean
fun Map<String, Any?>.asNullableInt(token: String, default: Int? = null) =
    (this[token] as Long?)?.toInt() ?: default

fun Map<String, Any?>.asStringList(token: String) = this[token] as List<String?>


fun CoroutineScope.launchPeriodicAsync(
    repeatMillis: Long,
    action: suspend () -> Unit
) = this.async {
    if (repeatMillis > 0) {
        while (isActive) {
            action()
            delay(repeatMillis)
        }
    } else {
        action()
    }
}

fun currentTimeAtUTC(): String {
    return ZonedDateTime.now(ZoneId.of("UTC")).toLocalDateTime().toString()
}
