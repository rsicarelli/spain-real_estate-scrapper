package utils.fixtures

import domain.valueobjects.EngelPagination
import domain.valueobjects.PropertyDetail
import domain.valueobjects.PropertySearchResult
import it.skrape.core.htmlDocument
import it.skrape.selects.Doc
import utils.loadResource

object Engel {

    inline fun <reified T> T.defaultSearchResults(): Pair<Doc, List<PropertySearchResult>> {
        return Pair(htmlDocument(loadResource("/engel/engel-search-result.txt")), Fixtures.defaultSearchResults)
    }

    inline fun <reified T> T.defaultSearchResultsWithPagination() =
        Triple(
            htmlDocument(loadResource("/engel/engel-search-result.txt")),
            Fixtures.defaultSearchResults,
            Fixtures.defaultPagination
        )

    inline fun <reified T> T.emptySearchResult() = htmlDocument(loadResource("/engel/engel-empty-search-result.txt"))

    inline fun <reified T> T.defaultPropertyDetail() =
        Pair(
            htmlDocument(loadResource("/engel/engel-property-detail.txt")),
            Fixtures.defaultPropertyDetail
        )

    inline fun <reified T> T.propertyDetailMissingData() =
        Pair(
            htmlDocument(loadResource("/engel/engel-property-detail-missing-data.txt")),
            Fixtures.propertyDetailMissingData
        )

    inline fun <reified T> T.badPropertyDetailMissing() =
        htmlDocument(loadResource("/engel/engel-bad-property-detail.txt"))

    object Fixtures {
        const val searchUrl =
            "https://www.engel.es/search?searchbar=1&mod=rental&zone=3&area=7&group=&loc=42&dis="

        const val propertyUrl =
            "https://www.engel.es/valencia/arrancapins/av2109083-piso-de-alquiler-de-una-habitacion-en-calle-xativa"

