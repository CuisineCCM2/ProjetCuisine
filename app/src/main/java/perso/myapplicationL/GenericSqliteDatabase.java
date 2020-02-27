package perso.myapplicationL;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;


import java.io.File;


public class GenericSqliteDatabase {
    public GenericSqliteDatabase(Context context) {
        initializedatabase(context);
        this.context = context;
    }

    private static final String database_name = "myrecipes";
    SQLiteDatabase mydatabasenoconnection;
    Context context;


    private SQLiteDatabase creatingdatabase() {
        return SQLiteDatabase.openOrCreateDatabase(database_name, null);
    }

    private void initializedatabasesqlite(SQLiteDatabase mydatabasenoconnection) {
        String createIngredientTable = "CREATE TABLE ingredient(\n" +
                "\tID_ingredient    INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT ,\n" +
                "\tname             TEXT NOT NULL\n" +
                ")";

        String createRecipeTable = "CREATE TABLE recipe(\n" +
                "\tID_recipe      INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT ,\n" +
                "\tname           TEXT NOT NULL ,\n" +
                "\tdescription    TEXT NOT NULL ,\n" +
                "\tcategory       TEXT NOT NULL ,\n" +
                "\tinstruction    TEXT NOT NULL ,\n" +
                "\tcalories       REAL NOT NULL ,\n" +
                "\ttime           REAL NOT NULL\n" +
                ");";

        String createQuantityTable = "CREATE TABLE quantity(\n" +
                "\tID_quantity     INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT ,\n" +
                "\tquantity        REAL,\n" +
                "\tquantity_met    REAL\n" +
                ");";

        String createUnitTable = "CREATE TABLE unit(\n" +
                "\tID_unit    INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT ,\n" +
                "\tname       TEXT NOT NULL\n" +
                ");";

        String createUnitmetTable = "CREATE TABLE unitmet(\n" +
                "\tID_unitmet    INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT ,\n" +
                "\tname          TEXT NOT NULL\n" +
                ");";

        String createHasTable = "CREATE TABLE has(\n" +
                "\tID_recipe        INTEGER NOT NULL ,\n" +
                "\tID_ingredient    INTEGER NOT NULL,\n" +
                "\tCONSTRAINT has_PK PRIMARY KEY (ID_recipe,ID_ingredient)\n" +
                "\n" +
                "\t,CONSTRAINT has_recipe_FK FOREIGN KEY (ID_recipe) REFERENCES recipe(ID_recipe)\n" +
                "\t,CONSTRAINT has_ingredient0_FK FOREIGN KEY (ID_ingredient) REFERENCES ingredient(ID_ingredient)\n" +
                ");";

        String createContainTable = "CREATE TABLE contain(\n" +
                "\tID_quantity      INTEGER NOT NULL ,\n" +
                "\tID_ingredient    INTEGER NOT NULL,\n" +
                "\tCONSTRAINT contain_PK PRIMARY KEY (ID_quantity,ID_ingredient)\n" +
                "\n" +
                "\t,CONSTRAINT contain_quantity_FK FOREIGN KEY (ID_quantity) REFERENCES quantity(ID_quantity)\n" +
                "\t,CONSTRAINT contain_ingredient0_FK FOREIGN KEY (ID_ingredient) REFERENCES ingredient(ID_ingredient)\n" +
                ");";

        String createPossesses = "CREATE TABLE possesses(\n" +
                "\tID_quantity    INTEGER NOT NULL ,\n" +
                "\tID_unit        INTEGER NOT NULL ,\n" +
                "\tID_unitmet     INTEGER NOT NULL,\n" +
                "\tCONSTRAINT possesses_PK PRIMARY KEY (ID_quantity,ID_unit,ID_unitmet)\n" +
                "\n" +
                "\t,CONSTRAINT possesses_quantity_FK FOREIGN KEY (ID_quantity) REFERENCES quantity(ID_quantity)\n" +
                "\t,CONSTRAINT possesses_unit0_FK FOREIGN KEY (ID_unit) REFERENCES unit(ID_unit)\n" +
                "\t,CONSTRAINT possesses_unitmet1_FK FOREIGN KEY (ID_unitmet) REFERENCES unitmet(ID_unitmet)\n" +
                ");";

        String[] statements = new String[]{
                createIngredientTable,
                createRecipeTable,
                createQuantityTable,
                createUnitTable,
                createUnitmetTable,
                createHasTable,
                createContainTable,
                createPossesses};

       for(String create : statements){
           mydatabasenoconnection.execSQL(create);
       }
    }


    private boolean doesDatabaseExist(Context context, String dbName) {
        File dbFile = context.getDatabasePath(dbName);
        return dbFile.exists();
    }

    public void initializedatabase(Context context) {
        if (doesDatabaseExist(context, database_name) == false) {
            initializedatabasesqlite(creatingdatabase());
            Log.d("Polo", "initializedatabase: ça fonctionne ");

        } else {
            Log.d("db_name", "La base de donnée est déja existante");
        }
    }
}
