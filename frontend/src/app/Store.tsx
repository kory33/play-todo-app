import { observable } from 'mobx';
import { TodoList, TodoItem } from "../api/models";
import { Api } from "../api/api";

export class AppState {

    @observable todoList: TodoList

    @observable todoItems: TodoItem[]

    constructor(readonly api: Api) {
        this.createEmptyState()
    }

    private async createEmptyState() {
        this.todoList = await this.api.createTodoList("Untitled Todo List") as TodoList
        this.todoItems = []
    }

}