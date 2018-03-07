import * as React from 'react';
import * as ReactDOM from 'react-dom';
import App from './app/App';
import registerServiceWorker from './registerServiceWorker';
import './index.css';
import { AppState } from "./app/Store";
import { Api } from "./api/api";

const api = new Api("/api");
const appState = new AppState(api);

ReactDOM.render(<App appState={appState} />, document.getElementById('root') as HTMLElement);

registerServiceWorker();
