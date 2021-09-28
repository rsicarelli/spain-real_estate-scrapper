package domain.valueobjects

data class EngelPagination(
    val pageCount: Int
) {
    fun getPagination(): List<String> {
        var pageIndex = 16
        val pages = mutableListOf(
            "https://www.engelvoelkers.com/en/search/?q=&startIndex=$pageIndex&businessArea=residential&sortOrder=DESC&sortField=sortPrice&pageSize=238&facets=bsnssr%3Aresidential%3Bcntry%3Aspain%3Brgn%3Avalencia%3Btyp%3Arent%3B"
        )
        while (pageCount - pageIndex >= 18) {
            pageIndex += 18
            val url =
                "https://www.engelvoelkers.com/en/search/?q=&startIndex=$pageIndex&businessArea=residential&sortOrder=DESC&sortField=sortPrice&pageSize=238&facets=bsnssr%3Aresidential%3Bcntry%3Aspain%3Brgn%3Avalencia%3Btyp%3Arent%3B"
            pages.add(url)
        }

        return pages
    }
}