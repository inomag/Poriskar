const express = require('express');
const router = express.Router();

const { addRoad } = require('../controller/routes');

router.post('/routes', addRoad);

module.exports = router;