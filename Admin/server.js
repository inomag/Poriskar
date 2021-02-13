const dotenv = require('dotenv');
dotenv.config();
const express = require('express');
const cookieParser = require('cookie-parser');
const session = require('express-session');

const app = express();


app.use(session({
    secret: 'BHARGAB',
    resave: false,
    saveUninitialized: false
}));

// app.use((req, res, next) => {
//     if (!req.session.viewCount) {
//         req.session.viewCount = 1;
//     } else {
//         req.session.viewCount += 1;
//     }
//     res.render('test', { viewCount: req.session.viewCount });
// })

app.use(express.json());
app.use(express.urlencoded({ extended: false }));
app.use(cookieParser());

app.use(express.static('public'));
app.set('view engine', 'ejs');

app.use((req, res, next) => {
    res.locals.user = req.cookies.poriskar || null;
    next();
});

const auth = require('./routes/auth');
const driver = require('./routes/drivers');
const route = require('./routes/routes');
const location = require('./routes/locations');

app.use('/', auth);
app.use('/', driver);
app.use('/', route);
app.use('/', location);

const PORT = process.env.PORT || 3000;

app.listen(PORT, () => console.log(`Server started on port ${PORT}...`));