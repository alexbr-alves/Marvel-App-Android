package alex.lop.io.alexProject.ui.favorite

import alex.lop.io.alexProject.data.local.MarvelDatabase
import alex.lop.io.alexProject.data.model.character.CharacterModel
import alex.lop.io.alexProject.repository.MarvelRepository
import alex.lop.io.alexProject.ui.state.ResourceState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteCharacterViewModel@Inject constructor(
    private val repository : MarvelRepository
): ViewModel() {

    private val _favorites = MutableStateFlow<ResourceState<List<CharacterModel>>>(ResourceState.Empty())
    val favorites: StateFlow<ResourceState<List<CharacterModel>>> = _favorites

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

    fun delete(characterModel : CharacterModel) = viewModelScope.launch {
        repository.delete(characterModel)
    }
    
}