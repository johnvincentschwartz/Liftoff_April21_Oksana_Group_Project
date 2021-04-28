//https://www.youtube.com/watch?v=oVr6unKZbg4
//https://developers.google.com/maps/documentation/javascript/markers - "Make a Marker Accessible"
//https://developers.google.com/maps/documentation/javascript/infowindows

let selectedZoom = 10;
let selectedCenter = {lat: 38.6009, lng: -90.4329};

function setZoom(id) {
    let selectedTrail = trails.find(trail => trail.id === id)
    let currentLat = selectedTrail.lat;
    let currentLng = selectedTrail.lng;
    selectedCenter = {lat: currentLat, lng: currentLng}
    selectedZoom = 14
    initMap()
}

function initMap() {

  const map = new google.maps.Map(document.getElementById("map"), {
    zoom: selectedZoom,
    center: selectedCenter,
  });

    let testArray = [];

    for (let i = 0; i < trails.length; i++){
        const googlable = [{lat: trails[i].lat, lng: trails[i].lng}, trails[i].name]
        testArray.push(googlable)
    }

  const infoWindow = new google.maps.InfoWindow();

  testArray.forEach(([position, title], i) => {
    const marker = new google.maps.Marker({
      position,
      map,
      title: `${i + 1}. ${title}`,
      label: `${i + 1}`,
      optimized: false,
    });

    marker.addListener("click", () => {
      infoWindow.close();
      infoWindow.setContent(marker.getTitle());
      infoWindow.open(marker.getMap(), marker);
    });
  });
}