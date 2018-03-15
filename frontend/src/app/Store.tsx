import { observable } from 'mobx';
import { TodoList, TodoItem } from '../api/models';
import { Api } from '../api/api';

export class AppState {

    @observable todoList: TodoList | null;

    @observable todoItems: TodoItem[];

    @observable showTodoItemCreationDialog: boolean = false;

    constructor(readonly api: Api, todoListId: string | null = null) {
        [this.todoItems, this.todoList] = [[], null];

        if (todoListId === null) {
            this.fillWithEmptyState();
        } else {
            this.fillStateUsing(todoListId);
        }
    }

    async postNewTodoItem(title: string, description: string) {
        if (this.todoList === null) {
            throw Error('Todo list is not yet initialized!');
        }

        return this.api.createTodoItem(this.todoList.id, title, description).then(result => {
            if (result !== null) {
                this.todoItems.push(result);
            }
            return result;
        });
    }

    async renameTodoList(newTitle: string) {
        if (this.todoList === null) {
            throw Error('Todo list is not yet initialized!');
        }

        if (newTitle === '') {
            throw Error('Title string cannot be empty.');
        }

        return this.api.renameTodoList(this.todoList.id, newTitle).then(result => {
            if (result !== null) {
                this.todoList = result;
            }
            return result;
        });
    }

    private async fillWithEmptyState() {
        this.todoList = await this.api.createTodoList('Untitled Todo List') as TodoList;
    }

    private async fillStateUsing(todoListId: string) {
        const fetchResult = await this.api.getTodoList(todoListId);
        if (fetchResult === null) {
            this.fillWithEmptyState();
        } else {
            [this.todoItems, this.todoList] = fetchResult;
        }
    }
}