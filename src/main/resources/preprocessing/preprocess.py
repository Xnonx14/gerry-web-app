from shapely.geometry import Polygon

file = open("step1.json", "r")

for line in file:
	if "Polygon" in line:
		print(line)