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

import ticketerrestfulservice.Ticket;
import ticketerrestfulservice.TicketBean;


@Named // so that dependency injection can be used for the EJB
@Path("/tickets")
public class TicketResource {

    @EJB
    private TicketBean ticketBean;
    @Context
    private UriInfo context;
    private static final char QUOTE = '\"';

    public TicketResource() {
    }

    @GET
    @Produces(MediaType.APPLICATION_XML)
    public String getAllTixketsXML() {
        StringBuilder buffer = new StringBuilder();
        buffer.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
        buffer.append("<tickets uri=").append(QUOTE).append(
                context.getAbsolutePath()).append(QUOTE).append(">");
        Collection<Ticket> allTickets = ticketBean.getAllTickets();
        for (Ticket ticket : allTickets) {
            buffer.append(ticket.getXMLString());
        }
        buffer.append("</tickets>");
        return buffer.toString();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getAllTicketsJSON() { 
        JsonObjectBuilder jsonBuilder = Json.createObjectBuilder();        
        JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
        
        Collection<Ticket> allTickets = ticketBean.getAllTickets();
        for (Ticket ticket : allTickets) {
            arrayBuilder.add(ticket.getJSONObject());
        }        
        JsonObject json = jsonBuilder.add("tickets", arrayBuilder).build();
        
        return json.toString();
    }

    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public void addNewTicket(MultivaluedMap<String, String> formParams) {
        String title = formParams.getFirst("title");
        String description = formParams.getFirst("description");
        String username = formParams.getFirst("username");
        ticketBean.addTicket(title, description, username);
    }
}
