package alex.lop.io.alexProject.viewModel.detailEvent

import alex.lop.io.alexProject.data.model.event.EventModel
import alex.lop.io.alexProject.repository.MarvelRepository
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsEventViewModel @Inject constructor(
    private val repository : MarvelRepository
) : ViewModel() {


    fun insert(comicModel : EventModel) = viewModelScope.launch {
        //repository.insert(ComicModel)
    }

    fun delete(comicModel : EventModel) = viewModelScope.launch {
        //repository.delete(comicModel)
    }

}