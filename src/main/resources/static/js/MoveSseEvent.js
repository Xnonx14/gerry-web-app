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

var subscribe = function () {
    var eventSource = new EventSource('algorithm/feed');
    var elem = document.getElementById("pause_button");

    eventSource.onmessage = function (e) {
        var move = JSON.parse(e.data);
        queue.push(move);
        if(state == "NORMAL" && queue.length > 0){
            move = queue.shift();
            var precinctId = move.precinctId;
            var districtId = move.districtId;
            var color = genColor(districtId);
            new_Hampshire.setFeatureStyle(precinctId, colorStyle(color))
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
    var elem = document.getElementById("pause_button");
    if(state == "NOT_INIT"){

    }
    else if(state == "NORMAL"){
        state  = "PAUSED";
        elem.value = "Unpause";
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
            var precinctId = move.precinctId;
            var districtId = move.districtId;
            var color = genColor(districtId);
            new_Hampshire.setFeatureStyle(precinctId, colorStyle(color))
    }
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
    var params = {
        state: state,
        reock: reock,
        polsbyPopper: polsbyPopper,
        convexHull: convexHull,
        politicalFairness: politicalFairness,
        populationEquality: populationEquality,
		seedCount: seedCount
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