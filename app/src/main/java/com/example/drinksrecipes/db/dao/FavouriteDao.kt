package com.example.drinksrecipes.db.dao

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.drinksrecipes.db.data_class.Favourite

@Dao
interface FavouriteDao {

    //Insert new record in favourites
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(favourite: Favourite)

    @Query("select * from favourite")
    fun findDrinks(): LiveData<MutableList<Favourite>>

    //Delete selected Item in Favourite
    @Delete
    suspend fun deleteFavouriteItem(favourite: Favourite)


    @Query("select * from favourite where idDrink = :idDrink")
    fun fetchFavouriteRecord(idDrink:String): LiveData<MutableList<Favourite>>
}