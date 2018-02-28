package app

import api.TodoItem
import api.TodoList
import kotlinx.html.InputType
import kotlinx.html.js.onChangeFunction
import kotlinx.html.js.onClickFunction
import org.w3c.dom.HTMLInputElement
import react.RBuilder
import react.RComponent
import react.RState
import react.dom.button
import react.dom.div
import react.dom.input
import react.setState

/**
 * Component representing a todo-item
 */
class TodoItemComponent(props: TodoItem): RComponent<TodoItem, RState>(props) {

    override fun RBuilder.render() {
        div("todo-item") {
            div("item-title") { props.title }
            div("item-description") { props.description }
        }
    }

}


/**
 * Set of components which allows user to create a todo-item
 */
class TodoItemCreator(screenProps: ScreenProps) : RComponent<ScreenProps, TodoItemCreatorState>(screenProps) {

    override fun RBuilder.render() {
        input(InputType.text) {
            attrs {
                value = state.inputText
                placeholder = "Todo item name"
                onChangeFunction = { e ->
                    setState {
                        inputText = (e.target as HTMLInputElement).value
                        displayEmptyInputError = false
                    }
                }
            }
        }

        button {
            +"Add"
            attrs {
                onClickFunction = {
                    if (state.inputText.isNotEmpty()) {
                        // TODO post to todo-item creation endpoint
                    } else {
                        setState {
                            displayEmptyInputError = true
                        }
                    }
                }
            }
        }

        if (state.displayEmptyInputError) {
            div("error") {
                +"Please enter a title for the todo-item."
            }
        }
    }

}


/**
 * Header that displays / allows user to edit the name of the todo-list
 */
class TodoListHeader(props: TodoList): RComponent<TodoList, RState>(props) {

    override fun RBuilder.render() {
        TODO("not implemented")
    }

}