package com.xwl.common_lib.Ext

import java.lang.reflect.ParameterizedType

fun <VM> getVmClazz(obj: Any): VM {
    return (obj.javaClass.genericSuperclass as ParameterizedType).actualTypeArguments[0] as VM
}