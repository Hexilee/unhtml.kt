package me.hexilee

import me.hexilee.annotations.Elem
import me.hexilee.annotations.Selector
import me.hexilee.exceptions.LackAnnotationException
import me.hexilee.exceptions.NotPrimitiveException
import kotlin.reflect.full.findAnnotation
import org.jsoup.Jsoup
import org.jsoup.select.Elements
import kotlin.reflect.KFunction
import kotlin.reflect.full.primaryConstructor
import kotlin.reflect.jvm.jvmErasure

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
  inline fun <reified T : Any> new(): T {
    return newObject(root, T::class.primaryConstructor!!)
  }

  fun <T : Any> newObject(_root: Elements, primaryConstructor: KFunction<T>): T {
    val rootSelector = primaryConstructor.findAnnotation<Selector>()
    var rootNodes = _root
    if (rootSelector != null) {
      rootNodes = rootNodes.select(rootSelector.selector)
    }

    return primaryConstructor.run {
      call(parameters.stream().map {
        val parameterType = it.type
        val paramSelector = parameterType.findAnnotation<Selector>()
        var nodes = rootNodes
        if (paramSelector != null) {
          nodes = nodes.select(paramSelector.selector)
        }

        if (parameterType.jvmErasure.java.isArray) {
          val elemClass =
              parameterType.findAnnotation<Elem>() ?: throw LackAnnotationException(Elem::class)
          return@map nodes.map { newObject(Elements(it), elemClass.kClass.primaryConstructor!!) }
        }
        return@map nodes.first()
      })
    }
  }
}