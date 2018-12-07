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

var isAlgoRunning = false;
var queue = [];
var isPaused = false;
var move;
var init = false;
var subscribe = function () {
    if(init == true){
        isPaused = false;
    }
    var eventSource = new EventSource('algorithm/feed');

    eventSource.onmessage = function (e) {
        var move = JSON.parse(e.data);
        queue.push(move);
        if(isPaused == false && queue.length > 0){
            move = queue.shift();
            var precinctId = move.precinctId;
            var districtId = move.districtId;
            var color = genColor(districtId);
            new_Hampshire.setFeatureStyle(precinctId, colorStyle(color))
        }
    };

    eventSource.onopen = function () {
        if(init == false){
            init = true;
            startAlgorithm();
        }
    }

    window.onbeforeunload = function () {
        eventSource.close();
    }
}
var pause = function () {
    if(isPaused == false){
        isPaused  = true;
        queue = [];
    }
}

var make_step = function(){
    if(init == false){
        isPaused = true;
        subscribe();
    }
    if(isPaused == true && queue.length > 0){
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