function algoSelected(){
    if(document.getElementById("selected_algo").value === "region"){
        document.getElementById("algoTitle").innerHTML = "Region Growing";
        document.getElementById("regionSeeds").style = "display: block"; 
        
    }else{
        document.getElementById("algoTitle").innerHTML = "Simulated Annealing";
        document.getElementById("regionSeeds").style = "display: none"; 
    }
    document.getElementById("algoConfigR").style = "display: block";
}

function stateSelected(){
    document.getElementById("selectAlgorithm").style = "visibility: visible";
	var state = document.getElementById("selected_state").value;
	
	var xhr = new XMLHttpRequest();
    xhr.open("POST", '/setupState', true);
    xhr.setRequestHeader("Content-Type", "application/json");
    var params = {
        state: state
    };
	
	xhr.onreadystatechange = function() {        
		console.log(Object.keys(xhr));
		console.log(Object.values(xhr));
		console.log(xhr);
    }
	
    xhr.send(JSON.stringify(params));
	
	
	
	
	
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

//Color Generation
function genColor (seed) {
    color = Math.floor((Math.abs(Math.sin(parseInt(seed)) * 16777215)) % 16777215);
    color = color.toString(16);
    while(color.length < 6) {
        color = '0' + color;
    }

    return '#' + color;
}

//Parse Json
$.getJSON("/geo/states.json",function(data){
stateData = L.geoJson(data, {
style: style,
onEachFeature: onEachFeature,
}).addTo(map);
})
//fetch('geo/Illinois_P.json').then(function(response){
//			return response.json();
//		}).then(function(json){
//var illinois = L.vectorGrid.slicer( json, {
//	minZoom: 8,
//	rendererFactory: L.svg.tile,
//	vectorTileLayerStyles: {
//	sliced: function(properties, zoom) {
//	var p = parseInt(properties.COUNTYFP10);
//		return {
//		fillColor: genColor(p),
//		fillOpacity: 0.5,
//		stroke: true,
//		fill: true,
//		color: 'white',
//		weight: 1,
//		}
//	}
//},
//	interactive: true,
//	getFeatureId: function(f) {
//		return f.properties.wb_a3;
//	}
//})
//		.on('click', function(e) {
//			var properties = e.layer.properties;
//			L.popup()
//				.setContent(properties.VTDST10)
//				.setLatLng(e.latlng)
//				.openOn(map);
//		})
//.addTo(map);
//});
//
//var illinois_district = L.vectorGrid.slicer(illinoisDistrict, {
//	minZoom: 6,
//	rendererFactory: L.svg.tile,
//	vectorTileLayerStyles: {
//	sliced: function(properties, zoom) {
//		return {
//		fillColor: 'white',
//		fillOpacity: 0,
//		stroke: true,
//		fill: true,
//		color: 'black',
//		weight: 1,
//		}
//	}
//}
//}).addTo(map);
var new_Hampshire = L.vectorGrid.slicer(nH_data, {
	minZoom: 7,
	rendererFactory: L.svg.tile,
	vectorTileLayerStyles: {
	sliced: function(properties, zoom) {
		var district = parseInt(properties.CONG_DISTR);
		return {
		fillColor: 'white',
		fillOpacity: 0.6,
		stroke: true,
		fill: true,
		color: 'white',
		weight: 1,
		}
	}
},
	interactive: true,
	getFeatureId: function(f) {
		return f.properties.PRECINCT_ID;
	}
})
		.on('click', function(e) {
			var properties = e.layer.properties;
			L.popup()
				.setContent(properties.VTDST10)
				.setLatLng(e.latlng)
				.openOn(map);
		})
.addTo(map);

var west_Virginia = L.vectorGrid.slicer(wV_data, {
	minZoom: 7,
	rendererFactory: L.svg.tile,
	vectorTileLayerStyles: {
	sliced: function(properties, zoom) {
		var district = parseInt(properties.CONG_DISTR);
		return {
		fillColor: genColor(district),
		fillOpacity: 0.6,
		stroke: true,
		fill: true,
		color: 'white',
		weight: 1,
		}
	}
},
	interactive: true,
	getFeatureId: function(f) {
		return f.properties.PRECINCT_ID;
	}
})
		.on('click', function(e) {
			var properties = e.layer.properties;
			L.popup()
				.setContent(properties.VTDST10)
				.setLatLng(e.latlng)
				.openOn(map);
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
        case 4:
            clean_map();
            stateData.addTo(map);
            break;
        case 5:
            clean_map();
            stateData.addTo(map);
            break;
        case 6:
            clean_map();
            break;
        case 7:
            clean_map();
            break;
        case 8:
            clean_map();
            break;
        default:
            break;
    }
}