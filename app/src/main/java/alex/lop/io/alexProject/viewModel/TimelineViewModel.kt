package alex.lop.io.alexProject.viewModel

import alex.lop.io.alexProject.R
import alex.lop.io.alexProject.data.model.timeline.TimelineModelResponse
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
class TimelineViewModel @Inject constructor(
    private val repository : MarvelRepository
) : ViewModel() {

    private val _listComic =
        MutableStateFlow<ResourceState<TimelineModelResponse>>(ResourceState.Loading())
    val listComic : StateFlow<ResourceState<TimelineModelResponse>> = _listComic

    private val _listEvent =
        MutableStateFlow<ResourceState<TimelineModelResponse>>(ResourceState.Loading())
    val listEvent : StateFlow<ResourceState<TimelineModelResponse>> = _listEvent

    init {
        fetch()
    }

    fun fetch() = viewModelScope.launch {
        safeFetch()
    }

    private suspend fun safeFetch() {
        try {
            val comicList = handleResponse(repository.getComicTimeline())
            _listComic.value = comicList
        } catch (t : Throwable) {
            when (t) {
                is IOException -> _listComic.value =
                    ResourceState.Error(R.string.error_internet_connection.toString())

                else -> _listComic.value =
                    ResourceState.Error(R.string.error_data_conversion_failure.toString())
            }
        }
    }

    private fun handleResponse(response : Response<TimelineModelResponse>) : ResourceState<TimelineModelResponse> {
        if (response.isSuccessful) {
            response.body()?.let { values ->
                return ResourceState.Success(values)
            }
        }
        return ResourceState.Error(response.message())
    }
}