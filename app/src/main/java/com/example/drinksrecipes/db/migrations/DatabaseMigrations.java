package com.example.drinksrecipes.db.migrations;


import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

public class DatabaseMigrations {

    public static final Migration MIGRATION_1_2 = new Migration(1, 2) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            database.execSQL("ALTER TABLE favourite  ADD COLUMN isAlochol INT NOT NULL DEFAULT 0");

        }
    };

}
