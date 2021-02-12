const { Router } = require('express');
const router = Router();

//import functions from controller
const { login_post,login_get, logout_get } = require('../controller/auth');

//login route
router.get('/login', login_get);
router.post('/login', login_post);

//logout route
router.get('/logout', logout_get);

module.exports = router;