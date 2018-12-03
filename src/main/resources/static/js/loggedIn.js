function algoSelected(){
    if(document.getElementById("selected_algo").value == "region"){
        document.getElementById("algoConfigR").style = "display: block";
        document.getElementById("algoConfigS").style = "display: none";
    }else{
        document.getElementById("algoConfigS").style = "display: block";
        document.getElementById("algoConfigR").style = "display: none";
    }


}
function stateSelected(){
    document.getElementById("selectAlgorithm").style = "visibility: visible";
}
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


//Color and Style Code
function getColor(name) {
return name == "New Hampshire" ? '#EE82EE' :
       name == "Illinois"  ? '#EE82EE' :
       name == "West Virginia"  ? '#EE82EE' :
       '#FFEDA0';
}

function style(feature) {
return {
    fillColor: getColor(feature.properties.NAME),
    weight: 2,
    opacity: 1,
    color: 'white',
    dashArray: '3',
    fillOpacity: 0.3
};
}

function styleDistrict(feature) {
return {
    fillColor: '#3399ff',
    weight: 2,
    opacity: 1,
    color: 'white',
    dashArray: '3',
    fillOpacity: 0.3
};
}


//Highlight on hover Code
function highlightFeature(e) {
var layer = e.target;

layer.setStyle({
    weight: 1,
    color: '#4286f4',
    dashArray: '',
    fillOpacity: 0.3
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
    fillOpacity: 0.3
});

}

//Click to Zoom Code
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


//Add info to Data on hover code
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


//Button Click Zoom to State
$(function() {
    $( "#select_state" ).click(function() {
		stateData.eachLayer(function (layer) {
			if (layer.feature.properties.NAME === $("#selected_state").val()) {
				map.fitBounds(layer.getBounds());
			}
		});
    });
});

//Parse Json

$.getJSON("/geo/states.json",function(data){
stateData = L.geoJson(data, {
style: style,
onEachFeature: onEachFeature,
}).addTo(map);
})

//$.getJSON("/geo/Illinois_D.json",function(data){
//IllinoisDistrict = L.geoJson(data, {
//style: styleDistrict,
//onEachFeature: onEachFeature,
//})
//});
//
//$.getJSON("/geo/Illinois_P.json",function(data){
//IllinoisPrecinct = L.geoJson(data, {
//style: styleDistrict,
//onEachFeature: onEachFeature,
//})
//});


var precinct = L.vectorGrid.slicer( illinoisPrecienct, {
	minZoom: 8,
	rendererFactory: L.svg.tile,
	vectorTileLayerStyles: {
	sliced: function(properties, zoom) {
	var p = parseInt(properties.GEOID10);
		return {
		fillColor:
		(p % 2 == 0)  ?  '#F92828' :
		(p % 2 == 1)  ?  '#5AA5EC' : '#F92828',
		fillOpacity: 0.5,
		stroke: true,
		fill: true,
		color: 'black',
		weight: 0.5,
		}
	}
},
	interactive: true,
	getFeatureId: function(f) {
		return f.properties.wb_a3;
	}
})
.addTo(map);

var district = L.vectorGrid.slicer(illinoisDistrict, {
	minZoom: 6,
	rendererFactory: L.svg.tile,
	vectorTileLayerStyles: {
	sliced: function(properties, zoom) {
		return {
		fillColor: '#FFFFE0',
		fillOpacity: 0.3,
	 	//fillOpacity: 1,
		stroke: true,
		fill: true,
		color: 'black',
		weight: 1,
		}
	}
},
	interactive: true,
	getFeatureId: function(f) {
		return f.properties.wb_a3;
	}
})
.addTo(map);

map.on('zoomend', function (e) {
    zoomHandler();
});

function clean_map() {
    map.eachLayer(function (layer) {
        if (layer instanceof L.GeoJSON)
        {
            map.removeLayer(layer);
        }
    });
}

function zoomHandler() {
var currentZoom = map.getZoom();
    switch (currentZoom) {
        case 5:
            clean_map();
            stateData.addTo(map);
            break;
        case 6:
            clean_map();
            break;
        default:
            break;
    }
}