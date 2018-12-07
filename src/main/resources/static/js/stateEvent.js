function updateData() {
  console.log(this.responseText);
}



function getStateData() {
    var selectedState = document.getElementById("selected_state").value;

	var params = {
        state: selectedState
    };
	
	var oReq = new XMLHttpRequest();
    oReq.open("POST", '/serverListener', true);
	oReq.setRequestHeader("Content-Type", "application/json");
	oReq.addEventListener("load", updateData);
	oReq.send(JSON.stringify(params));
}