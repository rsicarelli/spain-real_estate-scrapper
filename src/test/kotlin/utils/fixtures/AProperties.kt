package utils.fixtures

import domain.model.Pagination
import domain.model.PropertyDetail
import domain.model.PropertyItem
import domain.model.PropertySearchResult
import it.skrape.core.htmlDocument
import it.skrape.selects.Doc
import utils.loadResource

object AProperties {
    inline fun <reified T> T.defaultSearchResults(): Pair<Doc, PropertySearchResult> {
        return Pair(
            htmlDocument(loadResource("/aproperties/aproperties-search-result.txt")),
            Fixtures.defaultSearchResults
        )
    }

    //
//    inline fun <reified T> T.defaultSearchResultsWithPagination() =
//        Triple(
//            htmlDocument(loadResource("/aproperties/aproperties-search-result.txt")),
//            Fixtures.defaultSearchResults,
//            Fixtures.defaultPagination
//        )
//
    inline fun <reified T> T.singleSearchResultWithMissingData() =
        Pair(
            htmlDocument(loadResource("/aproperties/aproperties-single-search-result-missing-data.txt")),
            Fixtures.singleSearchResultWithMissingData
        )

    inline fun <reified T> T.emptySearchResult() =
        htmlDocument(loadResource("/aproperties/aproperties-empty-search-result.txt"))

    inline fun <reified T> T.invalidSearchResult() =
        Pair(
            htmlDocument(loadResource("/aproperties/aproperties-invalid-search-result.txt")),
            null
        )

    inline fun <reified T> T.defaultPropertyDetail() =
        Pair(
            htmlDocument(loadResource("/aproperties/aproperties-property-detail.txt")),
            Fixtures.defaultPropertyDetail
        )

    inline fun <reified T> T.propertyDetailMissingData() =
        Pair(
            htmlDocument(loadResource("/aproperties/aproperties-property-detail-missing-data.txt")),
            Fixtures.propertyDetailMissingData
        )

    inline fun <reified T> T.badPropertyDetailMissing() =
        htmlDocument(loadResource("/aproperties/aproperties-bad-property-detail.txt"))

    object Fixtures {
        const val searchUrl =
            "https://www.aproperties.es/search?searchbar=1&mod=rental&zone=3&area=7&group=&loc=42&dis="

        const val propertyUrl =
            "https://www.aproperties.es/valencia/arrancapins/av2109083-piso-de-alquiler-de-una-habitacion-en-calle-xativa"

        val defaultPagination = Pagination(
            154,
            listOf(
                "https://www.aproperties.es/en/search?searchbar=1&mod=rental&zone=3&area=7&group=&loc=42&dis=&p=2",
                "https://www.aproperties.es/en/search?searchbar=1&mod=rental&zone=3&area=7&group=&loc=42&dis=&p=3",
                "https://www.aproperties.es/en/search?searchbar=1&mod=rental&zone=3&area=7&group=&loc=42&dis=&p=4",
                "https://www.aproperties.es/en/search?searchbar=1&mod=rental&zone=3&area=7&group=&loc=42&dis=&p=5",
                "https://www.aproperties.es/en/search?searchbar=1&mod=rental&zone=3&area=7&group=&loc=42&dis=&p=6"
            )
        )

