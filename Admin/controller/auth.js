const jwt = require('jsonwebtoken');

const firebase = require('../db/firebase');

//Create a jwt token using user email id 
const maxAge = 365 * 24 * 60 * 60;
const createToken = user => {
    return jwt.sign({ user }, 'secretKey', {
        expiresIn: maxAge
    });
};

exports.home_get = (req, res) => {
    res.render('home');
}

exports.login_get = async (req, res) => {
    await res.render('auth/login');
}

exports.login_post = async (req, res) => {
    const { email, password } = req.body;

    try {
        const snapshot = await firebase.firestore().collection('admin').get();
        const datas = await snapshot.docs.map(doc => doc.data());

        for (var i = 0; i < datas.length; i++) {
            const user = await datas[i].email === email ? datas[i] : 'Some error occured!';

            if (user !== 'Some error occured!') {
                if (password === user.password) {
                    const token = await createToken(user.email);
                    res.cookie('poriskar', token, { httpOnly: true, maxAge });
                    res.redirect('/')
                } else {
                    res.redirect('/login');
                }
            } else {
                res.redirect('/login');
                // return res.status(400).json({ login_error: user });
            }
        }
    }
    catch (err) {
        const errors = err;
        res.status(400).json({ errors });
    }
}

exports.logout_get = (req, res) => {
    res.cookie('poriskar', '', { maxAge: 1 });
    res.redirect('/');
}