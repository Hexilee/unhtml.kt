package me.hexilee.unhtml.exceptions

import java.lang.RuntimeException

class NotPrimitiveException(javaClass: Class<*>): RuntimeException("$javaClass is not primitive")