package utils.fixtures

import domain.valueobjects.APropertiesPagination
import domain.valueobjects.PropertyDetail
import domain.valueobjects.PropertySearchResult
import it.skrape.core.htmlDocument
import it.skrape.selects.Doc
import utils.loadResource


inline fun <reified T> T.defaultSearchResults(): Pair<Doc, List<PropertySearchResult>> {
    return Pair(htmlDocument(loadResource("/aproperties-search-result.txt")), Fixtures.defaultSearchResults)
}

inline fun <reified T> T.defaultSearchResultsWithPagination() =
    Triple(
        htmlDocument(loadResource("/aproperties-search-result.txt")),
        Fixtures.defaultSearchResults,
        Fixtures.defaultPagination
    )

inline fun <reified T> T.singleSearchResultWithMissingData() =
    Pair(
        htmlDocument(loadResource("/aproperties-single-search-result-missing-data.txt")),
        Fixtures.singleSearchResultWithMissingData
    )

inline fun <reified T> T.emptySearchResult() = htmlDocument(loadResource("/aproperties-empty-search-result.txt"))

inline fun <reified T> T.invalidSearchResult() =
    Pair(
        htmlDocument(loadResource("/aproperties-invalid-search-result.txt")),
        null
    )

inline fun <reified T> T.defaultPropertyDetail() =
    Pair(
        htmlDocument(loadResource("/aproperties-property-detail.txt")),
        Fixtures.defaultPropertyDetail
    )

inline fun <reified T> T.propertyDetailMissingData() =
    Pair(
        htmlDocument(loadResource("/aproperties-property-detail-missing-data.txt")),
        Fixtures.propertyDetailMissingData
    )

inline fun <reified T> T.badPropertyDetailMissing() = htmlDocument(loadResource("/aproperties-bad-property-detail.txt"))

object Fixtures {
    const val searchUrl = "https://www.aproperties.es/search?searchbar=1&mod=rental&zone=3&area=7&group=&loc=42&dis="

    const val propertyUrl = "https://www.aproperties.es/valencia/arrancapins/av2109083-piso-de-alquiler-de-una-habitacion-en-calle-xativa"

