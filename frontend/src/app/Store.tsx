import { observable } from 'mobx';
import { TodoList, TodoItem } from '../api/models';
import { Api } from '../api/api';

export class AppState {

    @observable todoList: TodoList | null;

    @observable todoItems: TodoItem[];

    constructor(readonly api: Api) {
        this.todoItems = [];
        this.todoList = null;

        this.createEmptyState();
    }

    async postNewTodoItem(title: string, description: string) {
        if (this.todoList === null) {
            throw Error('Todo list is not yet initialized!');
        }

        this.api.createTodoItem(this.todoList.id, title, description).then(result => {
            if (result !== null) {
                this.todoItems.push(result);
            }
        });
    }

    private async createEmptyState() {
        this.todoList = await this.api.createTodoList('Untitled Todo List') as TodoList;
    }

}