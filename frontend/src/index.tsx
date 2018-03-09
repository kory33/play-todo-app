import * as React from 'react';
import * as ReactDOM from 'react-dom';
import App from './app/App';
import registerServiceWorker from './registerServiceWorker';
import './index.css';
import { AppState } from './app/Store';
import { Api } from './api/api';
import { observe } from 'mobx';

const api = new Api('/api');
const hash = window.location.hash.substr(1) || null;
const appState = new AppState(api, hash);

ReactDOM.render(<App appState={appState} />, document.getElementById('root') as HTMLElement);

// updates hash
observe(appState, 'todoList', (change) => {
    const newList = appState.todoList;
    if (newList !== null) {
        window.location.hash = newList.id;
    }
});

registerServiceWorker();
