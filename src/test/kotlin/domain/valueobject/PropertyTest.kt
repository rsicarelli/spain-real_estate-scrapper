package domain.valueobject

import domain.entity.Property.Tag.*
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

}