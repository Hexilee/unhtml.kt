package me.hexilee.exceptions

import java.lang.RuntimeException
import kotlin.reflect.KClass

class LackAnnotationException(vararg classes: KClass<*>): RuntimeException("lack of annotations: $classes")