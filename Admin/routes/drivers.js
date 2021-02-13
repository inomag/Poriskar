const express = require('express');
const router = express.Router();

const { addDriver, getAllDrivers, updateDriver, assign_route_get} = require('../controller/drivers');

router.get('/drivers', getAllDrivers);

//Add driver
router.post('/drivers', addDriver);

//assign route to driver
router.get('/drivers/assign-route', assign_route_get);
router.post('/drivers/assign-route', updateDriver);

module.exports = router;