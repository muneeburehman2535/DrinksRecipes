package com.example.drinksrecipes.fragments.home

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.drinksrecipes.db.DrinksDatabase
import com.example.drinksrecipes.db.data_class.Favourite
import com.example.drinksrecipes.db.repository.RoomDBRepository
import com.example.drinksrecipes.models.DrinkResponse
import com.example.drinksrecipes.repositories.DrinksRepository
import com.example.drinksrecipes.repositories.Response
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomeViewModel(application: Application) : AndroidViewModel(application) {


    val favDao = DrinksDatabase.getInstance(application).favouriteDao

    private val repository:RoomDBRepository = RoomDBRepository(favDao)

    private var drinksRepository: DrinksRepository = DrinksRepository()


    fun getDrinksDetailResponse(s:String): LiveData<Response<DrinkResponse>> {
        return drinksRepository.getDrinksDetailResponseLiveData(s)
    }

    fun getDrinksDetailByAlphaResponse(f:String): LiveData<Response<DrinkResponse>> {
        return drinksRepository.getDrinksDetailByAlphaResponseLiveData(f)
    }

    fun addDrinks(favourite: Favourite){
        viewModelScope.launch(Dispatchers.IO) {
            repository.insertFavouriteItem(favourite)
        }
    }

    fun deleteFavouriteRecord(favourite: Favourite){
        viewModelScope.launch {
            repository.deleteFavouriteItem(favourite)
        }
    }
}