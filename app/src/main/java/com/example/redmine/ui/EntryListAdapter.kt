package com.example.redmine.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.redmine.R
import com.example.redmine.data.model.Entry
import com.example.redmine.databinding.EntryListItemBinding

class EntryListAdapter(private val onClicked: (Entry) -> Unit): ListAdapter<Entry, EntryListAdapter.EntryViewHolder>(DiffCallback)  {

    inner class EntryViewHolder(private val binding: EntryListItemBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(entry: Entry){
            binding.apply {
                taskTextView.text = entry.task
                dateTextView.text = entry.date
                hourTextView.text = itemView.context.getString(R.string.hour_text, entry.hours)
                commentTextView.text = entry.comment
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EntryViewHolder {
        val binding = EntryListItemBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return EntryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: EntryViewHolder, position: Int) {
        val currentEntry = getItem(position)
        holder.itemView.setOnClickListener{
            onClicked(currentEntry)
        }
        holder.bind(currentEntry)
    }

    companion object{
        private val DiffCallback = object: DiffUtil.ItemCallback<Entry>(){
            override fun areItemsTheSame(oldItem: Entry, newItem: Entry): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Entry, newItem: Entry): Boolean {
                return oldItem.comment == newItem.comment
            }

        }
    }
}