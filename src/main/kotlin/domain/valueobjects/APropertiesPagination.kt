package domain.valueobjects

data class APropertiesPagination(
    val pageCount: Int
) {
    fun getPagination(page: Int): String {
        return "https://www.aproperties.es/search?searchbar=1&mod=rental&zone=3&area=7&group=&loc=42&dis=&p=$page"
    }
}