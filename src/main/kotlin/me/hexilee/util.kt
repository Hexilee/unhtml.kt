package me.hexilee

import me.hexilee.annotations.ArrayType
import me.hexilee.annotations.InnerText
import me.hexilee.annotations.Selector
import me.hexilee.annotations.Value
import me.hexilee.exceptions.LackAnnotationException
import me.hexilee.exceptions.NotPrimitiveException
import me.hexilee.exceptions.UnsupportedType
import org.jsoup.select.Elements
import kotlin.reflect.KFunction
import kotlin.reflect.KType
import kotlin.reflect.full.createType
import kotlin.reflect.full.findAnnotation
import kotlin.reflect.full.primaryConstructor
import kotlin.reflect.jvm.jvmErasure

fun <T : Any> newData(rootNodes: Elements, primaryConstructor: KFunction<T>): T =
    primaryConstructor.run {
      call(*(parameters.stream().map {
        val paramSelector = it.findAnnotation<Selector>()
        val nodes =
            if (paramSelector == null) rootNodes else rootNodes.select(paramSelector.selector)

        if (it.type.jvmErasure.java.isArray) {
          val arrayType =
              it.findAnnotation<ArrayType>() ?: throw LackAnnotationException(ArrayType::class)
          if (arrayType.elemClass.isData) {
            return@map newDataArray(nodes, arrayType.elemClass.createType()) as Any?
          }

          val valueAttr = it.findAnnotation<Value>() ?: throw LackAnnotationException(Value::class)
          if (arrayType.elemClass.java.isPrimitive) {
            if (!arrayType.elemClass.createType().isMarkedNullable) {
              when(arrayType.elemClass.java) {
                Boolean::class.java -> return@map newBooleanArray(nodes, valueAttr)
                Char::class.java -> return@map newCharArray(nodes, valueAttr)
                Byte::class.java -> return@map newByteArray(nodes, valueAttr)
                Short::class.java -> return@map newShortArray(nodes, valueAttr)
                Int::class.java -> return@map newIntArray(nodes, valueAttr)
                Long::class.java -> return@map newLongArray(nodes, valueAttr)
                Float::class.java -> return@map newFloatArray(nodes, valueAttr)
                Double::class.java -> return@map newDoubleArray(nodes, valueAttr)
                else -> throw NotPrimitiveException(arrayType.elemClass.java)
              }
            } else {
              when(arrayType.elemClass.java) {
                Boolean::class.java -> return@map newNullableBooleanArray(nodes, valueAttr)
                Char::class.java -> return@map newNullableCharArray(nodes, valueAttr)
                Byte::class.java -> return@map newNullableByteArray(nodes, valueAttr)
                Short::class.java -> return@map newNullableShortArray(nodes, valueAttr)
                Int::class.java -> return@map newNullableIntArray(nodes, valueAttr)
                Long::class.java -> return@map newNullableLongArray(nodes, valueAttr)
                Float::class.java -> return@map newNullableFloatArray(nodes, valueAttr)
                Double::class.java -> return@map newNullableDoubleArray(nodes, valueAttr)
                else -> throw NotPrimitiveException(arrayType.elemClass.java)
              }
            }
          }
          if (arrayType.elemClass.java.isAssignableFrom(String::class.java)) {
            return@map newStringArray(nodes, valueAttr) as Array<String>
          }
          throw UnsupportedType(arrayType.elemClass.createType())
        }

        if (it.type.jvmErasure.isData) {
          return@map newData(nodes, it.type.jvmErasure.primaryConstructor!!)
        }

        val valueAttr = it.findAnnotation<Value>() ?: throw LackAnnotationException(Value::class)
        val rawValue =
            if (valueAttr.attr == InnerText && nodes.hasText()) nodes.text() else nodes.attr(
                valueAttr.attr)
        if (it.type.jvmErasure.java.isPrimitive) {
          return@map if (it.type.isMarkedNullable) newNullablePrimitive(rawValue,
              it.type.jvmErasure.java) else newPrimitive(rawValue, it.type.jvmErasure.java)
        }

        if (it.type.jvmErasure.java.isAssignableFrom(String::class.java)) {
          return@map rawValue
        }
        throw UnsupportedType(it.type)
      }.toArray()))
    }

fun newDataArray(nodes: Elements, elemType: KType) = nodes.map {
  return@map newData(Elements(it), elemType.jvmErasure.primaryConstructor!!)
}.toTypedArray()

fun newBooleanArray(nodes: Elements, valueAttr: Value) = nodes.map {
  return@map (if (valueAttr.attr == InnerText && it.hasText()) it.text() else it.attr(
      valueAttr.attr))!!.toBoolean()
}.toTypedArray()

