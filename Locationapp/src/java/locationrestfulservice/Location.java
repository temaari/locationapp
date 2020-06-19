package locationrestfulservice;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

@Entity
@Table(name = "userlocation")
@IdClass(value = LocationPK.class)
public class Location implements Serializable {

    // private LocalDateTime creationDate;
    private static final long serialVersionUID = 1L;
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    private Integer id;
    @Column(name = "longitude")
    private String longitude;
    @Column(name = "altitude")
    private String altitude;
    @Column(name = "username")
    private String username;
    @Column(name = "creation_date")
    private LocalDateTime creationDate;

    public Location() {

    }

    public Location(String longitude, String altitude, String username) {
        this.longitude = longitude;
        this.altitude = altitude;
        this.username = username;
        this.creationDate = LocalDateTime.now();
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getAltitude() {
        return altitude;
    }

    public void setAltitude(String altitude) {
        this.altitude = altitude;
    }

    public String getUsername() {
        return this.username;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public String getCreationDateString() {
         DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm dd/MM/yyyy");
         String creationDateString = dtf.format(this.creationDate);
        return creationDateString;
    }

    public String getXMLString() {
        StringBuilder buffer = new StringBuilder();
        buffer.append("<userlocation>");
        buffer.append("<longitude>").append(getLongitude()).append("</longitude>");
        buffer.append("<altitude>").append(getAltitude()).append("</altitude>");
        buffer.append("<username>").append(getUsername()).append("</username>");
        buffer.append("<creation_date>").append(getCreationDateString()).append("</creation_date>");
        buffer.append("</userlocation>");
        return buffer.toString();
    }

    public JsonObject getJSONObject() {
        JsonObjectBuilder jsonBuilder = Json.createObjectBuilder();
        jsonBuilder.add("longitude", longitude);
        jsonBuilder.add("altitude", altitude);
        jsonBuilder.add("username", username);
        jsonBuilder.add("creation_date", getCreationDateString());
        return jsonBuilder.build();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

}
