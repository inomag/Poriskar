const firebase = require('../db/firebase');
const Driver = require('../model/driver');
const Route = require('../model/route');

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

exports.getAllDrivers = async (req, res) => {
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

exports.assign_route_get = async (req, res) => {
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

        const routes = await firestore.collection('routes');
        const roadsData = await routes.get();
        const routesArray = [];
        if (roadsData.empty) {
            res.status(404).send('No route found!');
        } else {
            roadsData.forEach(doc => {
                const route = new Route(
                    doc.id,
                    doc.data().name,
                    doc.data().latitude,
                    doc.data().longitude
                );
                routesArray.push(doc.data());
            });
        }

        const today = new Date();
        const day = today.getDay();
        const daylist = ["Sunday", "Monday", "Tuesday", "Wednesday ", "Thursday", "Friday", "Saturday"];

        res.render('drivers/assign-route', {
            drivers: driversArray,
            routes: routesArray,
            today: daylist[day]
        });
    }
}

exports.updateDriver = async (req, res) => {
    const { driverId, route } = req.body;
    const drivers = await firestore.collection('drivers');
    const doc = await drivers.doc(driverId);

    await doc.update({
        route
    });

    res.redirect('/drivers/assign-route');
}