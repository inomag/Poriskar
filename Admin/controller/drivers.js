const firebase = require('../db/firebase');
const Driver = require('../model/driver');

const firestore = firebase.firestore();

exports.addDriver = async (req, res) => {
    try {
        const data = req.body;
        const countData = await firestore.collection('drivers');
        const getCount = await countData.get();
        let driversArray = [];
        getCount.forEach(doc => {
            driversArray.push(doc.data());
        });
        await firestore.collection('drivers').doc(`driver-${driversArray.length + 1}`).set(data);
        res.send('Record saved successfully!');
    } catch (err) {
        res.status(404).send(err.message);
    }
}

exports.getAllDrivers = async (req, res, next) => {
    try {
        const drivers = await firestore.collection('drivers');
        const data = await drivers.get();
        const driversArray = [];
        if (data.empty) {
            res.status(404).send('No driver found!');
        } else {
            data.forEach(doc => {
                const driver = new Driver(
                    doc.id,
                    doc.data().name,
                    doc.data().phone_no,
                    doc.data().route
                );
                driversArray.push(driver);
            });
            res.render('drivers/index', {
                drivers: driversArray
            });
        }
    } catch (err) {
        res.status(404).send(err.message);
    }
}