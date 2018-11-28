from shapely.geometry import Polygon
from matplotlib import pyplot as plt
from descartes import PolygonPatch

file = open("step1.json", "r")
polygons = []
for line in file:
<<<<<<< HEAD:preprocessing/preprocess.py
	#if "Polygon" in line:
		#print(line)
	if "Multi" in line:
		print(line)
=======
	#There is one polygon per line
	arr = []
	if "Multi" in line:
		line = line[15:-3]
		line = line.strip("[")
		line = line.strip("]")
		for set in line.split("], ["):
			temp = set.split(", ")
			arr.append((float(temp[0]),float(temp[1])))
	else:
		line = line[9:-2]
		line = line.strip("[")
		line = line.strip("]")
		
		for set in line.split("], ["):
			temp = set.split(", ")
			arr.append((float(temp[0]),float(temp[1])))
	polygons.append(Polygon(arr))
fig = plt.figure()

ax = fig.add_subplot(111)

adjacencyMap = {}

for i in range(0,len(polygons)):
	polygonList = []
	for j in range(0, len(polygons)):
		if i == j: 
			continue
		if polygons[i].touches(polygons[j]):
			polygonList.append(j)
	adjacencyMap[i] = polygonList

file = open("outputAdj", "w")
print(adjacencyMap)
file.write(str(adjacencyMap))
>>>>>>> 39d19ca3c9d828803b3ce3e988bc3f3388dd8138:src/main/resources/preprocessing/preprocess.py
