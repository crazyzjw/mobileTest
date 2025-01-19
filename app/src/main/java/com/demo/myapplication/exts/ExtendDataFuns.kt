package com.demo.myapplication.exts

import com.google.gson.Gson

/**
 * json字符串转对象
 */
fun <T>String.toBean(clazz: Class<T>):T{
    return Gson().fromJson<T>(this, clazz)
}
