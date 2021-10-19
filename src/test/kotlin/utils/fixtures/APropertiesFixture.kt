package utils.fixtures

import domain.entity.Pagination
import domain.entity.PropertyItem
import domain.entity.PropertySearchResult
import domain.valueobject.PropertyDetail
import it.skrape.core.htmlDocument
import it.skrape.selects.Doc
import utils.loadResource

object APropertiesFixture {
    inline fun <reified T> T.defaultSearchResults(): Pair<Doc, PropertySearchResult> {
        return Pair(
            htmlDocument(loadResource("/aproperties/aproperties-search-result-no-pagination.txt")),
            Fixtures.defaultSearchResults
        )
    }


    inline fun <reified T> T.searchResultsWithPagination() =
        Pair(
            htmlDocument(loadResource("/aproperties/aproperties-search-result-with-pagination.txt")),
            Fixtures.pagination
        )

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

        private val defaultPagination = Pagination(
            11,
            emptyList()
        )

        val pagination = Pagination(
            152,
            listOf(
                "https://www.aproperties.es/en/search?searchbar=1&mod=rental&zone=3&area=7&group=&loc=42&dis=&p=2",
                "https://www.aproperties.es/en/search?searchbar=1&mod=rental&zone=3&area=7&group=&loc=42&dis=&p=3",
                "https://www.aproperties.es/en/search?searchbar=1&mod=rental&zone=3&area=7&group=&loc=42&dis=&p=4",
                "https://www.aproperties.es/en/search?searchbar=1&mod=rental&zone=3&area=7&group=&loc=42&dis=&p=5",
                "https://www.aproperties.es/en/search?searchbar=1&mod=rental&zone=3&area=7&group=&loc=42&dis=&p=6",
            )
        )

