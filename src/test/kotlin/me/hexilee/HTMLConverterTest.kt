package me.hexilee

import me.hexilee.annotations.ArrayType
import me.hexilee.annotations.Selector
import me.hexilee.annotations.Value
import org.junit.Test
import org.junit.Assert.*

class HTMLConverterTest {
  @Test
  fun simpleDataTest() {
    val user = HTMLConverter("""<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
</head>
<body>
    <div id="test">
        <div>
            <p>Hexilee</p>
            <p>20</p>
            <p>true</p>
        </div>
    </div>
</body>
</html>""", "#test").new<SimpleUser>()
    assertEquals("Hexilee", user.name)
    assertEquals(20, user.age)
    assertTrue(user.likeLemon)
  }

  @Test
  fun embeddedDataTest() {
    val lecture = HTMLConverter("""<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
</head>
<body>
    <div id="test">
        <div>
            <p>Hexilee</p>
            <p>20</p>
            <p>true</p>
        </div>
        <p>Hello World!</p>
    </div>
</body>
</html>""", "#test").new<Lecture>()
    val user = lecture.speaker
    assertEquals("Hexilee", user.name)
    assertEquals(20, user.age)
    assertTrue(user.likeLemon)
    assertEquals("Hello World!", lecture.content)
  }

  @Test
  fun primitiveArrayTest() {
    val homePage = HTMLConverter("""<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
</head>
<body>
    <div id="test">
        <ul>
            <li>0</li>
            <li>1</li>
            <li>2</li>
            <li>3</li>
        </ul>
        <div>
            <p>Hexilee</p>
            <p>20</p>
            <p>true</p>
        </div>
    </div>
</body>
</html>""", "#test").new<HomePage>()
    val user = homePage.master
    assertEquals("Hexilee", user.name)
    assertEquals(20, user.age)
    assertTrue(user.likeLemon)
    assertArrayEquals(arrayOf(0, 1, 2, 3), homePage.pages)
  }
}

data class SimpleUser(
  @Selector("p:nth-child(1)")
  @Value
  val name: String,

  @Selector("p:nth-child(2)")
  @Value
  val age: Int,

  @Selector("p:nth-child(3)")
  @Value
  val likeLemon: Boolean
)

data class Lecture(
  @Selector("#test > div")
  val speaker: SimpleUser,

  @Selector("#test > p:nth-child(2)")
  @Value
  val content: String
)

data class HomePage(
  @Selector("ul > li")
  @ArrayType(Int::class)
  @Value
  val pages: Array<Int>,

  @Selector("#test > div")
  val master: SimpleUser
)