    val defaultSearchResults = listOf(
        PropertySearchResult(
            reference = "AV2109033",
            price = 2000.0,
            title = "Piso en alquiler de dos dormitorios junto a Plaza de la Reina",
            location = "San Francesc, Valencia ciudad",
            surface = "120m2",
            dormCount = 2,
            description = "Piso de dos dormitorios de diseño moderno con las mejores calidades y materiales del mercado. Recientemente reformado en un edificio clásico de Sant Francesc, junto a la milla de oro y muy próximo a la Plaza del Ayuntamiento, Plaza de la Reina y Calle de la Paz. Completamente equipado, amplio y muy luminoso. La vivienda cuenta con un salón-comedor con ventanales, cocina independiente completamente equipada, dos dormitorios, dos baños, uno de ellos en suite y vestidor. El piso dispone de aire acondicionado por conductos frío-calor, se presenta completamente equipado y amueblado, con un diseño exquisito, listo para entrar a vivir. Ventanales de climalit que otorgan a esta fabulosa vivienda de más comodidad y silencio. Esta exquisita vivienda se encuentra situada en uno de los puntos más demandados de Valencia, con servicios públicos a escasos metros, y con todo tipo de edificios gubernamentales que podemos encontrar a unos pasos, además de las tiendas más elegantes de la ciudad, así como centros comerciales y zonas de ocio.",
            bathCount = 2,
            imageUrl = "https://www.aproperties.es/aproperties-search-result_files/thumb(27)",
            tag = "",
            propertyUrl = "https://www.aproperties.es/valencia/san-francesc/piso-en-alquiler-de-dos-dormitorios-junto-a-plaza-de-la-reina",
        ),
        PropertySearchResult(
            reference = "AV2109032",
            price = 900.0,
            title = "Piso el alquiler reformado junto a Gran Vía",
            location = "Ruzafa, Valencia ciudad",
            surface = "115m2",
            dormCount = 3,
            description = "DISPONIBLE A PARTIR 1 DE OCTUBRE Ponemos a su disposición, en la demandada zona de Ruzafa, esta vivienda semi-reformada y sin amueblar. La propiedad cuenta con un amplio y luminoso salón-comedor, una cocina completamente equipada, tres dormitorios, uno de ellos con un amplio vestidor, y dos baños completos. Además, la vivienda dispone de AA frío/calor. Ubicada frente al Parque Central y muy cercana al centro de la ciudad. En la zona encontraremos comercios de toda índole, supermercados, farmacias, colegios y centro de salud. Una ocasión excepcional en uno de los barrios mas demandados de València. Contacte con nosotros y estaremos encantados de ampliarle cuanta información precise.",
            bathCount = 2,
            imageUrl = "https://www.aproperties.es/aproperties-search-result_files/thumb(28)",
            tag = "",
            propertyUrl = "https://www.aproperties.es/valencia/ruzafa/piso-el-alquiler-reformado-junto-a-gran-via"
        ),
        PropertySearchResult(
            reference = "AV2109027",
            price = 1100.0,
            title = "Ático en alquiler de 2 habitaciones en Marina Alta",
            location = "Sant Pau, Valencia ciudad",
            surface = "81m2",
            dormCount = 2,
            description = "Ático en alquiler, super bien decorado, todo exterior, dispone de dos habitaciones y dos baños uno con bañera y otro con ducha, con ventana exterior. La viviendad dispone de dos preciosas terrazas, una de ellas con vistas al Residencial, a los jardines, al parque infantil y la piscina, dispone de riego por goteo para las plantas y un gran toldo automatico, con celula de recogida por viento y por falta de sol, otra de las terrazas se accede por la cocina y por la segunda habitación, esta orientada al Este para dar servicio a la cocina y a la zona para tender ropa, en donde también se ha hecho un Trastero para la vivienda. La vivienda tiene muy bien aprovechado el espacio, la cocina esta completamente equipada y con armariada completa desde arriba abajo, incluso tiene una barra de desayuno con dos taburetes y dos vitrinas. Dispone de grifo de agua de Osmiosis inversa. Tiene incluida una plaza de parking y un trastero en el sotano del edificio. Las dos habitaciones son exteriores, además de que disponen de doble encristalamiento en forma de esquina, es una vivienda super luminosa y muy acogedora. Esta en una de las mejores zonas de Valencia, la Marina Alta es uno de residenciales más prestigiosos. Si quieres visitar, estaré encantada en mostrartelo.",
            bathCount = 2,
            imageUrl = "https://www.aproperties.es/aproperties-search-result_files/thumb(29)",
            tag = "Alquilada",
            propertyUrl = "https://www.aproperties.es/valencia/sant-pau/atico-en-alquiler-de-2-habitaciones-en-marina-alta"
        ),
        PropertySearchResult(
            reference = "AV1711006-1",
            price = 1100.0,
            title = "Vivienda en alquiler de tres habitaciones en La Petxina.",
            location = "La Petxina, Valencia ciudad",
            surface = "120m2",
            dormCount = 3,
            description = "DISPONIBLE A PARTIR DE 15 SEPTIEMBRE En la zona de La Petxina encontramos esta encantadora vivienda completamente amueblada y de diseño y calidad en todas las estancias. La propiedad cuenta con un amplio y luminoso salón-comedor, una cocina completamente equipada, tres dormitorios, uno de ellos con un amplio vestidor, otro en suite y dos baños. Además, la vivienda tiene aire acondicionado por conductos y calefacción. Está muy bien ubicada, cerca del antiguo cauce del río Turia, del centro urbano y de jardines y paseos donde poder practicar diferentes actividades deportivas. No dude en ponerse en contacto con nosotros !",
            bathCount = 2,
            imageUrl = "https://www.aproperties.es/aproperties-search-result_files/thumb(30)",
            tag = "Alquilada",
            propertyUrl = "https://www.aproperties.es/valencia/la-petxina/vivienda-en-alquiler-de-tres-habitaciones-en-la-petxina"
        ),
        PropertySearchResult(
            reference = "AV2109078",
            price = 750.0,
            title = "Piso en alquiler en Primado Reig",
            location = "Benimaclet, Valencia ciudad",
            surface = "60m2",
            dormCount = 2,
            description = "Este fantástico piso se encuentra en la zona de Primado Reig, y cuenta con una superficie de 100 metros cuadrados que queda distribuida en un salón con grandes ventanales y vistas bonitas y despejadas de la ciudad, una cocina muy grande con espacio para una mesa de comedor y equipada con todos los electrodomésticos, un aseo, y dos dormitorios exteriores con armarios empotrados, uno de ellos con cuarto de baño en suite, completo con bañera. La vivienda se encuentra amueblada, es muy luminosa pues se encuentra en un sexto piso, todo exterior, dispone de aire acondicionado dío calor en el salón y en el dormitorio principal, y el edificio cuenta con ascensor. La zona en la que se encuentra es ideal, pues está a un paso de Primado Reig, con líneas de autobús principales, tranvía, metro, estaciones ValenBisi, y también a un paso del Antiguo Cauce del Río, y por ende, del centro de la ciudad de Valencia. A su vez rodeada de todo tipo de servicios, como supermercados, bares, cafeterías, farmacias, etc. Si te interesa visitar este estupendo piso no dudes en contactar con nosotros. Estaremos encantados de enseñartelo. (Se solicitan 3 meses de fianza)",
            bathCount = 2,
            imageUrl = "https://www.aproperties.es/aproperties-search-result_files/thumb(3)",
            tag = "Alquilada",
            propertyUrl = "https://www.aproperties.es/valencia/benimaclet/piso-en-alquiler-en-primado-reig"
        ),
        PropertySearchResult(
            reference = "AV2108039-1",
            price = 1300.0,
            title = "Piso en alquiler de cinco dormitorios SIN AMUEBLAR junto a Plaza Pintor Segrelles",
            location = "Arrancapins, Valencia ciudad",
            surface = "196m2",
            dormCount = 5,
            description = "A unos pasos de la Gran Vía Fernando el Católico, junto a la Finca Roja y en una de las calles más demandadas del barrio de Arrancapins de València se encuentra esta excelente vivienda, totalmente exterior con balcón a la calle y disponible para entrar a vivir a partir de Septiembre de 2021. La distribución de la casa nos lleva desde el recibidor a un habitación idonea para un despacho, zona de noche con tres dormitorios, uno de ellos, el principal, con cuarto de baño completo con bañera en suite. Junto a la zona de noche, se encuentra un pasillo en el que se encuentra, un dormitorio espacioso, cuarto de baño completo con bañera y la zona de día, en la que se encuentra un luminoso y amplio salón-comedor con dos estancias y salida a balcón que le confiere amplitud y luminosidad. Dispone de cocina amplia totalmente equipada que cuenta con galería y un aseo amplio de cortesia. La vivienda dispone de aire acondicionado frío-calor por conductos, armarios empotrados y ventanas de climalit. Se alquila sin muebles y cabe la posibilidad de alquilar plaza de garage en el mismo edificio por un precio adicional. Este precioso piso se situa en el barrio de Arrancapins, una de las zonas más demandadas de la ciudad de Valencia, por su perfecta ubicación, dispone de todos los servicios en el mismo barrio, colegios, comercios, supermercados, paradas de metro y autobús, parques, ... etc junto a los jardines del antiguo cauce del Río Turia.",
            bathCount = 3,
            imageUrl = "https://www.aproperties.es/aproperties-search-result_files/thumb(31)",
            tag = "Alquilada",
            propertyUrl = "https://www.aproperties.es/valencia/arrancapins/piso-en-alquiler-de-cinco-dormitorios-sin-amueblar-junto-a-plaza-pintor-segrelles"
        ),
        PropertySearchResult(
            reference = "AV2101119",
            price = 825.0,
            title = "**Alquiler '**'' de apartamento de 2 habitaciones en Avenida del Cid",
            location = "Arrancapins, Valencia ciudad",
            surface = "70m2",
            dormCount = 2,
            description = "Bonito apartamento a estrenar de 2 habitaciones y 1 baño, totalmente reformado, CON 100 EUROS MÁS INCLUYE TODOS LOS SUMINISTROS. Al entrar en este piso, vemos un amplio salón con chimenea y cocina americana, totalmente amueblado y de diseño, con vistas a una increíble terraza para poder disfrutar del aire libre, continuamos la visita y tenemos un amplio baño con ducha y una habitación amplia con armariada,continuamos y entramos en la habitación principal con espacio para poder teletrabajar o estudiar y tiene salida a la terraza y un magnífico vestidor. Una zona muy bien comunicada con todos los servicios a mano, muy cercana a la Plaza de España y al centro de la ciudad. Si desea más información sobre este inmueble no dude en contactar con nosotros.",
            bathCount = 1,
            imageUrl = "https://www.aproperties.es/aproperties-search-result_files/thumb(32)",
            tag = "Nuevo",
            propertyUrl = "https://www.aproperties.es/valencia/arrancapins/**alquiler-**-de-apartamento-de-2-habitaciones-en-avenida-del-cid"
        ),
        PropertySearchResult(
            reference = "AV2106020",
            price = 2600.0,
            title = "Alquiler de vivienda con vistas en Colón",
            location = "Pla del remei, Valencia ciudad",
            surface = "250m2",
            dormCount = 4,
            description = "Espectacular piso de cuatro habitaciones y tres baños ubicado en cuarta planta y situado en uno de los puntos más solicitados de la ciudad de València. Con una superficie de 250 m2, se distribuye en cuatro amplios dormitorios. La zona social se compone de un amplio salón comedor y con acceso a la cocina de diseño completamente equipada. La vivienda dispone de tres baños, dos independientes y otro in suite y están completos. El edificio, fue reconstruido totalmente en 2008, conservándose sólo su fachada histórica, por lo que estamos ante una vivienda nueva y se han empleado materiales de gran calidad (suelo de parqué, puertas correderas, puerta de entrada acorazada, armarios empotrados). Se alquila sin amueblar. Se encuentra ubicado en la zona de Pla del Remei, en la conocida y comercial calle Colón. Emplazamiento único por su proximidad a las mejores zonas comerciales, de ocio y restauración de la ciudad y al jardín del Río Turia. Cuenta con hoteles cercanos, teatros (Teatro Rialto), salas de cine (Cines ABC Park), centros comerciales y colegios. Si desea visitar esta amplia vivienda, contacte con nosotros, estaremos encantados de atenderle.",
            bathCount = 3,
            imageUrl = "https://www.aproperties.es/aproperties-search-result_files/thumb(33)",
            tag = "",
            propertyUrl = "https://www.aproperties.es/valencia/pla-del-remei/alquiler-de-vivienda-con-vistas-en-colon"
        ),
        PropertySearchResult(
            reference = "AV2109048",
            price = 750.0,
            title = "Loft en alquiler en Patraix",
            location = "Patraix - Vara de Quart, Valencia ciudad",
            surface = "60m2",
            dormCount = 1,
            description = "Ubicado en zona de expansión, en el compleo residencial Ciudad Gran Turia, se encuentra este precioso dúplex tipo Loft de 85m² repartidos en dos alturas. La vivienda está completamente amueblada con muebles de diseño y guardando una línea vanguardista, con líneas blancas y neutras. La planta baja la compone un cuarto de baño con ducha, cocina completamente equipada integrada en el salón que nos da acceso a una amplio balcón. Con los techos altos tenemos gran luminosidad y espacio. En la planta primera se encuentra el dormitorio con cama de matrimonio. La vivienda completamente amueblada, siendo todos los muebles de diseño, tiene suelos de parqué, televisión de plasma, wifi incluido en el precio, varios sofás (sofá/cama), calefacción y aire acondicionado, además incluye 1 plaza de aparcamiento. La zona está consolidada y dispone de todos los servicios. Muy cerca del centro comercial Gran Turia con todo tipo de comercios, cines, supermercado. Con buen acceso a la autovía V30, a 8km del Aeropuerto de València y a 10 minutos del centro de la ciudad. Si lo que busca es un diseño moderno en un entorno tranquilo este piso le encantará. En definitiva, estamos ante una interesante y singular vivienda que se puede disfrutar todo el año. Si está interesado en visitar esta propeidad, no dude en ponerse en contacto con nosotros, estaremos encantados de atenderle.",
            bathCount = 1,
            imageUrl = "https://www.aproperties.es/aproperties-search-result_files/thumb(20)",
            tag = "Reservada",
            propertyUrl = "https://www.aproperties.es/valencia/patraix-vara-de-quart/av2109048-loft-en-alquiler-en-patraix"
        ),
        PropertySearchResult(
            reference = "AV1909034-2",
            price = 1900.0,
            title = "Piso de alquiler TEMPORAL de 2 habitaciones, en Playa Patacona.",
            location = "Patacona, Valencia ciudad",
            surface = "92m2",
            dormCount = 2,
            description = "SOLO ALQUILER TEMPORAL. Amplia y cuidada vivienda de 2 habitaciones y 2 baños, en un complejo con piscina, vistas al mar y mucha luz, disponible en alquiler de Octubre a Abril, en la playa de la Patacona. Se trata de una vivienda amplia,con una superficie de 93 m2 construidos, completamente amueblada y equipada. Con un gran salón y terraza, luminosa y completamente exterior. Construida en el año 2014. Incluye garaje. Dispone de 2 dormitorios ( 1 con cama de matrimonio y otra individual ), dos baños completos, una moderna cocina equipada con una amplia galería y un gran salón con salida a la terraza. Dispone de parqué, aire acondicionado por conductos frío- calor, pintura lisa, videoportero, armarios empotrados, acceso personas de movilidad reducida y excelente aislamiento con ventanas climalit. La vivienda se encuentra en complejo privado con piscina, parque infantil, conserje y cámaras de seguridad y vigilancia, situado a escasos metros de la playa. Zona con todos los servicios, supermercados, restaurantes, farmacia, centro de salud, escuela infantil, colegios y calles peatonales con parques y zonas de juego, además de un centro deportivo con piscina y gimnasio. A 10 minutos andando de las universidades y solo 15 minutos en coche al centro de València. Muy buena comunicación con varias lineas de autobús al centro de la ciudad. ¡ No dude en contactarnos !",
            bathCount = 2,
            imageUrl = "https://www.aproperties.es/aproperties-search-result_files/thumb(34)",
            tag = "",
            propertyUrl = "https://www.aproperties.es/valencia/patacona/piso-de-alquiler-temporal-de-2-habitaciones-en-playa-patacona"
        ),
    )

