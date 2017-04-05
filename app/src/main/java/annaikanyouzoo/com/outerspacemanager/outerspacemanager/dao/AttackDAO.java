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
import annaikanyouzoo.com.outerspacemanager.outerspacemanager.models.Attack;
import annaikanyouzoo.com.outerspacemanager.outerspacemanager.models.FleetAfterBattle;
import annaikanyouzoo.com.outerspacemanager.outerspacemanager.models.Search;
import annaikanyouzoo.com.outerspacemanager.outerspacemanager.models.ShipsIdAmount;

/**
 * Created by annaikanyouzoo on 20/03/2017.
 */

public class AttackDAO {

    // Database fields
    private SQLiteDatabase database;
    private OuterSpaceManagerDB dbHelper;
    private String[] allColumns = {
            OuterSpaceManagerDB.ATTACK_UUID,
            OuterSpaceManagerDB.ATTACK_TYPE,
            OuterSpaceManagerDB.ATTACK_FROM,
            OuterSpaceManagerDB.ATTACK_TO,
            OuterSpaceManagerDB.ATTACK_DATE,
            OuterSpaceManagerDB.ATTACK_DATE_INV,
            OuterSpaceManagerDB.ATTACK_GAS_WON,
            OuterSpaceManagerDB.ATTACK_MINERALS_WON,
            OuterSpaceManagerDB.ATTACK_ATTACKER_FLEET,
            OuterSpaceManagerDB.ATTACK_ATTACKER_FLEET_AFTER_BATTLE,
            OuterSpaceManagerDB.ATTACK_DEFENDER_FLEET,
            OuterSpaceManagerDB.ATTACK_DEFENDER_FLEET_AFTER_BATTLE,
            OuterSpaceManagerDB.ATTACK_START,
            OuterSpaceManagerDB.ATTACK_END,
    };

    public AttackDAO(Context context) {
        dbHelper = new OuterSpaceManagerDB(context);
    }
    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }
    public void close() {
        dbHelper.close();
    }

    /*
    * Creating an attack
    */
    public Attack addRunningAttack (int attackTime) {
        ContentValues values = new ContentValues();
        UUID uuid = UUID.randomUUID();
        values.put(OuterSpaceManagerDB.ATTACK_UUID, uuid.toString());
        //Gson gson = new Gson();
        //String attackerFleetSerialized = gson.toJson(attackerFleet);
        //String defenderFleetSerialized = gson.toJson(defenderFleet);
        //values.put(OuterSpaceManagerDB.ATTACK_ATTACKER_FLEET, attackerFleet);
        //values.put(OuterSpaceManagerDB.ATTACK_ATTACKER_FLEET_AFTER_BATTLE, attackerFleetAfterBattle);
        //values.put(OuterSpaceManagerDB.ATTACK_DEFENDER_FLEET, defenderFleet);
        //values.put(OuterSpaceManagerDB.ATTACK_DEFENDER_FLEET_AFTER_BATTLE, defenderFleetAfterBattle);
        values.put(OuterSpaceManagerDB.ATTACK_START, attackTime);
        values.put(OuterSpaceManagerDB.ATTACK_END, attackTime);

        // insert row
        database.insert(OuterSpaceManagerDB.BUILDING_TABLE_NAME, null, values);
        Cursor cursor = database.query(OuterSpaceManagerDB.ATTACK_TABLE_NAME,
                allColumns, OuterSpaceManagerDB.ATTACK_UUID + " = \"" + uuid +"\"", null,
                null, null, null);
        cursor.moveToFirst();
        Attack attack = cursorToAttack(cursor);
        cursor.close();
        return attack;
    }

    public List<Attack> getCurrentAttacksRunning() {
        List<Attack> attacksRunning = new ArrayList<Attack>();
        Cursor cursor = database.query(OuterSpaceManagerDB.ATTACK_TABLE_NAME,
                allColumns, null, null, null, null, null);
        cursor.moveToFirst();

        // looping through all rows and adding to list
        while (!cursor.isAfterLast()) {
            Attack attack = cursorToAttack(cursor);
            // Si la date de fin est inférieure à la date courante
            // c'est que l'attaque est en cours donc on le récupère
            if(new Date(attack.getAttackEnd()).compareTo(new Date()) < 0){
                // adding to attacks running list
                attacksRunning.add(attack);
            }
            cursor.moveToNext();
        }
        // make sure to close the cursor
        cursor.close();
        return attacksRunning;
    }

    public List<Attack> getAllAttacks() {
        List<Attack> attacks = new ArrayList<Attack>();
        Cursor cursor = database.query(OuterSpaceManagerDB.ATTACK_TABLE_NAME,
                allColumns, null, null, null, null, null);
        cursor.moveToFirst();

        // looping through all rows and adding to list
        while (!cursor.isAfterLast()) {
            Attack attack = cursorToAttack(cursor);
            // adding to attacks list
            attacks.add(attack);
            cursor.moveToNext();
        }
        // make sure to close the cursor
        cursor.close();
        return attacks;
    }

    private Attack cursorToAttack(Cursor cursor) {
        Attack attack = new Attack();
        attack.setType(cursor.getString(0));
        attack.setFrom(cursor.getString(1));
        attack.setTo(cursor.getString(2));
        attack.setDate(cursor.getInt(3));
        attack.setDateInv(cursor.getInt(4));
        //attack.setAttackerFleet(cursor.getInt(5));
        //attack.setAttackerFleetAfterBattle(cursor.getInt(6));
        //attack.setDefenderFleet(cursor.getInt(7));
        //attack.setDefenderFleetAfterBattle(cursor.getInt(8));
        attack.setAttackStart(cursor.getInt(9));
        attack.setAttackEnd(cursor.getInt(10));
        return attack;
    }
}
