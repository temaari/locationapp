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
public class LocationBean {

    @PersistenceContext
    private EntityManager entityManager;

    public Location updateLocation(String longitude, String altitude, String username) {
        Location newLocation = new Location(longitude, altitude, username);
        entityManager.persist(newLocation); // note already in transaction
        return newLocation;
    }

    public Location getLocation(Integer id) {
        LocationPK primaryKey = new LocationPK(id);
        Location location = entityManager.find(Location.class, primaryKey);
        return location;
    }    

    public List<Location> getAllLocation() {
        String jpqlCommand = "SELECT u FROM Location u";
        Query query = entityManager.createQuery(jpqlCommand);
        return query.getResultList();
    }
}