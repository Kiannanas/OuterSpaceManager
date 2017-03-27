package annaikanyouzoo.com.outerspacemanager.outerspacemanager.response;

import java.util.List;

import annaikanyouzoo.com.outerspacemanager.outerspacemanager.Building;

/**
 * Created by annaikanyouzoo on 07/03/2017.
 */

public class GetBuildingsResponse {

    private int size;
    private List<Building> buildings;

    public GetBuildingsResponse() {
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public List<Building> getBuildings() {
        return buildings;
    }

    public void setBuildings(List<Building> buildings) {
        this.buildings = buildings;
    }
}
