package com.example.drinksrecipes.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.drinksrecipes.R
import com.example.drinksrecipes.databinding.LayoutDrinksItemBinding
import com.example.drinksrecipes.models.DrinksData

class DrinksItemAdapter(private val requireContext: Context, private var drinkList: ArrayList<DrinksData>) : RecyclerView.Adapter<DrinksItemAdapter.ViewHolder>(){

    private lateinit var addToFavouriteListener: AddToFavouriteListener
    private lateinit var addToAlocholListener:AddToAlocholListener

    interface AddToFavouriteListener {
        fun onAddToFavouriteClick(
            position: Int
        ) // pass view as argument or whatever you want.
    }

    interface AddToAlocholListener {
        fun onAddToAlocholClick(
            position: Int
        ) // pass view as argument or whatever you want.
    }

    fun setAddToFavouriteListener(addToFavouriteListener: AddToFavouriteListener) {
        this.addToFavouriteListener = addToFavouriteListener
    }

    fun setAddToAlocholListener(addToAlocholListener:AddToAlocholListener) {
        this.addToAlocholListener = addToAlocholListener
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding:LayoutDrinksItemBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.layout_drinks_item,parent,false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(drinkList[position])


        holder.binding.favImage.setImageDrawable(requireContext.getDrawable(R.drawable.rating))

        if (drinkList[position].isFavourite){

            holder.binding.favImage.setImageDrawable(requireContext.getDrawable(R.drawable.ic_star))
        }

        holder.binding.checkBox.isChecked = drinkList[position].isAlochol==1

        holder.binding.favImage.setOnClickListener {

            addToFavouriteListener.onAddToFavouriteClick(position)
        }

        holder.binding.checkBox.setOnClickListener {
            addToAlocholListener.onAddToAlocholClick(position)
        }
    }

    override fun getItemCount(): Int {
        return drinkList.size
    }

    class ViewHolder(var binding: LayoutDrinksItemBinding):RecyclerView.ViewHolder(binding.root){

        fun bind(data: DrinksData){

            binding.drinksBinding = data
            binding.executePendingBindings()

        }

    }

    fun updateList(drinkList: ArrayList<DrinksData>){
        this.drinkList=drinkList
        notifyDataSetChanged()
    }
    companion object {
        @JvmStatic
        @BindingAdapter("loadDrinksImage")
        fun loadDrinksImage(profile_image: ImageView, url:String?){
            if (!url.isNullOrEmpty()){
                Glide.with(profile_image.context).load(url).into(profile_image)
            }
        }
    }

}