package com.danneu.kobx.mobx

@JsName("Object")
external class Object {
    companion object {
        fun getOwnPropertyDescriptors(obj: dynamic): dynamic
        fun keys(obj: dynamic): Array<String>
    }
}

fun Map<*, *>.toJsObject(): Object {
    val obj: dynamic = object {}
    this.entries.forEach { (k, v) ->
        if (v is Map<*, *>) {
            obj[k] = v.toJsObject()
        } else {
            obj[k] = v
        }
    }
    return obj
}
