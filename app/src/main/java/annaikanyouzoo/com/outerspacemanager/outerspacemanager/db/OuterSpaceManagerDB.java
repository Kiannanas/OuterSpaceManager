package annaikanyouzoo.com.outerspacemanager.outerspacemanager.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Environment;

import java.util.UUID;

/**
 * Created by annaikanyouzoo on 20/03/2017.
 */

public class OuterSpaceManagerDB extends SQLiteOpenHelper{

    // Logcat tag
    private static final String LOG = "OuterSpaceManagerDB";

    // Database Version
    private static final int DATABASE_VERSION = 2;

    // Database Name
    private static final String DATABASE_NAME = "OuterSpaceManager";

    // Table Names
    public static final String BUILDING_TABLE_NAME = "building";
    public static final String SHIP_TABLE_NAME = "ship";
    public static final String SEARCH_TABLE_NAME = "search";
    public static final String ATTACK_TABLE_NAME = "attack";

    // BUILDING Table - column names
    public static final String BUILDING_UUID = "uuid"; // Building's ID in database // ID du bâtiment dans la BDD
    public static final String BUILDING_BUILDING_ID = "buildingId"; // Building's ID in databse // ID du bâtiment dans l'API
    public static final String BUILDING_NAME = "name";
    public static final String BUILDING_LEVEL = "level";
    public static final String BUILDING_EFFECT = "effect";
    public static final String BUILDING_IMAGE_URL = "imageUrl";
    public static final String BUILDING_AMOUNT_OF_EFFECT_LEVEL0 = "amountOfEffectLevel0";
    public static final String BUILDING_AMOUNT_OF_EFFECT_BY_LEVEL = "amountOfEffectByLevel";
    public static final String BUILDING_GAS_COST_LEVEL0 = "gasCostLevel0";
    public static final String BUILDING_GAS_COST_BY_LEVEL = "gasCostByLevel";
    public static final String BUILDING_MINERAL_COST_LEVEL0 = "mineralCostLevel0";
    public static final String BUILDING_MINERAL_COST_BY_LEVEL = "mineralCostByLevel";
    public static final String BUILDING_TIME_TO_BUILD_BY_LEVEL = "timeToBuildByLevel";
    public static final String BUILDING_TIME_TO_BUILD_LEVEL0 = "timeToBuildLevel0";
    public static final String BUILDING_CONSTRUCTION_START = "constructionStart";
    public static final String BUILDING_CONSTRUCTION_END = "constructionEnd";

    // BUILDING Table - column names
    public static final String SHIP_UUID = "uuid"; // Building's ID in database // ID du bâtiment dans la BDD
    public static final String SHIP_SHIP_ID = "buildingId"; // Building's ID in databse // ID du bâtiment dans l'API
    public static final String SHIP_NAME = "name";
    public static final String SHIP_IMAGE_URL = "imageUrl";
    public static final String SHIP_LIFE = "life";
    public static final String SHIP_SHIELD = "shield";
    public static final String SHIP_SPEED = "speed";
    public static final String SHIP_MIN_ATTACK = "minAttack";
    public static final String SHIP_MAX_ATTACK = "maxAttack";
    public static final String SHIP_GAS_COST = "gasCost";
    public static final String SHIP_MINERAL_COST = "mineralCost";
    public static final String SHIP_SPATIOPORT_LEVEL_NEEDED = "spatioportLevelNeeded";
    public static final String SHIP_TIME_TO_BUILD = "timeToBuild";
    public static final String SHIP_CONSTRUCTION_START = "constructionStart";
    public static final String SHIP_CONSTRUCTION_END = "constructionEnd";

    // SEARCH Table - column names
    public static final String SEARCH_UUID = "uuid"; // Searches ID in database // ID de la recherche dans la BDD
    public static final String SEARCH_BUILDING = "building";
    public static final String SEARCH_NAME = "name";
    public static final String SEARCH_LEVEL = "level";
    public static final String SEARCH_EFFECT = "effect";
    public static final String SEARCH_AMOUNT_OF_EFFECT_LEVEL0 = "amountOfEffectLevel0";
    public static final String SEARCH_AMOUNT_OF_EFFECT_BY_LEVEL = "amountOfEffectByLevel";
    public static final String SEARCH_GAS_COST_LEVEL0 = "gasCostLevel0";
    public static final String SEARCH_GAS_COST_BY_LEVEL = "gasCostByLevel";
    public static final String SEARCH_MINERAL_COST_LEVEL0 = "mineralCostLevel0";
    public static final String SEARCH_MINERAL_COST_BY_LEVEL = "mineralCostByLevel";
    public static final String SEARCH_TIME_TO_BUILD_LEVEL0 = "timeToBuildLevel0";
    public static final String SEARCH_TIME_TO_BUILD_BY_LEVEL = "timeToBuildByLevel";
    public static final String SEARCH_START = "searchStart";
    public static final String SEARCH_END = "searchEnd";

    // ATTACK Table - column names
    public static final String ATTACK_UUID = "uuid"; // Attack's ID in database // ID de l'attaque dans la BDD
    public static final String ATTACK_TYPE = "type";
    public static final String ATTACK_FROM = "fromUser";
    public static final String ATTACK_TO = "toUser";
    public static final String ATTACK_DATE = "date";
    public static final String ATTACK_DATE_INV = "dateInv";
    public static final String ATTACK_GAS_WON = "gasWon";
    public static final String ATTACK_MINERALS_WON = "mineralsWon";
    public static final String ATTACK_ATTACKER_FLEET = "attackerFleet";
    public static final String ATTACK_ATTACKER_FLEET_AFTER_BATTLE = "attackerFleetAfterBattle";
    public static final String ATTACK_DEFENDER_FLEET = "defenderFleet";
    public static final String ATTACK_DEFENDER_FLEET_AFTER_BATTLE = "defenderFleetAfterBattle";
    public static final String ATTACK_START = "attackStart";
    public static final String ATTACK_END = "attackEnd";

