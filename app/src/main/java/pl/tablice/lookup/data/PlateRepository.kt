package pl.tablice.lookup.data

import android.content.Context
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

class PlateRepository {

    private val json = Json { ignoreUnknownKeys = true }

    /**
     * Czysta funkcja parsująca — testowalna bez kontekstu Androida (JUnit/JVM).
     * Każdy kod z "pelne_kody" danej jednostki staje się osobnym wierszem [PlateEntry].
     */
    fun parse(jsonText: String): List<PlateEntry> {
        val file = json.decodeFromString<PlatesFileDto>(jsonText)
        return file.wojewodztwa.flatMap { woj ->
            woj.jednostki.flatMap { jednostka ->
                jednostka.pelne_kody.map { kod ->
                    PlateEntry(
                        kod = kod,
                        nazwaJednostki = jednostka.nazwa,
                        wojewodztwo = woj.wojewodztwo,
                        typJednostki = jednostka.typ
                    )
                }
            }
        }
    }

    fun loadFromAssets(context: Context): List<PlateEntry> {
        val text = context.assets.open(ASSET_FILE_NAME).bufferedReader().use { it.readText() }
        return parse(text)
    }

    companion object {
        const val ASSET_FILE_NAME = "plates.json"
    }
}
