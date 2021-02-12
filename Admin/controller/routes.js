const firebase = require('../db/firebase');

const firestore = firebase.firestore();

exports.addRoad = async (req, res) => {
    try {
        const { name, latitude, longitude } = req.body;
        const countData = await firestore.collection('routes');
        const getCount = await countData.get();
        let routesArray = [];
        getCount.forEach(doc => {
            routesArray.push(doc.data());
        });
        await firestore.collection('routes').doc(`route-${routesArray.length + 1}`).set({
            id: routesArray.length + 1,
            name,
            latitude,
            longitude
        });
        res.send('Record saved successfully!');
    } catch (err) {
        res.status(404).send(err.message);
    }
}