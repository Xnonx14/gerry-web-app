function foolproofStart(){
    document.getElementById("startBtnID").disabled = true;
    document.getElementById("startBtnID").className = "disabledBtn";
    document.getElementById("pauseBtnID").className = "button2";
    document.getElementById("pauseBtnID").disabled = false;
    document.getElementById("stepBtnID").className = "button2";
    document.getElementById("stepBtnID").disabled = false;
    document.getElementById("stopBtnID").className = "button2";
    document.getElementById("stopBtnID").disabled = false;
    subscribe();
}

function foolproofStop(){
    document.getElementById("startBtnID").className = "button2";
    document.getElementById("startBtnID").disabled = false;
    document.getElementById("pauseBtnID").className = "disabledBtn";
    document.getElementById("pauseBtnID").disabled = true;
    document.getElementById("stepBtnID").className = "disabledBtn";
    document.getElementById("stepBtnID").disabled = true;
    document.getElementById("stopBtnID").className = "disabledBtn";
    document.getElementById("stopBtnID").disabled = true;
    stop();
}

function displayStateData(){
	if(document.getElementById("innerData").style['display'] != "block"){
        document.getElementById("innerData").style = "display:block; float:left; width:49%;";
        document.getElementById("precinctDataID").style = "display:block; float:left; width:49%;";
		document.getElementById("data").style="width: 30%"
		document.getElementById("displayBtn").value = "-";
		document.getElementById("first").style = "width: 50%";
    }else{
        document.getElementById("innerData").style = "display: none";
        document.getElementById("precinctDataID").style = "display:none;";
		document.getElementById("data").style="width: 3%"
		document.getElementById("displayBtn").value = "+";
		document.getElementById("first").style = "width: 77%";
    }
	
}

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

var precinct_data;
var StateMap = {};

