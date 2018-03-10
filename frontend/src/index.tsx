import * as React from 'react';
import * as ReactDOM from 'react-dom';
import App from './app/App';
import registerServiceWorker from './registerServiceWorker';
import './index.css';
import { AppState } from './app/Store';
import { Api } from './api/api';
import { observe } from 'mobx';
import { MuiThemeProvider } from 'material-ui/styles';

const api = new Api('/api');
const hash = window.location.hash.substr(1) || null;
const appState = new AppState(api, hash);

const WrappedApp: React.StatelessComponent<{state: AppState}> = ({state}) => (
    <MuiThemeProvider>
        <App appState={state} />
    </MuiThemeProvider>
);

ReactDOM.render(<WrappedApp state={appState} />, document.getElementById('root') as HTMLElement);

// updates hash
observe(appState, 'todoList', (change) => {
    const newList = appState.todoList;
    if (newList !== null) {
        window.location.hash = newList.id;
    }
});

registerServiceWorker();
