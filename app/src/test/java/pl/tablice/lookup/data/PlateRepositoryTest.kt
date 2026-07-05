package pl.tablice.lookup.data

import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class PlateRepositoryTest {

    private val repository = PlateRepository()

    private fun loadRealPlatesJson(): String =
        checkNotNull(javaClass.classLoader?.getResourceAsStream("plates.json")) {
            "Nie znaleziono src/test/resources/plates.json"
        }.bufferedReader().use { it.readText() }

    @Test
    fun `parsowanie prostego wycinka splaszcza pelne kody do osobnych wierszy`() {
        val json = """
            {
              "wojewodztwa": [
                {
                  "wojewodztwo": "małopolskie",
                  "wyroznik_wojewodztwa": ["K", "J"],
                  "jednostki": [
                    {
                      "nazwa": "Kraków",
                      "typ": "miasto_na_prawach_powiatu",
                      "wyroznik_powiatu": ["R", "K"],
                      "pelne_kody": ["KR", "KK", "JR", "JK"]
                    }
                  ]
                }
              ]
            }
        """.trimIndent()

        val entries = repository.parse(json)

        assertEquals(4, entries.size)
        assertEquals(setOf("KR", "KK", "JR", "JK"), entries.map { it.kod }.toSet())
        assertTrue(entries.all { it.nazwaJednostki == "Kraków" && it.wojewodztwo == "małopolskie" })
    }

    @Test
    fun `parsowanie ignoruje nieznane pola opisowe w pliku`() {
        val json = """
            {
              "opis": "coś nieużywanego",
              "aktualizacje_2026": [],
              "wojewodztwa": [
                {
                  "wojewodztwo": "opolskie",
                  "wyroznik_wojewodztwa": ["O"],
                  "jednostki": [
                    {
                      "nazwa": "Opole",
                      "typ": "miasto_na_prawach_powiatu",
                      "wyroznik_powiatu": ["P"],
                      "pelne_kody": ["OP"],
                      "uwaga": "pole nieużywane przez model",
                      "wyroznik_powiatu_2026": ["XX"]
                    }
                  ]
                }
              ]
            }
        """.trimIndent()

        val entries = repository.parse(json)

        assertEquals(1, entries.size)
        assertEquals("OP", entries.first().kod)
    }

    @Test
    fun `parsowanie pelnego pliku plates json daje 683 unikalne wiersze`() {
        val entries = repository.parse(loadRealPlatesJson())

        assertEquals(683, entries.size)
        assertEquals(683, entries.map { it.kod }.toSet().size)
        assertEquals(16, entries.map { it.wojewodztwo }.toSet().size)
    }
}
