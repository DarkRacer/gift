const express = require('express');
const session = require('express-session')
const app = express();

app.use(express.static('./dist/frontend'));
app.set('trust proxy', 1)
app.use(session({
  secret: 'secret',
  saveUninitialized: true,
  proxy: true,
  cookie: {
    secure: true,
    maxAge: 360
  }
}))

app.get('/*', (req, res) =>
  res.sendFile('index.html', {root: 'dist/frontend/'}),
);

app.listen(process.env.PORT || 8000);
