from shapely.geometry import Polygon, shape, mapping
from shapely.ops import cascaded_union
import json

with open("nh_final.json") as f:
	data = json.load(f)

counties = dict()
polygons = dict()

for feature in data['features']:
	arr = []
	countyName = feature['properties']['COUNTYFP10']
	precinctName = feature['properties']['VTDST10']
	geometry = feature['geometry']
	count = 0
	coordinates = geometry['coordinates']
	type = geometry['type']
	if(type == "MultiPolygon"):
		for coord in coordinates[0][0]:
			arr.append((coord[0],coord[1]))
	else:
		for coord in coordinates[0]:
			arr.append((coord[0],coord[1]))
	polygons[precinctName] = (Polygon(arr))
	if countyName in counties:
		counties[countyName].append(precinctName);
	else:
		counties[countyName] = [precinctName]


#print(counties)
#print(polygons)
jsonData = {}
#boundaryCoordinates	cascaded_union(polygons)
for chunk in counties.keys():
	precincts = counties[chunk]
	countyCoordinates = []
	for temp in precincts:
		countyCoordinates.append(polygons[temp])
	boundary = cascaded_union(countyCoordinates)
	temp = {}
	temp["precincts"] = counties[chunk]

	temp["geometry"] = mapping(boundary)
	jsonData[chunk] = temp

#adjacencyMap
# adjacencyMap = {}
# for i in polygons.keys():
# 	polygonList = []
# 	for j in polygons.keys():
# 		if i == j: 
# 			continue
# 		if polygons[i].touches(polygons[j]):
# 			polygonList.append(j)
# 	adjacencyMap[i] = polygonList

# file = open("IL_outputAdj", "w")
# # print(adjacencyMap)
# file.write(str(adjacencyMap))

f = open("NH_counties.txt", "w")
for c in counties.keys():
	f.write(str(c) + ": "+str(counties[c]) + "\n")
		
with open('NH_counties.json', 'w') as outfile:
    json.dump(jsonData, outfile)