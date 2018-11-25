package me.hexilee

import me.hexilee.annotations.InnerText
import me.hexilee.annotations.Selector
import me.hexilee.annotations.Value
import me.hexilee.exceptions.LackAnnotationException
import me.hexilee.exceptions.NotPrimitiveException
import me.hexilee.exceptions.UnsupportedType
import org.jsoup.select.Elements
import kotlin.reflect.KFunction
import kotlin.reflect.full.findAnnotation
import kotlin.reflect.full.primaryConstructor
import kotlin.reflect.jvm.jvmErasure

fun <T : Any> newData(rootNodes: Elements, primaryConstructor: KFunction<T>): T =
    primaryConstructor.run {
      call(parameters.stream().map {
        val paramSelector = it.findAnnotation<Selector>()
        val nodes =
            if (paramSelector == null) rootNodes else rootNodes.select(paramSelector.selector)

        if (it.type.jvmErasure.java.isArray) {

        }

        if (it.type.jvmErasure.isData) {
          return@map newData(nodes, it.type.jvmErasure.primaryConstructor!!)
        }

        val valueAttr = it.findAnnotation<Value>() ?: throw LackAnnotationException(Value::class)
        val rawValue =
            if (valueAttr.attr === InnerText && nodes.hasText()) nodes.text() else nodes.attr(
                valueAttr.attr)

        if (it.type.jvmErasure.java.isPrimitive) {
          return@map if (it.type.isMarkedNullable) newNullablePrimitive(rawValue,
              it.type.jvmErasure.java) else newPrimitive(rawValue, it.type.jvmErasure.java)
        }

        if (it.type.jvmErasure.java.isAssignableFrom(String::class.java)) {
          return@map rawValue
        }
        throw UnsupportedType(it.type)
      })
    }

/**
 * Boolean#TYPE
 * Char#TYPE
 * Byte#TYPE
 * Short#TYPE
 * Int#TYPE
 * Long#TYPE
 * Float#TYPE
 * Double#TYPE
 */
fun newPrimitive(value: String, javaClass: Class<*>): Any = when (javaClass) {
  Boolean::class.java -> value.toBoolean()
  Char::class.java -> value.single()
  Byte::class.java -> value.toByte()
  Short::class.java -> value.toShort()
  Int::class.java -> value.toInt()
  Long::class.java -> value.toLong()
  Float::class.java -> value.toFloat()
  Double::class.java -> value.toDouble()
  else -> throw NotPrimitiveException(javaClass)
}

fun newNullablePrimitive(value: String, javaClass: Class<*>): Any? = when (javaClass) {
  Boolean::class.java -> value.toBoolean()
  Char::class.java -> value.singleOrNull()
  Byte::class.java -> value.toByteOrNull()
  Short::class.java -> value.toShortOrNull()
  Int::class.java -> value.toIntOrNull()
  Long::class.java -> value.toLongOrNull()
  Float::class.java -> value.toFloatOrNull()
  Double::class.java -> value.toDoubleOrNull()
  else -> throw NotPrimitiveException(javaClass)
}