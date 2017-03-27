package annaikanyouzoo.com.outerspacemanager.outerspacemanager.models;

import java.util.List;

/**
 * Created by annaikanyouzoo on 14/03/2017.
 */

public class FleetAfterBattle {

    private int capacity;
    private List<FleetAmount> fleet;
    private int survivingShips;

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public List<FleetAmount> getFleet() {
        return fleet;
    }

    public void setFleet(List<FleetAmount> fleet) {
        this.fleet = fleet;
    }

    public int getSurvivingShips() {
        return survivingShips;
    }

    public void setSurvivingShips(int survivingShips) {
        this.survivingShips = survivingShips;
    }
}
