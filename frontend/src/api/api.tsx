class Api {

    constructor(readonly endpointRoot: string) {}

    createTodoList(title: string): Promise<TodoList | null> {
        const body = JSON.stringify({ title });
        const method = "POST";
        const headers = this.jsonRequestHeader

        return fetch(`${this.endpointRoot}/todo-lists`, { method, body, headers })
                .then(r => r.json())
                .then((json: any) => {
                    const { id, title } = json;
                    return new TodoList(id, title)
                })
                .catch(() => null)
    }

    createTodoItem(todoListId: string, title: string, description: string) {
        const body = JSON.stringify({ todoListId, title, description });
        const method = "POST";
        const headers = this.jsonRequestHeader;

        return fetch(`${this.endpointRoot}/todo-lists/${todoListId}/todo-items`, { method, body, headers })
                .then(r => r.ok ? r.json() : null)
                .then((json: any) => {
                    if (json === null) return null;

                    const { id, title, description } = json
                    return new TodoItem(id, title, description)
                })
                .catch(() => null)
    }


    private jsonRequestHeader = {
        'Accept': 'application/json',
        'Content-Type': 'application/json'
    }

}