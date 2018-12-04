package me.hexilee.unhtml

import org.jsoup.Jsoup
import org.jsoup.select.Elements
import kotlin.reflect.full.primaryConstructor

class Deserializer {
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
  inline fun <reified T : Any> new() = newData(root,
      T::class.primaryConstructor!!)

  inline fun <reified T : Any> newArray() = root.map {
    newData(Elements(it), T::class.primaryConstructor!!)
  }.toTypedArray()
}