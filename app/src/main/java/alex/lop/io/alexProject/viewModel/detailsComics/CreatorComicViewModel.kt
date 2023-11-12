package alex.lop.io.alexProject.viewModel.detailsComics

import alex.lop.io.alexProject.R
import alex.lop.io.alexProject.data.model.creator.CreatorModelResponse
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
class CreatorComicViewModel @Inject constructor(
    private val repository : MarvelRepository
) : ViewModel()  {

    private val _list =
        MutableStateFlow<ResourceState<CreatorModelResponse>>(ResourceState.Loading())
    val list : StateFlow<ResourceState<CreatorModelResponse>> = _list

    fun fetch(comicId : Int) = viewModelScope.launch {
        safeFetch(comicId)
    }

    private suspend fun safeFetch(comicId : Int) {
        _list.value = ResourceState.Loading()
        try {
            val response = repository.getCreatorComics(comicId)
            _list.value = handleResponse(response)
        } catch (t : Throwable) {
            when (t) {
                is IOException -> _list.value =
                    ResourceState.Error(R.string.error_internet_connection.toString())

                else -> _list.value =
                    ResourceState.Error(R.string.error_data_conversion_failure.toString())
            }
        }
    }

    private fun handleResponse(response : Response<CreatorModelResponse>) : ResourceState<CreatorModelResponse> {
        if (response.isSuccessful) {
            response.body()?.let { values ->
                return ResourceState.Success(values)
            }
        }
        return ResourceState.Error(response.message())
    }
}