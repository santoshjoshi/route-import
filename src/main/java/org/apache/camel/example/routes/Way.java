package org.apache.camel.example.routes;

import org.apache.camel.dataformat.bindy.annotation.CsvRecord;
import org.apache.camel.dataformat.bindy.annotation.DataField;

/**
 * 
 * @author santosh joshi
 *
 */

@CsvRecord(separator = ",")
public class Way {

	@DataField(pos = 1, required = true, trim= true)
	private String point1;

	@DataField(pos = 2, required = true, trim= true)
	private String point2;

	@DataField(pos = 3, required = true, trim= true)
	private int distance;

	public String getPoint1() {
		return point1;
	}

	public void setPoint1(String point1) {
		this.point1 = point1;
	}

	public String getPoint2() {
		return point2;
	}

	public void setPoint2(String point2) {
		this.point2 = point2;
	}

	public int getDistance() {
		return distance;
	}

	public void setDistance(int distance) {
		this.distance = distance;
	}

	@Override
	public String toString() {
		return "Way [point1=" + point1 + ", point2=" + point2 + ", distance="
				+ distance + "]";
	}
	
	
	
}
