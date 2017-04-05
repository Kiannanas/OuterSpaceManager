package annaikanyouzoo.com.outerspacemanager.outerspacemanager.models;

import java.util.UUID;

/**
 * Created by annaikanyouzoo on 07/03/2017.
 */

public class Building {

    private UUID uuid; // ID du bâtiment dans la BDD
    private int buildingId; // ID du bâtiment dans l'API
    private String name;
    private int level;
    private String effect;
    private String imageUrl;
    private int amountOfEffectLevel0;
    private int amountOfEffectByLevel;
    private int gasCostLevel0;
    private int gasCostByLevel;
    private int mineralCostLevel0;
    private int mineralCostByLevel;
    private int timeToBuildLevel0;
    private int timeToBuildByLevel;
    private Long constructionStart;
    private Long constructionEnd;

    public UUID getUUID() {
        return uuid;
    }

    public void setUUID(UUID id) {
        this.uuid = id;
    }

    public int getBuildingId() {
        return buildingId;
    }

    public void setBuildingId(int buildingId) {
        this.buildingId = buildingId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getEffect() {
        return effect;
    }

    public void setEffect(String effect) {
        this.effect = effect;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public int getAmountOfEffectLevel0() {
        return amountOfEffectLevel0;
    }

    public void setAmountOfEffectLevel0(int amountOfEffectLevel0) {
        this.amountOfEffectLevel0 = amountOfEffectLevel0;
    }

    public int getAmountOfEffectByLevel() {
        return amountOfEffectByLevel;
    }

    public void setAmountOfEffectByLevel(int amountOfEffectByLevel) {
        this.amountOfEffectByLevel = amountOfEffectByLevel;
    }

    public int getAmountOfEffect(){
        if (this.getLevel() == 0){
            return this.amountOfEffectLevel0;
        } else {
            return this.amountOfEffectLevel0 * this.getLevel();
        }
    }

    public int getGasCostLevel0() {
        return gasCostLevel0;
    }

    public void setGasCostLevel0(int gasCostLevel0) {
        this.gasCostLevel0 = gasCostLevel0;
    }

    public int getGasCostByLevel() {
        return gasCostByLevel;
    }

    public void setGasCostByLevel(int gasCostByLevel) {
        this.gasCostByLevel = gasCostByLevel;
    }

    public int getGasCost(){
        if (this.getLevel() == 0){
            return this.gasCostLevel0;
        } else {
            return this.gasCostByLevel * this.getLevel();
        }
    }

    public int getMineralCostLevel0() {
        return mineralCostLevel0;
    }

    public void setMineralCostLevel0(int mineralCostLevel0) {
        this.mineralCostLevel0 = mineralCostLevel0;
    }

    public int getMineralCostByLevel() {
        return mineralCostByLevel;
    }

    public void setMineralCostByLevel(int mineralCostByLevel) {
        this.mineralCostByLevel = mineralCostByLevel;
    }

    public int getMineralCost(){
        if (this.getLevel() == 0){
            return this.mineralCostLevel0;
        } else {
            return this.mineralCostByLevel * this.getLevel();
        }
    }

    public int getTimeToBuildLevel0() {
        return timeToBuildLevel0;
    }

    public void setTimeToBuildLevel0(int timeToBuildLevel0) {
        this.timeToBuildLevel0 = timeToBuildLevel0;
    }

    public Long getConstructionStart() {
        return constructionStart;
    }

    public void setConstructionStart(Long constructionStart) {
        this.constructionStart = constructionStart;
    }

    public int getTimeToBuildByLevel() {
        return timeToBuildByLevel;
    }

    public void setTimeToBuildByLevel(int timeToBuildByLevel) {
        this.timeToBuildByLevel = timeToBuildByLevel;
    }

    public int getTimeToBuild(){
        if (this.getLevel() == 0){
            return this.getTimeToBuildLevel0();
        } else {
            return this.getTimeToBuildByLevel() * this.getLevel();
        }
    }

    public Long getConstructionEnd() {
        return constructionEnd;
    }

    public void setConstructionEnd(Long constructionEnd) {
        this.constructionEnd = constructionEnd;
    }

}
