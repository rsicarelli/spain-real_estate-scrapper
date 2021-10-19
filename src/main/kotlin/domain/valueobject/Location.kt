package domain.entity

import domain.entity.LocationMapper.Valencia

data class Location(
    val lat: Double,
    val lng: Double,
    val name: String,
    val isApproximated: Boolean,
    val isUnknown: Boolean
) {
    fun fixLocation(): Location {
        val locationMappers = LocationMapper::class.nestedClasses.map { it.objectInstance as LocationMapper }
        val filtered = locationMappers.filter {
            this.name == it.name || it.variants.contains(this.name)
        }

        filtered.firstOrNull()?.let {
            return this.copy(
                name = it.name,
                lat = it.lat,
                lng = it.lng,
                isApproximated = true,
                isUnknown = false
            )
        } ?: return this.copy(
            name = this.name,
            lat = Valencia.lat,
            lng = Valencia.lng,
            isApproximated = true,
            isUnknown = true
        )
    }

    companion object {
        fun fromLatLng(name: String, lat: Double?, lng: Double?): Location {
            return if (lat == null || lng == null) {
                Location(
                    name = name,
                    lat = 0.0,
                    lng = 0.0,
                    isApproximated = true,
                    isUnknown = false
                )
            } else {
                Location(
                    name = name,
                    lat = lat,
                    lng = lng,
                    isApproximated = false,
                    isUnknown = false
                )
            }
        }
    }
}


@Suppress("SpellCheckingInspection", "unused")
sealed class LocationMapper(val name: String, val lat: Double, val lng: Double, val variants: List<String>) {
    object LaPetxina : LocationMapper("La Petxina", 39.4739599, -0.3958677, variants = emptyList())
    object SantFrancesc : LocationMapper("Sant Francesc", 39.4704735, -0.3782936, variants = listOf("San Francesc"))
    object ElPilar : LocationMapper(name = "El Pilar", 39.473362, -0.3841066, variants = emptyList())
    object PlaDelRemei :
        LocationMapper(name = "Pla del remei", 39.4687974, -0.3717662, variants = listOf("Pla del Remei"))

    object Malvarrosa : LocationMapper(name = "Malvarrosa", 39.4802394, -0.3323593, variants = emptyList())
    object Campanar : LocationMapper(name = "Campanar", 39.4811009, -0.3986108, variants = listOf("Nou Campanar"))
    object CiutatUniversitaria : LocationMapper(
        name = "Ciutat Universitaria",
        39.4785574,
        -0.361154,
        variants = listOf("Ciutat Universitària", "Ciutat Universitária")
    )

    object CiudadDeLaJusticia :
        LocationMapper(name = "Ciudad de la Justicia", 39.4558122, -0.3630757, variants = emptyList())

    object Patacona : LocationMapper(name = "Patacona", 39.4885004, -0.3296019, variants = emptyList())
    object Ruzafa : LocationMapper(name = "Ruzafa", 39.461399, -0.3760729, variants = emptyList())
    object LaXerea : LocationMapper(name = "La Xerea", 39.474973, -0.371657, variants = emptyList())
    object CiudadDeLasCiencias : LocationMapper(
        name = "Ciudad de las Ciencias",
        39.460131,
        -0.350868,
        variants = listOf("Ciudad de las Ciencias", "Penya Roja", "Ciutat de les Arts i les Ciències")
    )

    object GranVia : LocationMapper(name = "Gran Vía", 39.4657589, -0.371725, variants = listOf("Gran Via"))
    object LaSeu : LocationMapper(name = "La Seu", 39.476734, -0.375341, variants = emptyList())
    object Exposicio : LocationMapper(name = "Exposició", 39.475973, -0.364409, variants = listOf("Exhibition"))
    object ElCabanal : LocationMapper(
        name = "El Cabañal",
        39.4622111,
        -0.3318331,
        variants = listOf(
            "El Cabañal - El Grau",
            "El Cabanal - El Grau",
            "El Cabanal",
            "El Cabanyal",
            "Cabanyal",
            "El Cabanyal - El Canyamelar",
        )
    )

    object Botanico :
        LocationMapper(
            name = "Botánico",
            39.475293,
            -0.387144,
            variants = listOf("El Botànic", "Botanico", "El Botanic")
        )

    object VaraDeQuart :
        LocationMapper(name = "Vara de Quart", 39.459614, -0.406563, variants = listOf("Patraix - Vara de Quart"))

