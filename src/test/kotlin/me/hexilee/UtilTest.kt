package me.hexilee

import org.junit.Test
import org.junit.Assert.*

class UtilTest {
  @Test
  fun isAssignableFromString() {
    assert(String::class.java.isAssignableFrom(String::class.java))
  }

  @Test
  fun newPrimitiveTest() {
    assert(newPrimitive("true", Boolean::class.java) as Boolean)
    assert(!(newPrimitive("false", Boolean::class.java) as Boolean))
    assertEquals('a', newPrimitive("a", Char::class.java))
    assertEquals('a'.toByte(), newPrimitive("97", Byte::class.java))
    assertEquals(1.toShort(), newPrimitive("1", Short::class.java))
    assertEquals(1, newPrimitive("1", Int::class.java))
    assertEquals(1L, newPrimitive("1", Long::class.java))
    assertEquals(1.11F, newPrimitive("1.11", Float::class.java))
    assertEquals(1.11, newPrimitive("1.11", Double::class.java))
  }

  @Test
  fun newNullablePrimitiveTest() {
    assert(newNullablePrimitive("true", Boolean::class.java) as Boolean)
    assert(!(newNullablePrimitive("false", Boolean::class.java) as Boolean))
    assert(!(newNullablePrimitive("1", Boolean::class.java) as Boolean))
    assertEquals('a', newNullablePrimitive("a", Char::class.java))
    assertEquals('a'.toByte(), newNullablePrimitive("97", Byte::class.java))
    assertEquals(1.toShort(), newNullablePrimitive("1", Short::class.java))
    assertEquals(1, newNullablePrimitive("1", Int::class.java))
    assertEquals(1L, newNullablePrimitive("1", Long::class.java))
    assertEquals(1.11F, newNullablePrimitive("1.11", Float::class.java))
    assertEquals(1.11, newNullablePrimitive("1.11", Double::class.java))

    assertNull(newNullablePrimitive("", Char::class.java))
    assertNull(newNullablePrimitive("", Byte::class.java))
    assertNull(newNullablePrimitive("a", Short::class.java))
    assertNull(newNullablePrimitive("a", Int::class.java))
    assertNull(newNullablePrimitive("a", Long::class.java))
    assertNull(newNullablePrimitive("a", Float::class.java))
    assertNull(newNullablePrimitive("a", Double::class.java))
  }
}