package com.example.redmine.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.example.redmine.R
import com.example.redmine.RedmineApplication
import com.example.redmine.data.EntryViewModel
import com.example.redmine.data.EntryViewModelFactory
import com.example.redmine.databinding.FragmentAddEntryBinding

class AddEntryFragment : Fragment() {

    // Instantiating the viewModel
    private val viewModel: EntryViewModel by activityViewModels {
        EntryViewModelFactory(
            (activity?.application as RedmineApplication).database.entryDao()
        )
    }

    private lateinit var binding: FragmentAddEntryBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentAddEntryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.saveBtn.setOnClickListener{
            val inputTask = binding.inputTask.text.toString()
            val inputDate = binding.inputDate.text.toString()
            val inputHour = binding.inputHour.text.toString().toDouble()
            val inputComment = binding.inputComment.text.toString()

            viewModel.addNewEntry(inputTask, inputDate, inputHour, inputComment)
            parentFragmentManager.popBackStack()
        }
    }
}