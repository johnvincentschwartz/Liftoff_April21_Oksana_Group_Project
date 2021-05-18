let initialLoad = true;

//called with onclick on HTML element for filter result title.
//function setZoom(id) {
//    let selectedTrail = trails.find(trail => trail.id === id)
//    let currentLat = selectedTrail.lat;
//    let currentLng = selectedTrail.lng;
//    selectedCenter = {lat: currentLat, lng: currentLng}
//    selectedZoom = 13;
//    selectedId = id;
//    initialLoad = false;
//    initMap();
//    console.log(selectedId)
//}

var selectedTrail = trails.find(trail => trail.id === id);
var currentLat = selectedTrail.lat;
var currentLng = selectedTrail.lng;


function initMap(id) {

    const map = new google.maps.Map(document.getElementById("map"), {
        zoom: 13,
        center: {lat: currentLat, lng: currentLng },
        mapTypeId: google.maps.MapTypeId.HYBRID,
        mapId: '59da3fe57cf0042e',
        mapTypeControl: false
    });

    new google.maps.Marker({
        position: {lat: currentLat, lng: currentLng },
        map,
        title: "Test"
    })
}




//    //Initialize array that will still the marker location and info window information for every trail in the left column
//    let allResults = [];
//
//    for (let i = 0; i < trails.length; i++){
//        //In a string, store the div that will appear as the info window for a given trail.
//        const infoWindowContent =
//            `<div style="background-color: #091E05; color: white; padding: 3rem; margin: 0; width: auto; height: auto">
//                <h3>${trails[i].name}</h3>
//                <h4>${trails[i].length} mi, Level ${trails[i].difficulty}</h4>
//            </div>`
//
//        //prepare data that will be needed to establish markers, including the trail object's ID so marker will recognize if trail's ID matches selected ID
//        const markerPosition = {lat: trails[i].lat, lng: trails[i].lng}
//        const title = trails[i].name
//        allResults.push({markerPosition, title, infoWindowContent, id: trails[i].id})
//    }

















//var map, infoWindow;
//
//function initMeetupMap() {
//    var options = {
//        center: { lat: 43.654, lng: -79.383 },
//        zoom: 10,
//        disableDefaultUI: true,
//        mapTypeId: google.maps.MapTypeId.HYBRID
//
//    };
//
//    map = new google.maps.Map(document.getElementById('map')), options);
//
//    var input = document.getElementById('search');
//    var searchBox = new google.maps.places.SearchBox(input);
//
//    map.addListener('bounds_changed', function() {
//        searchBox.setBounds(map.getBounds());
//    });
//
//    var markers = [];
//
//    searchBox.addListener('places_changed', function() {
//
//    })
//
//}














//infoWindow = new google.maps.infoWindow;
//
//    if (navigator.geolocation) {
//        navigator.geolocation.getCurrentPosition(function (p) {
//            var position = {
//            lat: p.coords.latitude,
//            lng: p.coords.longitude
//            };
//            infoWindow.setPosition(position);
//            infoWindow.setContent('Your location!');
//            infoWindow.open(map);
//       }, function () {
//            handleLocationError("Geolocation service failed", map.center());
//       })
//    } else {
//        handleLocationError('No geolocation available', map.center());
//    }
//function handleLocationError (content, position) {
//    infoWindow.setPosition(position);
//    infoWindow.setContent(content);
//    infoWindow.open(map);
//}