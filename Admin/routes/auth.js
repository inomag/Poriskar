const { Router } = require('express');
const router = Router();

//import functions from controller
const { login_post,login_get, logout_get } = require('../controller/auth');

//all routes here
router.get('/login', login_get);
router.post('/login', login_post);

router.get('/logout', logout_get);

module.exports = router;