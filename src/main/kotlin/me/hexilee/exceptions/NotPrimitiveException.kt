package me.hexilee.exceptions

import java.lang.RuntimeException
import kotlin.reflect.KClass

class NotPrimitiveException(javaClass: Class<*>): RuntimeException("lack of annotations: $javaClass")