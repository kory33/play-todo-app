package api

import kotlin.js.Promise

/**
 * REST API wrapper for the play-todo-app.
 */
interface ApiWrapper {

    fun createTodoList(title: String): Promise<TodoList?>

    fun fetchTodoList(id: String): Promise<TodoList?>

    fun createTodoItem(todoList: TodoList, title: String): Promise<TodoItem?>

    fun getTodoItems(list: TodoList): Promise<List<TodoItem>?>

}