    val defaultPagination = APropertiesPagination(6)

    val singleSearchResultWithMissingData = listOf(
        PropertySearchResult(
            reference = "AV1909034-2",
            price = 1900.0,
            title = "Piso de alquiler TEMPORAL de 2 habitaciones, en Playa Patacona.",
            location = "Patacona, Valencia ciudad",
            surface = null,
            dormCount = null,
            description = "SOLO ALQUILER TEMPORAL. Amplia y cuidada vivienda de 2 habitaciones y 2 baños, en un complejo con piscina, vistas al mar y mucha luz, disponible en alquiler de Octubre a Abril, en la playa de la Patacona. Se trata de una vivienda amplia,con una superficie de 93 m2 construidos, completamente amueblada y equipada. Con un gran salón y terraza, luminosa y completamente exterior. Construida en el año 2014. Incluye garaje. Dispone de 2 dormitorios ( 1 con cama de matrimonio y otra individual ), dos baños completos, una moderna cocina equipada con una amplia galería y un gran salón con salida a la terraza. Dispone de parqué, aire acondicionado por conductos frío- calor, pintura lisa, videoportero, armarios empotrados, acceso personas de movilidad reducida y excelente aislamiento con ventanas climalit. La vivienda se encuentra en complejo privado con piscina, parque infantil, conserje y cámaras de seguridad y vigilancia, situado a escasos metros de la playa. Zona con todos los servicios, supermercados, restaurantes, farmacia, centro de salud, escuela infantil, colegios y calles peatonales con parques y zonas de juego, además de un centro deportivo con piscina y gimnasio. A 10 minutos andando de las universidades y solo 15 minutos en coche al centro de València. Muy buena comunicación con varias lineas de autobús al centro de la ciudad. ¡ No dude en contactarnos !",
            bathCount = null,
            imageUrl = "https://www.aproperties.es/aproperties-search-result_files/thumb(34)",
            tag = "",
            propertyUrl = "https://www.aproperties.es/valencia/patacona/piso-de-alquiler-temporal-de-2-habitaciones-en-playa-patacona"
        )
    )

