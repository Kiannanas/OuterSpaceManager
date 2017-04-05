package annaikanyouzoo.com.outerspacemanager.outerspacemanager.response;

import java.util.List;

import annaikanyouzoo.com.outerspacemanager.outerspacemanager.models.Ship;

/**
 * Created by annaikanyouzoo on 14/03/2017.
 */

public class GetShipsResponse {

    private int size;
    private List<Ship> ships;

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public List<Ship> getShips() {
        return ships;
    }

    public void setShips(List<Ship> ships) {
        this.ships = ships;
    }
}
