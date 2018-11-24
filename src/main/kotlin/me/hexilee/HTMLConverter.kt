package me.hexilee

import me.hexilee.annotations.Selector
import kotlin.reflect.full.findAnnotation
import org.jsoup.Jsoup
import org.jsoup.nodes.Element
import kotlin.reflect.full.createType
import kotlin.reflect.full.declaredMemberProperties
import kotlin.reflect.full.isSubtypeOf
import kotlin.reflect.jvm.javaType
import kotlin.reflect.jvm.jvmErasure

class HTMLConverter : Converter {
  private var doc: Element

  constructor(src: String) {
    doc = Jsoup.parse(src)
  }

  constructor(src: String, root: String) {
    doc = Jsoup.parse(src).select(root).first()
  }

  override fun <T : Any> invoke(instance: T): T {
    val rootSelector = instance::class.findAnnotation<Selector>()
    if (rootSelector != null) {
      doc = doc.select(rootSelector.selector).first()
    }
    for (property in instance::class.declaredMemberProperties) {
      val memberSelector = property.findAnnotation<Selector>()
      if (memberSelector != null) {
        val elements = doc.select(memberSelector.selector)
        if (isArray(property.returnType)) {

          continue
        }
      }
    }
    return instance
  }


}