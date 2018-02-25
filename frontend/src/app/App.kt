package app

import react.*
import react.dom.*

class App : RComponent<RProps, RState>() {
    override fun RBuilder.render() {
        div("App-header") {
            h2 {
                +"Header test"
            }
        }
    }
}

fun RBuilder.app() = child(App::class) {}