    object Valencia : LocationMapper(name = "Valencia", 39.4686151, -0.3771574, variants = emptyList())
    object Arrancapins : LocationMapper(name = "Arrancapins", 39.465259, -0.384252, variants = emptyList())
    object Trinitat : LocationMapper(name = "Trinitat", 39.481904, -0.369303, variants = emptyList())
    object LaRoqueta : LocationMapper(name = "La Roqueta", 39.466529, -0.379936, variants = emptyList())
    object ElCarme : LocationMapper(name = "El Carme", 39.4787026, -0.3816449, variants = emptyList())
    object Alfahuir : LocationMapper(name = "Alfahuir", 39.491337, -0.3658403, variants = emptyList())
    object Patraix : LocationMapper(name = "Patraix", 39.462898, -0.391534, variants = emptyList())
    object Ayora : LocationMapper(name = "Ayora", 39.465777, -0.343234, variants = emptyList())
    object ElMercat : LocationMapper(name = "El Mercat", 39.474189, -0.378659, variants = listOf("Mercat"))
    object Jesus : LocationMapper(name = "Jesús", 39.447624, -0.390788, variants = listOf("Jesus"))
    object Monteolivete :
        LocationMapper(name = "Monteolivete", 39.459101, -0.364345, variants = listOf("Monte olivete"))

    object LIllaPerduda :
        LocationMapper(
            name = "L'Illa Perduda",
            39.468758,
            -0.340010,
            variants = listOf("LIlla Perduda", "L Illa Perduda")
        )

    object Mestalla : LocationMapper(name = "Mestalla", 39.471593, -0.358375, variants = emptyList())
    object LesTendetes : LocationMapper(name = "Les Tendetes", 39.482738, -0.385959, variants = emptyList())
    object SantPau : LocationMapper(name = "Sant Pau", 39.487963, -0.404549, variants = emptyList())
    object Marxalenes : LocationMapper(name = "Marxalenes", 39.4855502, -0.3846273, variants = emptyList())
    object Benicalap : LocationMapper(name = "Benicalap", 39.493408, -0.392233, variants = listOf("Nou Benicalap"))
    object Benimaclet : LocationMapper(name = "Benimaclet", 39.484914, -0.358410, variants = emptyList())
    object NouMoles : LocationMapper(
        name = "Nou Moles",
        39.471679,
        -0.399749,
        variants = listOf("Nou Moles - L'Olivereta", "Nou Moles - L Olivereta", "Nou Moles - LOlivereta")
    )

    object CamiFondo : LocationMapper(
        name = "Camí Fondo",
        39.4642365,
        -0.3542723,
        variants = listOf("Camí Fondo - Penya-Roja", "Cami Fondo - Penya-Roja")
    )

    object LaCreuDelGrau : LocationMapper(name = "La Creu del Grau", 39.4612847, -0.347012, variants = emptyList())
    object CiutatJardi : LocationMapper(name = "Ciutat Jardí", 39.472176, -0.345254, variants = listOf("Ciutat Jardi"))
    object PlaDelReal : LocationMapper(name = "Pla del Real", 39.472361, -0.356378, variants = emptyList())
    object Morvedre : LocationMapper(name = "Morvedre", 39.4830985, -0.3769443, variants = emptyList())
    object LAmistat : LocationMapper(name = "L'Amistat", 39.472572, -0.350871, variants = listOf("LAmistat", "Amistat"))
    object EnCorts : LocationMapper(name = "En Corts", 39.455979, -0.370804, variants = emptyList())
    object CiutatVella : LocationMapper(name = "Ciutat Vella", 39.475249, -0.376386, variants = emptyList())
    object Mislata : LocationMapper(name = "Mislata", 39.473902, -0.417261, variants = emptyList())
    object SantLlorenc : LocationMapper(
        name = "Sant Llorenç",
        39.496210,
        -0.363988,
        variants = listOf("Sant Llorenc", "San Llorenç", "San Llorenc")
    )

    object LaRaiosa : LocationMapper(name = "La Raiosa", 39.4572005, -0.3863188, variants = emptyList())
    object Viveros : LocationMapper(
        name = "Jardines de Viveros",
        39.4799792,
        -0.3650756,
        variants = listOf("Viveros", "Vivero", "Jardines Del Real")
    )

    object LaCreuCoberta : LocationMapper(name = "La Creu Coberta", 39.451170, -0.385465, variants = emptyList())
    object Extramurs : LocationMapper(name = "Extramurs", 39.471881, -0.385932, variants = emptyList())
    object SantMarcelli : LocationMapper(
        name = "Sant Marcellí",
        39.4448952,
        -0.3925439,
        variants = listOf("Sant Marcelli", "San Marcellí", "San Marcelli")
    )

    object ElsOrriols : LocationMapper(name = "Els Orriols", 39.493334, -0.370443, variants = emptyList())
    object SantAntoni : LocationMapper(name = "Sant Antoni", 39.488731, -0.373182, variants = emptyList())
}