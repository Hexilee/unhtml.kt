package me.hexilee

import me.hexilee.annotations.Selector
import org.junit.Test
import org.junit.Assert.*

@Selector("#root")
data class User(
  @Selector("name")
  val name: String,
  val age: Int
)

class HTMLConverterTest {
  @Test
  fun invoke() {
    HTMLConverter("").new<String>()
  }
}