/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ticketerrestfulservice;

import java.util.Collection;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.ws.rs.Consumes;
import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.UriInfo;

import ticketerrestfulservice.Location;
import ticketerrestfulservice.LocationBean;


@Named // so that dependency injection can be used for the EJB
@Path("/userlocation")
public class LocationResource {

    @EJB
    private LocationBean locationBean;
    @Context
    private UriInfo context;
    private static final char QUOTE = '\"';

    public LocationResource() {
    }

    @GET
    @Produces(MediaType.APPLICATION_XML)
    public String getAllLocationXML() {
        StringBuilder buffer = new StringBuilder();
        buffer.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
        buffer.append("<userlocation uri=").append(QUOTE).append(
                context.getAbsolutePath()).append(QUOTE).append(">");
        Collection<Location> allLocation = locationBean.getAllLocation();
        for (Location location : allLocation) {
            buffer.append(location.getXMLString());
        }
        buffer.append("</userlocation>");
        return buffer.toString();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getAllLocationJSON() { 
        JsonObjectBuilder jsonBuilder = Json.createObjectBuilder();        
        JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
        
        Collection<Location> allLocation = locationBean.getAllLocation();
        for (Location location : allLocation) {
            arrayBuilder.add(location.getJSONObject());
        }        
        JsonObject json = jsonBuilder.add("userlocation", arrayBuilder).build();
        
        return json.toString();
    }

    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public void addNewLocation(MultivaluedMap<String, String> formParams) {
        String longitude = formParams.getFirst("longitude");
        String altitude = formParams.getFirst("altitude");
        String username = formParams.getFirst("username");
        locationBean.updateLocation(longitude, altitude, username);
    }
}
