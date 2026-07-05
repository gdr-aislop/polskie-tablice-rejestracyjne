package pl.tablice.lookup.viewmodel

import pl.tablice.lookup.data.PlateEntry

/**
 * Usuwa z wejścia cyfry i znaki specjalne, zamienia na wielkie litery
 * i ogranicza do 3 znaków (maksymalna długość pełnego kodu tablicy).
 */
fun sanitizePlateQuery(rawInput: String): String =
    rawInput
        .uppercase()
        .filter { it.isLetter() }
        .take(3)

/**
 * Dopasowanie prefiksowe ("zaczyna się od"), posortowane od najkrótszych
 * (najbardziej trafnych) kodów, a przy równej długości alfabetycznie.
 * Pusty [query] zwraca pełną, posortowaną listę.
 */
fun filterPlates(entries: List<PlateEntry>, query: String): List<PlateEntry> {
    val normalizedQuery = sanitizePlateQuery(query)
    val matching = if (normalizedQuery.isEmpty()) {
        entries
    } else {
        entries.filter { it.kod.startsWith(normalizedQuery) }
    }
    return matching.sortedWith(compareBy({ it.kod.length }, { it.kod }))
}
