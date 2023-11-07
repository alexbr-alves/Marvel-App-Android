package alex.lop.io.alexProject.viewModel

import alex.lop.io.alexProject.R
import alex.lop.io.alexProject.data.model.character.CharacterModelResponse
import alex.lop.io.alexProject.repository.MarvelRepository
import alex.lop.io.alexProject.state.ResourceState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.Response
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class SearchCharacterViewModel @Inject constructor(
    private val repository : MarvelRepository
) : ViewModel() {
    private val _searchCharacter =
        MutableStateFlow<ResourceState<CharacterModelResponse>>(ResourceState.Empty())
    val searchCharacter : StateFlow<ResourceState<CharacterModelResponse>> = _searchCharacter

    fun fetch(nameStartsWith : String) = viewModelScope.launch {
        safeFetch(nameStartsWith)
    }

    private suspend fun safeFetch(nameStartsWith : String) {
        _searchCharacter.value = ResourceState.Loading()
        try {
            val response = repository.characters(nameStartsWith)
            _searchCharacter.value = handleResponse(response)
        } catch (t : Throwable) {
            when (t) {
                is IOException -> _searchCharacter.value =
                    ResourceState.Error(R.string.error_internet_connection.toString())

                else -> _searchCharacter.value =
                    ResourceState.Error(R.string.error_data_conversion_failure.toString())
            }
        }
    }

    private fun handleResponse(response : Response<CharacterModelResponse>) : ResourceState<CharacterModelResponse> {
        if (response.isSuccessful) {
            response.body()?.let { values ->
                return ResourceState.Success(values)
            }
        }
        return ResourceState.Error(response.message())
    }
}