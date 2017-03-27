package annaikanyouzoo.com.outerspacemanager.outerspacemanager.models;

import java.util.List;

/**
 * Created by annaikanyouzoo on 14/03/2017.
 */

public class Attack {

    private List<ShipsIdAmount> attackerFleet;
    private FleetAfterBattle attackerFleetAfterBattle;
    private int date;
    private int dateInv;
    private List<ShipsIdAmount> defenderFleet;
    private FleetAfterBattle defenderFleetAfterBattle;
    private String from;
    private int gasWon;
    private int mineralsWon;
    private String to;
    private String type;

    public List<ShipsIdAmount> getAttackerFleet() {
        return attackerFleet;
    }

    public void setAttackerFleet(List<ShipsIdAmount> attackerFleet) {
        this.attackerFleet = attackerFleet;
    }

    public FleetAfterBattle getAttackerFleetAfterBattle() {
        return attackerFleetAfterBattle;
    }

    public void setAttackerFleetAfterBattle(FleetAfterBattle attackerFleetAfterBattle) {
        this.attackerFleetAfterBattle = attackerFleetAfterBattle;
    }

    public int getDate() {
        return date;
    }

    public void setDate(int date) {
        this.date = date;
    }

    public int getDateInv() {
        return dateInv;
    }

    public void setDateInv(int dateInv) {
        this.dateInv = dateInv;
    }

    public List<ShipsIdAmount> getDefenderFleet() {
        return defenderFleet;
    }

    public void setDefenderFleet(List<ShipsIdAmount> defenderFleet) {
        this.defenderFleet = defenderFleet;
    }

    public FleetAfterBattle getDefenderFleetAfterBattle() {
        return defenderFleetAfterBattle;
    }

    public void setDefenderFleetAfterBattle(FleetAfterBattle defenderFleetAfterBattle) {
        this.defenderFleetAfterBattle = defenderFleetAfterBattle;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }
}
