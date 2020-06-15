/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ticketerrestfulservice;

import java.io.Serializable;

/**
 *
 * @author sinan
 */
public class UserPK implements Serializable {

    public String username;
    public String email;

    public UserPK(String username, String email) {
        this.username = username;
        this.email = email;
    }
    
    public UserPK(String username) {
        this.username = username;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null || !(obj instanceof UserPK)) {
            return false;
        } else {
            boolean isEqual = false;
            UserPK other = (UserPK) obj;

            if ((username != null) && (other.username != null)) {
                isEqual = username.equals(other.username);

//                if (isEqual && ((email != null) && (other.email != null))) {
//                    isEqual = email.equals(other.email);
//                }
            }

            return isEqual;
        }
    }

    @Override
    public int hashCode() {
        int hashCode = 0;
        if (username != null) {
            hashCode ^= username.hashCode();
        }
        if (email != null) {
            hashCode ^= email.hashCode();
        }
        return hashCode;
    }

}