        val defaultSearchResults = listOf(
            PropertySearchResult(
                reference = "W-02FEG9",
                price = 10000.0,
                title = "Exquisite palace in the center for short term",
                location = "Spain, Valencia, Ciutat Vella, El Carme",
                surface = "380 m²",
                dormCount = 4,
                description = "",
                bathCount = 4,
                imageUrl = "https://www.engelvoelkers.com/images/6fcf4b4f-829b-4d8e-a6d1-ea1d2bd55ce3/exquisite-palace-in-the-center-for-short-term?w=576&h=432",
                tag = "",
                propertyUrl = "https://www.engelvoelkers.com/en-es/property/exquisite-palace-in-the-center-for-short-term-4077801.877214_exp/",
            ),
            PropertySearchResult(
                reference = "W-023L8T",
                price = 4200.0,
                title = "Luxurious property in the center",
                location = "Spain, Valencia, Ciutat Vella, Sant Francesc",
                surface = "325 m²",
                dormCount = 5,
                description = "",
                bathCount = 4,
                imageUrl = "https://www.engelvoelkers.com/images/48583d5f-b005-4272-8837-76d28c3d90db/luxurious-property-in-the-center-living-room?w=576&h=432",
                tag = "",
                propertyUrl = "https://www.engelvoelkers.com/en-es/property/luxurious-property-in-the-center-3526733.874912_exp/"
            ),
            PropertySearchResult(
                reference = "W-02MZTR",
                price = 3500.0,
                title = "Luxury duplex penthouse with parking",
                location = "Spain, Valencia, Ciutat Vella, Sant Francesc",
                surface = "230 m²",
                dormCount = 4,
                description = "",
                bathCount = 4,
                imageUrl = "https://www.engelvoelkers.com/images/02b9375a-7645-42c2-9137-3a9156deb989/luxury-duplex-penthouse-with-parking-terrace?w=576&h=432",
                tag = "NEW",
                propertyUrl = "https://www.engelvoelkers.com/en-es/property/luxury-duplex-penthouse-with-parking-4432095.932238_exp/"
            ),
            PropertySearchResult(
                reference = "W-02M68X",
                price = 3500.0,
                title = "Magnificent renovated flat in “Paseo Alameda”",
                location = "Spain, Valencia, Pla del Real, Exposició",
                surface = "185 m²",
                dormCount = 4,
                description = "",
                bathCount = 2,
                imageUrl = "https://www.engelvoelkers.com/images/d91720fa-8849-4512-b6dc-f05ad5c1dc5f/magnificent-renovated-flat-in-%E2%80%9Cpaseo-alameda%E2%80%9D-terrace?w=576&h=432",
                tag = "",
                propertyUrl = "https://www.engelvoelkers.com/en-es/property/magnificent-renovated-flat-in-%E2%80%9Cpaseo-alameda%E2%80%9D-4393761.926597_exp/"
            ),
            PropertySearchResult(
                reference = "W-02LTAB",
                price = 3500.0,
                title = "Luxurious home in \"Sant Francesc\"",
                location = "Spain, Valencia, Ciutat Vella, Sant Francesc",
                surface = "242 m²",
                dormCount = 5,
                description = "",
                bathCount = 5,
                imageUrl = "https://www.engelvoelkers.com/images/d441f5d5-804f-4856-a873-191947616373/luxurious-home-in-sant-francesc-dining-room?w=576&h=432",
                tag = "",
                propertyUrl = "https://www.engelvoelkers.com/en-es/property/luxurious-home-in-sant-francesc-4376963.924376_exp/"
            ),
            PropertySearchResult(
                reference = "W-02EO9S",
                price = 3500.0,
                title = "Luxury property in \"Jacinto Benavente\"",
                location = "Spain, Valencia, Eixample, Gran Vía",
                surface = "307 m²",
                dormCount = 4,
                description = "",
                bathCount = 3,
                imageUrl = "https://www.engelvoelkers.com/images/34d53b8b-7c4f-4b4b-820f-9bb158a8a29e/luxury-property-in-jacinto-benavente-living-room?w=576&h=432",
                tag = "",
                propertyUrl = "https://www.engelvoelkers.com/en-es/property/luxury-property-in-jacinto-benavente-4043872.872984_exp/"
            ),
            PropertySearchResult(
                reference = "W-02I688",
                price = 3000.0,
                title = "Property next to the \"Plaza del Ayuntamiento\"",
                location = "Spain, Valencia, Ciutat Vella, Sant Francesc",
                surface = "186 m²",
                dormCount = 3,
                description = "",
                bathCount = 3,
                imageUrl = "https://www.engelvoelkers.com/images/96699eae-520e-4df9-aa1a-ab90a1ae46cb/property-next-to-the-plaza-del-ayuntamiento-living-room?w=576&h=432",
                tag = "",
                propertyUrl = "https://www.engelvoelkers.com/en-es/property/property-next-to-the-plaza-del-ayuntamiento-4207112.897051_exp/"
            ),
            PropertySearchResult(
                reference = "W-02CMWO",
                price = 3000.0,
                title = "Luxury studio for short term",
                location = "Spain, Valencia, Ciudad de las Ciencias, Camí Fondo - Penya-Roja",
                surface = "20 m²",
                dormCount = 1,
                description = "",
                bathCount = 1,
                imageUrl = "https://www.engelvoelkers.com/images/526bba4f-42e5-4bc7-ac08-55faaa6e28b1/luxury-studio-for-short-term-bedroom?w=576&h=432",
                tag = "",
                propertyUrl = "https://www.engelvoelkers.com/en-es/property/luxury-studio-for-short-term-3948792.859115_exp/"
            ),
            PropertySearchResult(
                reference = "W-023GJP",
                price = 3000.0,
                title = "Spectacular penthouse in the City of Sciences",
                location = "Spain, Valencia, Camins al Grau, Ciutat de les Arts i les Ciències",
                surface = "283 m²",
                dormCount = 2,
                description = "",
                bathCount = 2,
                imageUrl = "https://www.engelvoelkers.com/images/ebc205ff-446a-4a18-a121-3fcb3591f540/spectacular-penthouse-in-the-city-of-sciences-living-room?w=576&h=432",
                tag = "",
                propertyUrl = "https://www.engelvoelkers.com/en-es/property/spectacular-penthouse-in-the-city-of-sciences-3520645.797470_exp/"
            ),
            PropertySearchResult(
                reference = "W-02JLLT",
                price = 2800.0,
                title = "Magnificent flat in \"Colón\" Street",
                location = "Spain, Valencia, Eixample, Pla del Remei",
                surface = "217 m²",
                dormCount = 4,
                description = "",
                bathCount = 4,
                imageUrl = "https://www.engelvoelkers.com/images/471da8ee-0d46-4c10-8ed5-945e3a6b80e0/magnificent-flat-in-col%C3%B3n-street-living-room?w=576&h=432",
                tag = "",
                propertyUrl = "https://www.engelvoelkers.com/en-es/property/magnificent-flat-in-colon-street-4273697.908642_exp/"
            ),
            PropertySearchResult(
                reference = "W-02MVO5",
                price = 2700.0,
                title = "Masive property in front of the Turia Gardens",
                location = "Spain, Valencia, Pla del Real, Exposició",
                surface = "315 m²",
                dormCount = 5,
                description = "",
                bathCount = 4,
                imageUrl = "https://www.engelvoelkers.com/images/2a8b8e2a-ba92-46c0-943b-8ae58ce961af/masive-property-in-front-of-the-turia-gardens-living-room?w=576&h=432",
                tag = "",
                propertyUrl = "https://www.engelvoelkers.com/en-es/property/masive-property-in-front-of-the-turia-gardens-4426709.931671_exp/"
            ),
            PropertySearchResult(
                reference = "W-02MUNK",
                price = 2700.0,
                title = "Luxury property in the center of Valencia",
                location = "Spain, Valencia, Ciutat Vella, Sant Francesc",
                surface = "315 m²",
                dormCount = 4,
                description = "",
                bathCount = 3,
                imageUrl = "https://www.engelvoelkers.com/images/ce90ca67-0942-4f85-a8db-5b9cf87d4120/luxury-property-in-the-center-of-valencia-dining-room-and-living-room?w=576&h=432",
                tag = "NEW",
                propertyUrl = "https://www.engelvoelkers.com/en-es/property/luxury-property-in-the-center-of-valencia-4425392.931926_exp/"
            ),
            PropertySearchResult(
                reference = "W-02FSJZ",
                price = 2700.0,
                title = "Precious apartment in “Colón” Market",
                location = "Spain, Valencia, Eixample, Pla del Remei",
                surface = "160 m²",
                dormCount = 3,
                description = "",
                bathCount = 3,
                imageUrl = "https://www.engelvoelkers.com/images/23d496c4-c9e8-439a-bb8e-dc5df5d32e0a/precious-apartment-in-%E2%80%9Ccol%C3%B3n%E2%80%9D-market-views-from-the-property?w=576&h=432",
                tag = "",
                propertyUrl = "https://www.engelvoelkers.com/en-es/property/precious-apartment-in-%E2%80%9Ccolon%E2%80%9D-market-4096079.880357_exp/"
            ),
            PropertySearchResult(
                reference = "W-02LPUH",
                price = 2600.0,
                title = "Luxurious apartment in the center of Valencia",
                location = "Spain, Valencia, Eixample, Pla del Remei",
                surface = "164 m²",
                dormCount = 4,
                description = "",
                bathCount = 3,
                imageUrl = "https://www.engelvoelkers.com/images/c1fc44a4-4a78-4424-9367-627ce6439e97/luxurious-apartment-in-the-center-of-valencia-living-room?w=576&h=432",
                tag = "",
                propertyUrl = "https://www.engelvoelkers.com/en-es/property/luxurious-apartment-in-the-center-of-valencia-4372505.928709_exp/"
            ),
            PropertySearchResult(
                reference = "W-02L9ZH",
                price = 2600.0,
                title = "Apartment with views to \"Plaza de la Reina\"",
                location = "Spain, Valencia, Ciutat Vella, Sant Francesc",
                surface = "187 m²",
                dormCount = 3,
                description = "",
                bathCount = 2,
                imageUrl = "https://www.engelvoelkers.com/images/4740c9d1-4ee2-442a-9022-317e24ca3def/apartment-with-views-to-plaza-de-la-reina-living-room?w=576&h=432",
                tag = "",
                propertyUrl = "https://www.engelvoelkers.com/en-es/property/apartment-with-views-to-plaza-de-la-reina-4351949.920475_exp/"
            ),
            PropertySearchResult(
                reference = "W-02M1DG",
                price = 2500.0,
                title = "Spacious property in the centre of Valencia",
                location = "Spain, Valencia, Ciutat Vella, La Seu",
                surface = "307 m²",
                dormCount = 5,
                description = "",
                bathCount = 4,
                imageUrl = "https://www.engelvoelkers.com/images/609696fd-f3bd-4ba7-9880-a5c98130fbec/spacious-property-in-the-centre-of-valencia-living-room?w=576&h=432",
                tag = "",
                propertyUrl = "https://www.engelvoelkers.com/en-es/property/spacious-property-in-the-centre-of-valencia-4387444.926274_exp/"
            ),
        )

