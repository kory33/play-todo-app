package api

/**
 * REST API wrapper for the play-todo-app.
 */
interface ApiWrapper {

    fun createTodoList(title: String): TodoList

    fun fetchTodoList(id: String): TodoList

    fun createTodoItem(todoList: TodoList, title: String): TodoItem

    fun getTodoItems(list: TodoList): List<TodoItem>

    fun TodoList.populateWithItems(): TodoList = TodoList(id, title, getTodoItems(this), tags)

}