package ticketerrestfulservice;

import java.util.List;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.jws.WebService;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@WebService
@Stateless
@LocalBean
public class LocationBean {

    @PersistenceContext
    private EntityManager entityManager;

    public void updateLocation(String l, String a, String username) {
//        String jpqlCommand = "UPDATE Location u SET u.longiture = :l "+", u.altitude = :a "+" WHERE u.username= :username";
        String jpqlCommand = "UPDATE Location u SET u.longitude = '0', u.altitude = '0' WHERE u.username = 'jason'";
//        System.out.println("UPDATELOCATION: Longitude:"+l+", Altitude: "+a+", Username: "+username);
        
        
        Query query = entityManager.createQuery(jpqlCommand);
        
        query.executeUpdate();
//        query.setParameter("l", l);
//        query.setParameter("a", a);
//        query.setParameter("username", username);
//        query.executeUpdate();

//         return query.getResultList();

//        String jpqlCommand = "UPDATE Location u SET u.longitude = :l "+", u.altitude = :a "+" WHERE u.username= :username";
//        System.out.println("UPDATELOCATION: Longitude:"+l+", Altitude: "+a+", Username: "+username);
//        
//        
//        Query query = entityManager.createQuery(jpqlCommand);
//        query.setParameter("l", l);
//        query.setParameter("a", a);
//        query.setParameter("username", username);
//        query.executeUpdate();
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