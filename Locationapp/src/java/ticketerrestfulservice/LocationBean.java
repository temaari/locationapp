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

    public void updateLocation(String l, String a, String username) {
         String jpqlCommand = "UPDATE Location u SET u.longiture = :l "+", u.altitude = :a "+" WHERE u.username= :username";
         Query query = entityManager.createQuery(jpqlCommand);
         query.executeUpdate();
//         return query.getResultList();
        
//        String jpqlCommand = "Update Location SET longiture='"+updateLocation.getLongitude()+"', altitude='"+updateLocation.getAltitude()+"' WHERE username="+updateLocation.getUsername();
//        Query query = entityManager.createQuery(jpqlCommand);
//        
//        if(query.executeUpdate() == 0) {
//            throw new RuntimeException("Failed to Update location");
//        }
//        else {
//            return updateLocation;
//        }
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