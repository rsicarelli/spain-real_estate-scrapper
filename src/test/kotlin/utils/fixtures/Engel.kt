package utils.fixtures

import domain.model.Pagination
import domain.model.PropertyDetail
import domain.model.PropertyItem
import domain.model.PropertySearchResult
import it.skrape.core.htmlDocument
import it.skrape.selects.Doc
import utils.loadResource

object Engel {

    inline fun <reified T> T.defaultSearchResults(): Pair<Doc, PropertySearchResult> {
        return Pair(htmlDocument(loadResource("/engel/engel-search-result.txt")), Fixtures.defaultSearchResults)
    }

    inline fun <reified T> T.defaultSearchResultsWithPagination() =
        Pair(
            htmlDocument(loadResource("/engel/engel-search-result.txt")),
            Fixtures.defaultSearchResults
        )

    inline fun <reified T> T.emptySearchResult() = htmlDocument(loadResource("/engel/engel-empty-search-result.txt"))

    inline fun <reified T> T.defaultPropertyDetail() =
        Pair(
            htmlDocument(loadResource("/engel/engel-property-detail.txt")),
            Fixtures.defaultPropertyDetail
        )

    inline fun <reified T> T.badPropertyDetailMissing() =
        htmlDocument(loadResource("/engel/engel-bad-property-detail.txt"))

    inline fun <reified T> T.singleSearchResult() =
        Pair(
            htmlDocument(loadResource("/engel/engel-single-search-result.txt")),
            Fixtures.singleSearchResult
        )

