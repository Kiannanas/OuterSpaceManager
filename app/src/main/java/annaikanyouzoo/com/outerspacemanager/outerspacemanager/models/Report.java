package annaikanyouzoo.com.outerspacemanager.outerspacemanager.models;

import java.util.List;

/**
 * Created by annaikanyouzoo on 28/03/2017.
 */

public class Report {

    private String type;
    private String from;
    private String to;
    private long date;
    private long dateInv;
    private int gasWon;
    private int mineralsWon;
    private List<ShipAmount> attackerFleet;
    private FleetAfterBattle attackerFleetAfterBattle;
    private List<ShipAmount> defenderFleet;
    private FleetAfterBattle defenderFleetAfterBattle;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public long getDateInv() {
        return dateInv;
    }

    public void setDateInv(long dateInv) {
        this.dateInv = dateInv;
    }

    public int getGasWon() {
        return gasWon;
    }

    public void setGasWon(int gasWon) {
        this.gasWon = gasWon;
    }

    public int getMineralsWon() {
        return mineralsWon;
    }

    public void setMineralsWon(int mineralsWon) {
        this.mineralsWon = mineralsWon;
    }

    public List<ShipAmount> getAttackerFleet() {
        return attackerFleet;
    }

    public void setAttackerFleet(List<ShipAmount> attackerFleet) {
        this.attackerFleet = attackerFleet;
    }

    public FleetAfterBattle getAttackerFleetAfterBattle() {
        return attackerFleetAfterBattle;
    }

    public void setAttackerFleetAfterBattle(FleetAfterBattle attackerFleetAfterBattle) {
        this.attackerFleetAfterBattle = attackerFleetAfterBattle;
    }

    public List<ShipAmount> getDefenderFleet() {
        return defenderFleet;
    }

    public void setDefenderFleet(List<ShipAmount> defenderFleet) {
        this.defenderFleet = defenderFleet;
    }

    public FleetAfterBattle getDefenderFleetAfterBattle() {
        return defenderFleetAfterBattle;
    }

    public void setDefenderFleetAfterBattle(FleetAfterBattle defenderFleetAfterBattle) {
        this.defenderFleetAfterBattle = defenderFleetAfterBattle;
    }
}
