class Route {
    constructor(id, name, latitude, longitude, driver) {
        this.id = id;
        this.name = name;
        this.lat = latitude;
        this.lng = longitude;
        this.driver = driver;
    }
}

module.exports = Route;