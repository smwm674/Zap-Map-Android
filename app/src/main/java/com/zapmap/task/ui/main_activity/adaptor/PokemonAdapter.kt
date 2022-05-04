package com.zapmap.task.ui.main_activity.adaptor

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.zapmap.task.data.model.PokemonItem
import com.zapmap.task.databinding.ItemLayoutBinding
import com.zapmap.task.utils.Utils.setText

class PokemonAdapter(
    private var modelList: ArrayList<PokemonItem>,
    private var listener: pokemonClickAction
) : RecyclerView.Adapter<PokemonAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.onBind(modelList.get(position))
    }

    override fun getItemCount() = modelList.size

    interface pokemonClickAction {
        fun onClick(name: String)
    }

    inner class ViewHolder(val binding: ItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind(data: PokemonItem) {
            binding.apply {
                setText(name, data.name)
                setText(url, data.url)
                layout.setOnClickListener() {
                    listener.onClick(data.name)
                }
            }
        }
    }

}
