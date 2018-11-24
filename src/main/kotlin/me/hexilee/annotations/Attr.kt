package me.hexilee.annotations

@Target(AnnotationTarget.CLASS, AnnotationTarget.PROPERTY_SETTER)
annotation class Attr(val attr: String)