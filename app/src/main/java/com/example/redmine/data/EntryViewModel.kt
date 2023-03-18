package com.example.redmine.data

import androidx.lifecycle.*
import com.example.redmine.data.model.Entry
import kotlinx.coroutines.launch

class EntryViewModel(private val entryDao: EntryDao): ViewModel() {

    val allEntries: LiveData<List<Entry>> = entryDao.getEntries().asLiveData()


    private fun insertEntry(entry: Entry){
        viewModelScope.launch {
            entryDao.insert(entry)
        }
    }

    private fun getNewEntry(task: String, date: String, hours: Double, comment: String ): Entry{
        return Entry(
            task = task,
            date = date,
            hours = hours,
            comment = comment
        )
    }

    private fun getUpdatedEntry(id: Int, task: String, date: String, hours: Double, comment: String): Entry{
        return Entry(
            id = id,
            task = task,
            date = date,
            hours = hours,
            comment = comment
        )
    }

    fun addNewEntry(task: String, date: String, hours: Double, comment: String ){
        val newEntry = getNewEntry(task, date, hours, comment)
        insertEntry(newEntry)
    }

    fun retrieveEntry(id: Int): LiveData<Entry>{
        return entryDao.getEntry(id).asLiveData()
    }

    fun deleteEntry(entry: Entry){
        viewModelScope.launch {
            entryDao.delete(entry)
        }
    }

    fun updateEntry(id: Int, task: String, date: String, hours: Double, comment: String ){
        val updatedEntry = getUpdatedEntry(id, task, date, hours, comment)
        viewModelScope.launch {
            entryDao.update(updatedEntry)
        }
    }
}

class EntryViewModelFactory(private val entryDao: EntryDao): ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(EntryViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return EntryViewModel(entryDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
