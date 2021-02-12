const express = require('express');
const router = express.Router();

const { addRoad, getAllRoutes} = require('../controller/routes');

router.get('/routes', getAllRoutes);

router.post('/routes', addRoad);

module.exports = router;