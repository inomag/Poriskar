const jwt = require('jsonwebtoken');

module.exports.requireAuth = (req, res, next) => {
    const token = req.cookies.poriskar;
    if (token) {
        jwt.verify(token, 'secretKey', (err, decodedToken) => {
            if (err) {
                console.log(err);
                res.redirect('/login');
            } else {
                next();
            }
        });
    } else {
        res.redirect('/login');
    }
}   