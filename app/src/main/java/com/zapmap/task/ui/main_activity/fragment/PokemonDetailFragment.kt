package com.zapmap.task.ui.main_activity.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import com.zapmap.task.R
import com.zapmap.task.databinding.FragmentPokemonDetailBinding
import com.zapmap.task.ui.main_activity.MainActivity
import com.zapmap.task.utils.Utils
import com.zapmap.task.utils.setImage

private const val NAME_PARAM = "name"
private const val URL_PARAM = "url"
private const val TYPE_PARAM = "type"
private const val WEIGHT_PARAM = "weight"
private const val HEIGHT_PARAM = "height"

class PokemonDetailFragment : Fragment() {
    private var name: String? = null
    private var url: String? = null
    private var type: String? = null
    private var weight: Float? = null
    private var height: Float? = null
    private lateinit var binding: FragmentPokemonDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            name = it.getString(NAME_PARAM)
            url = it.getString(URL_PARAM)
            type = it.getString(TYPE_PARAM)
            weight = it.getFloat(WEIGHT_PARAM)
            height = it.getFloat(HEIGHT_PARAM)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentPokemonDetailBinding.inflate(inflater, container, false)

        //use toolbar(defined in the xml file of this fragment) as an action bar
        (activity as AppCompatActivity).setSupportActionBar(binding.myToolbar)
        //disable the title show on the actionbar (myToolbar)
        (activity as AppCompatActivity).supportActionBar?.setDisplayShowTitleEnabled(false);
        //enable back button on the actionbar (myToolbar)
        (activity as AppCompatActivity).supportActionBar?.setHomeButtonEnabled(true)
        //enable display of back button on the actionbar (myToolbar)
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
        setHasOptionsMenu(true)

        name?.let { Utils.setText(binding.name, it) }
        type?.let { Utils.setText(binding.types, it) }
        weight?.let { Utils.setText(binding.weight, it.toString() + " kg") }
        height?.let { Utils.setText(binding.height, it.toString() + " cm") }
        url?.let { context?.setImage(binding.imageview, it) }
        return binding.root
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.getItemId()) {
            android.R.id.home ->
                getParentFragmentManager().popBackStackImmediate()
        }
        return super.onOptionsItemSelected(item)
    }

    companion object {
        @JvmStatic
        fun newInstance(
            name: String,
            url: String,
            type: String,
            weight: Float,
            height: Float
        ) = PokemonDetailFragment().apply {
            arguments = Bundle().apply {
                putString(NAME_PARAM, name)
                putString(URL_PARAM, url)
                putString(TYPE_PARAM, type)
                putFloat(WEIGHT_PARAM, weight)
                putFloat(HEIGHT_PARAM, height)
            }
        }
    }
}