        val defaultSearchResults =
            PropertySearchResult(
                pagination = defaultPagination,
                items = listOf(
                    PropertyItem(
                        reference = "AV2109076",
                        price = 3000.0,
                        title = "Spectacular stately home in the Alameda area",
                        location = "Exposició, Valencia city",
                        surface = "315m2",
                        dormCount = 3,
                        description = "Two steps from the Paseo de la Alameda we find this stately home, with marble and parquet floors, unfurnished. The location of this magnificent property is located in an excellent point between the Alameda and Blasco Ibañez, a few steps from art galleries, exclusive boutiques, the English Court, cinemas, EMT bus lines, Metro and just minutes walk from the street Colón and Xàtiva, the most commercial in Valencia, restaurants, all kinds of bars and entertainment venues. The house has two bedrooms with bathroom en suite and a master bedroom of 70 square meters with sauna and shower, all of stone, dressing room with fitted wardrobes and then the bedroom itself, with its own bathroom with bathtub. In addition to a large living room with access to a comfortable independent balcony, fully air-conditioned, where you can enjoy your relaxing moments after a day of work. For your convenience, it has a large garage. It is a unique opportunity to live in the best area of Valencia two steps from downtown.",
                        bathCount = 5,
                        imageUrl = "https://www.aproperties.es/thumb?src=/media/properties/31723/31723_16321439854418.jpg&w=320&h=240",
                        tag = "",
                        propertyUrl = "https://www.aproperties.es/en/valencia/exposicio/av2109076-spectacular-stately-home-in-the-alameda-area",
                    ),
                    PropertyItem(
                        reference = "AV2010119-1",
                        price = 1450.0,
                        title = "Penthouse for rent with terrace in Pla del remei (Valencia)",
                        location = "Pla del remei, Valencia city",
                        surface = "60m2",
                        dormCount = 2,
                        description = "Brand new 60 sqm penthouse with a 22sqm terrace and views in Pla del remei, Valencia.The property has 2 bedrooms, 1 bathroom, air conditioning, fitted wardrobes, laundry room and balcony.",
                        bathCount = 1,
                        imageUrl = "https://www.aproperties.es/thumb?src=/media/properties/24325/24325_16040633643909.jpg&w=320&h=240",
                        tag = "",
                        propertyUrl = "https://www.aproperties.es/en/valencia/pla-del-remei/av2010119-1-penthouse-for-rent-with-terrace-in-pla-del-remei-valencia"
                    ),
                    PropertyItem(
                        reference = "AV1807026",
                        price = 850.0,
                        title = "Excellent apartment for rent in Plà del Remei Valencia",
                        location = "Pla del remei, Valencia city",
                        surface = "60m2",
                        dormCount = 1,
                        description = "Magnificent bright apartment, recently renovated in Plà del Remei, next to Colón street. It has 1 living room with a small interior terrace. In the same room we find a kitchen, equipped with all appliances. The room is spacious, spacious and independent, has built-in wardrobes, for daily storage. The bathroom is complete with shower tray. The furniture of the apartment is of modern style, with very good taste, recently renovated and with parquet flooring. A hundred euros of supplies are included in the price. In case of exceeding this amount, the price of the monthly payment would increase. The area is ideal for living in the center of Valencia. It has around all kinds of areas to taste the best cuisine in Valencia and the best shopping areas in the city. In addition to having to a step the district of Ruzafa and Ciutat Vella. It has very good communication both by metro, train, bus and valen-bici.",
                        bathCount = 1,
                        imageUrl = "https://www.aproperties.es/thumb?src=/media/properties/9662/9662_16321486425714.jpg&w=320&h=240",
                        tag = "Rented",
                        propertyUrl = "https://www.aproperties.es/en/valencia/pla-del-remei/excellent-apartment-for-rent-in-pla-del-remei-valencia"
                    ),
                    PropertyItem(
                        reference = "AV2009079-1",
                        price = 1200.0,
                        title = "Flat for rent with views in Botánico (Valencia)",
                        location = "Botánico, Valencia city",
                        surface = "151m2",
                        dormCount = 4,
                        description = "151 sqm flat with views in Botánico, Valencia.The property has 4 bedrooms, 3 bathrooms, 1 parking space, air conditioning, fitted wardrobes, heating and concierge.",
                        bathCount = 3,
                        imageUrl = "https://www.aproperties.es/thumb?src=/media/properties/23096/23096_16003371629457.jpg&w=320&h=240",
                        tag = "",
                        propertyUrl = "https://www.aproperties.es/en/valencia/botanico/flat-for-rent-with-views-in-botanico-valencia"
                    ),
                    PropertyItem(
                        reference = "AV1711006-1",
                        price = 1100.0,
                        title = "Charming and furnished apartment in Valencia",
                        location = "La Petxina, Valencia city",
                        surface = "120m2",
                        dormCount = 3,
                        description = "In La Petxina area we find this charming housing completely furnished and of design and quality in all rooms. The property has a spacious and bright living-dining room, a fully equipped kitchen, three bedrooms, one with a large dressing room, another bedroom en-suite and two bathrooms. In addition, the housing has air conditioning and heating. It is very well located, near Turia river, the urban center and gardens and areas where we can practice different sports activities.",
                        bathCount = 2,
                        imageUrl = "https://www.aproperties.es/thumb?src=/media/properties/6713/6713_15904075335777.jpg&w=320&h=240",
                        tag = "Rented",
                        propertyUrl = "https://www.aproperties.es/en/valencia/la-petxina/charming-and-furnished-apartment-in-valencia"
                    ),
                    PropertyItem(
                        reference = "AV2109083",
                        price = 600.0,
                        title = "Flat for rent with terrace in Arrancapins (Valencia)",
                        location = "Arrancapins, Valencia city",
                        surface = "51m2",
                        dormCount = 1,
                        description = "51 sqm flat with terrace in Arrancapins, Valencia.The property has bedroom, 1 bathroom, air conditioning, fitted wardrobes, heating and concierge.",
                        bathCount = 1,
                        imageUrl = "https://www.aproperties.es/thumb?src=/media/properties/31800/31800_1632323417028.jpg&w=320&h=240",
                        tag = "Reserved",
                        propertyUrl = "https://www.aproperties.es/en/valencia/arrancapins/av2109083-flat-for-rent-with-terrace-in-arrancapins-valencia"
                    ),
                    PropertyItem(
                        reference = "AV2109078",
                        price = 750.0,
                        title = "Flat for rent with views in Benimaclet (Valencia)",
                        location = "Benimaclet, Valencia city",
                        surface = "60m2",
                        dormCount = 2,
                        description = "60 sqm flat with views in Benimaclet, Valencia.The property has 2 bedrooms, 1 bathroom, air conditioning, fitted wardrobes and heating.",
                        bathCount = 2,
                        imageUrl = "https://www.aproperties.es/thumb?src=/media/properties/31735/31735_16322135269252.jpg&w=320&h=240",
                        tag = "Rented",
                        propertyUrl = "https://www.aproperties.es/en/valencia/benimaclet/av2109078-flat-for-rent-with-views-in-benimaclet-valencia"
                    ),
                    PropertyItem(
                        reference = "AV2108039-1",
                        price = 1300.0,
                        title = "Flat for rent with views in Arrancapins (Valencia)",
                        location = "Arrancapins, Valencia city",
                        surface = "196m2",
                        dormCount = 5,
                        description = "196 sqm flat with views in Arrancapins, Valencia.The property has 5 bedrooms, 2 bathrooms, parking space, air conditioning, fitted wardrobes, balcony and heating.",
                        bathCount = 3,
                        imageUrl = "https://www.aproperties.es/thumb?src=/media/properties/31189/31189_16304994567208.JPG&w=320&h=240",
                        tag = "Rented",
                        propertyUrl = "https://www.aproperties.es/en/valencia/arrancapins/av2108039-1-flat-for-rent-with-views-in-arrancapins-valencia"
                    ),
                    PropertyItem(
                        reference = "AV2101119",
                        price = 825.0,
                        title = "Loft for rent with terrace in Arrancapins (Valencia)",
                        location = "Arrancapins, Valencia city",
                        surface = "70m2",
                        dormCount = 2,
                        description = "70 sqm loft with terrace and views in Arrancapins, Valencia.The property has 2 bedrooms, 1 bathroom, fireplace, air conditioning, fitted wardrobes, laundry room and heating.",
                        bathCount = 1,
                        imageUrl = "https://www.aproperties.es/thumb?src=/media/properties/26217/26217_16119186163488.jpg&w=320&h=240",
                        tag = "New",
                        propertyUrl = "https://www.aproperties.es/en/valencia/arrancapins/av2101119-loft-for-rent-with-terrace-in-arrancapins-valencia"
                    ),
                    PropertyItem(
                        reference = "AV2106020",
                        price = 2600.0,
                        title = "Spectacular flat with views in Pla del remei",
                        location = "Pla del remei, Valencia city",
                        surface = "250m2",
                        dormCount = 4,
                        description = "Spectacular apartment with four bedrooms and three bathrooms located on the fourth floor and located in one of the most sought after points of the city of Valencia. With an area of 250 m2, they are distributed in four large bedrooms. The social area consists of a spacious living room and access to the fully equipped designer kitchen. The house has three bathrooms, two independent and another in suite and are complete. The building was completely rebuilt in 2008, conserving only its historical façade, so we are facing a new house and high quality materials have been used (parquet floor, sliding doors, armored entrance door, built-in wardrobes). It is rented unfurnished. It is located in the area of Pla del Remei, in the well-known and commercial Colón street. Unique location due to its proximity to the best shopping, leisure and restoration areas of the city and the River Turia garden. It has nearby hotels, theaters (Teatro Rialto), movie theaters (ABC Park Cinemas), shopping centers and schools. If you want to visit this large house, contact us, we will be happy to assist you.",
                        bathCount = 3,
                        imageUrl = "https://www.aproperties.es/thumb?src=/media/properties/29596/29596_16227898512202.jpg&w=320&h=240",
                        tag = "",
                        propertyUrl = "https://www.aproperties.es/en/valencia/pla-del-remei/av2106020-spectacular-flat-with-views-in-pla-del-remei"
                    ),
                )
            )


