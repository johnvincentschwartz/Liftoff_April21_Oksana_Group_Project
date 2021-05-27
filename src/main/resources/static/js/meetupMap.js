let initialLoad = true;
let map;
let selectedId;

function initMap(id) {
    let meetupTrail = meetups.trail.id;
    //console.log(meetupTrail)
    let selectedTrail = trails.find(trail => trail.id === meetupTrail);
    selectedId = id;
    let currentLat = selectedTrail.lat;
    let currentLng = selectedTrail.lng;


    map = new google.maps.Map(document.getElementById("map"), {
        zoom: 16,
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