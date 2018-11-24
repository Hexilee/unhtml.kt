package me.hexilee.annotations

@Target(AnnotationTarget.CLASS, AnnotationTarget.PROPERTY_SETTER, AnnotationTarget.VALUE_PARAMETER)
annotation class Selector(val selector: String)