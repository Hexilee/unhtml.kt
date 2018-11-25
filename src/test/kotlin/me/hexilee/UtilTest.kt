package me.hexilee

import org.junit.Test
import kotlin.reflect.full.createType

class UtilTest {
  @Test
  fun isAssignableFromString() {
    assert(String::class.java.isAssignableFrom(String::class.java))
  }
}