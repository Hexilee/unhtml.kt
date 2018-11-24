package me.hexilee.annotations

@Target(AnnotationTarget.CLASS, AnnotationTarget.PROPERTY_SETTER)
annotation class Selector(val selector: String)