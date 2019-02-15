package com.example.kafka.model;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="locations",catalog="temp")
public class Location {
	
	@Id
    @Column(name = "timestamp")
 	private String timestamp;
	@Column(name = "x")
	private String x;
	 @Column(name = "y")
    private String y;
    
    public String getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}

	public String getX() {
		return x;
	}

	public void setX(String x) {
		this.x = x;
	}

	public String getY() {
		return y;
	}

	public void setY(String y) {
		this.y = y;
	}



    public Location() {
    }
    
    public Location(String timestamp, String x, String y) {
		super();
		this.timestamp = timestamp;
		this.x = x;
		this.y = y;
	}
    
    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Location{");
        sb.append("timestamp='").append(timestamp).append('\'');
        sb.append(", x ='").append(x).append('\'');
        sb.append(", y ='").append(y).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
