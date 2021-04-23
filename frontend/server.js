const express = require('express');
const session = require('express-session');
const cors = require('cors');
const app = express();

app.use(express.static('./dist/frontend'));
app.set('trust proxy', 1);
app.use(cors({
  credentials: true,
  origin: "https://search-gift-backend.herokuapp.com/"
}))
app.use(session({
  secret: 'secret',
  credentials: true,
  saveUninitialized: true,
  proxy: true,
  cookie: {
    secure: true,
    maxAge: 360 * 1000
  }
}))

app.get('/*', (req, res) =>
  res.sendFile('index.html', {root: 'dist/frontend/'}),
);

app.listen(process.env.PORT || 8000);
