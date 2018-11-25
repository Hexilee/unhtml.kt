package me.hexilee

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
