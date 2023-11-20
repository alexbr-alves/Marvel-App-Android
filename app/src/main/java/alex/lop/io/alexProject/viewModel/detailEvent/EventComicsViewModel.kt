package alex.lop.io.alexProject.viewModel.detailEvent

import alex.lop.io.alexProject.R
import alex.lop.io.alexProject.data.model.comic.ComicModelResponse
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
class EventComicsViewModel @Inject constructor(
    private val repository : MarvelRepository
) : ViewModel() {
    private val _list =
        MutableStateFlow<ResourceState<ComicModelResponse>>(ResourceState.Loading())
    val list : StateFlow<ResourceState<ComicModelResponse>> = _list

    fun fetch(eventId : Int) = viewModelScope.launch {
        safeFetchComic(eventId)
    }

    private suspend fun safeFetchComic(eventId : Int) {
        _list.value = ResourceState.Loading()
        try {
            val response = repository.getComicEvent(eventId)
            _list.value = handleComicResponse(response)
        } catch (t : Throwable) {
            when (t) {
                is IOException -> _list.value =
                    ResourceState.Error(R.string.error_internet_connection.toString())

                else -> _list.value =
                    ResourceState.Error(R.string.error_data_conversion_failure.toString())
            }
        }
    }

    private fun handleComicResponse(response : Response<ComicModelResponse>) : ResourceState<ComicModelResponse> {
        if (response.isSuccessful) {
            response.body()?.let { values ->
                return ResourceState.Success(values)
            }
        }
        return ResourceState.Error(response.message())
    }
}