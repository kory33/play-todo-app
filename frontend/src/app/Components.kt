package app

import api.TodoItem
import api.TodoList
import react.RBuilder
import react.RComponent
import react.RProps
import react.RState
import react.dom.div

/**
 * Component representing a todo-item
 */
class TodoItemComponent(props: TodoItem): RComponent<TodoItem, RState>(props) {

    override fun RBuilder.render() {
        div("item-title") { props.title }
        div("item-description") { props.description }
    }

}


/**
 * Set of components which allows user to create a todo-item
 */
class TodoItemCreator : RComponent<RProps, RState>() {

    override fun RBuilder.render() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}


/**
 * Header that displays / allows user to edit the name of the todo-list
 */
class TodoListHeader(props: TodoList): RComponent<TodoList, RState>(props) {

    override fun RBuilder.render() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}