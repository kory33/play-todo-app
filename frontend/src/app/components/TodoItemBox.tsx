import { TodoItem } from '../../api/models';
import * as React from 'react';
import { Paper } from 'material-ui';

export class TodoItemBox extends React.Component<{ todoItem: TodoItem }, {}> {
  render() {
    const {title, description} = this.props.todoItem;

    return (
      <Paper className="todo-item" zDepth={2}>
        <div className="todo-item-title">
          {title}
        </div>
        {description !== '' ? <hr className="todo-item-divider" /> : null}
        <div className="todo-item-description">
          {description}
        </div>
      </Paper>
    );
  }
}