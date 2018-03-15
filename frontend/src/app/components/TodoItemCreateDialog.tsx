import { AppState } from '../Store';
import * as React from 'react';
import { Dialog, FlatButton, TextField, RaisedButton, CircularProgress } from 'material-ui';
import { observer } from 'mobx-react';

interface DialogState {
  title: string;
  description: string;
  inputStarted: boolean;
  submitting: boolean;
}

@observer
export default class TodoItemCreateDialog
    extends React.Component<{ appState: AppState }, DialogState> {

  get initialState() {
    return { title: '', description: '', inputStarted: false, submitting: false };
  }
  
  constructor(props: { appState: AppState }) {
    super(props);
    this.state = this.initialState;
  }

  get titleFormErrorText(): string | null {
    if (this.state.inputStarted && this.state.title === '') {
      return 'Title is required.';
    }

    return null;
  }

  closeDialog = () => {
    this.props.appState.showTodoItemCreationDialog = false;
    this.setState(this.initialState);
  }

  postTodoItem = () => {
    this.setState({ inputStarted: true });

    if (this.state.title === '') {
      return;
    }

    this.setState({ submitting: true });

    this.props.appState.postNewTodoItem(this.state.title, this.state.description)
      .then((item) => {
        this.setState({ submitting: false });
        if (item !== null) {
          this.closeDialog();
        }
      });
  }

  render() {
    const state = this.props.appState;

    const createButtonElement = !this.state.submitting
      ? <RaisedButton key="createtodo" label="Create" primary={true} onClick={this.postTodoItem} />
      : <CircularProgress key="progress" size={20} thickness={2} />;

    const actions = [
      createButtonElement,
      <FlatButton key="cancelcreatetodo" label="Cancel" primary={true} onClick={this.closeDialog} />
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
          name="title"
          style={{
            display: 'block',
            width: '50%'
          }}
          onChange={(_: any, value: string) => this.setState({ title: value, inputStarted: true })}
          errorText={this.titleFormErrorText}
        />
        <TextField
          hintText="Todo descriptions"
          floatingLabelText="descriptions"
          multiLine={true}
          rowsMax={8}
          onChange={(_, value) => this.setState({ description: value, inputStarted: true })}
          style={{
            width: '100%'
          }}
        />
      </Dialog>
    );
  }
}