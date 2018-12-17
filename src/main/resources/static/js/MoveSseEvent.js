function colorStyle(color){
        return {
            	fillColor: color,
            	fillOpacity: 0.3,
            	stroke: true,
            	fill: true,
            	color: 'white',
            	weight: 1,
            };
}

var queue = [];
var state = "NOT_INIT";
var move;
var districtMap = {};
var dataMap = {};
var eventSource = null;

var nh_start = 17;
var nh_end = 345;
var wv_start = 11904;
var wv_end = 13759;

var subscribe = function () {

    var selected_state = document.getElementById("selected_state").value;
    document.getElementById("tfObjectiveFunction").value = "";
    if(state == "CLOSED"){
        if(selected_state === "New Hampshire") {
            if(state == "NORMAL" || state == "PAUSED"){
                for(var i = nh_start; i <= nh_end; i++){
                    StateMap[state].resetFeatureStyle(i);
                }
            }
        }
        if(selected_state === "West Virginia") {
            if(state == "NORMAL" || state == "PAUSED"){
                for(var i = wv_start; i <= wv_end; i++){
                    StateMap[state].resetFeatureStyle(i);
                }
            }
        }
        state = "NOT_INIT";
    }

    var selectedAlgo = document.getElementById("selected_algo");

    if(selectedAlgo.value === "region") {
        console.log("NONE");
        if(selected_state === "New Hampshire") {
            if(state == "NORMAL" || state == "PAUSED"){
                for(var i = nh_start; i <= nh_end; i++){
                    StateMap[state].resetFeatureStyle(i);
                }
            }
        }
        if(selected_state === "West Virginia") {
            if(state == "NORMAL" || state == "PAUSED"){
                for(var i = wv_start; i <= wv_end; i++){
                    StateMap[state].resetFeatureStyle(i);
                }
            }
        }
    }

    eventSource = new EventSource('algorithm/feed');
    var elem = document.getElementById("pauseBtnID");

    eventSource.onmessage = function (e) {
        console.log("running");
        var move = JSON.parse(e.data);
        var district_data = {
            population: move.destDistrictPopulation,
            gain: move.objectiveGain,
            value: move.objectiveValue
        };
        districtMap[move.precinctId] = move.destDistrictId;
        dataMap[move.destDistrictId] = district_data;
        queue.push(move);
        if(state == "CLOSED"){
           state = "NOT_INIT";
           elem.value = "Pause";
           queue = [];
           eventSource.close();
        }
        if(state == "NORMAL" && queue.length > 0){
            move = queue.shift();
            document.getElementById("tfObjectiveFunction").value = move.objectiveValue;
            if(selected_state == "West Virginia"){
                var districtId = move.destDistrictId;
                var Ids = move.precinctIds;
                var color = genColor(districtId);
                Ids.forEach(function(precinctId) {
                     StateMap[selected_state].setFeatureStyle(precinctId, colorStyle(color))
                });
            }
            else{
            var precinctId = move.precinctId;
            var districtId = move.destDistrictId;
            var color = genColor(districtId);
            StateMap[selected_state].setFeatureStyle(precinctId, colorStyle(color))
            }
        }
    };
    eventSource.onopen = function () {
        if(state == "NOT_INIT"){
            state = "NORMAL";
            startAlgorithm();
        }
        else if(state == "INIT_FROM_STEP"){
            state = "PAUSED";
            elem.value = "Unpause";
            startAlgorithm();
        }
    }

    window.onbeforeunload = function () {
        eventSource.close();
    }
}
var pause = function () {
    var elem = document.getElementById("pauseBtnID");
    if(state == "NOT_INIT"){

    }
    else if(state == "NORMAL"){
        state  = "PAUSED";
        elem.value = "Resume";
    }
    else if(state == "PAUSED"){
        state  = "NORMAL";
        elem.value = "Pause";
    }
}

var make_step = function(){
    if(state == "NOT_INIT"){
        state = "INIT_FROM_STEP"
        subscribe();
    }
    if(state == "PAUSED" && queue.length > 0){
            move = queue.shift();
            document.getElementById("tfObjectiveFunction").value = move.objectiveValue;
            var precinctId = move.precinctId;
            var districtId = move.districtId;
            var color = genColor(districtId);
            StateMap[state].setFeatureStyle(precinctId, colorStyle(color))
    }
}

var stop = function(){
    state = "CLOSED";
    eventSource.close();
    queue = [];
    eventSource = null;
    var xhr = new XMLHttpRequest();
    xhr.open("POST", '/algorithm/stop', true);
    xhr.setRequestHeader("Content-Type", "application/json");
    xhr.send();
}

var startAlgorithm = function () {
    isAlgoRunning = true;
    console.log("Entered start algo function.")
    var state = document.getElementById("selected_state").value;
    var reock = document.getElementById("reock").value;
    var polsbyPopper = document.getElementById("polsbyPopper").value;
    var convexHull = document.getElementById("convexHull").value;
    var politicalFairness = document.getElementById("politicalFairness").value;
    var populationEquality = document.getElementById("populationEquality").value;
	var seedCount = document.getElementById("seedCount").value;
	var algo = document.getElementById("selected_algo").value;
	//algo can be "region" or "simulated"
    var params = {
        state: state,
        reock: reock,
        polsbyPopper: polsbyPopper,
        convexHull: convexHull,
        politicalFairness: politicalFairness,
        populationEquality: populationEquality,
		seedCount: seedCount,
		mode: algo
    };

    var xhr = new XMLHttpRequest();
    xhr.open("POST", '/algorithm/start', true);
    xhr.setRequestHeader("Content-Type", "application/json");
    xhr.onreadystatechange = function() {
        if (this.readyState === XMLHttpRequest.DONE && this.status === 200) {
            // Request finished. Do processing here.
            console.log("Algorithm ")
        }
    }
    xhr.send(JSON.stringify(params));
}