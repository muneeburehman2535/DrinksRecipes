package com.example.drinksrecipes.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.drinksrecipes.db.dao.FavouriteDao
import com.example.drinksrecipes.db.data_class.Favourite
import com.example.drinksrecipes.db.migrations.DatabaseMigrations

@Database(entities = [Favourite::class], version = 2,exportSchema = false)
abstract class DrinksDatabase:RoomDatabase(){


    abstract val favouriteDao: FavouriteDao
    companion object {

        @Volatile
        private var INSTANCE: DrinksDatabase? = null

        fun getInstance(context: Context): DrinksDatabase {
            // Multiple threads can ask for the database at the same time, ensure we only initialize
            // it once by using synchronized. Only one thread may enter a synchronized block at a
            // time.
            synchronized(this) {

                // Copy the current value of INSTANCE to a local variable so Kotlin can smart cast.
                // Smart cast is only available to local variables.
                var instance = INSTANCE

                // If instance is `null` make a new database instance.
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        DrinksDatabase::class.java,
                        "drinks-database")
                        .addMigrations(DatabaseMigrations.MIGRATION_1_2)
                        .fallbackToDestructiveMigration()
                        .build()
                    // Assign INSTANCE to the newly created database.
                    INSTANCE = instance
                }

                // Return instance; smart cast to be non-null.
                return instance
            }
        }
    }
}