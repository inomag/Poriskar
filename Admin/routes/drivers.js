const express = require('express');
const router = express.Router();

const { addDriver, getAllDrivers, updateDriver, assign_route_get} = require('../controller/drivers');
const { requireAuth } = require('../middleware');

router.get('/drivers', requireAuth, getAllDrivers);

//Add driver
router.post('/drivers', requireAuth, addDriver);

//assign route to driver
router.get('/drivers/assign-route', requireAuth, assign_route_get);
router.post('/drivers/assign-route',requireAuth, updateDriver);

module.exports = router;