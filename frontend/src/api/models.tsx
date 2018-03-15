export class TodoItem {
    static fromObject(object: any | null): TodoItem | null {
        if (object === null) { return null; }

        const { id, title, description } = object;
        return new TodoItem(id, title, description);
    }

    constructor(readonly id: string, readonly title: string, readonly description: string) {}
}

export class TodoList {
    static fromObject(object: any | null): TodoList | null {
        if (object === null) { return null; }

        const { id, title } = object;
        return new TodoList(id, title);
    }

    constructor(readonly id: string, readonly title: string) {}
}