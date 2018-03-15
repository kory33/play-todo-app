import { TodoList, TodoItem } from './models';

export class Api {

    private jsonRequestHeader = {
        'Accept': 'application/json',
        'content-type': 'application/json'
    };

    constructor(readonly endpointRoot: string, private readonly optionalRequestInit: any = {}) {}

    async createTodoList(title: string): Promise<TodoList | null> {
        const body = JSON.stringify({ title });

        return this.issueRequest(`todo-lists`, 'POST', body)
                .then(TodoList.fromObject)
                .catch(() => null);
    }

    async createTodoItem(todoListId: string, title: string, description: string): Promise<TodoItem | null> {
        const body = JSON.stringify({ title, description });

        return this.issueRequest(`todo-lists/${todoListId}/todo-items`, 'POST', body)
                .then(TodoItem.fromObject)
                .catch(() => null);
    }

    async getTodoList(todoListId: string): Promise<[TodoItem[], TodoList] | null> {
        return this.issueRequest(`todo-lists/${todoListId}`, 'GET')
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

        return this.issueRequest(`todo-lists/${todoListId}/title`, 'PUT', body)
            .then(TodoList.fromObject)
            .catch(() => null);
    }

    private issueRequest(
            endpoint: string,
            method: string,
            body: string | null = null,
            optionalHeaders: any = {}): Promise<any | null> {
        const headers = Object.assign(optionalHeaders, this.jsonRequestHeader);
        const requestInit = Object.assign({ method, body, headers}, this.optionalRequestInit) as RequestInit;

        return fetch(`${this.endpointRoot}/${endpoint}`, requestInit)
            .then(r => r.ok ? r.json() : null);
    }

}