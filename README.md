# play-todo-app

A todo application build with Play+Scala(backend) and TypeScript+React+mobx+Material UI(frontend).

# Installation and Running the Application

## Using Heroku

Run `heroku create` on the project directory.

Add `heroku/nodejs` and then `heroku/scala` buildpacks.
Let activator generate the secret through

```
activator playGenerateSecret
```

Copy the secret generated by activator and add it as
`APPLICATION_SECRET` configuration variable on Heroku.

Push to the Heroku remote and the service should start working.

## Through Manual Compilation

To run the application locally, you need to have npm and activator.

Run the following sequence of commands

to clone the repository
```
git clone https://github.com/kory33/play-todo-app.git
cd play-todo-app
```

to compile frontend sources
```
npm run build
```

to start the server(development mode)
```
activator run
```

## Debugging

You might want to debug the application in order to check the component functionalities.

If you need to debug the backend, just use `activator run`.
For frontend debugging, follow these steps:

1. Open `frontend/src/index.tsx` and change the url inside `new Api(...)` to `'http://localhost:9000/api'`.
2. Run `npm start` to start npm development server.
3. Run `activator run` to start play development server.
4. Before accessing `https://localhost:3000/`, disable the security features of the browser concerning CORS.
Refer [this page](https://stackoverflow.com/questions/3102819/disable-same-origin-policy-in-chrome) to do this for chromium browsers.
I highly recommend you to apply this settings to the browser which is not for your daily usage.
