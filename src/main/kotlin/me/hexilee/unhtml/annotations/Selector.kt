package me.hexilee.unhtml.annotations

@Target(AnnotationTarget.PROPERTY_SETTER, AnnotationTarget.VALUE_PARAMETER)
annotation class Selector(
  val selector: String
)