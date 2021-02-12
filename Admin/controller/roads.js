const firebase = require('../db/firebase');

const firestore = firebase.firestore();

exports.addRoad = async (req, res, next) => {
    try {
        const data = req.body;
        await firestore.collection('roads').doc().set(data);
        res.send('Record saved successfully!');
    } catch (err) {
        res.status(404).send(err.message);
    }
}