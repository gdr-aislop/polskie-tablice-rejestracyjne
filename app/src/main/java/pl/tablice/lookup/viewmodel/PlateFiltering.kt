package pl.tablice.lookup.viewmodel

import java.text.Collator
import java.util.Locale
import pl.tablice.lookup.data.PlateEntry

// Zwykłe String.compareTo porównuje po wartościach UTF-16 (np. 'ł' > 'z'),
// co psuje alfabetyczną kolejność polskich nazw województw. Collator z polskim
// Locale sortuje je tak, jak oczekiwałby użytkownik (np. "małopolskie" przed "mazowieckie").
private val polishLocale: Locale = Locale.Builder().setLanguage("pl").setRegion("PL").build()
private val polishWojewodztwoCollator: Collator = Collator.getInstance(polishLocale)

private val defaultOrderComparator: Comparator<PlateEntry> =
    Comparator<PlateEntry> { a, b -> polishWojewodztwoCollator.compare(a.wojewodztwo, b.wojewodztwo) }
        .thenBy { it.kod.length }
        .thenBy { it.kod }

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
 * Pusty [query]: pełna lista domyślnie posortowana wg województwa (alfabetycznie),
 * a w obrębie województwa od najkrótszych kodów.
 *
 * Niepusty [query]: dopasowanie prefiksowe ("zaczyna się od"), posortowane od
 * najkrótszych (najbardziej trafnych) kodów, a przy równej długości alfabetycznie.
 */
fun filterPlates(entries: List<PlateEntry>, query: String): List<PlateEntry> {
    val normalizedQuery = sanitizePlateQuery(query)
    return if (normalizedQuery.isEmpty()) {
        entries.sortedWith(defaultOrderComparator)
    } else {
        entries.filter { it.kod.startsWith(normalizedQuery) }
            .sortedWith(compareBy({ it.kod.length }, { it.kod }))
    }
}
