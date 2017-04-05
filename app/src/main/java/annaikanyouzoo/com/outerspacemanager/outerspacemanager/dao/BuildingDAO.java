package annaikanyouzoo.com.outerspacemanager.outerspacemanager.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import annaikanyouzoo.com.outerspacemanager.outerspacemanager.db.OuterSpaceManagerDB;
import annaikanyouzoo.com.outerspacemanager.outerspacemanager.models.Building;

/**
 * Created by annaikanyouzoo on 20/03/2017.
 */

public class BuildingDAO {

    // Database fields
    private SQLiteDatabase database;
    private OuterSpaceManagerDB dbHelper;
    private String[] allColumns = {
            OuterSpaceManagerDB.BUILDING_UUID, // ID du bâtiment dans la BDD
            OuterSpaceManagerDB.BUILDING_BUILDING_ID, // ID du bâtiment dans l'API
            OuterSpaceManagerDB.BUILDING_NAME,
            OuterSpaceManagerDB.BUILDING_LEVEL,
            OuterSpaceManagerDB.BUILDING_EFFECT,
            OuterSpaceManagerDB.BUILDING_IMAGE_URL,
            OuterSpaceManagerDB.BUILDING_AMOUNT_OF_EFFECT_LEVEL0,
            OuterSpaceManagerDB.BUILDING_AMOUNT_OF_EFFECT_BY_LEVEL,
            OuterSpaceManagerDB.BUILDING_GAS_COST_LEVEL0,
            OuterSpaceManagerDB.BUILDING_GAS_COST_BY_LEVEL,
            OuterSpaceManagerDB.BUILDING_MINERAL_COST_LEVEL0,
            OuterSpaceManagerDB.BUILDING_MINERAL_COST_BY_LEVEL,
            OuterSpaceManagerDB.BUILDING_TIME_TO_BUILD_LEVEL0,
            OuterSpaceManagerDB.BUILDING_TIME_TO_BUILD_BY_LEVEL,
            OuterSpaceManagerDB.BUILDING_CONSTRUCTION_START,
            OuterSpaceManagerDB.BUILDING_CONSTRUCTION_END,
    };

    public BuildingDAO(Context context) {
        dbHelper = new OuterSpaceManagerDB(context);
    }
    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }
    public void close() {
        dbHelper.close();
    }

    /*
    * Creating a building
    */
    public Building addBuildingRunning (
            Building building,
            Long constructionStart,
            Long constructionEnd) {

        ContentValues values = new ContentValues();
        UUID uuid = UUID.randomUUID();
        values.put(OuterSpaceManagerDB.BUILDING_UUID, uuid.toString());
        values.put(OuterSpaceManagerDB.BUILDING_BUILDING_ID, building.getBuildingId());
        values.put(OuterSpaceManagerDB.BUILDING_NAME, building.getName());
        values.put(OuterSpaceManagerDB.BUILDING_LEVEL, building.getLevel());
        values.put(OuterSpaceManagerDB.BUILDING_EFFECT, building.getEffect());
        values.put(OuterSpaceManagerDB.BUILDING_IMAGE_URL, building.getImageUrl());
        values.put(OuterSpaceManagerDB.BUILDING_AMOUNT_OF_EFFECT_LEVEL0, building.getAmountOfEffectLevel0());
        values.put(OuterSpaceManagerDB.BUILDING_AMOUNT_OF_EFFECT_BY_LEVEL, building.getAmountOfEffectByLevel());
        values.put(OuterSpaceManagerDB.BUILDING_GAS_COST_LEVEL0, building.getGasCostLevel0());
        values.put(OuterSpaceManagerDB.BUILDING_GAS_COST_BY_LEVEL, building.getGasCostByLevel());
        values.put(OuterSpaceManagerDB.BUILDING_MINERAL_COST_LEVEL0, building.getMineralCostLevel0());
        values.put(OuterSpaceManagerDB.BUILDING_MINERAL_COST_BY_LEVEL, building.getMineralCostByLevel());
        values.put(OuterSpaceManagerDB.BUILDING_TIME_TO_BUILD_LEVEL0, building.getTimeToBuildLevel0());
        values.put(OuterSpaceManagerDB.BUILDING_TIME_TO_BUILD_BY_LEVEL, building.getTimeToBuildByLevel());
        values.put(OuterSpaceManagerDB.BUILDING_CONSTRUCTION_START, constructionStart);
        values.put(OuterSpaceManagerDB.BUILDING_CONSTRUCTION_END, constructionEnd);

        // insert row
        database.insert(OuterSpaceManagerDB.BUILDING_TABLE_NAME, null, values);
        Cursor cursor = database.query(OuterSpaceManagerDB.BUILDING_TABLE_NAME,
                allColumns, OuterSpaceManagerDB.BUILDING_UUID + " = \"" + uuid +"\"", null,
                null, null, null);
        cursor.moveToFirst();
        Building newBuilding = cursorToBuilding(cursor);
        cursor.close();
        return newBuilding;
    }

    public List<Building> getCurrentBuildingsRunning () {
        List<Building> buildingsRunning = new ArrayList<Building>();
        Cursor cursor = database.query(OuterSpaceManagerDB.BUILDING_TABLE_NAME,
                allColumns, null, null, null, null, null);
        cursor.moveToFirst();

        // looping through all rows and adding to list
        while (!cursor.isAfterLast()) {
            Building building = cursorToBuilding(cursor);
            // Si la date de fin est inférieure à la date courante
            // c'est que le bâtiment est en cours de construction donc on le récupère
            if(new Date(building.getConstructionEnd()).compareTo(new Date()) < 0){
                // adding to buildings running list
                buildingsRunning.add(building);
            }
            cursor.moveToNext();
        }
        // make sure to close the cursor
        cursor.close();
        return buildingsRunning;
    }
    private Building cursorToBuilding(Cursor cursor) {
        Building building = new Building();
        String uuid = cursor.getString(0);
        building.setUUID(UUID.fromString(uuid));
        building.setBuildingId(cursor.getInt(1));
        building.setName(cursor.getString(2));
        building.setLevel(cursor.getInt(3));
        building.setEffect(cursor.getString(4));
        building.setImageUrl(cursor.getString(4));
        building.setAmountOfEffectLevel0(cursor.getInt(6));
        building.setAmountOfEffectByLevel(cursor.getInt(7));
        building.setGasCostLevel0(cursor.getInt(8));
        building.setGasCostByLevel(cursor.getInt(9));
        building.setMineralCostLevel0(cursor.getInt(10));
        building.setMineralCostByLevel(cursor.getInt(11));
        building.setTimeToBuildLevel0(cursor.getInt(12));
        building.setTimeToBuildByLevel(cursor.getInt(13));
        building.setConstructionStart(cursor.getLong(14));
        building.setConstructionEnd(cursor.getLong(15));
        return building;
    }
}
