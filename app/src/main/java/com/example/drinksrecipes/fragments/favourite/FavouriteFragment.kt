package com.example.drinksrecipes.fragments.favourite

import android.app.ProgressDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.drinksrecipes.R
import com.example.drinksrecipes.adapters.DrinksItemAdapter
import com.example.drinksrecipes.adapters.FavouriteItemAdapter
import com.example.drinksrecipes.databinding.FragmentFavouriteBinding
import com.example.drinksrecipes.databinding.FragmentHomeBinding
import com.example.drinksrecipes.db.DrinksDatabase
import com.example.drinksrecipes.db.data_class.Favourite
import com.example.drinksrecipes.fragments.home.HomeViewModel
import com.example.drinksrecipes.models.DrinksData


class FavouriteFragment : Fragment() {
    companion object {
        fun newInstance() = FavouriteFragment()
    }

    private lateinit var mBinding: FragmentFavouriteBinding
    private lateinit var favouriteItemAdapter: FavouriteItemAdapter
    private lateinit var favouriteList:ArrayList<Favourite>
    private lateinit var databaseCreator: DrinksDatabase


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding=
            DataBindingUtil.inflate(inflater,R.layout.fragment_favourite ,container,false)
        return mBinding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setFavouriteAdapter()
        databaseCreator= DrinksDatabase.getInstance(requireActivity())
        checkDrinksById()
    }


    private fun setFavouriteAdapter() {
        favouriteList= arrayListOf()
        favouriteItemAdapter = FavouriteItemAdapter(requireActivity(),favouriteList)
        mBinding.rvFavourite.layoutManager = LinearLayoutManager(
            context,
            LinearLayoutManager.VERTICAL,
            false
        )
        mBinding.rvFavourite.adapter = favouriteItemAdapter


    }


    private fun checkDrinksById(){
        val favouriteLiveData=databaseCreator.favouriteDao.findDrinks()

        favouriteLiveData.observe(requireActivity(), Observer {

            favouriteList= it as ArrayList<Favourite>

            favouriteItemAdapter.updateFavouriteList(favouriteList)

        })
    }
}