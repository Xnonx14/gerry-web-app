function running(compactness) {
    var source;
    var url = 'http://localhost:8080/running?';
    url += "compactness=" + compactness;
    console.log("enter here");

    if (!!window.EventSource) {
        var source = new EventSource(url);
        var elements = document.getElementById("test_messages");

        function add(message) {
            var element = document.createElement("li");
            element.innerHTML = message;
            elements.appendChild(element);
         }

        source.addEventListener('message', function(e) {
            var data = JSON.parse(e.data);
            add(data.count);
        }, true);

        source.addEventListener('open', function(e) {
            console.log("Connection opened");
        }, true);

        source.addEventListener('error', function(e) {
            console.log("Connection error");
        }, true);

        source.addEventListener('close', function(e) {
            console.log("Connection closed");
        }, true);


    }

}