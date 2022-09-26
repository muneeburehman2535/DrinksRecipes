package com.example.drinksrecipes.db.repository


import com.example.drinksrecipes.db.dao.FavouriteDao
import com.example.drinksrecipes.db.data_class.Favourite
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


class RoomDBRepository(private val favouriteDao: FavouriteDao) {



    /*******************************************Favourite Methods**************************************/

    suspend fun insertFavouriteItem(favourite: Favourite)
    {
        favouriteDao.insert(favourite)
    }


    suspend fun deleteFavouriteItem(favourite: Favourite)
    {
        favouriteDao.deleteFavouriteItem(favourite)
    }



}
