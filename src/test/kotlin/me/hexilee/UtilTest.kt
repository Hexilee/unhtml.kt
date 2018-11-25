package me.hexilee

import org.junit.Test
import kotlin.reflect.full.createType

class UtilTest {
  @Test
  fun isArray() {
    assert(Array<Int>::class.java.isArray)
  }
}