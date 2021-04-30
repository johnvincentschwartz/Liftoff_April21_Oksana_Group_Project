//https://www.youtube.com/watch?v=oVr6unKZbg4
//https://developers.google.com/maps/documentation/javascript/markers - "Make a Marker Accessible"
//https://developers.google.com/maps/documentation/javascript/infowindows
//https://developers.google.com/maps/documentation/javascript/events - "Listening to DOM Events"
//https://developers.google.com/maps/documentation/javascript/geolocation

//defaults map zoom and center when map is first loaded. selectedId defaults to the first item in the initial results.
let selectedZoom = 10;
let selectedCenter = {lat: 38.6009, lng: -90.4329};
let selectedId = 1;

let initialLoad = true;

//called with onclick on HTML element for filter result title.
function setZoom(id) {
    let selectedTrail = trails.find(trail => trail.id === id)
    let currentLat = selectedTrail.lat;
    let currentLng = selectedTrail.lng;
    selectedCenter = {lat: currentLat, lng: currentLng}
    selectedZoom = 13;
    selectedId = id;
    initialLoad = false;
    initMap();
    console.log(selectedId)
}

//sets up map to put in div #map
function initMap() {

    const map = new google.maps.Map(document.getElementById("map"), {
        zoom: selectedZoom,
        center: selectedCenter,
        mapTypeId: 'hybrid',
        mapId: '59da3fe57cf0042e',
        mapTypeControl: false
    });

    if (navigator.geolocation) {
          navigator.geolocation.getCurrentPosition(
            (position) => {
              const pos = {
                lat: position.coords.latitude,
                lng: position.coords.longitude,
              };
              map.setCenter(pos);
              console.log(position)
            },
            () => {
              handleLocationError(true, infoWindow, map.getCenter());
            }
          );
        }

    //Initialize array that will still the marker location and info window information for every trail in the left column
    let allResults = [];

    for (let i = 0; i < trails.length; i++){
        //In a string, store the div that will appear as the info window for a given trail.
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

    //Initialize the pop-up box for a given array that appears on the map.
    const infoWindow = new google.maps.InfoWindow();

    //Iterate through the completed allResults array to create a marker for each.
    allResults.forEach((result, i) => {
        console.log(i)
        const marker = new google.maps.Marker({
            position: result.markerPosition,
            map,
            title: `${i + 1}. ${result.title}`,
            label: `${i + 1}`,
            optimized: false,
        });

        //InfoWindow will automatically open if the marker's ID matches the selectedId, the id that has just been clicked on the left
        if (!initialLoad && selectedId === result.id){
            infoWindow.setContent(result.infoWindowContent);
            infoWindow.open(marker.getMap(), marker);
        }

        //Also allow info window to open if marker is clicked on
        marker.addListener("click", () => {
          infoWindow.close();
          infoWindow.setContent(result.infoWindowContent);
          infoWindow.open(marker.getMap(), marker);
        });

    });
}