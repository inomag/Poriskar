const express = require('express');
const router = express.Router();

const { getAllMarkedLocations, approve_location_post, discard_location_post,  } = require('../controller/locations');
const { requireAuth } = require('../middleware');

router.get('/social', requireAuth, getAllMarkedLocations);

//Approve the location
router.post('/locations/approve', requireAuth, approve_location_post);

//Discard the location
router.post('/locations/discard', requireAuth, discard_location_post);

module.exports = router;