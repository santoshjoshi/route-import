package org.apache.camel.example.routes;

import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Transaction;
import org.neo4j.graphdb.index.Index;
import org.neo4j.graphdb.index.IndexManager;
import org.neo4j.kernel.EmbeddedGraphDatabase;

/**
 * 
 * @author santosh joshi
 *
 */
public class GraphUtil {

	private static GraphDatabaseService graphDb = new EmbeddedGraphDatabase("target/graph.db");
	
	public static void create(Way way) {
		Transaction tx = graphDb.beginTx();
		try {
			IndexManager index = graphDb.index();
			Index<Node> waypointsIndex = index.forNodes("waypoints");
			
			Waypoint point1 = null;
			Waypoint point2 = null;
			
			Node node1 = (Node) waypointsIndex.query(Waypoint.NAME,  way.getPoint1()).getSingle() ;
			Node node2 = (Node) waypointsIndex.query(Waypoint.NAME,  way.getPoint2()).getSingle() ;
			
			boolean point1IsNull = false;
			boolean point2IsNull = false;
			
			String point1Name = firstLetterUpperCase(way.getPoint1());
			String point2Name = firstLetterUpperCase(way.getPoint2());
			
			if(node1 == null ){
				point1 = new Waypoint(graphDb.createNode(), point1Name);
				point1IsNull = true;
			}else{
				point1 = new Waypoint(node1);
			}
			
			if(node2 == null ){
				point2 = new Waypoint(graphDb.createNode(), point2Name);
				point2IsNull = true;
			}else{
				point2 = new Waypoint(node2);
			}
			
			point1.createRoadTo(point2, way.getDistance());

			if(point1IsNull){
				waypointsIndex.add(point1.getUnderlyingNode(), Waypoint.NAME, point1.getUnderlyingNode().getProperty(Waypoint.NAME));
			}
			if(point2IsNull){
				waypointsIndex.add(point2.getUnderlyingNode(), Waypoint.NAME, point2.getUnderlyingNode().getProperty(Waypoint.NAME));
			}

			tx.success();
		} finally {
			tx.finish();
		}
	}
	
	public static String firstLetterUpperCase(String sString){
		
		sString = sString.toLowerCase().trim();
		sString = Character.toString(sString.charAt(0)).toUpperCase()+sString.substring(1);
		
		return sString;
	}
}
