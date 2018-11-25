package me.hexilee

import me.hexilee.exceptions.NotPrimitiveException

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