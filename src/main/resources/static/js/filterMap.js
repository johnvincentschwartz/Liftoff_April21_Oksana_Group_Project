//Globals passed by HTML:
//    let trails = /*[[${trails}]]*/
//    let searchLocation = /*[[${trailFilterDTO.searchLocation}]]*/
//    let sort = /*[[${sort}]]*/

let selectedZoom = 10;
let selectedLocation = {lat: 38.6009, lng: -90.4330};
let selectedId;
let userLocation;
let initialLoad = true;
let map;
const searchLocationInput = document.getElementById('search-location');
const testDiv = document.getElementById('test-div')

function setZoom(id) {

    selectedZoom = 13;
    lastSelectedId = selectedId;
    selectedId = id;

    initialLoad = false;

    //If search location has been changed, recalculate with search map so distances don't recalculated to default/userLoc when you click on a trail result
    if (searchLocation){
        console.log(searchLocation)
        initSearchMap(searchLocation)
    } else {
        initDefaultMap()
    }

}

function calculateDistance(trails, userLocation){
    const service = new google.maps.DistanceMatrixService();

    for (let i = 0; i < trails.length; i++){

        service.getDistanceMatrix({
            origins: [userLocation],
            destinations: [{lat: trails[i].lat, lng: trails[i].lng}],
            travelMode: 'DRIVING',
            unitSystem: google.maps.UnitSystem.IMPERIAL
        }, callback)

        const distanceAwayLi = document.getElementById(`distance-from-${trails[i].id}`)

        function callback(response, status) {
            let origin = response.originAddresses[0];
            let destination = response.destinationAddresses[0]
            let element = response.rows[0].elements[0]
            let distance = element.distance.text
            let duration = element.duration.text

            distanceAwayLi.innerHTML = `${distance} away (${duration} drive)`;
        }
    };
}

function initSearchMap(searchLocation) {
    const geocoder = new google.maps.Geocoder();

    alert(searchLocation)

    geocoder.geocode({ address: searchLocation }, (results, status) => {
        if (status === "OK") {
              const {lat, lng} = results[0].geometry.location;
              userLocation = {lat: lat(), lng: lng()}
        } else {
            alert("status", status, "SEARCHLOC", searchLocation, "RESULTS", results)
        }
         map = new google.maps.Map(document.getElementById("map"), {
            zoom: selectedZoom,
            center: userLocation,
            mapTypeId: 'hybrid',
            mapId: '59da3fe57cf0042e',
            mapTypeControl: false
        });
        processResults(trails,map)
    })
}

function initDefaultMap() {
    alert("default map")
    userLocation = new Object();
    const geocoder = new google.maps.Geocoder();

    navigator.geolocation.getCurrentPosition(function(position) {
        userLocation.lat = position.coords.latitude,
        userLocation.lng = position.coords.longitude,

        geocoder.geocode({ location: userLocation }, (results, status) => {
                if (status === "OK") {
                      const zip = results[0].address_components[6].long_name;
                      searchLocationInput.value = zip;
                } else {
                    alert(status)
                }
            })

        map = new google.maps.Map(document.getElementById("map"), {
            zoom: 10,
            center: userLocation,
            mapTypeId: 'hybrid',
            mapId: '59da3fe57cf0042e',
            mapTypeControl: false
        });
        processResults(trails, map)
    })
}

function processResults(trails, map){

    const marker = new google.maps.Marker({
        position: userLocation,
        map,
        title: `You are here`,
        icon: {url: "http://maps.google.com/mapfiles/ms/icons/blue-dot.png"},
        optimized: false
    });

    let allResults = [];

    calculateDistance(trails, selectedLocation)

    const infoWindow = new google.maps.InfoWindow();

    for (let trail of trails){
        const infoWindowContent =
            `<div style="background-color: #091E05; color: white; padding: 3rem; margin: 0; width: auto; height: auto">
                <h3>${trail.name}</h3>
                <h4>${trail.length} mi, Level ${trail.difficulty}</h4>
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

        const markerPosition = {lat: trail.lat, lng: trail.lng}
        const title = trail.name
        allResults.push({markerPosition, title, infoWindowContent, id: trail.id})
    }

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