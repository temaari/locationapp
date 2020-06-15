package ticketerrestfulservice;

import java.util.List;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.jws.WebService;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@WebService
@Stateless
@LocalBean
public class TicketBean {

    @PersistenceContext
    private EntityManager entityManager;

    public Ticket addTicket(String title, String description, String username) {
        Ticket newTicket = new Ticket(title, description, username);
        entityManager.persist(newTicket); // note already in transaction
        return newTicket;
    }

    public Ticket getTicket(Integer id) {
        TicketPK primaryKey = new TicketPK(id);
        Ticket ticket = entityManager.find(Ticket.class, primaryKey);
        return ticket;
    }    

    public List<Ticket> getAllTickets() {
        String jpqlCommand = "SELECT u FROM Ticket u";
        Query query = entityManager.createQuery(jpqlCommand);
        return query.getResultList();
    }
}