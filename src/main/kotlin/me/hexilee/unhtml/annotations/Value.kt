package me.hexilee.unhtml.annotations

const val InnerText = "innerText"

@Target(AnnotationTarget.PROPERTY_SETTER, AnnotationTarget.VALUE_PARAMETER)
annotation class Value(
  val attr: String = InnerText
)