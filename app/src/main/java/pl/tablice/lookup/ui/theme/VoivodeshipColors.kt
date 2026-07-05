package pl.tablice.lookup.ui.theme

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.luminance

/**
 * Stałe, skontrastowane kolory chipów dla 16 województw. Mapowanie po nazwie
 * (a nie po indeksie/hashu), żeby uniknąć przypadkowych kolizji kolorów.
 */
private val voivodeshipContainerColors: Map<String, Color> = mapOf(
    "dolnośląskie" to Color(0xFFE57373),
    "kujawsko-pomorskie" to Color(0xFFFFB74D),
    "lubelskie" to Color(0xFFDCE775),
    "lubuskie" to Color(0xFF81C784),
    "łódzkie" to Color(0xFF4DB6AC),
    "małopolskie" to Color(0xFF4FC3F7),
    "mazowieckie" to Color(0xFF7986CB),
    "opolskie" to Color(0xFFBA68C8),
    "podkarpackie" to Color(0xFFF06292),
    "podlaskie" to Color(0xFFA1887F),
    "pomorskie" to Color(0xFF4DD0E1),
    "śląskie" to Color(0xFF9575CD),
    "świętokrzyskie" to Color(0xFFFF8A65),
    "warmińsko-mazurskie" to Color(0xFF64B5F6),
    "wielkopolskie" to Color(0xFFAED581),
    "zachodniopomorskie" to Color(0xFFFFD54F)
)

private val fallbackContainerColor = Color(0xFF90A4AE)

fun voivodeshipContainerColor(wojewodztwo: String): Color =
    voivodeshipContainerColors[wojewodztwo] ?: fallbackContainerColor

/** Czarny lub biały tekst — w zależności od jasności koloru tła chipu. */
fun voivodeshipContentColor(containerColor: Color): Color =
    if (containerColor.luminance() > 0.5f) Color.Black else Color.White
