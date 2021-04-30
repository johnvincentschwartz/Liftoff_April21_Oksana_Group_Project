//https://www.youtube.com/watch?v=oVr6unKZbg4
//https://developers.google.com/maps/documentation/javascript/markers - "Make a Marker Accessible"
//https://developers.google.com/maps/documentation/javascript/infowindows
//https://developers.google.com/maps/documentation/javascript/events - "Listening to DOM Events"
//https://developers.google.com/maps/documentation/javascript/geolocation

//defaults map zoom and center when map is first loaded. selectedId defaults to the first item in the initial results.
let selectedZoom = 10;
let selectedLocation = {lat: 38.6009, lng: -90.4330};
let userLocation = {lat: 38.6009, lng: -90.4329};
console.log("SET DEFAULT LOC")
let selectedId = 1;
let initialLoad = true;

function getUserLocation(){
    if (navigator.geolocation) {
        navigator.geolocation.getCurrentPosition(
            (position) => {
                //position = GeolocationPosition object {coords, timestamp}
                const pos = {
                    lat: position.coords.latitude,
                    lng: position.coords.longitude,
                };
                //pos = LatLng obj
                userLocation = pos;
                calculateDistance(trails);
                initMap();
            },
            () => {
              handleLocationError(true, infoWindow, map.getCenter());
            }
        )
    }
}

getUserLocation()

function setZoom(id) {
    let selectedTrail = trails.find(trail => trail.id === id)
    let currentLat = selectedTrail.lat;
    let currentLng = selectedTrail.lng;

    selectedLocation = {lat: currentLat, lng: currentLng}
    selectedZoom = 13;
    selectedId = id;
    initialLoad = false;
    initMap();
}

function calculateDistance(trails){
    const service = new google.maps.DistanceMatrixService();

    for (let i = 0; i < trails.length; i++){
        if (i === 0){
            console.log("USER LOC WHEN DISTANCE MATRIX IS RUN: ", userLocation)
        }

        service.getDistanceMatrix({
            origins: [userLocation],
            destinations: [{lat: trails[i].lat, lng: trails[i].lng}],
            travelMode: 'DRIVING',
            unitSystem: google.maps.UnitSystem.IMPERIAL
        }, callback)

        const distanceAwayLi = document.getElementById(`distance-from-${trails[i].id}`)

        function callback(response, status) {
            let origin = response.originAddresses[0];
            console.log("ORIGIN", origin)
            let destination = response.destinationAddresses[0]
            console.log("DESTINATION", destination)
            let element = response.rows[0].elements[0]
            console.log("ELEMENT", element)
            let distance = element.distance.text
            let duration = element.duration.text
            distanceAwayLi.innerHTML = `${distance} away (${duration} drive)`;
        }
    };
}

function initMap() {

    const map = new google.maps.Map(document.getElementById("map"), {
        zoom: selectedZoom,
        center: userLocation,
        mapTypeId: 'hybrid',
        mapId: '59da3fe57cf0042e',
        mapTypeControl: false
    });

    let allResults = [];

    for (let i = 0; i < trails.length; i++){
        const infoWindowContent =
            `<div style="background-color: #091E05; color: white; padding: 3rem; margin: 0; width: auto; height: auto">
                <h3>${trails[i].name}</h3>
                <h4>${trails[i].length} mi, Level ${trails[i].difficulty}</h4>
                <strong>Features:</strong>
                <ul>
                    <li>A feature</li>
                    <li>A feature</li>
                </ul>
                <div id="min-rating" class="rating" style="margin:0 auto;overflow:hidden">
                    <input disabled type="radio" name="rating" value="5" id="5">
                    <label for="5">☆</label>
                    <input disabled type="radio" name="rating" value="4" id="4">
                    <label for="4">☆</label>
                    <input disabled type="radio" name="rating" value="3" id="3">
                    <label for="3">☆</label>
                    <input disabled type="radio" name="rating" value="2" id="2">
                    <label for="2">☆</label>
                    <input disabled type="radio" name="rating" value="1" id="1">
                    <label for="1">☆</label>
                </div>
                <button class="btn btn-primary" style="width: 100%; margin: 0.5rem 0">Add to Favorites</button><br />
                <button class="btn btn-primary" style="width: 100%; margin: 0.5rem 0">Plan a Meetup</button>
            </div>`

        //prepare data that will be needed to establish markers, including the trail object's ID so marker will recognize if trail's ID matches selected ID
        const markerPosition = {lat: trails[i].lat, lng: trails[i].lng}
        const title = trails[i].name
        allResults.push({markerPosition, title, infoWindowContent, id: trails[i].id})
    }

    const infoWindow = new google.maps.InfoWindow();

    allResults.forEach((result, i) => {
        const marker = new google.maps.Marker({
            position: result.markerPosition,
            map,
            title: `${i + 1}. ${result.title}`,
            label: `${i + 1}`,
            optimized: false
        });

        if (!initialLoad && selectedId === result.id){
            infoWindow.setContent(result.infoWindowContent);
            infoWindow.open(marker.getMap(), marker);
        }

        marker.addListener("click", () => {
            infoWindow.close();
            infoWindow.setContent(result.infoWindowContent);
            infoWindow.open(marker.getMap(), marker);
        });
    });
}