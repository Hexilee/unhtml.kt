package me.hexilee.annotations

import kotlin.reflect.KClass

@Target(AnnotationTarget.PROPERTY_SETTER, AnnotationTarget.VALUE_PARAMETER)
annotation class Elem(val kClass: KClass<*>)