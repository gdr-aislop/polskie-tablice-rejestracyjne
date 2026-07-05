package pl.tablice.lookup.data

import kotlinx.serialization.Serializable

/**
 * Modele odwzorowujące dokładnie strukturę pliku assets/plates.json
 * (klucz "wojewodztwa" -> lista województw -> lista "jednostki").
 * Pola opisowe pliku (opis, zrodlo_*, uwagi, aktualizacje_2026) są pomijane
 * przez ignoreUnknownKeys, bo nie są potrzebne do wyszukiwania.
 */
@Serializable
data class PlatesFileDto(
    val wojewodztwa: List<WojewodztwoDto>
)

@Serializable
data class WojewodztwoDto(
    val wojewodztwo: String,
    val wyroznik_wojewodztwa: List<String>,
    val jednostki: List<JednostkaDto>
)

@Serializable
data class JednostkaDto(
    val nazwa: String,
    val typ: String,
    val wyroznik_powiatu: List<String>,
    val pelne_kody: List<String>
)
