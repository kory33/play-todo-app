import { TodoList, TodoItem } from './models';

export class Api {

    private jsonRequestHeader = {
        'Accept': 'application/json',
    };

    constructor(readonly endpointRoot: string, private readonly optionalRequestInit: any = {}) {}

    createTodoList(title: string): Promise<TodoList | null> {
        const body = JSON.stringify({ title });
        const method = 'POST';
        const headers = this.jsonRequestHeader;

        const requestInit = Object.assign({ method, body, headers }, this.optionalRequestInit) as RequestInit;

        return fetch(`${this.endpointRoot}/todo-lists`, requestInit)
                .then(r => r.json())
                .then((json: any | null) => {
                    const { id, title } = json;
                    return new TodoList(id, title);
                })
                .catch(() => null);
    }

    createTodoItem(todoListId: string, title: string, description: string) {
        const body = JSON.stringify({ todoListId, title, description });
        const method = 'POST';
        const headers = this.jsonRequestHeader;
        
        const requestInit = Object.assign({ method, body, headers }, this.optionalRequestInit) as RequestInit;

        return fetch(`${this.endpointRoot}/todo-lists/${todoListId}/todo-items`, requestInit)
                .then(r => r.ok ? r.json() : null)
                .then((json: any | null) => {
                    if (json === null) { return null; }

                    const { id, title, description } = json;
                    return new TodoItem(id, title, description);
                })
                .catch(() => null);
    }

}