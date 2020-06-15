/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ticketerrestfulservice;

import java.io.Serializable;


public class TicketPK implements Serializable {

    public Integer id;

    public TicketPK(Integer id) {
        this.id = id;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (obj == null || !(obj instanceof TicketPK)) {
            return false;
        } else {
            TicketPK other = (TicketPK) obj;
            return id.equals(other.id);
        }
    }

    @Override
    public int hashCode() {
        if (id == null) {
            return 0;
        } else {
            return id.hashCode();
        }
    }
}
