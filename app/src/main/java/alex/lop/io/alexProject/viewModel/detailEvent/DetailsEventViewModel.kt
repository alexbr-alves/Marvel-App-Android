package alex.lop.io.alexProject.viewModel.detailEvent

import alex.lop.io.alexProject.data.model.FavoriteModel
import alex.lop.io.alexProject.data.model.event.EventModel
import alex.lop.io.alexProject.repository.MarvelRepository
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsEventViewModel @Inject constructor(
    private val repository : MarvelRepository
) : ViewModel() {

    private val _searchCharacter =
        MutableLiveData<Boolean>()
    val searchCharacter : LiveData<Boolean> = _searchCharacter

    fun insert(characterModel : FavoriteModel) = viewModelScope.launch {
        repository.insert(characterModel)
    }

    fun delete(characterModel : FavoriteModel) = viewModelScope.launch {
        repository.delete(characterModel)
    }

    fun searchFavorite(id : Int) = viewModelScope.launch {
        val response = repository.searchFavorite(id)
        _searchCharacter.value = response
    }


}