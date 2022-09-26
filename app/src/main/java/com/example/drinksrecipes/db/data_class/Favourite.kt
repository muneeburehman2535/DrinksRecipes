package com.example.drinksrecipes.db.data_class

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favourite")
data class Favourite(
    @PrimaryKey()
    @ColumnInfo(name = "idDrink")
    var idDrink:String,
    @ColumnInfo(name = "strDrink")
    var strDrink:String,
    @ColumnInfo(name = "strInstructions")
    var strInstructions:String,
    @ColumnInfo(name = "strDrinkThumb")
    var strDrinkThumb:String,
    @ColumnInfo(name = "isAlochol")
    var isAlochol:Int
)
