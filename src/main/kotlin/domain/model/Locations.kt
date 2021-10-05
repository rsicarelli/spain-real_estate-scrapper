package domain.model

data class Location(
    val value: String,
    val geometry: Geometry
)

data class Geometry(
    val lat: Double,
    val lng: Double
)

fun allLocations() {
    listOf(
        Location("El Carme", Geometry(39.479155, -0.3795618)),
        Location("Sant Francesc", Geometry(39.4704734, -0.3804823)),
        Location("Gran Vía", Geometry(39.4657589, -0.371725)),
        Location("Camí Fondo - Penya-Roja", Geometry(39.4645274, -0.3549855)),
        Location("Ciutat de les Arts i les Ciències", Geometry(39.4520311, -0.3478163)),
        Location("Exposició", Geometry(39.4760449, -0.3682044)),
        Location("Pla del Remei", Geometry(39.4687974, -0.3739549)),
        Location("La Seu", Geometry(39.4761055, -0.3776623)),
        Location("Sant Pau", Geometry(39.4845487, -0.4300689)),
        Location("Ciutat Jardi", Geometry(39.4721124, -0.3489417)),
        Location("Extramurs", Geometry(39.4685891, -0.4044086)),
        Location("Benicalap", Geometry(39.4937951, -0.4000469)),
        Location("El Mercat", Geometry(39.4722485, -0.37823)),
    )
}

//12 = " El Mercat"
//13 = " Campanar"
//14 = " El Cabanyal - El Canyamelar"
//15 = " Ayora"
//16 = " L'Amistat"
//17 = " Viveros"
//18 = " Botánico"
//19 = " Mestalla"
//20 = " Ruzafa"
//21 = " Arrancapins"
//22 = " La Petxina"
//23 = " Ciudad de la Justicia"
//24 = " La Creu Coberta"
//25 = " La Roqueta"
//26 = " La Creu del Grau"
//27 = " Ciudad de las Ciencias"
//28 = " La Xerea"
//29 = " Ciutat Vella"
//30 = " Morvedre"
//31 = " El Pilar"
//32 = " Mislata"
//33 = " Els Orriols"
//34 = " Jesús"
//35 = " Jaume Roig"
//36 = " Les Tendetes"
//37 = " Nou Moles"
//38 = " Nou Campanar"
//39 = " Ciutat Universitària"
//40 = " Sant Llorenç"
//41 = " Patraix"
//42 = " Pla del Real"
//43 = " Marxalenes"
//44 = " La Raiosa"
//45 = " En Corts"
//46 = " La Vega Baixa"
//47 = " Vara de Quart"
//48 = " Nou Benicalap"