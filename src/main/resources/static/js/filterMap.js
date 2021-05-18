//Available variables passed by Thymeleaf from allTrails.html:
//    let trails = /*[[${trails}]]*/
//    let searchLocation = /*[[${trailFilterDTO.searchLocation}]]*/
//    let sort = /*[[${sort}]]*/

let map;
let startLocation = {lat: 38.6009, lng: -90.4330};
let selectedZoom = 10;
let selectedId;
let initialLoad = true;

const searchLocationInput = document.getElementById('search-location');

//Called when clicking on a search result title
function focusTrail(id) {
    selectedZoom = 13;
    lastSelectedId = selectedId;
    selectedId = id;

    //The initialLoad variable keeps the infoWindow from appearing when the page first loads without the user having clicked on anything
    initialLoad = false;

    //If search location has been changed, recalculate with search map so distances don't recalculated to default/startLoc when you click on a trail result
    if (searchLocation){
        initSearchMap(searchLocation)
    } else {
        initDefaultMap()
    }

}

//Called to find distance between each trail and startLocation, and add that data to the search results column
function calculateDistance(trails, startLocation){
    const service = new google.maps.DistanceMatrixService();

    //request distance/drive information from google
    for (let i = 0; i < trails.length; i++){
        service.getDistanceMatrix({
            origins: [startLocation],
            destinations: [{lat: trails[i].lat, lng: trails[i].lng}],
            travelMode: 'DRIVING',
            unitSystem: google.maps.UnitSystem.IMPERIAL
        }, callback)

        //Assign dynamic variable to each trail's HTML element displaying distance and drivetime.
        const distanceAwayLi = document.getElementById(`distance-from-${trails[i].id}`)

        //After google's data is retrieved, isolate the numeric value of distances and drivetimes
        function callback(response, status) {
            let origin = response.originAddresses[0];
            let destination = response.destinationAddresses[0]
            let element = response.rows[0].elements[0]
            let distance = element.distance.text
            let duration = element.duration.text
            //Add those numbers to HTML
            distanceAwayLi.innerHTML = `${distance} away (${duration} drive)`;
        }
    };
}

//Triggers from clicking Use My Location button
function checkLocation() {
    //If site not already Allowed to use location, will trigger browser alert asking for permission.
    if (navigator.geolocation){
         navigator.geolocation.getCurrentPosition(function(position) {
            //If granted permission, startLocation becomes user position.
            startLocation.lat = position.coords.latitude,
            startLocation.lng = position.coords.longitude

            //Google geocoder is used to find approximate zip code to put into Search box of Filter
            const geocoder = new google.maps.Geocoder();
            geocoder.geocode({ location: startLocation }, (results, status) => {
                if (status === "OK") {
                    const zip = results[0].address_components[6].long_name;
                    searchLocationInput.value = zip;
                    initDefaultMap();
                } else {
                    alert("Unable to use location at this time.")
                }
            })
        },
        //Runs if you click Use My Location while browser has Location Services blocked
        () => {alert("Your web browser has blocked using your location. Please enable to use your current location.")}
    )
    // !navigator.geolocation only the case when using a browser not capable of geolocation
    } else {
        alert("Your web browser does not support geolocation.")
    }
}

//Initiate map for initial load and when no other start location is specified.
function initDefaultMap() {
    map = new google.maps.Map(document.getElementById("map"), {
        zoom: 10,
        center: startLocation,
        mapTypeId: 'hybrid',
        mapId: '59da3fe57cf0042e',
        mapTypeControl: false
    });

    processResults(trails, map)
}

//Initiate map for when the start point is modified by filter form.
function initSearchMap() {
    const geocoder = new google.maps.Geocoder();

    geocoder.geocode({ address: searchLocation }, (results, status) => {
        if (status === "OK") {
              const {lat, lng} = results[0].geometry.location;
              startLocation = {lat: lat(), lng: lng()}
        } else {
            alert("Unable to find search location.")
        }
         map = new google.maps.Map(document.getElementById("map"), {
            zoom: selectedZoom,
            center: startLocation,
            mapTypeId: 'hybrid',
            mapId: '59da3fe57cf0042e',
            mapTypeControl: false
        });

        processResults(trails,map)
    })
}

//Processing that needs to be done for both default map and search map
function processResults(trails, map){
    //Set blue marker for Start Location
    const marker = new google.maps.Marker({
        position: startLocation,
        map,
        title: `You are here`,
        icon: {url: "http://maps.google.com/mapfiles/ms/icons/blue-dot.png"},
        optimized: false
    });

    //Generate innerHTML for <li>'s displaying distance from start location in results
    calculateDistance(trails, startLocation)

    //Generate the InfoWindow content for each search result
    let allResults = [];
    const infoWindow = new google.maps.InfoWindow();
    for (let trail of trails){
        const infoWindowContent =
            `<div style="background-color: #091E05; color: white; padding: 1rem; margin: 0; width: auto; height: auto">
                <h4>${trail.name}</h4>
                <h5>${trail.length} mi, Level ${trail.difficulty}</h5>
            </div>`

        //Create object for each result containing the position and info window content for its marker
        const markerPosition = {lat: trail.lat, lng: trail.lng}
        const title = trail.name
        allResults.push({markerPosition, title, infoWindowContent, id: trail.id})
  }

    //Generate a marker for each search result
    allResults.forEach((result, i) => {
        const marker = new google.maps.Marker({
            position: result.markerPosition,
            map,
            title: `${i + 1}. ${result.title}`,
            label: `${i + 1}`,
            optimized: false
        });

        //When result title is clicked, open info window
        if (!initialLoad && selectedId === result.id){
            infoWindow.setContent(result.infoWindowContent);
            infoWindow.open(marker.getMap(), marker);
        }

        //Info window closes when another marker is clicked and a new one opens
        marker.addListener("click", () => {
            infoWindow.close();
            infoWindow.setContent(result.infoWindowContent);
            infoWindow.open(marker.getMap(), marker);
        });
    });
}