    object Fixtures {
        val defaultSearchResults =
            PropertySearchResult(
                pagination = Pagination(
                    234, listOf(
                        "https://www.engelvoelkers.com/en/search/?q=&startIndex=16&businessArea=residential&sortOrder=DESC&sortField=sortPrice&pageSize=18&facets=bsnssr%3Aresidential%3Bcntry%3Aspain%3Brgn%3Avalencia%3Btyp%3Arent%3B",
                        "https://www.engelvoelkers.com/en/search/?q=&startIndex=34&businessArea=residential&sortOrder=DESC&sortField=sortPrice&pageSize=18&facets=bsnssr%3Aresidential%3Bcntry%3Aspain%3Brgn%3Avalencia%3Btyp%3Arent%3B",
                        "https://www.engelvoelkers.com/en/search/?q=&startIndex=52&businessArea=residential&sortOrder=DESC&sortField=sortPrice&pageSize=18&facets=bsnssr%3Aresidential%3Bcntry%3Aspain%3Brgn%3Avalencia%3Btyp%3Arent%3B",
                        "https://www.engelvoelkers.com/en/search/?q=&startIndex=70&businessArea=residential&sortOrder=DESC&sortField=sortPrice&pageSize=18&facets=bsnssr%3Aresidential%3Bcntry%3Aspain%3Brgn%3Avalencia%3Btyp%3Arent%3B",
                        "https://www.engelvoelkers.com/en/search/?q=&startIndex=88&businessArea=residential&sortOrder=DESC&sortField=sortPrice&pageSize=18&facets=bsnssr%3Aresidential%3Bcntry%3Aspain%3Brgn%3Avalencia%3Btyp%3Arent%3B",
                        "https://www.engelvoelkers.com/en/search/?q=&startIndex=106&businessArea=residential&sortOrder=DESC&sortField=sortPrice&pageSize=18&facets=bsnssr%3Aresidential%3Bcntry%3Aspain%3Brgn%3Avalencia%3Btyp%3Arent%3B",
                        "https://www.engelvoelkers.com/en/search/?q=&startIndex=124&businessArea=residential&sortOrder=DESC&sortField=sortPrice&pageSize=18&facets=bsnssr%3Aresidential%3Bcntry%3Aspain%3Brgn%3Avalencia%3Btyp%3Arent%3B",
                        "https://www.engelvoelkers.com/en/search/?q=&startIndex=142&businessArea=residential&sortOrder=DESC&sortField=sortPrice&pageSize=18&facets=bsnssr%3Aresidential%3Bcntry%3Aspain%3Brgn%3Avalencia%3Btyp%3Arent%3B",
                        "https://www.engelvoelkers.com/en/search/?q=&startIndex=160&businessArea=residential&sortOrder=DESC&sortField=sortPrice&pageSize=18&facets=bsnssr%3Aresidential%3Bcntry%3Aspain%3Brgn%3Avalencia%3Btyp%3Arent%3B",
                        "https://www.engelvoelkers.com/en/search/?q=&startIndex=178&businessArea=residential&sortOrder=DESC&sortField=sortPrice&pageSize=18&facets=bsnssr%3Aresidential%3Bcntry%3Aspain%3Brgn%3Avalencia%3Btyp%3Arent%3B",
                        "https://www.engelvoelkers.com/en/search/?q=&startIndex=196&businessArea=residential&sortOrder=DESC&sortField=sortPrice&pageSize=18&facets=bsnssr%3Aresidential%3Bcntry%3Aspain%3Brgn%3Avalencia%3Btyp%3Arent%3B",
                        "https://www.engelvoelkers.com/en/search/?q=&startIndex=214&businessArea=residential&sortOrder=DESC&sortField=sortPrice&pageSize=18&facets=bsnssr%3Aresidential%3Bcntry%3Aspain%3Brgn%3Avalencia%3Btyp%3Arent%3B",
                        "https://www.engelvoelkers.com/en/search/?q=&startIndex=232&businessArea=residential&sortOrder=DESC&sortField=sortPrice&pageSize=18&facets=bsnssr%3Aresidential%3Bcntry%3Aspain%3Brgn%3Avalencia%3Btyp%3Arent%3B",
                    )
                ),
                items = listOf(
                    PropertyItem(
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
                    PropertyItem(
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
                    PropertyItem(
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
                    PropertyItem(
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
                    PropertyItem(
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
                    PropertyItem(
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
                    PropertyItem(
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
                    PropertyItem(
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
                    PropertyItem(
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
                    PropertyItem(
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
                    PropertyItem(
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
                    PropertyItem(
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
                    PropertyItem(
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
                    PropertyItem(
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
                    PropertyItem(
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
                    PropertyItem(
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
            )

        val singleSearchResult = PropertySearchResult(
            pagination = Pagination(
                1, emptyList()
            ),
            items = listOf(
                PropertyItem(
                    reference = "W-02LFEE",
                    price = 990.0,
                    title = "Original house next to the Central Market",
                    location = "Spain, Valencia, Ciutat Vella, El Mercat",
                    surface = "78 m²",
                    dormCount = 3,
                    description = "",
                    bathCount = 2,
                    imageUrl = "https://www.engelvoelkers.com/images/d613d9d9-b0ad-4c70-b4fc-d0a9ddad0a87/original-house-next-to-the-central-market-full-kitchen-between-floors?w=576&h=432",
                    tag = "",
                    propertyUrl = "https://www.engelvoelkers.com/en-es/property/original-house-next-to-the-central-market-4358966.922184_exp/",
                )
            )

        )

        val defaultPropertyDetail = PropertyDetail(
            reference = "W-02LH5J",
            videoUrl = "",
            fullDescription = "This house in a traditional style is located in one of the best areas of \"El Cabañal\". Completely renovated property for short term rentals. Available from July for the price 1800 euros, August 2000 euros. The house consists of three double bedrooms, three bathrooms, large living room and a balcony. It also has a modern kitchen equipped with appliances. House is fully furnished and ready to move in. Do no hesitate to contact us to arrange a visit, we will be happy to assist you.",
            locationDescription = "The area of “El Cabañal” originally was an area with traditional fishermen houses, it has been recently renovated and it´s located very close to “Malvarrosa” beach and “Las Arenas” beach. Currently it has a pleasant promenade, a very quiet white sand beach and all kinds of leisure activities, such as restaurants, bars, shops, supermarkets, etc. Very well connected by bus or tram to the city center. A unique opportunity to live right next to the beach with all the necessary services.",
            characteristics = listOf(
                "Property subtype House",
                "Rooms 3",
                "Bedrooms 3",
                "Bathrooms 3",
                "Living Area 105 m²",
                "Total Surface Area 105 m²",
                "Condition Top",
                "Location Very good",
                "Heating Central heating",
                "Central heating",
                "Floor type Tiles",
                "Tiles"
            ),
            photosGalleryUrls = listOf(
                "https://www.engelvoelkers.com/images/4c7fb9d3-9a88-4808-9099-03149554f7a1/house-in-el-caba%C3%B1al-for-short-term-balcony",
                "https://www.engelvoelkers.com/images/b9f25234-873d-412a-81fb-21ad50723019/house-in-el-caba%C3%B1al-for-short-term-balcony",
                "https://www.engelvoelkers.com/images/373c73e4-173c-4c8e-8bae-fa03d4346b90/house-in-el-caba%C3%B1al-for-short-term-balcony",
                "https://www.engelvoelkers.com/images/50a5509a-9359-405d-bf9a-c5a426043f38/house-in-el-caba%C3%B1al-for-short-term-living-room",
                "https://www.engelvoelkers.com/images/22942d39-01d2-43b8-b586-4ea04d008ecd/house-in-el-caba%C3%B1al-for-short-term-balcony",
                "https://www.engelvoelkers.com/images/6aabecb3-7633-42bc-b8dd-c8f5a30c69ba/house-in-el-caba%C3%B1al-for-short-term-kitchen",
                "https://www.engelvoelkers.com/images/e33de738-8ce2-4174-82d1-1f3425cc1ed8/house-in-el-caba%C3%B1al-for-short-term-living-room",
                "https://www.engelvoelkers.com/images/0f7d8c7f-345a-4ed0-bf41-c1f51a7d80af/house-in-el-caba%C3%B1al-for-short-term-kitchen",
                "https://www.engelvoelkers.com/images/17ba5f09-c845-4066-8d5a-b1388e1fdb66/house-in-el-caba%C3%B1al-for-short-term-living-room",
                "https://www.engelvoelkers.com/images/b923a2fa-b6db-49ca-86e1-6f158cec6ac1/house-in-el-caba%C3%B1al-for-short-term-dining-area",
                "https://www.engelvoelkers.com/images/78676165-4d30-48f0-98d9-6ecd0674a23c/house-in-el-caba%C3%B1al-for-short-term-living-room",
                "https://www.engelvoelkers.com/images/50dd3663-f814-4527-805c-7fc99074e66f/house-in-el-caba%C3%B1al-for-short-term-living-room",
                "https://www.engelvoelkers.com/images/7363e201-8fb6-4197-aeda-6caf8d07ae5e/house-in-el-caba%C3%B1al-for-short-term-dining-area",
                "https://www.engelvoelkers.com/images/983c4e6a-8572-424b-8358-695eb9afa91c/house-in-el-caba%C3%B1al-for-short-term-bedroom",
                "https://www.engelvoelkers.com/images/e65febcc-da73-4eaf-bbde-de64709d9a5d/house-in-el-caba%C3%B1al-for-short-term-bedroom",
                "https://www.engelvoelkers.com/images/c199dd3a-e70f-4168-8dbf-4b7605cc06f5/house-in-el-caba%C3%B1al-for-short-term-bedroom",
                "https://www.engelvoelkers.com/images/0477eed6-5f7f-44ae-89f5-11951b38f21c/house-in-el-caba%C3%B1al-for-short-term-bathroom",
                "https://www.engelvoelkers.com/images/83ff0391-a933-4d2b-9d3b-e19d719fdec2/house-in-el-caba%C3%B1al-for-short-term-bathroom",
                "https://www.engelvoelkers.com/images/c8234648-e011-43ab-a85a-fe3dd01a41d1/house-in-el-caba%C3%B1al-for-short-term-bathroom",
                "https://www.engelvoelkers.com/images/1e63af95-50c3-424d-806d-e037894904b8/house-in-el-caba%C3%B1al-for-short-term-bedroom-2",
                "https://www.engelvoelkers.com/images/d75c0bcb-f8e0-440f-9821-7a3fa1eb89e7/house-in-el-caba%C3%B1al-for-short-term-bedroom-3",
                "https://www.engelvoelkers.com/images/7158ae0c-3fe7-4bf8-accf-9962c73a7c88/house-in-el-caba%C3%B1al-for-short-term-bedroom-3",
                "https://www.engelvoelkers.com/images/d189e634-b5b3-4626-9b0e-f17ef773b1c7/house-in-el-caba%C3%B1al-for-short-term-hallway",
                "https://www.engelvoelkers.com/images/2d6d252b-cb67-44f8-b066-264623877bab/house-in-el-caba%C3%B1al-for-short-term-hallway",
                "https://www.engelvoelkers.com/images/8442bd05-fc94-440a-af19-f2baf3e74f86/house-in-el-caba%C3%B1al-for-short-term-bathroom",
                "https://www.engelvoelkers.com/images/859f24ae-1256-493a-9566-1ee860fb18d1/house-in-el-caba%C3%B1al-for-short-term-bedroom-3",
                "https://www.engelvoelkers.com/images/dfe18ee5-f66b-4707-b2b0-8e83f76e7792/house-in-el-caba%C3%B1al-for-short-term-plan",
                "https://www.engelvoelkers.com/images/38111c99-59d9-4d88-a6c5-b5ec65ff67f9/house-in-el-caba%C3%B1al-for-short-term-plan",
            ),
            lat = 0.0,
            lng = 0.0,
            pdfUrl = ""
        )


    }

}

