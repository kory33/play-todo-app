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
                .then((json: any | null) => {
                    const { id, title } = json;
                    return new TodoList(id, title);
                })
                .catch(() => null);
    }

    async createTodoItem(todoListId: string, title: string, description: string): Promise<TodoItem | null> {
        const body = JSON.stringify({ title, description });

        return this.issueRequest(`todo-lists/${todoListId}/todo-items`, 'POST', body)
                .then((json: any | null) => {
                    if (json === null) { return null; }

                    const { id, title, description } = json;
                    return new TodoItem(id, title, description);
                })
                .catch(() => null);
    }

    async getTodoItems(todoListId: string): Promise<TodoItem[] | null> {
        return this.issueRequest(`todo-lists/${todoListId}`, 'GET')
            .then((json: any | null) => {
                if (json === null) { return null; }
                const items = json.todo_items as any[];
                return items.map(({ id, title, description }) => new TodoItem(id, title, description));
            })
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