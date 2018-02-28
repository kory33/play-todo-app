package app

import react.RBuilder
import react.RComponent
import react.RState
import react.dom.div

class App(props: ScreenProps) : RComponent<ScreenProps, RState>(props) {

    override fun RBuilder.render() {
        div("todo-list-header") { TodoListHeader(props.todoList) }
        div("create-item") { TodoItemCreator() }
        div("todo-items") { props.todoItems.map { TodoItemComponent(it) } }
    }

}

fun RBuilder.app() = child(App::class) {}
