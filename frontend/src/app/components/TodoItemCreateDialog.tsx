import { AppState } from '../Store';
import * as React from 'react';
import { Dialog, FlatButton, TextField, RaisedButton } from 'material-ui';
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

    this.props.appState.postNewTodoItem(this.inputTitle, this.inputDescription)
      .then(() => this.closeDialog());
  }

  render() {
    const state = this.props.appState;

    const actions = [
      <FlatButton key="cancelcreatetodo" label="Cancel" primary={true} onClick={this.closeDialog} />,
      <RaisedButton key="createtodo" label="Create" primary={true} onClick={this.postTodoItem} />
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
          floatingLabelText="title"
          onChange={(_, value) => this.inputTitle = value}
          style={{
            display: 'block',
            width: '50%'
          }}
        />
        <TextField
          hintText="Todo descriptions"
          floatingLabelText="descriptions"
          multiLine={true}
          rowsMax={8}
          onChange={(_, value) => this.inputDescription = value}
          style={{
            width: '100%'
          }}
        />
      </Dialog>
    );
  }
}