
// Create variable to hold map element, give initial settings to map
var mapboxAccessToken = "pk.eyJ1IjoiZGNib3k2ODY4IiwiYSI6ImNqbXBlbG5wejB6M3kzcHFjZDN0dDg1N2wifQ.fv6q_orjFeF9Vcx5nLEu3w";
var map = L.map('map').setView([37.8, -96], 4);
map.setMinZoom(4);
L.tileLayer('https://api.tiles.mapbox.com/v4/{id}/{z}/{x}/{y}.png?access_token=' + mapboxAccessToken, {
maxZoom: 18,
    attribution: 'Map data &copy; <a href="https://www.openstreetmap.org/">OpenStreetMap</a> contributors, ' +
        '<a href="https://creativecommons.org/licenses/by-sa/2.0/">CC-BY-SA</a>, ' +
        'Imagery © <a href="https://www.mapbox.com/">Mapbox</a>',
    id: 'mapbox.light'
}).addTo(map);

function getColor(name) {
return name == "New Hampshire" ? '#3399ff' :
       name == "Illinois"  ? '#3399ff' :
       name == "West Virginia"  ? '#3399ff' :
       '#FFEDA0';
}

function style(feature) {
return {
    fillColor: getColor(feature.properties.name),
    weight: 2,
    opacity: 1,
    color: 'white',
    dashArray: '3',
    fillOpacity: 0.7
};
}

function highlightFeature(e) {
var layer = e.target;

layer.setStyle({
    weight: 1,
    color: '#4286f4',
    dashArray: '',
    fillOpacity: 0.7
});

if (!L.Browser.ie && !L.Browser.opera && !L.Browser.edge) {
    layer.bringToFront();
}
}

function resetHighlight(e) {
var layer = e.target;

layer.setStyle({
    weight: 1,
    color: '#FFFFFF',
    dashArray: '',
    fillOpacity: 0.7
});

}

function zoomToFeature(e) {
map.fitBounds(e.target.getBounds());



}

function onEachFeature(feature, layer) {
layer.on({
    mouseover: highlightFeature,
    mouseout: resetHighlight,
    click: zoomToFeature
});
}


$.getJSON("/geo/Illinois_D.json",function(data){
L.geoJson(data, {
style: style,
onEachFeature: onEachFeature,
}).addTo(map);
});


var info = L.control();

info.onAdd = function (map) {
this._div = L.DomUtil.create('div', 'info'); // create a div with a class "info"
this.update();
return this._div;
};

// method that we will use to update the control based on feature properties passed
info.update = function (props) {
this._div.innerHTML = '<h4>Data</h4>' +  (props ?
    '<b>' + props.name + '</b><br />' + props.density + ' people / mi<sup>2</sup>'
    : 'Hover over a state');
};

info.addTo(map);
