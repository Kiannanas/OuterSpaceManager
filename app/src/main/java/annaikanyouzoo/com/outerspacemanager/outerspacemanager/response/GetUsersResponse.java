package annaikanyouzoo.com.outerspacemanager.outerspacemanager.response;

import java.util.List;

import annaikanyouzoo.com.outerspacemanager.outerspacemanager.models.User;

/**
 * Created by annaikanyouzoo on 14/03/2017.
 */

public class GetUsersResponse {

    private List<User> users;

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }
}
