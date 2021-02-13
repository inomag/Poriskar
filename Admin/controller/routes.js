const fetch = require('node-fetch');

const firebase = require('../db/firebase');
const Route = require('../model/route');

const firestore = firebase.firestore();

exports.routes_list_get = (req, res) => {
    res.render('routes/index');
}

exports.getAllRoutes = async (req, res, next) => {
    try {
        const roads = await firestore.collection('routes');
        const data = await roads.get();
        const roadsArray = [];
        let latlng = [];
        if (data.empty) {
            res.status(404).send('No route found!');
        } else {
            data.forEach(doc => {
                const route = new Route(
                    doc.id,
                    doc.data().name,
                    doc.data().latitude,
                    doc.data().longitude
                );
                roadsArray.push(doc.data());
            });
            res.render('routes/index', {
                routes: roadsArray
            });
        }
    } catch (err) {
        res.status(404).send(err.message);
    }
}

function geocodeLatLng(geocoder, lat, lng) {
    const latlng = {
        // lat: 26.4169232,
        // lng: 91.062411
        lat,
        lng
    };
    geocoder.geocode({
        location: latlng
    }, (results, status) => {
        if (status === "OK") {
            if (results) {
                console.log(results);
                // document.getElementById('map').innerHTML = `<h2>Address: ${results[1].formatted_address} </h2>`;
                return results;
            } else {
                window.alert("No results found");
            }
        } else {
            window.alert("Geocoder failed due to: " + status);
        }
    });
}

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