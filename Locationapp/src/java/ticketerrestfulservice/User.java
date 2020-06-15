/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ticketerrestfulservice;

import java.io.Serializable;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

/**
 *
 * @author sinan
 */
@Entity
@Table(name = "users")
@IdClass(value = UserPK.class)
public class User implements Serializable {

    private static final long serialVersionUID = 1L;
    @Column(name = "firstName")
    private String firstName;
    @Column(name = "lastName")
    private String lastName;
    @Id
    @Column(name = "username")
    private String username;
    @Id
    @Column(name = "email")
    private String email;
    @Column(name = "password")
    private String password;

    public User() {

    }

    public User(String firstName, String lastName, String username, String email, String password) {
        setFirstName(firstName);
        setLastName(lastName);
        setUsername(username);
        setEmail(email);
        setPassword(password);
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getXMLString() {
        StringBuilder buffer = new StringBuilder();
        buffer.append("<user>");
        buffer.append("<firstName>").append(getFirstName()).append("</firstName>");
        buffer.append("<lastName>").append(getLastName()).append("</lastName>");
        buffer.append("<username>").append(getUsername()).append("</username>");
        buffer.append("<email>").append(getEmail()).append("</email>");
        buffer.append("<password>").append(getPassword()).append("</password>");
        buffer.append("</user>");
        return buffer.toString();
    }

    public JsonObject getJSONObject() {
        JsonObjectBuilder jsonBuilder = Json.createObjectBuilder();
        jsonBuilder.add("firstName", firstName);
        jsonBuilder.add("lastName", lastName);
        jsonBuilder.add("username", username);
        jsonBuilder.add("email", email);
        jsonBuilder.add("password", password);
        return jsonBuilder.build();
    }
}
