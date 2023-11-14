package alex.lop.io.alexProject.viewModel

import alex.lop.io.alexProject.data.model.character.CharacterModel
import alex.lop.io.alexProject.data.model.character.CharacterModelResponse
import alex.lop.io.alexProject.data.model.comic.ComicModelResponse
import alex.lop.io.alexProject.repository.MarvelRepository
import alex.lop.io.alexProject.state.ResourceState
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository : MarvelRepository
) : ViewModel() {
}