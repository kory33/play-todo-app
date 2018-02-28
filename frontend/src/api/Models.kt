package api

import react.RProps

data class Reply(val id: Long, val content: String): RProps

data class ItemTag(val id: Long, val name: String, val color: String): RProps

data class TodoItem(val id: Long, val title: String, val description: String, val tags: List<ItemTag>?, val replies: List<Reply>): RProps

data class TodoList(val id: String, val title: String, val todoItems: List<TodoItem>?, val tags: List<ItemTag>?): RProps

