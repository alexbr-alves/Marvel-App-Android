package alex.lop.io.alexProject.viewModel

import alex.lop.io.alexProject.data.model.FavoriteModel
import alex.lop.io.alexProject.repository.MarvelRepository
import alex.lop.io.alexProject.state.ResourceState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(
    private val repository : MarvelRepository
) : ViewModel() {

    private val _favorites =
        MutableStateFlow<ResourceState<List<FavoriteModel>>>(ResourceState.Empty())
    val favorites : StateFlow<ResourceState<List<FavoriteModel>>> = _favorites

    init {
        fetch()
    }

    private fun fetch() = viewModelScope.launch {
        repository.getAll().collectLatest { characters ->
            if (characters.isEmpty()) {
                _favorites.value = ResourceState.Empty()
            } else {
                _favorites.value = ResourceState.Success(characters)
            }
        }
    }

    fun delete(characterModel : FavoriteModel) = viewModelScope.launch {
        repository.delete(characterModel)
    }

}