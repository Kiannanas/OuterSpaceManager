package annaikanyouzoo.com.outerspacemanager.outerspacemanager.models;

import java.util.List;
import java.util.UUID;

/**
 * Created by annaikanyouzoo on 14/03/2017.
 */

public class Attack {

    private UUID uuid;
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
    private int attackStart;
    private int attackEnd;

    public UUID getUUID() {
        return uuid;
    }

    public void setUUID(UUID uuid) {
        this.uuid = uuid;
    }

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

    public int getMineralsWon() {
        return mineralsWon;
    }

    public void setMineralsWon(int mineralsWon) {
        this.mineralsWon = mineralsWon;
    }

    public int getGasWon() {
        return gasWon;
    }

    public void setGasWon(int gasWon) {
        this.gasWon = gasWon;
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

    public int getAttackStart() {
        return attackStart;
    }

    public void setAttackStart(int attackStart) {
        this.attackStart = attackStart;
    }

    public int getAttackEnd() {
        return attackEnd;
    }

    public void setAttackEnd(int attackEnd) {
        this.attackEnd = attackEnd;
    }
}
