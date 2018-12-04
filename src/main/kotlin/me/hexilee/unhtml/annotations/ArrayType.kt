package me.hexilee.unhtml.annotations

import kotlin.reflect.KClass

@Target(AnnotationTarget.PROPERTY_SETTER, AnnotationTarget.VALUE_PARAMETER)
annotation class ArrayType (
  val elemClass: KClass<*>
)