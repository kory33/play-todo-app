package app

import api.TodoItem
import api.TodoList
import com.danneu.kobx.mobx.Observable
import kotlinx.html.Tag


class Store(var todoList: TodoList,
            var todoItems: List<TodoItem>,
            var tags: List<Tag>) : Observable {

    init { activate() }

}

// TODO instanciate somewhere
lateinit var store: Store