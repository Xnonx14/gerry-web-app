import json
with open("nh_final.geojson", "r") as read_file:
	data = json.load(read_file)
f = open("processed.json", "w")
	
count = 0
for feature in data['features']:
	for (key,value) in feature['geometry'].items():
		if(key == "type"): 
			f.write(str(value)+":")
			continue
		f.write(str(value[0]))
	f.write("\n")
	count+=1
	
f.close()

