const dotenv = require('dotenv');
dotenv.config();
const express = require('express');

const app = express();

app.use(express.json());
app.use(express.urlencoded({ extended: false }));

app.use(express.static('public'));
app.set('view engine', 'ejs');

const auth = require('./routes/auth');
const driver = require('./routes/drivers');
const route = require('./routes/routes');

app.use('/', auth);
app.use('/', driver);
app.use('/', route);

const PORT = process.env.PORT | 3000;

app.listen(PORT, () => console.log(`Server started on port ${PORT}...`));