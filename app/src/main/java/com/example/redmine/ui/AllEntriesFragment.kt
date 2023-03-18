package com.example.redmine.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.redmine.MainActivity
import com.example.redmine.R
import com.example.redmine.RedmineApplication
import com.example.redmine.data.EntryViewModel
import com.example.redmine.data.EntryViewModelFactory
import com.example.redmine.databinding.FragmentAllEntriesBinding

class AllEntriesFragment : Fragment() {

    private val viewModel: EntryViewModel by activityViewModels{
        EntryViewModelFactory(
            (activity?.application as RedmineApplication).database.entryDao()
        )
    }
    private lateinit var binding: FragmentAllEntriesBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        // Initialise the binding object and return its root
        binding = FragmentAllEntriesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Instantiating the ListAdapter and passing the click handler callback
        val adapter = EntryListAdapter{

            // When a list item is clicked the entry detail fragment is instantiated
            // The id of the entry which is clicked it passed as its argument
            val newFragment = EntryDetailFragment()
            newFragment.arguments = bundleOf("id" to it.id)

            parentFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, newFragment)
                .addToBackStack(null)
                .commit()
        }

            binding.recyclerView.adapter = adapter
            binding.recyclerView.layoutManager = LinearLayoutManager(context)
            // Setting  observer on allENtries in viewModel which is live data
            // Whenever there is any change in data the submitList is called and UI is refreshed using DiffUtil
            viewModel.allEntries.observe(this.viewLifecycleOwner) { entries ->
                entries.let {
                    adapter.submitList(it)
                }
            }

            binding.addBtn.setOnClickListener{
                // For adding a new entry AddEntryFragment is called
                parentFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, AddEntryFragment())
                    .addToBackStack(null)
                    .commit()
            }
        }

}