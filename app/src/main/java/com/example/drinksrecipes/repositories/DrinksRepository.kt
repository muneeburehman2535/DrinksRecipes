package com.example.drinksrecipes.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.drinksrecipes.conversions.ConvertResponseToString
import com.example.drinksrecipes.models.DrinkResponse
import com.example.drinksrecipes.network.RetrofitClass
import com.google.gson.Gson
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import timber.log.Timber

class DrinksRepository {

    private lateinit var drinksResponseLiveData: MutableLiveData<Response<DrinkResponse>>


    fun getDrinksDetailResponseLiveData(s:String): LiveData<Response<DrinkResponse>> {
        drinksResponseLiveData= MutableLiveData<Response<DrinkResponse>>()

        drinksResponseLiveData.postValue(Response.Loading())

        RetrofitClass.getHomeInstance()?.getHomeRequestsInstance()?.getDrinksByName(s)?.enqueue(object :
            Callback<ResponseBody?> {
            override fun onResponse(call: Call<ResponseBody?>, response: retrofit2.Response<ResponseBody?>) {
                var drinksResponse: DrinkResponse?=null

                drinksResponse = if (response.body()==null) {
                    Gson().fromJson(response.errorBody()?.string(), DrinkResponse::class.java)
                } else{
                    Gson().fromJson(ConvertResponseToString.getString(response), DrinkResponse::class.java)
                }
                Timber.d(Gson().toJson("Drinks Detail: $drinksResponse"))
                drinksResponseLiveData.postValue(Response.Success(drinksResponse))
            }

            override fun onFailure(call: Call<ResponseBody?>, t: Throwable) {
                Timber.e("Error: ${t.message.toString()}")
                drinksResponseLiveData.postValue(Response.Error(t.message.toString()))

            }
        })
        return drinksResponseLiveData
    }


    fun getDrinksDetailByAlphaResponseLiveData(f:String): LiveData<Response<DrinkResponse>> {
        drinksResponseLiveData= MutableLiveData<Response<DrinkResponse>>()

        drinksResponseLiveData.postValue(Response.Loading())

        RetrofitClass.getHomeInstance()?.getHomeRequestsInstance()?.getDrinksByAlphabet(f)?.enqueue(object :
            Callback<ResponseBody?> {
            override fun onResponse(call: Call<ResponseBody?>, response: retrofit2.Response<ResponseBody?>) {
                var drinksResponse: DrinkResponse?=null

                drinksResponse = if (response.body()==null) {
                    Gson().fromJson(response.errorBody()?.string(), DrinkResponse::class.java)
                } else{
                    Gson().fromJson(ConvertResponseToString.getString(response), DrinkResponse::class.java)
                }
                Timber.d(Gson().toJson("Drinks Detail: $drinksResponse"))
                drinksResponseLiveData.postValue(Response.Success(drinksResponse))
            }

            override fun onFailure(call: Call<ResponseBody?>, t: Throwable) {
                Timber.e("Error: ${t.message.toString()}")
                drinksResponseLiveData.postValue(Response.Error(t.message.toString()))

            }
        })
        return drinksResponseLiveData
    }

}