        val defaultPagination = EngelPagination(234)

        val defaultPropertyDetail = PropertyDetail(
            reference = "AV2109083",
            videoUrl = "https://player.vimeo.com/video/510636212",
            fullDescription = "Fabulosa vivienda ubicada en calle Xàtiva en pleno centro y corazón de la ciudad. Totalmente amueblado, en una finca residencial con tres ascensores y servicio de Portería. Distribuido en un amplio comedor, con terraza que da a la calle Xátiva con unas vistas espectaculares de la ciudad, habitación amplia y con armarios empotrados, cuarto de baño completo, cocina equipada con electrodomésticos, totalmente amueblado. los muebles pueden variar , Equipado con aire acondicionado. No dude en ponerse en contacto con nosotros, estremos encantados de atenderle.",
            locationDescription = "",
            characteristics = listOf("Calefacción", "Amueblado", "Aacc", "Terraza", "Ascensor", "Conserje"),
            photosGalleryUrls = listOf(
                "https://www.engel.es/engel-property-detail_files/31800_1632323417028.jpg",
                "https://www.engel.es/engel-property-detail_files/31800_1632323417065.jpg",
                "https://www.engel.es/engel-property-detail_files/31800_16323234170252.jpg",
                "https://www.engel.es/engel-property-detail_files/31800_16323234170444.jpg",
                "https://www.engel.es/engel-property-detail_files/31800_1632323417058.jpg",
                "https://www.engel.es/engel-property-detail_files/31800_16323234170475.jpg",
                "https://www.engel.es/engel-property-detail_files/31800_16323234170513.jpg",
                "https://www.engel.es/engel-property-detail_files/31800_1632323417032.jpg",
                "https://www.engel.es/engel-property-detail_files/31800_16323234170399.jpg",
                "https://www.engel.es/engel-property-detail_files/31800_16323234170618.jpg",
                "https://www.engel.es/engel-property-detail_files/31800_16323234170359.jpg",
                "https://www.engel.es/engel-property-detail_files/31800_16323234170541.jpg",
                "https://www.engel.es/engel-property-detail_files/31800_16323234170214.jpg",
            ),
            lat = 39.4676,
            lng = -0.378454,
            pdfUrl = "https://www.engel.es/pdf/properties/es/av2109083.pdf"
        )

