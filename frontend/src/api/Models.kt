package api

import react.RProps

data class ItemTag(val id: Long, val name: String, val color: String): RProps

data class TodoItem(val id: Long, val title: String, val description: String): RProps

data class TodoList(val id: String, val title: String): RProps

