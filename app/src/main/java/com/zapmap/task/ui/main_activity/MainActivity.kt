package com.zapmap.task.ui.main_activity

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.zapmap.task.R
import com.zapmap.task.data.model.PokemonItem
import com.zapmap.task.databinding.ActivityMainBinding
import com.zapmap.task.ui.main_activity.adaptor.EndlessRecyclerViewScrollListener
import com.zapmap.task.ui.main_activity.adaptor.PokemonAdapter
import com.zapmap.task.ui.main_activity.fragment.PokemonDetailFragment
import com.zapmap.task.utils.Resource
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : AppCompatActivity(), PokemonAdapter.pokemonClickAction {
    private lateinit var bindingMainActivity: ActivityMainBinding
    private val viewModel: MainViewModel by viewModels()
    private lateinit var context: Context
    private lateinit var pokemonAdapter: PokemonAdapter
    private var totalPokemon: Int = 0
    private val pokemonList = ArrayList<PokemonItem>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bindingMainActivity = ActivityMainBinding.inflate(layoutInflater)
        setContentView(bindingMainActivity.root)
        context = this@MainActivity

        //api call to fetch the list of first 20 Pokemon when manin activity is loaded
        fetchPokemonList(0, 20)
    }

    private fun fetchPokemonList(offset: Int, limit: Int) {
        viewModel.getPokemonList(offset, limit).observe(this, Observer {
            when (it.status) {
                Resource.Status.SUCCESS -> {
                    totalPokemon = it.data!!.count
                    it.data!!.items?.let { it1 ->
                        if (it1.size > 0) {
                            pokemonList.addAll(it1)
                            if (offset == 0)
                                setUpRecyclerView(pokemonList) //set list in the recyclerView once pokemon list received from the api call
                            else
                                pokemonAdapter.notifyDataSetChanged()
                        } else {
                            with(bindingMainActivity) {
                                if (offset == 0) {
                                    //show "Empty list" message on the screen if list received is empty from the api call
                                    txtErrorMessage.setText("${it.message}+/n Empty List!")
                                    txtErrorMessage.visibility = View.VISIBLE
                                } else Toast.makeText(
                                    context,
                                    "${it.message}+/n Empty List!",
                                    Toast.LENGTH_SHORT
                                ).show()
                                progessBar.visibility = View.GONE
                            }
                        }
                    }
                    bindingMainActivity.progessBar.visibility = View.GONE
                }
                Resource.Status.ERROR -> {
                    with(bindingMainActivity) {
                        if (offset == 0) {
                            //show Error message on the screen if any error occur during api call
                            txtErrorMessage.setText(it.message)
                            txtErrorMessage.visibility = View.VISIBLE
                        } else Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
                        progessBar.visibility = View.GONE
                    }
                }
                Resource.Status.LOADING -> {
                    with(bindingMainActivity) {
                        //show progress bar while api call is in the progress
                        progessBar.visibility = View.VISIBLE
                    }
                }
            }
        })
    }

    private fun setUpRecyclerView(list: ArrayList<PokemonItem>) {
        pokemonAdapter = PokemonAdapter(list, this)
        val linearLayoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        val scrollListener = object : EndlessRecyclerViewScrollListener(linearLayoutManager) {
            override fun onLoadMore( totalItemsCount: Int) {
                // Triggered only when new data needs to be appended to the existing Pokemon list
                if (totalItemsCount < totalPokemon)
                    fetchPokemonList(totalItemsCount, 20)
            }
        }
        with(bindingMainActivity) {
            recyclerView.setHasFixedSize(true)
            recyclerView.layoutManager = linearLayoutManager
            recyclerView.adapter = pokemonAdapter
            recyclerView.addOnScrollListener(scrollListener)
        }
    }

    override fun onClick(name: String) {
        fetchPokemonDetails(name)
    }

    private fun fetchPokemonDetails(name: String) {
        viewModel.getPokemonDetail(name).observe(this, Observer {
            when (it.status) {
                Resource.Status.SUCCESS -> {
                    it.data!!.let { it1 ->
                        var type = ""
                        for (s in it1.items!!) {
                            type += s.type.name + ", "
                        }
                        detailFragment(
                            it1.name,
                            it1.Sprites.front_default,
                            type.substring(0,type.length-2),
                            it1.weight,
                            it1.height
                        )
                    }
                    bindingMainActivity.progessBar.visibility = View.GONE
                }
                Resource.Status.ERROR -> {
                    with(bindingMainActivity) {
                        Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
                        progessBar.visibility = View.GONE
                    }
                }
                Resource.Status.LOADING -> {
                    with(bindingMainActivity) {
                        //show progress bar while api call is in the progress
                        progessBar.visibility = View.VISIBLE
                    }
                }
            }
        })
    }

    private fun detailFragment(
        name: String,
        url: String,
        type: String,
        weight: Float,
        height: Float
    ) {
        val bundle = Bundle()
        bundle.putString("name", name)
        bundle.putString("url", url)
        bundle.putString("type", type)
        bundle.putFloat("weight", weight)
        bundle.putFloat("height", height)
        val pokemonDetailFragment = PokemonDetailFragment()
        pokemonDetailFragment.arguments = bundle
        supportFragmentManager.beginTransaction().replace(R.id.root_container, pokemonDetailFragment,pokemonDetailFragment.tag).addToBackStack(pokemonDetailFragment.tag).commit()
    }
}