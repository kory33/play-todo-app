import * as React from 'react';
import './App.css';
import { observer } from 'mobx-react';
import { AppState } from "./Store";
import { TodoItemBox } from "./TodoItemBox";

@observer
class App extends React.Component<{ appState: AppState }, {}> {
  render() {
    return (
      <div className="app">
        <div className="app-header">
          { this.props.appState.todoList.title }
        </div>
        <div className="app-body">
          <div className="todo-item-box-container">
            { this.props.appState.todoItems.map(item => <TodoItemBox todoItem={item} />) }
          </div>
        </div>
      </div>
    );
  }
}

export default App;
