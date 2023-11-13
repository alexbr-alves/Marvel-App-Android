package alex.lop.io.alexProject.viewModel.detailEvent

import alex.lop.io.alexProject.R
import alex.lop.io.alexProject.data.model.stories.StoriesModelResponse
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
class StoriesEventViewModel@Inject constructor(
    val repository: MarvelRepository
) : ViewModel() {

    private val _list =
        MutableStateFlow<ResourceState<StoriesModelResponse>>(ResourceState.Loading())
    val list: StateFlow<ResourceState<StoriesModelResponse>> = _list

    fun fetch(eventId: Int) = viewModelScope.launch {
        safeFetch(eventId)
    }

    private suspend fun safeFetch(eventId: Int) {
        _list.value = ResourceState.Loading()
        try {
            val response = repository.getStoriesEvent(eventId)
            _list.value = handleStoryResponse(response)
        } catch (t: Throwable) {
            when (t) {
                is IOException -> _list.value =
                    ResourceState.Error(R.string.error_internet_connection.toString())

                else -> _list.value =
                    ResourceState.Error(R.string.error_data_conversion_failure.toString())
            }
        }
    }

    private fun handleStoryResponse(response: Response<StoriesModelResponse>): ResourceState<StoriesModelResponse> {
        if (response.isSuccessful) {
            response.body()?.let { values ->
                return ResourceState.Success(values)
            }
        }
        return ResourceState.Error(response.message())
    }
}