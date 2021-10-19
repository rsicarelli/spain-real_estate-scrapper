package domain.valueobject

import domain.entity.Mapper
import domain.entity.Property.Tag.*
import domain.entity.toMap
import domain.entity.toProperty
import domain.entity.toTag
import org.junit.jupiter.api.Test
import utils.fixtures.*
import kotlin.test.assertEquals


class PropertyTest {
    @Test
    fun `given a property with empty tag when get tag then should return correct type`() {
        //given
        val property = defaultProperty

        //when
        val tag = property.tag.toTag()

        //then
        assertEquals(EMPTY, tag)
    }

    @Test
    fun `given a property with rented tag when get tag then should return correct type`() {
        //given
        val property = rentedProperty

        //when
        val tag = property.tag.toTag()

        //then
        assertEquals(RENTED, tag)
    }

    @Test
    fun `given a property with reserved tag when get tag then should return correct type`() {
        //given
        val property = reservedProperty

        //when
        val tag = property.tag.toTag()

        //then
        assertEquals(RESERVED, tag)
    }

    @Test
    fun `given a property with new tag when get tag then should return correct type`() {
        //given
        val property = newProperty

        //when
        val tag = property.tag.toTag()

        //then
        assertEquals(NEW, tag)
    }

    @Test
    fun `given a property when toMap then should build correct map`() {
        //given
        val property = defaultProperty

        //when
        val propertyMap = property.toMap()

        //then
        with(property) {
            propertyMap.assertEquals(Mapper.REFERENCE, _id)
            propertyMap.assertEquals(Mapper.PRICE, price)
            propertyMap.assertEquals(Mapper.TITLE, title)
            propertyMap.assertEquals(Mapper.LOCATION, location)
            propertyMap.assertEquals(Mapper.SURFACE, surface)
            propertyMap.assertEquals(Mapper.DORM_COUNT, dormCount)
            propertyMap.assertEquals(Mapper.DESCRIPTION, description)
            propertyMap.assertEquals(Mapper.BATH_COUNT, bathCount)
            propertyMap.assertEquals(Mapper.AVATAR_URL, avatarUrl)
            propertyMap.assertEquals(Mapper.TAG, tag.toTag())
            propertyMap.assertEquals(Mapper.PROPERTY_URL, propertyUrl)
            propertyMap.assertEquals(Mapper.VIDEO_URL, videoUrl)
            propertyMap.assertEquals(Mapper.FULL_DESCRIPTION, fullDescription)
            propertyMap.assertEquals(Mapper.CHARACTERISTICS, characteristics)
            propertyMap.assertEquals(Mapper.PHOTO_GALLERY_URLS, photoGalleryUrls)
            /*propertyMap.assertEquals(Mapper.LAT, lat)
            propertyMap.assertEquals(Mapper.LNG, lng)*/
            propertyMap.assertEquals(Mapper.PDF_URL, pdfUrl)
            propertyMap.assertEquals(Mapper.LOCATION_DESCRIPTION, locationDescription)
        }
    }

    @Test
    fun `given a property map when convert to property then should build correct object`() {
        //given
        val propertyMap = defaultPropertyMap()

        //when
        val result = propertyMap.toProperty()

        //then
        assertEquals(defaultProperty, result)
    }

}

fun Map<String, Any?>.assertEquals(token: String, expectedValue: Any?) {
    assertEquals(getValue(token), expectedValue)
}