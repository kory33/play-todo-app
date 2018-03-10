import { AppState } from '../Store';
import * as React from 'react';
import { Dialog } from 'material-ui';

class TodoItemCreateBox extends React.Component<{ appState: AppState }, {}> {
  private inputTitle: string = '';
  private inputDescription: string = '';

  render() {
    return (
      <div className="todo-item-create-box">
        <input
          className="todo-item-create-input-title"
          type="text"
          onChange={(e: any) => this.inputTitle = e.target.value}
        />
        <input
          className="todo-item-create-input-description"
          type="text"
          onChange={(e: any) => this.inputDescription = e.target.value}
        />
        <button onClick={() => this.props.appState.postNewTodoItem(this.inputTitle, this.inputDescription)}>
          Create new item
        </button>
      </div>
    );
  }
}

export default class TodoItemCreateDialog extends React.Component<{ appState: AppState }, {}> {
  render() {
    const state = this.props.appState;

    return (
      <Dialog
        title="Create Todo Item"
        actions={[]}
        modal={false}
        open={state.showTodoItemCreationDialog}
        onRequestClose={() => { state.showTodoItemCreationDialog = false; }}
      >
        <TodoItemCreateBox appState={state} />      
      </Dialog>
    );
  }
}