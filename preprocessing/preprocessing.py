from Shapely.geometry import Polygon
from matplotlib import pyplot as plt
from descartes import PolygonPatch

with open("nh_final.json") as f:
	data = json.load(f)

counties = dict()
polygons = dict()

for feature in data['features']:
	arr = []
	countyName = feature['COUNTYFP']
	precinctName = feature['VTDST10']
	coordinates = feature['geometry']['coordinates']
	coordinates = coordinates.strip("[");
	coordinates = coordinates.strip("]");
	for set in line.split("], ["):
		temp = set.split(", ")
		arr.append((float(temp[0]),float(temp[1])))
	polygons[precinctName] = (Polygon(arr))
	if countyName in counties:
		counties[countyName].append(precinctName);
	else:
		counties[countyName] = [precinctName]

fig = plt.figure()

ax = fig.add_subplot(111)

adjacencyMap = {}

for i in polygons.keys():
	polygonList = []
	for j in polygons.keys():
		if i == j: 
			continue
		if polygons[i].touches(polygons[j]):
			polygonList.append(j)
	adjacencyMap[i] = polygonList

file = open("NH_outputAdj", "w")
# print(adjacencyMap)
file.write(str(adjacencyMap))

f = open("NH_counties.txt", "w")
for c in counties.keys():
	f.write(str(counties[c]) + "\n")