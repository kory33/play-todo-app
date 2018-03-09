export class TodoItem {
    constructor(readonly id: string, readonly title: string, readonly description: string) {}
}

export class TodoList {
    constructor(readonly id: string, readonly title: string) {}
}