package me.hexilee

interface Converter {
  operator fun <T: Any> invoke(instance: T): T
}