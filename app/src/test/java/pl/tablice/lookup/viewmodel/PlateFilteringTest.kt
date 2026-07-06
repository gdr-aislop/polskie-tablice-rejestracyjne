package pl.tablice.lookup.viewmodel

import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test
import pl.tablice.lookup.data.PlateEntry

class PlateFilteringTest {

    private val sampleEntries = listOf(
        PlateEntry("KR", "Kraków", "małopolskie", "miasto_na_prawach_powiatu"),
        PlateEntry("KRA", "krakowski", "małopolskie", "powiat"),
        PlateEntry("KRK", "krakowski", "małopolskie", "powiat"),
        PlateEntry("JR", "Kraków", "małopolskie", "miasto_na_prawach_powiatu"),
        PlateEntry("WA", "m.st. Warszawa", "mazowieckie", "miasto_na_prawach_powiatu"),
        PlateEntry("WAW", "m.st. Warszawa", "mazowieckie", "miasto_na_prawach_powiatu"),
        PlateEntry("C", "przykład", "kujawsko-pomorskie", "test"),
        PlateEntry("DW", "Wrocław", "dolnośląskie", "miasto_na_prawach_powiatu")
    )

    @Test
    fun `prefix KR zwraca KR i KRA ale nie WA`() {
        val result = filterPlates(sampleEntries, "KR").map { it.kod }

        assertTrue(result.containsAll(listOf("KR", "KRA", "KRK")))
        assertFalse(result.contains("WA"))
        assertFalse(result.contains("WAW"))
        assertFalse(result.contains("JR"))
    }

    @Test
    fun `wynik posortowany od najkrotszych kodow`() {
        val result = filterPlates(sampleEntries, "KR").map { it.kod }

        assertEquals(listOf("KR", "KRA", "KRK"), result)
    }

    @Test
    fun `pusty query zwraca pelna liste posortowana wg wojewodztwa`() {
        val result = filterPlates(sampleEntries, "")

        assertEquals(sampleEntries.size, result.size)

        // grupy województw w kolejności alfabetycznej: dolnośląskie, kujawsko-pomorskie, małopolskie, mazowieckie
        assertEquals(
            listOf("dolnośląskie", "kujawsko-pomorskie", "małopolskie", "małopolskie", "małopolskie", "małopolskie", "mazowieckie", "mazowieckie"),
            result.map { it.wojewodztwo }
        )
    }

    @Test
    fun `w obrebie wojewodztwa wynik posortowany od najkrotszych kodow`() {
        val result = filterPlates(sampleEntries, "")
            .filter { it.wojewodztwo == "małopolskie" }
            .map { it.kod }

        assertEquals(listOf("JR", "KR", "KRA", "KRK"), result)
    }

    @Test
    fun `brak dopasowania zwraca pusta liste`() {
        val result = filterPlates(sampleEntries, "ZZZ")

        assertTrue(result.isEmpty())
    }

    @Test
    fun `wyszukiwanie jest bez rozrozniania wielkosci liter dzieki sanitizacji`() {
        val result = filterPlates(sampleEntries, "kr").map { it.kod }

        assertTrue(result.containsAll(listOf("KR", "KRA", "KRK")))
    }

    @Test
    fun `sanitizePlateQuery usuwa cyfry i znaki specjalne oraz wymusza wielkie litery`() {
        assertEquals("KR", sanitizePlateQuery("k1r!"))
        assertEquals("ABC", sanitizePlateQuery("abc123"))
    }

    @Test
    fun `sanitizePlateQuery ogranicza dlugosc do 3 znakow`() {
        assertEquals("KRA", sanitizePlateQuery("krakow"))
    }
}
