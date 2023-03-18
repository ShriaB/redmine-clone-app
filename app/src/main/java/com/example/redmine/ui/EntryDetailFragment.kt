package com.example.redmine.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.example.redmine.R
import com.example.redmine.RedmineApplication
import com.example.redmine.data.EntryViewModel
import com.example.redmine.data.EntryViewModelFactory
import com.example.redmine.data.model.Entry
import com.example.redmine.databinding.FragmentEntryDetailBinding


class EntryDetailFragment : Fragment() {

    private val viewModel: EntryViewModel by activityViewModels {
        EntryViewModelFactory(
            (activity?.application as RedmineApplication).database.entryDao()
        )
    }
    private lateinit var binding: FragmentEntryDetailBinding
    private lateinit var entry: Entry

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentEntryDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Fetching the id that is passed to the fragment as argument
        val id = arguments?.getInt("id")
        // using the id to retrieve a particular entry from database
        // and setting an observer on the return value
        // when the value is returned the views are bound with the returned data
        viewModel.retrieveEntry(id!!).observe(this.viewLifecycleOwner){selectedEntry ->
            entry = selectedEntry
            bind(selectedEntry)
        }
    }

    private fun bind(entry: Entry){
        binding.apply {
            inputTask.setText(entry.task.toString())
            inputDate.setText(entry.date.toString())
            inputHour.setText(entry.hours.toString())
            inputComment.setText(entry.comment.toString())

            saveEditBtn.setOnClickListener{
                viewModel.updateEntry(
                    entry.id,
                    inputTask.text.toString(),
                    inputDate.text.toString(),
                    inputHour.text.toString().toDouble(),
                    inputComment.text.toString()
                )
                parentFragmentManager.popBackStack()
            }

            deleteBtn.setOnClickListener {
                viewModel.deleteEntry(entry)
                parentFragmentManager.popBackStack()
            }
        }
    }
}