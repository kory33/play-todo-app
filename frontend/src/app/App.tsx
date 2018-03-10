import * as React from 'react';
import './App.css';
import { observer } from 'mobx-react';
import { AppState } from './Store';
import CircularProgress from 'material-ui/CircularProgress';
import { TodoItemCreateBox } from './TodoItemCreateBox';
import { TodoItemBox } from './TodoItemBox';

@observer
class App extends React.Component<{ appState: AppState }, {}> {
  render() {
    if (this.props.appState.todoList === null) {
      return (
        <div className="loading-screen">
          <CircularProgress size={80} thickness={5} />
        </div>
      );
    }
    return (
      <div className="app">
        <div className="app-header">
          {this.props.appState.todoList.title}
        </div>
        <div className="app-body">
          <TodoItemCreateBox appState={this.props.appState} />
          <div className="todo-item-box-container">
            {this.props.appState.todoItems.map(item => <TodoItemBox key={item.id} todoItem={item} />)}
          </div>
        </div>
      </div>
    );
  }
}

export default App;