    val defaultPropertyDetail = PropertyDetail(
        reference = "AV2109083",
        videoUrl = "https://player.vimeo.com/video/510636212",
        fullDescription = "Fabulosa vivienda ubicada en calle Xàtiva en pleno centro y corazón de la ciudad. Totalmente amueblado, en una finca residencial con tres ascensores y servicio de Portería. Distribuido en un amplio comedor, con terraza que da a la calle Xátiva con unas vistas espectaculares de la ciudad, habitación amplia y con armarios empotrados, cuarto de baño completo, cocina equipada con electrodomésticos, totalmente amueblado. los muebles pueden variar , Equipado con aire acondicionado. No dude en ponerse en contacto con nosotros, estremos encantados de atenderle.",
        locationDescription = "",
        characteristics = listOf("Calefacción", "Amueblado", "Aacc", "Terraza", "Ascensor", "Conserje"),
        photosGalleryUrls = listOf(
            "https://www.aproperties.es/aproperties-property-detail_files/31800_1632323417028.jpg",
            "https://www.aproperties.es/aproperties-property-detail_files/31800_1632323417065.jpg",
            "https://www.aproperties.es/aproperties-property-detail_files/31800_16323234170252.jpg",
            "https://www.aproperties.es/aproperties-property-detail_files/31800_16323234170444.jpg",
            "https://www.aproperties.es/aproperties-property-detail_files/31800_1632323417058.jpg",
            "https://www.aproperties.es/aproperties-property-detail_files/31800_16323234170475.jpg",
            "https://www.aproperties.es/aproperties-property-detail_files/31800_16323234170513.jpg",
            "https://www.aproperties.es/aproperties-property-detail_files/31800_1632323417032.jpg",
            "https://www.aproperties.es/aproperties-property-detail_files/31800_16323234170399.jpg",
            "https://www.aproperties.es/aproperties-property-detail_files/31800_16323234170618.jpg",
            "https://www.aproperties.es/aproperties-property-detail_files/31800_16323234170359.jpg",
            "https://www.aproperties.es/aproperties-property-detail_files/31800_16323234170541.jpg",
            "https://www.aproperties.es/aproperties-property-detail_files/31800_16323234170214.jpg",
        ),
        lat = 39.4676,
        lng = -0.378454,
        pdfUrl = "https://www.aproperties.es/pdf/properties/es/av2109083.pdf"
    )

