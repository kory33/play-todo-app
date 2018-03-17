import { TodoList, TodoItem } from './models';

export class Api {

    private jsonRequestHeaders = {
        'Accept': 'application/json',
        'content-type': 'application/json'
    };

    constructor(readonly endpointRoot: string, private readonly optionalRequestInit: any = {}) {}

    async createTodoList(title: string): Promise<TodoList | null> {
        const body = JSON.stringify({ title });

        return this.issueJsonRequest(`todo-lists`, 'POST', body)
                .then(TodoList.fromObject)
                .catch(() => null);
    }

    async createTodoItem(todoListId: string, title: string, description: string): Promise<TodoItem | null> {
        const body = JSON.stringify({ title, description });

        return this.issueJsonRequest(`todo-lists/${todoListId}/todo-items`, 'POST', body)
                .then(TodoItem.fromObject)
                .catch(() => null);
    }

    async getTodoList(todoListId: string): Promise<[TodoItem[], TodoList] | null> {
        return this.issueJsonRequest(`todo-lists/${todoListId}`, 'GET')
            .then((json: any | null) => {
                if (json === null) { return null; }

                const todoList = TodoList.fromObject(json);
                const todoItems = json.todo_items.map(TodoItem.fromObject);
                return [todoItems, todoList] as [TodoItem[], TodoList];
            })
            .catch(() => null);
    }

    async renameTodoList(todoListId: string, newTitle: string): Promise<TodoList | null> {
        const body = JSON.stringify({ title: newTitle });

        return this.issueJsonRequest(`todo-lists/${todoListId}/title`, 'PUT', body)
            .then(TodoList.fromObject)
            .catch(() => null);
    }

    /**
     * Deletes the specified todo item
     * @param todoListId id of todo-list in which the item is to be deleted
     * @param todoItemId id of todo-item to be deleted
     * @returns the promise that returns `true` if deletion is successful
     */
    async deleteTodoItem(todoListId: string, todoItemId: string): Promise<boolean> {
        return this.issueRequest(`todo-lists/${todoListId}/todo-items/${todoItemId}`, 'DELETE', '')
            .then(r => r.ok);
    }

    private issueRequest(
            endpoint: string,
            method: string,
            body: string | null = null,
            headers: any = {}): Promise<Response> {
        const requestInit = Object.assign({ method, body, headers }, this.optionalRequestInit) as RequestInit;

        return fetch(`${this.endpointRoot}/${endpoint}`, requestInit);
    }

    private issueJsonRequest(
            endpoint: string,
            method: string,
            body: string | null = null,
            additionalHeaders: any = {}): Promise<any | null> {
        const headers = Object.assign(additionalHeaders, this.jsonRequestHeaders);

        return this.issueRequest(endpoint, method, body, headers)
            .then(r => r.ok ? r.json() : null);
    }

}