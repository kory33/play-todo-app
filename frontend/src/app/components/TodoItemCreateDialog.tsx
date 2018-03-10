import { AppState } from '../Store';
import * as React from 'react';
import { Dialog, FlatButton, TextField } from 'material-ui';
import { observer } from 'mobx-react';

@observer
export default class TodoItemCreateDialog extends React.Component<{ appState: AppState }, {}> {

  private inputTitle: string = '';
  private inputDescription: string = '';

  closeDialog = () => {
    this.props.appState.showTodoItemCreationDialog = false;
  }

  postTodoItem = () => {
    if (this.inputTitle === '') {
      return;
    }

    this.props.appState.postNewTodoItem(this.inputTitle, this.inputDescription);
    this.closeDialog();
  }

  render() {
    const state = this.props.appState;

    const actions = [
      <FlatButton key="cancelcreatetodo" label="Cancel" primary={true} onClick={this.closeDialog} />,
      <FlatButton key="createtodo" label="Create" primary={true} onClick={this.postTodoItem} />
    ];

    return (
      <Dialog
        title="Create Todo Item"
        actions={actions}
        modal={false}
        autoScrollBodyContent={true}
        open={state.showTodoItemCreationDialog}
        onRequestClose={this.closeDialog}
      >
        <TextField
          hintText="Todo title"
          onChange={(e) => this.inputTitle = (e.target as any).value}
        />
        <TextField
          hintText="Todo descriptions"
          floatingLabelText="descriptions"
          multiLine={true}
          onChange={(e) => this.inputDescription = (e.target as any).value}
        />
      </Dialog>
    );
  }
}