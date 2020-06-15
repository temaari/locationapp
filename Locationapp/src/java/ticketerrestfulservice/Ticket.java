package ticketerrestfulservice;

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
@Table(name = "tickets")
@IdClass(value = TicketPK.class)
public class Ticket implements Serializable {

    // private LocalDateTime creationDate;
    private static final long serialVersionUID = 1L;
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    private Integer id;
    @Column(name = "title")
    private String title;
    @Column(name = "description")
    private String description;
    @Column(name = "username")
    private String username;
    @Column(name = "creation_date")
    private LocalDateTime creationDate;

    public Ticket() {

    }

    public Ticket(String title, String description, String username) {
        this.title = title;
        this.description = description;
        this.username = username;
        this.creationDate = LocalDateTime.now();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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
        buffer.append("<ticket>");
        buffer.append("<title>").append(getTitle()).append("</title>");
        buffer.append("<description>").append(getDescription()).append("</description>");
        buffer.append("<username>").append(getUsername()).append("</username>");
        buffer.append("<creation_date>").append(getCreationDateString()).append("</creation_date>");
        buffer.append("</ticket>");
        return buffer.toString();
    }

    public JsonObject getJSONObject() {
        JsonObjectBuilder jsonBuilder = Json.createObjectBuilder();
        jsonBuilder.add("title", title);
        jsonBuilder.add("description", description);
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
