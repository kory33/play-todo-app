import { AppState } from '../Store';
import * as React from 'react';
import { Toolbar, ToolbarGroup, RaisedButton } from 'material-ui';
import { observer } from 'mobx-react';

@observer
export default class AppToolbar extends React.Component<{ appState: AppState }, {}> {
  render() {
    const state = this.props.appState;

    if (state.todoList === null) {
      throw Error('Todo list is null.');
    }

    return (
      <Toolbar className="app-header">
        <ToolbarGroup firstChild={true}>
          {state.todoList.title}
        </ToolbarGroup>
        <ToolbarGroup>
          <RaisedButton label="Create Todo" primary={true} onClick={() => state.showTodoItemCreationDialog = true} />
        </ToolbarGroup>
      </Toolbar>
    );
  }
}