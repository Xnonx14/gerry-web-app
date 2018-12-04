//Call Script
//<script id="changeColor" src="js/changeColor.js" precinct = ".." district = "..">/script>

//var precinct = $('#changeColor').attr("precinct");
//var district = $('#changeColor').attr("district");

function colorStyle(color){
    return {
    		fillColor: color,
    		fillOpacity: 0.3,
    		stroke: true,
    		fill: true,
    		color: 'black',
    		weight: 1,
    };
}


var district = '19';
for(i = 17; i <= 342; i++){
    var precinct = i.toString();
    if(district == '19'){
    district = '20';
    }
    else{district = '19';}
    var color = genColor(district);
    new_Hampshire.setFeatureStyle(precinct, colorStyle(color));
}
