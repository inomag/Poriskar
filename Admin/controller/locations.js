const firebase = require('../db/firebase');
const MarkedLocation = require('../model/MarkedLocation');

const firestore = firebase.firestore();

exports.getAllMarkedLocations = async (req, res) => {
    try {
        const markedLocationsRef = await firestore.collection('marked_locations');
        const data = await markedLocationsRef.get();
        const markedLocations = [];
        if (data.empty) {
            res.status(404).send('Not any locations are found!');
        } else {
            data.forEach(doc => {
                // console.log(doc.id);
                const markedlocation = new MarkedLocation(
                    doc.id,
                    doc.data().image,
                    doc.data().address
                );
                markedLocations.push(markedlocation);
                // console.log(markedLocations);
            });
            res.render('locations/marked', {
                markedLocations,
                firestore
            });
        }
    } catch (err) {
        res.status(404).send(err.message);
    }
}

exports.approve_location_post = async (req, res) => {
    try {
        const { id, image, address } = req.body;

        // Add the data to a new documnent of approve_locations
        const approvedLocationRef = await firestore.collection('approved_locations');
        await approvedLocationRef.doc().set({
            image,
            address
        });

        // Delete the document for markedLocation
        await firestore.collection('marked_locations').doc(id).delete();

        res.redirect('/social');
    } catch (err) {
        res.status(404).send(err.message);
    }
}

exports.discard_location_post = async (req, res) => {
    try {
        const { id } = req.body;

        // Delete the document for markedLocation
        await firestore.collection('marked_locations').doc(id).delete();

        res.redirect('/social');
    } catch (err) {
        res.status(404).send(err.message);
    }
}