package me.hexilee

import kotlin.reflect.KType
import kotlin.reflect.full.createType
import kotlin.reflect.full.isSubtypeOf

fun isArray(type: KType): Boolean {
  return type.isSubtypeOf(Array<Any>::class.createType())
}