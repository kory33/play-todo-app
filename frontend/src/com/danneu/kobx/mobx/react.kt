package com.danneu.kobx.mobx

import org.w3c.dom.Element

interface VNode

abstract external class Component {
    abstract fun render(): VNode
}

external fun render(vNode: VNode, domNode: Element)


external fun h(nodeName: String, attrs: Object? = definedExternally, vararg kids: Any?): VNode
external fun h(jsClass: JsClass<*>, attrs: Object? = definedExternally, vararg kids: Any?): VNode

fun h(nodeName: String, attrs: Map<String, Any>, vararg kids: Any?): VNode {
    // A List child will throw a runtime error, so preprocess lists into arrays before passing them on
    // to the external functions.
    return h(nodeName, attrs.toJsObject(), *kids.map { kid ->
        when (kid) {
            is List<*> ->
                kid.toTypedArray()
            else ->
                kid
        }
    }.toTypedArray())
}
