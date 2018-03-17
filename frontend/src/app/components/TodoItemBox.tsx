import { TodoItem } from '../../api/models';
import * as React from 'react';
import { Paper, RaisedButton } from 'material-ui';
import { AppState } from '../Store';

export class TodoItemBox extends React.Component<{ todoItem: TodoItem, appState: AppState }, {}> {
  render() {
    const todoItem = this.props.todoItem;
    const state = this.props.appState;

    return (
      <Paper className="todo-item" zDepth={2}>
        <div className="todo-item-title">{todoItem.title}</div>
        {todoItem.description !== '' ? <hr className="todo-item-divider" /> : null}
        <div className="todo-item-description">{todoItem.description}</div>
        <Paper className="todo-item-overlay">
          <RaisedButton backgroundColor="#ccc" label="delete" onClick={() => state.deleteTodoItem(todoItem)} />
        </Paper>
      </Paper>
    );
  }
}