const express = require('express');
const router = express.Router();

const { getAllMarkedLocations } = require('../controller/locations');
const { requireAuth } = require('../middleware');

router.get('/social', requireAuth, getAllMarkedLocations);

module.exports = router;