    val propertyDetailMissingData = PropertyDetail(
        reference = "AV2109083",
        videoUrl = "https://player.vimeo.com/video/510636212",
        fullDescription = "Fabulosa vivienda ubicada en calle Xàtiva en pleno centro y corazón de la ciudad. Totalmente amueblado, en una finca residencial con tres ascensores y servicio de Portería. Distribuido en un amplio comedor, con terraza que da a la calle Xátiva con unas vistas espectaculares de la ciudad, habitación amplia y con armarios empotrados, cuarto de baño completo, cocina equipada con electrodomésticos, totalmente amueblado. los muebles pueden variar , Equipado con aire acondicionado. No dude en ponerse en contacto con nosotros, estremos encantados de atenderle.",
        locationDescription = "",
        characteristics = listOf("Calefacción", "Amueblado", "Aacc", "Terraza", "Ascensor", "Conserje"),
        photosGalleryUrls = listOf(
            "https://www.aproperties.es/aproperties-property-detail_files/31800_1632323417028.jpg",
            "https://www.aproperties.es/aproperties-property-detail_files/31800_1632323417065.jpg",
            "https://www.aproperties.es/aproperties-property-detail_files/31800_16323234170252.jpg",
            "https://www.aproperties.es/aproperties-property-detail_files/31800_16323234170444.jpg",
            "https://www.aproperties.es/aproperties-property-detail_files/31800_1632323417058.jpg",
            "https://www.aproperties.es/aproperties-property-detail_files/31800_16323234170475.jpg",
            "https://www.aproperties.es/aproperties-property-detail_files/31800_16323234170513.jpg",
            "https://www.aproperties.es/aproperties-property-detail_files/31800_1632323417032.jpg",
            "https://www.aproperties.es/aproperties-property-detail_files/31800_16323234170399.jpg",
            "https://www.aproperties.es/aproperties-property-detail_files/31800_16323234170618.jpg",
            "https://www.aproperties.es/aproperties-property-detail_files/31800_16323234170359.jpg",
            "https://www.aproperties.es/aproperties-property-detail_files/31800_16323234170541.jpg",
            "https://www.aproperties.es/aproperties-property-detail_files/31800_16323234170214.jpg",
        ),
        lat = null,
        lng = null,
        pdfUrl = "https://www.aproperties.es/pdf/properties/es/av2109083.pdf"
    )


}

