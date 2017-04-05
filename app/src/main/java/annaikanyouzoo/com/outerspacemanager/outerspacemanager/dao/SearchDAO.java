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
import annaikanyouzoo.com.outerspacemanager.outerspacemanager.models.Search;

/**
 * Created by annaikanyouzoo on 20/03/2017.
 */

public class SearchDAO {

    // Database fields
    private SQLiteDatabase database;
    private OuterSpaceManagerDB dbHelper;
    private String[] allColumns = {
            OuterSpaceManagerDB.SEARCH_UUID,
            OuterSpaceManagerDB.SEARCH_BUILDING,
            OuterSpaceManagerDB.SEARCH_NAME,
            OuterSpaceManagerDB.SEARCH_LEVEL,
            OuterSpaceManagerDB.SEARCH_EFFECT,
            OuterSpaceManagerDB.SEARCH_AMOUNT_OF_EFFECT_LEVEL0,
            OuterSpaceManagerDB.SEARCH_AMOUNT_OF_EFFECT_BY_LEVEL,
            OuterSpaceManagerDB.SEARCH_GAS_COST_LEVEL0,
            OuterSpaceManagerDB.SEARCH_GAS_COST_BY_LEVEL,
            OuterSpaceManagerDB.SEARCH_MINERAL_COST_LEVEL0,
            OuterSpaceManagerDB.SEARCH_MINERAL_COST_BY_LEVEL,
            OuterSpaceManagerDB.SEARCH_TIME_TO_BUILD_LEVEL0,
            OuterSpaceManagerDB.SEARCH_TIME_TO_BUILD_BY_LEVEL,
            OuterSpaceManagerDB.SEARCH_START,
            OuterSpaceManagerDB.SEARCH_END
    };

    public SearchDAO(Context context) {
        dbHelper = new OuterSpaceManagerDB(context);
    }
    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }
    public void close() {
        dbHelper.close();
    }

    /*
    * Creating a search
    */
    public Search addSearchRunning (
            boolean building,
            String name,
            int level,
            String effect,
            int amountOfEffectLevel0,
            int amountOfEffectByLevel,
            int gasCostLevel0,
            int gasCostByLevel,
            int mineralCostLevel0,
            int mineralCostByLevel,
            int timeToBuildLevel0,
            int timeToBuildByLevel,
            int searchStart,
            int searchEnd) {
        ContentValues values = new ContentValues();
        UUID uuid = UUID.randomUUID();
        values.put(OuterSpaceManagerDB.SEARCH_UUID, uuid.toString());
        values.put(OuterSpaceManagerDB.SEARCH_BUILDING, building);
        values.put(OuterSpaceManagerDB.SEARCH_NAME, name);
        values.put(OuterSpaceManagerDB.SEARCH_LEVEL, level);
        values.put(OuterSpaceManagerDB.SEARCH_EFFECT, effect);
        values.put(OuterSpaceManagerDB.SEARCH_AMOUNT_OF_EFFECT_LEVEL0, amountOfEffectLevel0);
        values.put(OuterSpaceManagerDB.SEARCH_AMOUNT_OF_EFFECT_BY_LEVEL, amountOfEffectByLevel);
        values.put(OuterSpaceManagerDB.SEARCH_GAS_COST_LEVEL0, gasCostLevel0);
        values.put(OuterSpaceManagerDB.SEARCH_GAS_COST_BY_LEVEL, gasCostByLevel);
        values.put(OuterSpaceManagerDB.SEARCH_MINERAL_COST_LEVEL0, mineralCostLevel0);
        values.put(OuterSpaceManagerDB.SEARCH_MINERAL_COST_BY_LEVEL, mineralCostByLevel);
        values.put(OuterSpaceManagerDB.SEARCH_TIME_TO_BUILD_LEVEL0, timeToBuildLevel0);
        values.put(OuterSpaceManagerDB.SEARCH_TIME_TO_BUILD_BY_LEVEL, timeToBuildByLevel);
        values.put(OuterSpaceManagerDB.SEARCH_START, searchStart);
        values.put(OuterSpaceManagerDB.SEARCH_END, searchEnd);

        // insert row
        database.insert(OuterSpaceManagerDB.SEARCH_TABLE_NAME, null, values);
        Cursor cursor = database.query(OuterSpaceManagerDB.SEARCH_TABLE_NAME,
                allColumns, OuterSpaceManagerDB.SEARCH_UUID + " = \"" + uuid +"\"", null,
                null, null, null);
        cursor.moveToFirst();
        Search newSearch = cursorToSearch(cursor);
        cursor.close();
        return newSearch;
    }

    public List<Search> getCurrentSearchesRunning() {
        List<Search> searchesRunning = new ArrayList<Search>();
        Cursor cursor = database.query(OuterSpaceManagerDB.SEARCH_TABLE_NAME,
                allColumns, null, null, null, null, null);
        cursor.moveToFirst();

        // looping through all rows and adding to list
        while (!cursor.isAfterLast()) {
            Search search = cursorToSearch(cursor);
            // Si la date de fin de recherche est inférieure à la date courante
            // c'est que la recherche est en cours donc on la récupère
            if(new Date(search.getSearchEnd()).compareTo(new Date()) < 0){
                // adding to running searches list
                searchesRunning.add(search);
            }
            cursor.moveToNext();
        }
        // make sure to close the cursor
        cursor.close();
        return searchesRunning;
    }
    private Search cursorToSearch(Cursor cursor) {
        Search search = new Search();
        String uuid = cursor.getString(0);
        search.setUUID(UUID.fromString(uuid));
        search.setBuilding(cursor.getInt(1) > 0);
        search.setName(cursor.getString(2));
        search.setLevel(cursor.getInt(3));
        search.setEffect(cursor.getString(4));
        search.setAmountOfEffectLevel0(cursor.getInt(5));
        search.setAmountOfEffectByLevel(cursor.getInt(6));
        search.setGasCostLevel0(cursor.getInt(7));
        search.setGasCostByLevel(cursor.getInt(8));
        search.setMineralCostLevel0(cursor.getInt(9));
        search.setMineralCostByLevel(cursor.getInt(10));
        search.setTimeToBuildLevel0(cursor.getInt(11));
        search.setTimeToBuildByLevel(cursor.getInt(12));
        search.setSearchStart(cursor.getInt(13));
        search.setSearchEnd(cursor.getInt(14));
        return search;
    }
}
