package com.danneu.kobx.mobx

import react.RProps
import react.RState
import react.React

external fun <P: RProps, S: RState> observer(component: React.Component<P, S>): React.Component<P, S>

external fun observable(value: String): ObservableBox<String>
external fun observable(value: Int): ObservableBox<Int>
external fun <T> observable(value: T): ObservableBox<T>
external fun <A> observable(array: Array<A>): ObservableArray<A>
inline fun <reified A> observable(list: List<A>): ObservableArray<A> = observable(list.toTypedArray())

external fun autorun(block: () -> Unit)

external fun extendObservable(instance: Any, props: dynamic)

// https://mobxjs.github.io/mobx/refguide/boxed.html
external class ObservableBox <T> {
    fun get(): T
    fun set(newValue: T)
}

@JsName("ObservableArray")
external class ObservableArray <A> {
    // Javascript array methods

    fun push(item: A)
    val length: Int
    fun <B> map(xform: (A) -> B): Array<B>
    fun forEach(process: (A) -> Unit)

    // Mobx's array extensions

    fun remove(value: A): Boolean
}

/**
 * This interface exists for observables (stores) to call `init { activate() }`,
 * injecting the mobx interop into their class during initialization.
 * Must be called after all observable properties and computer getters are defined,
 * else it won't see them.
 *
 * Would be cool to have mobx's @observable, @computed, etc., but kotlin
 * doesn't yet support annotation reflection in javascript.
 */
interface Observable {
    fun activate() {
        val props = Object.getOwnPropertyDescriptors(this)

        Object.keys(props)
                // Skip internal mobx functions
                .filter { key -> key[0] != '$' }
                .forEach { key ->
                    val value = props[key].value
                    val mapping = objectOf(key to value)
                    println("key = $key, value = $value, mapping = ${JSON.stringify(mapping)})")
                    extendObservable(this, mapping)
                }
    }
}

private fun objectOf(vararg pairs: Pair<String, Any>): Object {
    val obj: dynamic = object {}
    pairs.forEach { (k, v) -> obj[k] = v }
    return obj
}