        val singleSearchResultWithMissingData =
            PropertySearchResult(
                pagination = Pagination(0, emptyList()),
                items = listOf(
                    PropertyItem(
                        reference = "AV2007103",
                        price = 600.0,
                        title = "Study in the Cabañal",
                        location = "El Cabañal - El Grau, Valencia city",
                        surface = null,
                        dormCount = null,
                        description = "For short and long stay. We present this beautiful furnished loft in the cozy and famous area of Cabañal, completely renovated, and located on a ground floor. The house of 47 m2, has an open space where we find a bright living room, dining room and fully equipped open kitchen and a bathroom with shower. It has a single bed and a sofa bed. The recently renovated house has air conditioning hot and cold, high ceilings with restored joists and an interior terrace to rest in the open air Located in the best atmosphere of the city, 5 minutes walking from the beach Las Arenas and Malvarrosa, with availability of any public transport and close to the promenade, surrounded by the best restaurants and hotels. Do not miss or miss the opportunity to enjoy the experience of living here.",
                        bathCount = null,
                        imageUrl = "https://www.aproperties.es/thumb?src=/media/properties/22281/22281_15958568695649.jpg&w=320&h=240",
                        tag = "Reserved",
                        propertyUrl = "https://www.aproperties.es/en/valencia/el-cabanal-el-grau/study-in-the-cabanal"
                    )
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
}



