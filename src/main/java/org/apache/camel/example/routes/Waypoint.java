package org.apache.camel.example.routes;

/**
 * @author santosh joshi
 */
import org.neo4j.graphdb.DynamicRelationshipType;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Relationship;

public class Waypoint
{
    public static final String NAME = "name";
    public static final String DISTANCE = "distance" ;
    public static final DynamicRelationshipType ROAD = DynamicRelationshipType.withName( "ROAD" );
    
    private final Node underlyingNode;

    public Waypoint( Node node, final String city ){
        this.underlyingNode = node;
        underlyingNode.setProperty( NAME, city );
        System.out.println( this );
    }

    public Waypoint(Node node) {
    	 this.underlyingNode = node;
	}

	public Node getUnderlyingNode(){
        return underlyingNode;
    }

    public String getName(){
        return (String) underlyingNode.getProperty( NAME );
    }


    public void createRoadTo( final Waypoint other, final double distance) {
        Relationship road = underlyingNode.createRelationshipTo(other.underlyingNode, ROAD );
      
        road.setProperty(DISTANCE, distance);
    }

    @Override
    public String toString()
    {
        return "Waypoint [name= " + getName() +" ]";
    }

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((underlyingNode == null) ? 0 : underlyingNode.getProperty(Waypoint.NAME).hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Waypoint other = (Waypoint) obj;
		if (underlyingNode == null) {
			if (other.underlyingNode != null)
				return false;
		} else if (!underlyingNode.getProperty(Waypoint.NAME).equals(other.underlyingNode.getProperty(Waypoint.NAME)))
			return false;
		return true;
	}
    
    
}
