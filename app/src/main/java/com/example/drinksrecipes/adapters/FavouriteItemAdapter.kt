package com.example.drinksrecipes.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.drinksrecipes.R
import com.example.drinksrecipes.databinding.LayoutDrinksItemBinding
import com.example.drinksrecipes.databinding.LayoutFavouriteItemBinding
import com.example.drinksrecipes.db.data_class.Favourite
import com.example.drinksrecipes.models.DrinksData
import com.example.drinksrecipes.utilities.AppGlobal

class FavouriteItemAdapter(private val requireContext: Context, private var favouriteList: ArrayList<Favourite>) : RecyclerView.Adapter<FavouriteItemAdapter.ViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val contactView= LayoutInflater.from(parent.context).inflate(R.layout.layout_favourite_item,parent,false)
        return ViewHolder(contactView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        AppGlobal.loadImageIntoGlide(favouriteList[position].strDrinkThumb,holder.imgDrink,requireContext)

        holder.txtDrinkName.text=favouriteList[position].strDrink
        holder.txtDrinkDesc.text=favouriteList[position].strInstructions
        //holder.txtTotalRating.text=favouriteList[position].rating

    }

    override fun getItemCount(): Int {
        return favouriteList.size
    }

    class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView) {
        val imgDrink= itemView.findViewById<ImageView>(R.id.profile_image_fav)!!
        val txtDrinkName= itemView.findViewById<TextView>(R.id.txt_drink_name_fav)!!
        val txtDrinkDesc= itemView.findViewById<TextView>(R.id.txt_drink_desc_fav)!!


    }

    fun updateFavouriteList(favouriteList: ArrayList<Favourite>){
        this.favouriteList=favouriteList
        notifyDataSetChanged()
    }

}