function stateSelected(){
    document.getElementById("selectAlgorithm").style = "visibility: visible";
	var state = document.getElementById("selected_state").value;
	if(state == "Illinois")
	document.getElementById("constitution_text").innerHTML = "Representative Districts shall be compact, contiguous, and substantially equal in population."
    if(state == "West Virginia")
    document.getElementById("constitution_text").innerHTML = "State Senate districts must be compact, contiguous, and bounded by county lines where doing so is not otherwise unlawful."
    if(state == "New Hampshire")
    document.getElementById("constitution_text").innerHTML = "For state representative districts, towns or wards near the average population for one or more seats are to constitute whole districts; additional population may be combined in overlapping, or floterial, districts. New Hampshire towns may determine whether they wish to split a multi-member district into multiple single-member districts."

	var xhr = new XMLHttpRequest();
    xhr.open("POST", '/setupState', true);
    xhr.setRequestHeader("Content-Type", "application/json");
    var params = {
        state: state
    };

	xhr.onreadystatechange = function() {
	if (this.readyState == 4 && this.status == 200) {
	    precinct_data = JSON.parse(xhr.responseText);
	    for (var key in precinct_data) {
	        var districtId = precinct_data[key];
	        var precinctId = key;
	        var color = genColor(districtId);
	        StateMap[state].setFeatureStyle(precinctId, colorStyle(color))
	    }
    }
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
    '<b>' + "ID" + '</b><br />' + props
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

var illinois = L.vectorGrid.slicer( illinoisData, {
	minZoom: 8,
	rendererFactory: L.svg.tile,
	vectorTileLayerStyles: {
	sliced: function(properties, zoom) {
	var p = parseInt(properties.CONG_DISTR);
		return {
		fillColor: genColor(p),
		fillOpacity: 0.5,
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
		.on('mouseover', function(e) {
			var properties = e.layer.properties;
			info.update(properties.PRECINCT_ID);
			document.getElementById("tfPrecinctID").innerHTML= properties.PRECINCT_ID;
            document.getElementById("tfDistrictID").innerHTML= districtMap[parseInt(properties.PRECINCT_ID)];
            document.getElementById("precinctPopulation").innerHTML= properties.POP100;
            if (typeof dataMap[districtMap[parseInt(properties.PRECINCT_ID)]] !== 'undefined'){
                document.getElementById("tfTotalPop").innerHTML = dataMap[districtMap[parseInt(properties.PRECINCT_ID)]].population;
                document.getElementById("tfObjectiveFunction").innerHTML = dataMap[districtMap[parseInt(properties.PRECINCT_ID)]].value;
                document.getElementById("dem").innerHTML = dataMap[districtMap[parseInt(properties.PRECINCT_ID)]]['wastedVotes']['Democrat'];
                document.getElementById("gre").innerHTML = dataMap[districtMap[parseInt(properties.PRECINCT_ID)]]['wastedVotes']['Green'];
                document.getElementById("lib").innerHTML = dataMap[districtMap[parseInt(properties.PRECINCT_ID)]]['wastedVotes']['Libertarian'];
                document.getElementById("oth").innerHTML = dataMap[districtMap[parseInt(properties.PRECINCT_ID)]]['wastedVotes']['Other'];
                document.getElementById("rep").innerHTML = dataMap[districtMap[parseInt(properties.PRECINCT_ID)]]['wastedVotes']['Republican'];
            }
            //parseInt(precinct_data[properties.PRECINCT_ID])

//			L.popup()
//				.setContent("My parent district ID is: " + precinct_data[properties.PRECINCT_ID])
//				.setLatLng(e.latlng)
//				.openOn(map);
		})
.addTo(map);

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
		.on('mouseover', function(e) {
			var properties = e.layer.properties;
			info.update(properties.PRECINCT_ID);
			document.getElementById("tfPrecinctID").innerHTML= properties.PRECINCT_ID;
            document.getElementById("tfDistrictID").innerHTML = districtMap[parseInt(properties.PRECINCT_ID)];
            document.getElementById("precinctPopulation").innerHTML= properties.POP100;
            if (typeof dataMap[districtMap[parseInt(properties.PRECINCT_ID)]] !== 'undefined'){
                document.getElementById("tfTotalPop").innerHTML = dataMap[districtMap[parseInt(properties.PRECINCT_ID)]].population;
                document.getElementById("tfObjectiveFunction").innerHTML = dataMap[districtMap[parseInt(properties.PRECINCT_ID)]].value;
                document.getElementById("dem").innerHTML = dataMap[districtMap[parseInt(properties.PRECINCT_ID)]]['wastedVotes']['Democrat'];
                document.getElementById("gre").innerHTML = dataMap[districtMap[parseInt(properties.PRECINCT_ID)]]['wastedVotes']['Green'];
                document.getElementById("lib").innerHTML = dataMap[districtMap[parseInt(properties.PRECINCT_ID)]]['wastedVotes']['Libertarian'];
                document.getElementById("oth").innerHTML = dataMap[districtMap[parseInt(properties.PRECINCT_ID)]]['wastedVotes']['Other'];
                document.getElementById("rep").innerHTML = dataMap[districtMap[parseInt(properties.PRECINCT_ID)]]['wastedVotes']['Republican'];
            }
            //parseInt(precinct_data[properties.PRECINCT_ID])

//			L.popup()
//				.setContent("My parent district ID is: " + precinct_data[properties.PRECINCT_ID])
//				.setLatLng(e.latlng)
//				.openOn(map);
		})
.addTo(map);

var west_Virginia = L.vectorGrid.slicer(wV_data, {
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
		.on('mouseover', function(e) {
			var properties = e.layer.properties;
			info.update(properties.PRECINCT_ID);
			document.getElementById("tfPrecinctID").innerHTML = properties.PRECINCT_ID;
            document.getElementById("tfDistrictID").innerHTML = districtMap[parseInt(properties.PRECINCT_ID)];
            document.getElementById("precinctPopulation").innerHTML= properties.POP100;
            if (typeof dataMap[districtMap[parseInt(properties.PRECINCT_ID)]] !== 'undefined'){
            document.getElementById("tfTotalPop").innerHTML = dataMap[districtMap[parseInt(properties.PRECINCT_ID)]].population;
            document.getElementById("tfObjectiveFunction").innerHTML = dataMap[districtMap[parseInt(properties.PRECINCT_ID)]].value;
            document.getElementById("dem").innerHTML = dataMap[districtMap[parseInt(properties.PRECINCT_ID)]]['wastedVotes']['Democrat'];
            document.getElementById("gre").innerHTML = dataMap[districtMap[parseInt(properties.PRECINCT_ID)]]['wastedVotes']['Green'];
            document.getElementById("lib").innerHTML = dataMap[districtMap[parseInt(properties.PRECINCT_ID)]]['wastedVotes']['Libertarian'];
            document.getElementById("oth").innerHTML = dataMap[districtMap[parseInt(properties.PRECINCT_ID)]]['wastedVotes']['Other'];
            document.getElementById("rep").innerHTML = dataMap[districtMap[parseInt(properties.PRECINCT_ID)]]['wastedVotes']['Republican'];
            }
            //parseInt(precinct_data[properties.PRECINCT_ID])

//			L.popup()
//				.setContent("My parent district ID is: " + precinct_data[properties.PRECINCT_ID])
//				.setLatLng(e.latlng)
//				.openOn(map);
		})
.addTo(map);

map.on('zoomend', function (e) {
    zoomHandler();
});

StateMap["Illinois"] = illinois;
StateMap["New Hampshire"] = new_Hampshire;
StateMap["West Virginia"] = west_Virginia;

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