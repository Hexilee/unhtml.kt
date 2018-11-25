package me.hexilee

import org.jsoup.Jsoup
import org.jsoup.select.Elements
import kotlin.reflect.full.primaryConstructor

class HTMLConverter {
  val root: Elements

  constructor(src: String) {
    root = Elements(Jsoup.parse(src))
  }

  constructor(src: String, rootSelector: String) {
    root = Jsoup.parse(src).select(rootSelector)
  }

  /**
   * @throws me.hexilee.exceptions.LackAnnotationException
   */
  inline fun <reified T : Any> new() = newData(root, T::class.primaryConstructor!!)
}