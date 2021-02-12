const firebase = require('../db');
// const Driver = require('../models/driver');

const firestore = firebase.firestore();

const addDriver = async (req, res) => {
    try {
        const data = req.body;
        await firestore.collection('drivers').doc().set(data);
        res.send('Record saved successfully!');
    } catch (err) {
        res.status(404).send(err.message);
    }
}

module.exports = {
    addDriver, getAllDrivers, getDriverById, updateDriver, deleteDriver
};