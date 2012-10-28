Route Import
-----------------------------------------

##### About:

Importing Waypoints from CSV file to neo4j using apache camel's Bindy Componentnt

the CSV file has the file format format

	point1,point2,distance_between_points
	-------------------------------------
	AIIMS,ANDREWS GANJ,2
	ANDREWS GANJ,LAJ PAT NAGAR,2
	LAJ PAT NAGAR,MAHARANI BAGH ,2


	The above line will create a route between AIIMS and ANDREWS GANJ having a relationship ROAD with distance of 2 km between each other
	An Index named waypoints will also be added to the nodes.
	
##### How to Execute:
	mvn camel:run	