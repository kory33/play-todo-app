import { AppState } from '../Store';
import * as React from 'react';
import { Toolbar, ToolbarGroup, RaisedButton, TextField, CircularProgress } from 'material-ui';
import { observer } from 'mobx-react';
import { TodoList } from '../../api/models';

interface ToolBarState {
  titleInput: string;
  updatingTitle: boolean;
}

@observer
export default class AppToolbar extends React.Component<{ appState: AppState }, ToolBarState> {

  titleField: TextField | null;

  constructor(props: { appState: AppState }) {
    super(props);

    const todoList = this.props.appState.todoList;
    const displayTitle = todoList === null ? '' : todoList.title;

    this.state = { titleInput: displayTitle, updatingTitle: false };
  }

  handleOnTitleFieldBlur = (activeTodoList: TodoList) => () => {
    const titleInput = this.state.titleInput;
    const oldTitle = activeTodoList.title;

    if (titleInput !== '' && titleInput !== oldTitle) {
      this.setState({ updatingTitle: true });
      this.props.appState
        .renameTodoList(titleInput)
        .then(() => this.setState({ updatingTitle: false }));
    } else if (titleInput === '') {
      this.setState({ titleInput: oldTitle });
    }
  }

  render() {
    const appState = this.props.appState;
    const activeTodoList = appState.todoList;

    if (activeTodoList === null) {
      throw Error('Todo list is null.');
    }

    return (
      <Toolbar className="app-header">
        <ToolbarGroup
          firstChild={true}
          style={{ marginLeft: 20 }}
        >
          <TextField
            value={this.state.titleInput}
            name="titlefield"
            ref={(field) => {this.titleField = field; }}
            onChange={(e, newValue) => this.setState({ titleInput: newValue })}
            onBlur={this.handleOnTitleFieldBlur(activeTodoList)}
            inputStyle={{ fontSize: 22 }}
            onKeyDown={(e) => {
              if (e.key === 'Enter' && this.titleField !== null) { this.titleField.blur(); }
            }}
          />
          {this.state.updatingTitle ? <CircularProgress size={20} thickness={2} /> : null}
        </ToolbarGroup>
        <ToolbarGroup>
          <RaisedButton label="Create Todo" primary={true} onClick={() => appState.showTodoItemCreationDialog = true} />
        </ToolbarGroup>
      </Toolbar>
    );
  }

}