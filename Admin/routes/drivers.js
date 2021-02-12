const express = require('express');
const router = express.Router();

const { addDriver } = require('../controller/drivers');

//Add driver
router.post('/drivers', addDriver);

module.exports = router;