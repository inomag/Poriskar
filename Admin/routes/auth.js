const { Router } = require('express');
const router = Router();

//import functions from controller
const { login_post } = require('../controller/auth');
const {login_get} = require('../controller/auth');

//all routes here
router.get('/login', login_get);
router.post('/login', login_post);

module.exports = router;