    // Table Create Statements
    // BUILDING table create statement
    private static final String BUILDING_TABLE_CREATE = "CREATE TABLE " + BUILDING_TABLE_NAME + " (" +
            BUILDING_UUID + " TEXT, " +
            BUILDING_BUILDING_ID + " TEXT, " +
            BUILDING_NAME + " TEXT, " +
            BUILDING_LEVEL + " TEXT, " +
            BUILDING_EFFECT + " TEXT, " +
            BUILDING_IMAGE_URL + " TEXT, " +
            BUILDING_AMOUNT_OF_EFFECT_LEVEL0 + " TEXT, " +
            BUILDING_AMOUNT_OF_EFFECT_BY_LEVEL + " TEXT, " +
            BUILDING_GAS_COST_LEVEL0 + " TEXT, " +
            BUILDING_GAS_COST_BY_LEVEL + " TEXT, " +
            BUILDING_MINERAL_COST_LEVEL0 + " TEXT, " +
            BUILDING_MINERAL_COST_BY_LEVEL + " TEXT, " +
            BUILDING_TIME_TO_BUILD_LEVEL0 + " TEXT, " +
            BUILDING_TIME_TO_BUILD_BY_LEVEL + " TEXT, " +
            BUILDING_CONSTRUCTION_START + " TEXT, " +
            BUILDING_CONSTRUCTION_END + " TEXT " + ");";

    // SHIP table create statement
    private static final String SHIP_TABLE_CREATE = "CREATE TABLE " + SHIP_TABLE_NAME + " (" +
            SHIP_UUID + " TEXT, " +
            SHIP_SHIP_ID + " TEXT, " +
            SHIP_NAME + " TEXT, " +
            SHIP_IMAGE_URL + " TEXT, " +
            SHIP_LIFE + " TEXT, " +
            SHIP_SHIELD + " TEXT, " +
            SHIP_SPEED + " TEXT, " +
            SHIP_MIN_ATTACK + " TEXT, " +
            SHIP_MAX_ATTACK + " TEXT, " +
            SHIP_GAS_COST + " TEXT, " +
            SHIP_MINERAL_COST + " TEXT, " +
            SHIP_SPATIOPORT_LEVEL_NEEDED + " TEXT, " +
            SHIP_TIME_TO_BUILD + " TEXT, " +
            SHIP_CONSTRUCTION_START + " TEXT, " +
            SHIP_CONSTRUCTION_END + " TEXT " + ");";

    // SEARCH table create statement
    private static final String SEARCH_TABLE_CREATE = "CREATE TABLE " + SEARCH_TABLE_NAME + " (" +
            SEARCH_UUID + " TEXT, " +
            SEARCH_BUILDING + " TEXT, " +
            SEARCH_NAME + " TEXT, " +
            SEARCH_LEVEL + " TEXT, " +
            SEARCH_EFFECT + " TEXT, " +
            SEARCH_AMOUNT_OF_EFFECT_LEVEL0 + " TEXT, " +
            SEARCH_AMOUNT_OF_EFFECT_BY_LEVEL + " TEXT, " +
            SEARCH_GAS_COST_LEVEL0 + " TEXT, " +
            SEARCH_GAS_COST_BY_LEVEL + " TEXT, " +
            SEARCH_MINERAL_COST_LEVEL0 + " TEXT, " +
            SEARCH_MINERAL_COST_BY_LEVEL + " TEXT, " +
            SEARCH_TIME_TO_BUILD_LEVEL0 + " TEXT, " +
            SEARCH_TIME_TO_BUILD_BY_LEVEL + " TEXT, " +
            SEARCH_START + " TEXT, " +
            SEARCH_END + " TEXT " + ");";

    // ATTACK table create statement
    private static final String ATTACK_TABLE_CREATE = "CREATE TABLE " + ATTACK_TABLE_NAME + " (" +
            ATTACK_UUID + " TEXT, " +
            ATTACK_TYPE + " TEXT, " +
            ATTACK_FROM + " TEXT, " +
            ATTACK_TO + " TEXT, " +
            ATTACK_DATE + " TEXT, " +
            ATTACK_DATE_INV + " TEXT, " +
            ATTACK_GAS_WON + " TEXT, " +
            ATTACK_MINERALS_WON + " TEXT, " +
            ATTACK_ATTACKER_FLEET + " TEXT, " +
            ATTACK_ATTACKER_FLEET_AFTER_BATTLE + " TEXT, " +
            ATTACK_DEFENDER_FLEET + " TEXT, " +
            ATTACK_DEFENDER_FLEET_AFTER_BATTLE + " TEXT, " +
            ATTACK_START + " TEXT, " +
            ATTACK_END + " TEXT " + ");";

    public OuterSpaceManagerDB(Context context) {
        super(context, Environment.getExternalStorageDirectory()+"/"+DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // creating required tables
        db.execSQL(BUILDING_TABLE_CREATE);
        db.execSQL(SHIP_TABLE_CREATE);
        db.execSQL(SEARCH_TABLE_CREATE);
        db.execSQL(ATTACK_TABLE_CREATE);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // on upgrade drop older tables
        db.execSQL("DROP TABLE IF EXISTS " + BUILDING_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + SHIP_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + SEARCH_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + ATTACK_TABLE_NAME);

        // create new tables
        onCreate(db);
    }

}
