package org.apache.camel.example.routes;

import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Transaction;
import org.neo4j.graphdb.index.Index;
import org.neo4j.graphdb.index.IndexManager;
import org.neo4j.helpers.collection.MapUtil;
import org.neo4j.kernel.EmbeddedGraphDatabase;

/**
 * 
 * @author santosh joshi
 *
 */
public class GraphUtil {

	private static GraphDatabaseService graphDb = new EmbeddedGraphDatabase("/easy-routes/data/graph.db");
	
	public static void create(Way way) {
		Transaction tx = graphDb.beginTx();
		try {
			IndexManager index = graphDb.index();
			Index<Node> waypointsIndex = index.forNodes("waypoints", MapUtil.stringMap( IndexManager.PROVIDER, "lucene", "type", "fulltext" ) );
			
			Waypoint point1 = null;
			Waypoint point2 = null;
			
			String point1Name = firstLetterUpperCase(way.getPoint1());
			String point2Name = firstLetterUpperCase(way.getPoint2());
			
			Node node1 = null; //(Node) waypointsIndex.query(Waypoint.NAME, point1Name).getSingle() ;
			Node node2 = null; //(Node) waypointsIndex.query(Waypoint.NAME, point2Name).getSingle() ;
			
			for (Node node : waypointsIndex.query(Waypoint.NAME, point1Name)) {
				if(node.getProperty(Waypoint.NAME).toString().trim().toLowerCase().equalsIgnoreCase(point1Name)){
					node1 = node;
					break;
				}
			}
			
			for (Node node : waypointsIndex.query(Waypoint.NAME, point2Name)) {
				if(node.getProperty(Waypoint.NAME).toString().trim().toLowerCase().equalsIgnoreCase(point2Name)){
					node2 = node;
					break;
				}
			}
			
			boolean point1IsNull = false;
			boolean point2IsNull = false;
			
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
