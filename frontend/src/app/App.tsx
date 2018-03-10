import * as React from 'react';
import './App.css';
import { observer } from 'mobx-react';
import { AppState } from './Store';
import CircularProgress from 'material-ui/CircularProgress';
import { TodoItemCreateBox } from './TodoItemCreateBox';
import { TodoItemBox } from './TodoItemBox';
import { Dialog } from 'material-ui';
import AppToolbar from './components/AppToolbar';

const LoadingScreen = () => (
  <div className="loading-screen">
    <CircularProgress size={80} thickness={5} />
  </div>
);

@observer
export default class App extends React.Component<{ appState: AppState }, {}> {
  render() {
    const state = this.props.appState;

    if (state.todoList === null) {
      return <LoadingScreen />;
    }

    return (
      <div className="app">
        <AppToolbar appState={state}/>
        <Dialog
          title="Create Todo Item"
          actions={[]}
          modal={false}
          open={state.showTodoItemCreationDialog}
          onRequestClose={() => { state.showTodoItemCreationDialog = false; }}
        >
          <TodoItemCreateBox appState={state} />      
        </Dialog>
        <div className="todo-item-box-container">
          {state.todoItems.map(item => <TodoItemBox key={item.id} todoItem={item} />)}
        </div>
      </div>
    );
  }
}
