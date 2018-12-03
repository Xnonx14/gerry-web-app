var subscribe = function () {
    var eventSource = new EventSource('algorithm/feed');

    eventSource.onmessage = function (e) {
        var move = JSON.parse(e.data);
        document.getElementById("notificationDiv").innerHTML += move.data + " at " + new Date(move.dateSent) + "<br/>";
    };

    eventSource.onopen = function () {
        startAlgorithm();
    }

    window.onbeforeunload = function () {
        eventSource.close();
    }
}

var startAlgorithm = function () {
    console.log("Entered start algo function.")
    var state = document.getElementById("selected_state").value;
    var compactness = document.getElementById("compactness").value;
    var politicalFairness = document.getElementById("political_fairness").value;
    var populationEquality = document.getElementById("population_equality").value;
    var params = {
        state: state,
        compactness: compactness,
        politicalFairness: politicalFairness,
        populationEquality: populationEquality
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