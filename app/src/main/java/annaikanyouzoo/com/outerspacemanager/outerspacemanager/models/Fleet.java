package annaikanyouzoo.com.outerspacemanager.outerspacemanager.models;

import java.util.List;

import annaikanyouzoo.com.outerspacemanager.outerspacemanager.ShipAmount;

/**
 * Created by annaikanyouzoo on 14/03/2017.
 */

public class Fleet {

    private int size;
    private List<ShipAmount> ships;

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public List<ShipAmount> getShips() {
        return ships;
    }

    public void setShips(List<ShipAmount> ships) {
        this.ships = ships;
    }
}
