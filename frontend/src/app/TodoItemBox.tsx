import { TodoItem } from "../api/models";
import * as React from "react";

export class TodoItemBox extends React.Component<{ todoItem: TodoItem }, {}> {
  render() {
    return (
      <div className="todo-item-box">
        <div className="todo-item-title">
          { this.props.todoItem.title }
        </div>
        <div className="todo-item-description">
          { this.props.todoItem.description }
        </div>
      </div>
    );
  }
}