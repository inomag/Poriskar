const firebase = require('../db/firebase');

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