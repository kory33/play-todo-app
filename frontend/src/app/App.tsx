import * as React from 'react';
import './App.css';
import { observer } from 'mobx-react';
import { AppState } from './Store';
import CircularProgress from 'material-ui/CircularProgress';
import { TodoItemBox } from './TodoItemBox';
import AppToolbar from './components/AppToolbar';
import TodoItemCreateDialog from './components/TodoItemCreateDialog';

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
        <TodoItemCreateDialog appState={state} />
        <div className="todo-item-box-container">
          {state.todoItems.map(item => <TodoItemBox key={item.id} todoItem={item} />)}
        </div>
      </div>
    );
  }
}
