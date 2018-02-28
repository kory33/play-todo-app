package app

import api.ApiWrapper
import api.TodoItem
import api.TodoList
import kotlinx.html.Tag
import react.RProps

/**
 * Props that are needed directly by the app screen.
 */
interface ScreenProps : RProps {

    val api: ApiWrapper

    val todoList: TodoList

    val todoItems: List<TodoItem>

    val tags: List<Tag>

}