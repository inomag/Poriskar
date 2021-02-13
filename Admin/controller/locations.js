const firebase = require('../db/firebase');

const firestore = firebase.firestore();

exports.getAllMarkedLocations = async (req, res) => {
    try {
        const markedLocationsRef = await firestore.collection('marked_locations');
        const data = await markedLocationsRef.get();
        const markedLocations = [];
        if (data.empty) {
            res.status(404).send('No driver found!');
        } else {
            data.forEach(doc => {
                // const driver = new Driver(
                //     doc.id,
                //     doc.data().name,
                //     doc.data().phone_no,
                //     doc.data().route
                // );
                markedLocations.push(doc.data());
            });
            res.render('locations/marked', {
                markedLocations
            });
        }
    } catch (err) {
        res.status(404).send(err.message);
    }
}