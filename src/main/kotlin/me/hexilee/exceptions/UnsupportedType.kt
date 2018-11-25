package me.hexilee.exceptions

import java.lang.RuntimeException
import kotlin.reflect.KType

class UnsupportedType(type: KType): RuntimeException("unsupported type $type")