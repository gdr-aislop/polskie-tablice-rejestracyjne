package pl.tablice.lookup.data

/**
 * Płaski model jednego wiersza wyników: jeden pełny kod tablicy
 * (np. "KRA") przypisany do jednej jednostki administracyjnej.
 */
data class PlateEntry(
    val kod: String,
    val nazwaJednostki: String,
    val wojewodztwo: String,
    val typJednostki: String
)
