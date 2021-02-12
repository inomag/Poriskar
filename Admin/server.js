const dotenv = require('dotenv');
dotenv.config();
const express = require('express');

const app = express();

app.use(express.json());
app.use(express.urlencoded({ extended: false }));

app.set('view engine', 'ejs');

const authRoutes = require('./routes/auth');

app.use('/api', authRoutes);

const PORT = process.env.PORT | 3000;

app.listen(PORT, () => console.log(`Server started on port ${PORT}...`));