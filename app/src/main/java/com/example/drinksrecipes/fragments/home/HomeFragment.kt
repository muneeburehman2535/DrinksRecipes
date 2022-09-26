package com.example.drinksrecipes.fragments.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.drinksrecipes.R
import com.example.drinksrecipes.databinding.FragmentHomeBinding
import com.example.drinksrecipes.repositories.Response
import com.example.drinksrecipes.utilities.AppGlobal
import com.google.gson.Gson
import timber.log.Timber
import android.app.ProgressDialog
import android.content.SharedPreferences
import android.text.method.TextKeyListener.clear
import android.widget.SearchView
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.drinksrecipes.adapters.DrinksItemAdapter
import com.example.drinksrecipes.db.DrinksDatabase
import com.example.drinksrecipes.db.data_class.Favourite
import com.example.drinksrecipes.models.DrinksData
import java.util.*
import kotlin.collections.ArrayList


class HomeFragment : Fragment(),DrinksItemAdapter.AddToFavouriteListener,DrinksItemAdapter.AddToAlocholListener {

    private lateinit var mBinding: FragmentHomeBinding
    private lateinit var viewModel: HomeViewModel
    private lateinit var progressDialog:ProgressDialog
    private lateinit var drinksItemAdapter: DrinksItemAdapter
    private lateinit var drinkList:ArrayList<DrinksData>
    private lateinit var tempArrayList: ArrayList<DrinksData>

    private lateinit var databaseCreator: DrinksDatabase
    

