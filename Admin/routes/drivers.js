const express = require('express');
const router = express.Router();

const { addDriver, getAllDrivers} = require('../controller/drivers');

router.get('/drivers', getAllDrivers);

//Add driver
router.post('/drivers', addDriver);

module.exports = router;