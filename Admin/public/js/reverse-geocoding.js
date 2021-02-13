const getAddress = (lat, lng) => {
    const geocoder = new google.maps.Geocoder();
    const location = new google.maps.LatLng(lat, lng);

    geocoder.geocode({
        'latLng': location
    }, (results, status) => {
        if (status === "OK") {
            if (results) {
                const address = document.createElement('p');
                address.classList.add('text-info');
                address.innerText = `Address: ${results[0].formatted_address}`;
                document.getElementById(`${lat}`).appendChild(address);
                document.getElementById(`btn-${lat}`).style.display = 'none'
                return results;
            } else {
                window.alert("No results found");
            }
        } else {
            window.alert("Geocoder failed due to: " + status);
        }
    });
}