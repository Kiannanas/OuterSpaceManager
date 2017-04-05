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
import annaikanyouzoo.com.outerspacemanager.outerspacemanager.models.Ship;

/**
 * Created by annaikanyouzoo on 21/03/2017.
 */

public class ShipDAO {

    // Database fields
    private SQLiteDatabase database;
    private OuterSpaceManagerDB dbHelper;
    private String[] allColumns = {
            OuterSpaceManagerDB.SHIP_UUID, // Ship's ID in database // ID du vaisseau dans la BDD
            OuterSpaceManagerDB.SHIP_SHIP_ID, // Ship's ID in API // ID du vaisseau dans l'API
            OuterSpaceManagerDB.SHIP_NAME,
            OuterSpaceManagerDB.SHIP_IMAGE_URL,
            OuterSpaceManagerDB.SHIP_LIFE,
            OuterSpaceManagerDB.SHIP_SHIELD,
            OuterSpaceManagerDB.SHIP_SPEED,
            OuterSpaceManagerDB.SHIP_MIN_ATTACK,
            OuterSpaceManagerDB.SHIP_MAX_ATTACK,
            OuterSpaceManagerDB.SHIP_GAS_COST,
            OuterSpaceManagerDB.SHIP_MINERAL_COST,
            OuterSpaceManagerDB.SHIP_TIME_TO_BUILD,
            OuterSpaceManagerDB.SHIP_CONSTRUCTION_START,
            OuterSpaceManagerDB.SHIP_CONSTRUCTION_END,
    };

    public ShipDAO(Context context) {
        dbHelper = new OuterSpaceManagerDB(context);
    }
    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }
    public void close() {
        dbHelper.close();
    }

    /*
    * Creating a ship
    */
    public Ship addShipRunning (
            Ship ship,
            Long constructionStart,
            Long constructionEnd) {

        ContentValues values = new ContentValues();
        UUID uuid = UUID.randomUUID();
        values.put(OuterSpaceManagerDB.SHIP_UUID, uuid.toString());
        values.put(OuterSpaceManagerDB.SHIP_SHIP_ID, ship.getShipId());
        values.put(OuterSpaceManagerDB.SHIP_NAME, ship.getName());
        values.put(OuterSpaceManagerDB.SHIP_IMAGE_URL, ship.getImageUrl());
        values.put(OuterSpaceManagerDB.SHIP_LIFE, ship.getLife());
        values.put(OuterSpaceManagerDB.SHIP_SHIELD, ship.getShield());
        values.put(OuterSpaceManagerDB.SHIP_SPEED, ship.getSpeed());
        values.put(OuterSpaceManagerDB.SHIP_MIN_ATTACK, ship.getMinAttack());
        values.put(OuterSpaceManagerDB.SHIP_MAX_ATTACK, ship.getMaxAttack());
        values.put(OuterSpaceManagerDB.SHIP_GAS_COST, ship.getGasCost());
        values.put(OuterSpaceManagerDB.SHIP_MINERAL_COST, ship.getMineralCost());
        values.put(OuterSpaceManagerDB.SHIP_TIME_TO_BUILD, ship.getTimeToBuild());
        values.put(OuterSpaceManagerDB.SHIP_CONSTRUCTION_START, ship.getConstructionStart());
        values.put(OuterSpaceManagerDB.SHIP_CONSTRUCTION_END, ship.getConstructionEnd());

        // insert row
        database.insert(OuterSpaceManagerDB.SHIP_TABLE_NAME, null, values);
        Cursor cursor = database.query(OuterSpaceManagerDB.SHIP_TABLE_NAME,
                allColumns, OuterSpaceManagerDB.SHIP_UUID + " = \"" + uuid +"\"", null,
                null, null, null);
        cursor.moveToFirst();
        Ship newShip = cursorToShip(cursor);
        cursor.close();
        return newShip;
    }

    public List<Ship> getCurrentShipsRunning () {
        List<Ship> shipsRunning = new ArrayList<Ship>();
        Cursor cursor = database.query(OuterSpaceManagerDB.SHIP_TABLE_NAME,
                allColumns, null, null, null, null, null);
        cursor.moveToFirst();

        // looping through all rows and adding to list
        while (!cursor.isAfterLast()) {
            Ship ship = cursorToShip(cursor);
            // Si la date de fin est inférieure à la date courante
            // c'est que le bâtiment est en cours de construction donc on le récupère
            if(new Date(ship.getConstructionEnd()).compareTo(new Date()) < 0){
                // adding to buildings running list
                shipsRunning.add(ship);
            }
            cursor.moveToNext();
        }
        // make sure to close the cursor
        cursor.close();
        return shipsRunning;
    }
    private Ship cursorToShip(Cursor cursor) {
        Ship ship = new Ship();
        String uuid = cursor.getString(0);
        ship.setUUID(UUID.fromString(uuid));
        ship.setShipId(cursor.getInt(1));
        ship.setName(cursor.getString(2));
        ship.setImageUrl(cursor.getString(3));
        ship.setLife(cursor.getInt(4));
        ship.setShield(cursor.getInt(5));
        ship.setSpeed(cursor.getInt(6));
        ship.setMinAttack(cursor.getInt(7));
        ship.setMaxAttack(cursor.getInt(8));
        ship.setGasCost(cursor.getInt(9));
        ship.setMineralCost(cursor.getInt(10));
        ship.setSpatioportLevelNeeded(cursor.getInt(11));
        ship.setTimeToBuild(cursor.getInt(12));
        ship.setConstructionStart(cursor.getLong(13));
        ship.setConstructionEnd(cursor.getLong(14));
        return ship;
    }

}
