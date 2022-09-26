package com.example.drinksrecipes.utilities

import android.content.Context
import android.content.SharedPreferences
import android.widget.ImageView
import androidx.appcompat.app.AlertDialog
import com.bumptech.glide.Glide
import com.example.drinksrecipes.R

class AppGlobal {

    companion object{

        /*****************************************************Base URLs********************************************************/
        var HOME_BASE_URL = "https://www.thecocktaildb.com"

        const val byName="by_name"
        const val byAlphabet="by_alphabet"




        /*****************************************************Shared Preferences**************************************************/
        private fun getPreferences(context: Context): SharedPreferences {
            return context.getSharedPreferences("DRINKSPREFERENCES",
                Context.MODE_PRIVATE)
        }

         fun getEditor(appContext: Context): SharedPreferences.Editor {
            return getPreferences(appContext).edit()
        }

        fun writeString(context: Context, key: String, value: String) {
            getEditor(context).putString(key, value).commit()
        }

        fun readString(context: Context, key: String, defValue: String): String {
            return getPreferences(context).getString(key, defValue)!!
        }

        fun writeBoolean(context: Context, key: String, value: Boolean?) {
            getEditor(context).putBoolean(key, value!!).commit()
        }

        fun readBoolean(context: Context, key: String,
                        defValue: Boolean?): Boolean {
            return getPreferences(context).getBoolean(key, defValue!!)
        }



        /*************************** Global Methods ******************************************************/

        fun showDialog(title: String, msg: String, context: Context) {
            val alertDialog = AlertDialog.Builder(context)
            alertDialog.setTitle(title)
            alertDialog.setMessage(msg)
            alertDialog.setCancelable(false)
            alertDialog.setPositiveButton("Ok") { dialog, _ ->
                dialog.cancel()
            }
            val alertDialog1 = alertDialog.create()
            alertDialog1.show()
        }


        fun loadImageIntoGlide(imageURL:String?, imageView: ImageView, context: Context)
        {
            Glide
                .with(context)
                .load(imageURL)
                .placeholder(R.drawable.star)
                .error(R.drawable.star)
                .fitCenter()
                .into(imageView)
        }

    }
}