const express = require('express');
const router = express.Router();

const { addRoad } = require('../controller/roads');

router.post('/roads', addRoad);

module.exports = router;