        val propertyDetailMissingData = PropertyDetail(
            reference = "AV2109083",
            videoUrl = "https://player.vimeo.com/video/510636212",
            fullDescription = "Fabulosa vivienda ubicada en calle Xàtiva en pleno centro y corazón de la ciudad. Totalmente amueblado, en una finca residencial con tres ascensores y servicio de Portería. Distribuido en un amplio comedor, con terraza que da a la calle Xátiva con unas vistas espectaculares de la ciudad, habitación amplia y con armarios empotrados, cuarto de baño completo, cocina equipada con electrodomésticos, totalmente amueblado. los muebles pueden variar , Equipado con aire acondicionado. No dude en ponerse en contacto con nosotros, estremos encantados de atenderle.",
            locationDescription = "",
            characteristics = listOf("Calefacción", "Amueblado", "Aacc", "Terraza", "Ascensor", "Conserje"),
            photosGalleryUrls = listOf(
                "https://www.engel.es/engel-property-detail_files/31800_1632323417028.jpg",
                "https://www.engel.es/engel-property-detail_files/31800_1632323417065.jpg",
                "https://www.engel.es/engel-property-detail_files/31800_16323234170252.jpg",
                "https://www.engel.es/engel-property-detail_files/31800_16323234170444.jpg",
                "https://www.engel.es/engel-property-detail_files/31800_1632323417058.jpg",
                "https://www.engel.es/engel-property-detail_files/31800_16323234170475.jpg",
                "https://www.engel.es/engel-property-detail_files/31800_16323234170513.jpg",
                "https://www.engel.es/engel-property-detail_files/31800_1632323417032.jpg",
                "https://www.engel.es/engel-property-detail_files/31800_16323234170399.jpg",
                "https://www.engel.es/engel-property-detail_files/31800_16323234170618.jpg",
                "https://www.engel.es/engel-property-detail_files/31800_16323234170359.jpg",
                "https://www.engel.es/engel-property-detail_files/31800_16323234170541.jpg",
                "https://www.engel.es/engel-property-detail_files/31800_16323234170214.jpg",
            ),
            lat = null,
            lng = null,
            pdfUrl = "https://www.engel.es/pdf/properties/es/av2109083.pdf"
        )


    }

}

