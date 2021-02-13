function initMap() {
    const geocoder = new google.maps.Geocoder();
    geocodeLatLng(geocoder,  26.4169232,91.062411);
    // geocodeLatLng(geocoder, 26.4169232,91.062411);
}

const geocodeLatLng = (geocoder, lat, lng) => {
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
                document.getElementById('map').innerHTML = `<h2>Address: ${results[1].formatted_address} </h2>`;
                return results;
            } else {
                window.alert("No results found");
            }
        } else {
            window.alert("Geocoder failed due to: " + status);
        }
    });
}