fun newCharArray(nodes: Elements, valueAttr: Value) = nodes.map {
  return@map (if (valueAttr.attr == InnerText && it.hasText()) it.text() else it.attr(
      valueAttr.attr))!!.single()
}.toTypedArray()

fun newByteArray(nodes: Elements, valueAttr: Value) = nodes.map {
  return@map (if (valueAttr.attr == InnerText && it.hasText()) it.text() else it.attr(
      valueAttr.attr))!!.toByte()
}.toTypedArray()

fun newShortArray(nodes: Elements, valueAttr: Value) = nodes.map {
  return@map (if (valueAttr.attr == InnerText && it.hasText()) it.text() else it.attr(
      valueAttr.attr))!!.toShort()
}.toTypedArray()

fun newIntArray(nodes: Elements, valueAttr: Value) = nodes.map {
  return@map (if (valueAttr.attr == InnerText && it.hasText()) it.text() else it.attr(
      valueAttr.attr))!!.toInt()
}.toTypedArray()

fun newLongArray(nodes: Elements, valueAttr: Value) = nodes.map {
  return@map (if (valueAttr.attr == InnerText && it.hasText()) it.text() else it.attr(
      valueAttr.attr))!!.toLong()
}.toTypedArray()

fun newFloatArray(nodes: Elements, valueAttr: Value) = nodes.map {
  return@map (if (valueAttr.attr == InnerText && it.hasText()) it.text() else it.attr(
      valueAttr.attr))!!.toFloat()
}.toTypedArray()

fun newDoubleArray(nodes: Elements, valueAttr: Value) = nodes.map {
  return@map (if (valueAttr.attr == InnerText && it.hasText()) it.text() else it.attr(
      valueAttr.attr))!!.toDouble()
}.toTypedArray()

fun newNullableBooleanArray(nodes: Elements, valueAttr: Value) = nodes.map {
  return@map (if (valueAttr.attr == InnerText && it.hasText()) it.text() else it.attr(
      valueAttr.attr))?.toBoolean()
}.toTypedArray()

fun newNullableCharArray(nodes: Elements, valueAttr: Value) = nodes.map {
  return@map (if (valueAttr.attr == InnerText && it.hasText()) it.text() else it.attr(
      valueAttr.attr))?.singleOrNull()
}.toTypedArray()

fun newNullableByteArray(nodes: Elements, valueAttr: Value) = nodes.map {
  return@map (if (valueAttr.attr == InnerText && it.hasText()) it.text() else it.attr(
      valueAttr.attr))?.toByteOrNull()
}.toTypedArray()

fun newNullableShortArray(nodes: Elements, valueAttr: Value) = nodes.map {
  return@map (if (valueAttr.attr == InnerText && it.hasText()) it.text() else it.attr(
      valueAttr.attr))?.toShortOrNull()
}.toTypedArray()

fun newNullableIntArray(nodes: Elements, valueAttr: Value) = nodes.map {
  return@map (if (valueAttr.attr == InnerText && it.hasText()) it.text() else it.attr(
      valueAttr.attr))?.toIntOrNull()
}.toTypedArray()

fun newNullableLongArray(nodes: Elements, valueAttr: Value) = nodes.map {
  return@map (if (valueAttr.attr == InnerText && it.hasText()) it.text() else it.attr(
      valueAttr.attr))?.toLongOrNull()
}.toTypedArray()

fun newNullableFloatArray(nodes: Elements, valueAttr: Value) = nodes.map {
  return@map (if (valueAttr.attr == InnerText && it.hasText()) it.text() else it.attr(
      valueAttr.attr))?.toFloatOrNull()
}.toTypedArray()

fun newNullableDoubleArray(nodes: Elements, valueAttr: Value) = nodes.map {
  return@map (if (valueAttr.attr == InnerText && it.hasText()) it.text() else it.attr(
      valueAttr.attr))?.toDoubleOrNull()
}.toTypedArray()

//fun newPrimitiveArray(nodes: Elements, valueAttr: Value) = nodes.map {
//  val rawValue =
//      if (valueAttr.attr == InnerText && it.hasText()) it.text() else it.attr(
//          valueAttr.attr)
//  return@map if (elemType.isMarkedNullable) newNullablePrimitive(rawValue,
//      elemType.jvmErasure.java) else newPrimitive(rawValue, elemType.jvmErasure.java)
//}.toTypedArray()

fun newStringArray(nodes: Elements, valueAttr: Value) = nodes.map {
  return@map if (valueAttr.attr == InnerText && it.hasText()) it.text() else it.attr(
      valueAttr.attr)
}.toTypedArray()

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