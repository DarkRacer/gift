const express = require('express');
const session = require('express-session');
const cors = require('cors');
const app = express();
const { createProxyMiddleware } = require('http-proxy-middleware');

const API_SERVER = process.env.API_SERVER;

app.use(express.static('./dist/frontend'));
app.set('trust proxy', 1);
app.use(cors({
  credentials: true,
  origin: "https://search-gift-backend.herokuapp.com/"
}))

app.use('/api', createProxyMiddleware({
  logLevel     : 'debug',
  target       : API_SERVER,
  changeOrigin : true,
  secure       : true,
  xfwd         : true,
  pathRewrite: {
    "^/api": ""
  },
  onProxyReq   : function (proxyReq, req, res) {
    if (proxyReq.getHeader('origin')) {
      proxyReq.setHeader('origin', API_SERVER);
    }
  }
}));

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
