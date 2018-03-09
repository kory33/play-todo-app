import * as React from 'react';
import './App.css';
import { observer } from 'mobx-react';
import { AppState } from './Store';
import { TodoItemBox } from './TodoItemBox';
import { TodoItemCreateBox } from './TodoItemCreateBox';

@observer
class App extends React.Component<{ appState: AppState }, {}> {
  render() {
    if (this.props.appState.todoList === null) {
      return (
        <div className="loading-screen">
          loading...
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
