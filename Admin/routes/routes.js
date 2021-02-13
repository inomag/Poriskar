const express = require('express');
const router = express.Router();

const { addRoad, getAllRoutes} = require('../controller/routes');
const { requireAuth } = require('../middleware');

router.get('/routes', requireAuth, getAllRoutes);

router.post('/routes', requireAuth, addRoad);

module.exports = router;