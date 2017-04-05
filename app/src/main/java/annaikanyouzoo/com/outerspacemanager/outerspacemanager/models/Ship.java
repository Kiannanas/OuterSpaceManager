package annaikanyouzoo.com.outerspacemanager.outerspacemanager.models;

import java.util.UUID;

/**
 * Created by annaikanyouzoo on 14/03/2017.
 */

public class Ship {

    private UUID uuid;
    private int shipId;
    private String name;
    private String imageUrl;
    private int life;
    private int shield;
    private int speed;
    private int minAttack;
    private int maxAttack;
    private int gasCost;
    private int mineralCost;
    private int spatioportLevelNeeded;
    private int timeToBuild;
    private Long constructionStart;
    private Long constructionEnd;

    public UUID getUUID() {
        return uuid;
    }

    public void setUUID(UUID uuid) {
        this.uuid = uuid;
    }

    public int getShipId() {
        return shipId;
    }

    public void setShipId(int shipId) {
        this.shipId = shipId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public int getLife() {
        return life;
    }

    public void setLife(int life) {
        this.life = life;
    }

    public int getShield() {
        return shield;
    }

    public void setShield(int shield) {
        this.shield = shield;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getMinAttack() {
        return minAttack;
    }

    public void setMinAttack(int minAttack) {
        this.minAttack = minAttack;
    }

    public int getMaxAttack() {
        return maxAttack;
    }

    public void setMaxAttack(int maxAttack) {
        this.maxAttack = maxAttack;
    }

    public int getGasCost() {
        return gasCost;
    }

    public void setGasCost(int gasCost) {
        this.gasCost = gasCost;
    }

    public int getMineralCost() {
        return mineralCost;
    }

    public void setMineralCost(int mineralCost) {
        this.mineralCost = mineralCost;
    }

    public int getSpatioportLevelNeeded() {
        return spatioportLevelNeeded;
    }

    public void setSpatioportLevelNeeded(int spatioportLevelNeeded) {
        this.spatioportLevelNeeded = spatioportLevelNeeded;
    }

    public int getTimeToBuild() {
        return timeToBuild;
    }

    public void setTimeToBuild(int timeToBuild) {
        this.timeToBuild = timeToBuild;
    }

    public Long getConstructionStart() {
        return constructionStart;
    }

    public void setConstructionStart(Long constructionStart) {
        this.constructionStart = constructionStart;
    }

    public Long getConstructionEnd() {
        return constructionEnd;
    }

    public void setConstructionEnd(Long constructionEnd) {
        this.constructionEnd = constructionEnd;
    }
}