        val defaultSearchResults =
            PropertySearchResult(
                pagination = defaultPagination,
                items = listOf(
                    PropertyItem(
                        reference = "AV2109058",
                        price = 800.0,
                        title = "Flat for rent in El Cabañal - El Grau (Valencia)",
                        location = "El Cabañal - El Grau, Valencia city",
                        surface = 40,
                        dormCount = 1,
                        description = "40 sqm flat in El Cabañal - El Grau, Valencia.The property has bedroom and 1 bathroom.",
                        bathCount = 1,
                        imageUrl = "https://www.aproperties.es/thumb?src=/media/properties/31618/31618_16317121137866.jpg&w=320&h=240",
                        tag = "",
                        propertyUrl = "https://www.aproperties.es/en/valencia/el-cabanal-el-grau/av2109058-flat-for-rent-in-el-cabanal-el-grau-valencia",
                    ),
                    PropertyItem(
                        reference = "AVT2004008-2",
                        price = 950.0,
                        title = "TEMPORARY apartment for rent in El Cabanyal",
                        location = "El Cabañal - El Grau, Valencia city",
                        surface = 70,
                        dormCount = 2,
                        description = "Temporary rental Price: 1050 eur / month (1850 eur in July and August) Beautiful 70m2 apartment completely renovated and perfectly furnished in the Cabañal. It has a spacious dining room, two bedrooms, parquet flooring, a perfectly equipped kitchen with appliances and a balcony. As for its location, it is a few minutes walk from the beach. It is very well connected with respect to public transport, so it has easy communication with the city center. The area has all kinds of services, supermarkets, the well-known Cabañal market, restaurants of all kinds, schools, institutes and pharmacies. If you want to live in one of the fashionable areas of València in a renovated apartment, this is your chance.",
                        bathCount = 1,
                        imageUrl = "https://www.aproperties.es/thumb?src=/media/properties/20035/20035_15913577048223.jpg&w=320&h=240",
                        tag = "",
                        propertyUrl = "https://www.aproperties.es/en/valencia/el-cabanal-el-grau/temporary-apartment-for-rent-in-el-cabanyal"
                    ),
                    PropertyItem(
                        reference = "AV2007103",
                        price = 600.0,
                        title = "Study in the Cabañal",
                        location = "El Cabañal - El Grau, Valencia city",
                        surface = 47,
                        dormCount = 1,
                        description = "For short and long stay. We present this beautiful furnished loft in the cozy and famous area of Cabañal, completely renovated, and located on a ground floor. The house of 47 m2, has an open space where we find a bright living room, dining room and fully equipped open kitchen and a bathroom with shower. It has a single bed and a sofa bed. The recently renovated house has air conditioning hot and cold, high ceilings with restored joists and an interior terrace to rest in the open air Located in the best atmosphere of the city, 5 minutes walking from the beach Las Arenas and Malvarrosa, with availability of any public transport and close to the promenade, surrounded by the best restaurants and hotels. Do not miss or miss the opportunity to enjoy the experience of living here.",
                        bathCount = 1,
                        imageUrl = "https://www.aproperties.es/thumb?src=/media/properties/22281/22281_15958568695649.jpg&w=320&h=240",
                        tag = "Reserved",
                        propertyUrl = "https://www.aproperties.es/en/valencia/el-cabanal-el-grau/study-in-the-cabanal"
                    ),
                    PropertyItem(
                        reference = "AV2107094",
                        price = 1100.0,
                        title = "Flat for rent with terrace in El Cabañal - El Grau (Valencia)",
                        location = "El Cabañal - El Grau, Valencia city",
                        surface = 103,
                        dormCount = 2,
                        description = "103 sqm flat with a 11sqm terrace in El Cabañal - El Grau, Valencia.The property has 2 bedrooms, 2 bathrooms, fitted wardrobes, balcony and heating.",
                        bathCount = 2,
                        imageUrl = "https://www.aproperties.es/thumb?src=/media/properties/30776/30776_16273848716655.JPG&w=320&h=240",
                        tag = "",
                        propertyUrl = "https://www.aproperties.es/en/valencia/el-cabanal-el-grau/av2107094-flat-for-rent-with-terrace-in-el-cabanal-el-grau-valencia"
                    ),
                    PropertyItem(
                        reference = "AV2106099",
                        price = 1250.0,
                        title = "Flat for rent in El Cabañal - El Grau (Valencia)",
                        location = "El Cabañal - El Grau, Valencia city",
                        surface = 200,
                        dormCount = 5,
                        description = "200 sqm flat in El Cabañal - El Grau, Valencia.",
                        bathCount = 1,
                        imageUrl = "https://www.aproperties.es/thumb?src=/media/properties/30005/30005_16242597469684.jpg&w=320&h=240",
                        tag = "Rented",
                        propertyUrl = "https://www.aproperties.es/en/valencia/el-cabanal-el-grau/av2106099-flat-for-rent-in-el-cabanal-el-grau-valencia"
                    ),
                    PropertyItem(
                        reference = "AV2105049",
                        price = 800.0,
                        title = "Flat for rent with views in El Cabañal - El Grau (Valencia)",
                        location = "El Cabañal - El Grau, Valencia city",
                        surface = 100,
                        dormCount = 3,
                        description = "100 sqm flat with views in El Cabañal - El Grau, Valencia.The property has 3 bedrooms, 1 bathroom, fitted wardrobes and balcony.",
                        bathCount = 2,
                        imageUrl = "https://www.aproperties.es/thumb?src=/media/properties/28917/28917_16242733329609.jpg&w=320&h=240",
                        tag = "Reserved",
                        propertyUrl = "https://www.aproperties.es/en/valencia/el-cabanal-el-grau/av2105049-flat-for-rent-with-views-in-el-cabanal-el-grau-valencia"
                    ),
                    PropertyItem(
                        reference = "AV2103031",
                        price = 800.0,
                        title = "Flat with terrace for rent in El Cabañal - El Grau (Valencia)",
                        location = "El Cabañal - El Grau, Valencia city",
                        surface = 70,
                        dormCount = 2,
                        description = "Brand new 70 sqm flat with terrace in El Cabañal - El Grau, Valencia.The property has 2 bedrooms, 2 bathrooms and fitted wardrobes.",
                        bathCount = 2,
                        imageUrl = "https://www.aproperties.es/thumb?src=/media/properties/27189/27189_16149349565982.jpg&w=320&h=240",
                        tag = "",
                        propertyUrl = "https://www.aproperties.es/en/valencia/el-cabanal-el-grau/av2103031-flat-with-terrace-for-rent-in-el-cabanal-el-grau-valencia"
                    ),
                    PropertyItem(
                        reference = "AV2103033",
                        price = 950.0,
                        title = "Flat for rent in El Cabañal - El Grau (Valencia)",
                        location = "El Cabañal - El Grau, Valencia city",
                        surface = 70,
                        dormCount = 2,
                        description = "Brand new 70 sqm flat in El Cabañal - El Grau, Valencia.The property has 2 bedrooms, 1 bathroom, fitted wardrobes and balcony.",
                        bathCount = 1,
                        imageUrl = "https://www.aproperties.es/thumb?src=/media/properties/27191/27191_16149425043413.jpg&w=320&h=240",
                        tag = "",
                        propertyUrl = "https://www.aproperties.es/en/valencia/el-cabanal-el-grau/av2103033-flat-for-rent-in-el-cabanal-el-grau-valencia"
                    ),
                    PropertyItem(
                        reference = "AV2103034",
                        price = 1050.0,
                        title = "Flat for rent in El Cabañal - El Grau (Valencia)",
                        location = "El Cabañal - El Grau, Valencia city",
                        surface = 69,
                        dormCount = 2,
                        description = "Brand new 69 sqm flat in El Cabañal - El Grau, Valencia.The property has 2 bedrooms, 2 bathrooms, fitted wardrobes and balcony.",
                        bathCount = 2,
                        imageUrl = "https://www.aproperties.es/thumb?src=/media/properties/27194/27194_16149438943054.jpg&w=320&h=240",
                        tag = "",
                        propertyUrl = "https://www.aproperties.es/en/valencia/el-cabanal-el-grau/av2103034-flat-for-rent-in-el-cabanal-el-grau-valencia",
                    ),
                    PropertyItem(
                        reference = "AV2010076",
                        price = 1200.0,
                        title = "House for rent in El Cabañal - El Grau (Valencia)",
                        location = "El Cabañal - El Grau, Valencia city",
                        surface = 35,
                        dormCount = 1,
                        description = "35 sqm house in El Cabañal - El Grau, Valencia.The property has bedroom, 1 bathroom, air conditioning, fitted wardrobes, laundry room and heating.",
                        bathCount = 1,
                        imageUrl = "https://www.aproperties.es/thumb?src=/media/properties/24067/24067_16032851265515.jpg&w=320&h=240",
                        tag = "New",
                        propertyUrl = "https://www.aproperties.es/en/valencia/el-cabanal-el-grau/house-for-rent-in-el-cabanal-el-grau-valencia",
                    ),
                    PropertyItem(
                        reference = "AV2008007",
                        price = 750.0,
                        title = "3 bedroom apartment for students",
                        location = "El Cabañal - El Grau, Valencia city",
                        surface = 87,
                        dormCount = 3,
                        description = "In the popular area of El Grau, next to the beaches, there is this cozy furnished apartment of 54 m2 distributed in a bright living-dining room, an independent kitchen, equipped with all electrical appliances, three bedrooms with wardrobes, beds and desks, and a full bathroom. The house is ideal for students, it has air conditioning in the living room. The area in which this house is located is ideal, since it has all kinds of services at hand (cafes, supermarkets such as Mercadona and Consum, pharmacies, hospitals, and green and sports areas), and has a very good connection to transport public, being located a step away from the Port of Valencia. 10 minutes from the Aqua shopping center and the Túria garden, 7 minutes walk from the metro and tram station. It is located on the third floor WITHOUT ELEVATOR. If you are interested in this wonderful home, do not miss the opportunity and contact us!",
                        bathCount = 1,
                        imageUrl = "https://www.aproperties.es/thumb?src=/media/properties/22437/22437_15964638819813.jpg&w=320&h=240",
                        tag = "New",
                        propertyUrl = "https://www.aproperties.es/en/valencia/el-cabanal-el-grau/av2008007-3-bedroom-apartment-for-students",
                    )
                )
            )


        val singleSearchResultWithMissingData =
            PropertySearchResult(
                pagination = Pagination(1, emptyList()),
                items = listOf(
                    PropertyItem(
                        reference = "AV2007103",
                        price = 600.0,
                        title = "Study in the Cabañal",
                        location = "El Cabañal - El Grau, Valencia city",
                        surface = 0,
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