    companion object {
        fun newInstance() = HomeFragment()
    }



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        mBinding = DataBindingUtil.inflate(inflater,R.layout.fragment_home, container, false)
        return mBinding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this)[HomeViewModel::class.java]

        databaseCreator = DrinksDatabase.getInstance(requireActivity().applicationContext)


        progressDialog = ProgressDialog(requireActivity())
        tempArrayList = arrayListOf()

       // getDrinksDetail("margarita")

        //getDrinksByAlphaDetail("a")

        var byName = AppGlobal.readString(requireActivity(),AppGlobal.byName,"")

        if (byName!=""){
            mBinding.radioGroupBtn.check(R.id.btn_by_name)
            getDrinksDetail("margarita")
            mBinding.searchBar.queryHint = "Search By Name"
        }
      else{
            mBinding.radioGroupBtn.check(R.id.btn_by_alpha)
           getDrinksByAlphaDetail("a")
           mBinding.searchBar.queryHint = "Search By Alphabet"
       }


        setOnCheckedChangeListener()
        setDrinkItemAdapter()
    }


    /**************************************************************************************************************************/
    //                                          Radio Button and Search Bar func
    /**************************************************************************************************************************/

    private fun setOnCheckedChangeListener() {
        mBinding.radioGroupBtn.setOnCheckedChangeListener { group, checkedId ->
            if (R.id.btn_by_name == checkedId){

                AppGlobal.writeString(requireActivity(),AppGlobal.byName,R.id.btn_by_name.toString())



                getDrinksDetail("margarita")
                Toast.makeText(requireActivity(), "margarita", Toast.LENGTH_SHORT).show()
                mBinding.searchBar.queryHint = "Search By Name"
                clearSearchView()


            }
            else{




                AppGlobal.getEditor(requireActivity()).remove(AppGlobal.byName).apply()
                getDrinksByAlphaDetail("a")
                Toast.makeText(requireActivity(), "a", Toast.LENGTH_SHORT).show()
                mBinding.searchBar.queryHint = "Search By Alphabet"
                clearSearchView()

            }

        }
    }


    private fun clearSearchView(){

        mBinding.searchBar.setQuery("", false);
        mBinding.searchBar.clearFocus();
    }

    /**************************************************************************************************************************/
    //                                          Recyclerview Adapters
    /**************************************************************************************************************************/

    private fun setDrinkItemAdapter() {
        drinkList = arrayListOf()
        drinksItemAdapter = DrinksItemAdapter(requireContext(), drinkList)
        mBinding.rvName.layoutManager = LinearLayoutManager(
            context,
            LinearLayoutManager.VERTICAL,
            false
        )
        mBinding.rvName.adapter = drinksItemAdapter
        drinksItemAdapter.setAddToFavouriteListener(this)
        drinksItemAdapter.setAddToAlocholListener(this)


        // Search Bar fun

        mBinding.searchBar.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {

                tempArrayList.clear()

                val searchText = newText!!.lowercase(Locale.getDefault())
                if (searchText.isNotEmpty()){

                    drinkList.forEach {
                        if (it.strDrink?.lowercase(Locale.getDefault())!!.startsWith(searchText)){

                            tempArrayList.add(it)
                        }
                    }


                    drinksItemAdapter.updateList(tempArrayList)
                }

                else{

                    tempArrayList.clear()
                    tempArrayList.addAll(drinkList)
                    drinksItemAdapter.updateList(tempArrayList)
                }

                return false
            }
        })

    }




    /**************************************************************************************************************************/
    //                                          Interfaces Section
    /**************************************************************************************************************************/

    override fun onAddToFavouriteClick(position: Int) {

        val favouriteLiveData =
            databaseCreator.favouriteDao.fetchFavouriteRecord(drinkList[position].idDrink.toString())

        favouriteLiveData.observe(requireActivity(), Observer {

            val favouriteList = it as ArrayList<Favourite>

            if (favouriteList.size > 0) {
                deleteFavourite(position, favouriteLiveData)
            } else {
                addToFavourite(position, favouriteLiveData)
            }

        })

    }

    override fun onAddToAlocholClick(position: Int) {



    }


    /**************************************************************************************************************************/
    //                                          Room Database Section
    /**************************************************************************************************************************/

    private fun checkDrinksById() {
        if (activity != null) {
            val favouriteLiveData = databaseCreator.favouriteDao.findDrinks()

            favouriteLiveData.observe(requireActivity(), Observer {

                val favouriteList = it as ArrayList<Favourite>

                if (favouriteList.size > 0) {
                    for (index in 0 until drinkList.size) {
                        for (ind in 0 until favouriteList.size) {
                            if (drinkList[index].idDrink == favouriteList[ind].idDrink) {
                                drinkList[index].isFavourite = true
                                break
                            }
                        }
                    }

                }

                drinksItemAdapter.updateList(drinkList)

            })
        }

    }


    private fun addToFavourite(position: Int, favouriteLiveData: LiveData<MutableList<Favourite>>) {
        favouriteLiveData.removeObservers(requireActivity())
        val mFavourite = Favourite(
            drinkList[position].idDrink.toString(),
            drinkList[position].strDrink.toString(),
            drinkList[position].strInstructions.toString(),
            drinkList[position].strDrinkThumb.toString(),
            drinkList[position].isAlochol

        )
        viewModel.addDrinks(mFavourite)
        drinkList[position].isFavourite = true
        drinksItemAdapter.updateList(drinkList)
    }

    private fun deleteFavourite(
        position: Int,
        favouriteLiveData: LiveData<MutableList<Favourite>>
    ) {
        favouriteLiveData.removeObservers(requireActivity())
        val mFavourite = Favourite(
            drinkList[position].idDrink.toString(),
            drinkList[position].strDrink.toString(),
            drinkList[position].strInstructions.toString(),
            drinkList[position].strDrinkThumb.toString(),
            drinkList[position].isAlochol
        )
        viewModel.deleteFavouriteRecord(mFavourite)
        drinkList[position].isFavourite = false
        drinksItemAdapter.updateList(drinkList)

    }



    /**************** Api Calling Section *****************/
    private fun getDrinksDetail(s:String) {

        viewModel.getDrinksDetailResponse(s).observe(requireActivity()) {


            when (it) {


                is Response.Loading -> {
                    progressDialog.setTitle("Please Wait")
                    progressDialog.show()
                }

                is Response.Success -> {

                    it.data?.let {

                        progressDialog.dismiss()

                        drinkList = it.drinks

                        checkDrinksById()
                        Timber.d("menu list: ${Gson().toJson(it.drinks)}")

                        drinksItemAdapter.updateList(drinkList)


                    }

                }


                is Response.Error -> {
//                    AppGlobal.showDialog(
//                        getString(R.string.title_alert),
//                        it.message.toString(),
//                        requireActivity()
//                    )
//                    if (progressDialog.isShowing) {
//                        progressDialog.dismiss()
//
//                    }
                }


            }

        }
    }


    private fun getDrinksByAlphaDetail(f:String) {

        viewModel.getDrinksDetailByAlphaResponse(f).observe(requireActivity()) {


            when (it) {


                is Response.Loading -> {
                    progressDialog.setTitle("Please Wait")
                    progressDialog.show()
                }

                is Response.Success -> {

                    it.data?.let {

                        progressDialog.dismiss()

                        drinkList = it.drinks

                        checkDrinksById()
                        Timber.d("menu list: ${Gson().toJson(it.drinks)}")

                        drinksItemAdapter.updateList(drinkList)


                    }

                }


                is Response.Error -> {
//                    AppGlobal.showDialog(
//                        getString(R.string.title_alert),
//                        it.message.toString(),
//                        requireActivity()
//                    )
//                    if (progressDialog.isShowing) {
//                        progressDialog.dismiss()
//
//                    }
                }


            }

        }
    }



}