var subscribe = function () {
    var eventSource = new EventSource('running/moves');

    eventSource.onmessage = function (e) {
        var move = JSON.parse(e.data);
        document.getElementById("notificationDiv").innerHTML += move.data + " at " + new Date(move.dateSent) + "<br/>";
    };

    window.onbeforeunload = function () {
        eventSource.close();
    }
}

var subscribeData = function () {
    var eventSource = new EventSource('running/data');

    eventSource.onmessage = function (e) {
        var move = JSON.parse(e.data);
        document.getElementById("notificationDiv").innerHTML += move.data + " at " + new Date(move.dateSent) + "<br/>";
    };

    window.onbeforeunload = function () {
        eventSource.close();
    }
}