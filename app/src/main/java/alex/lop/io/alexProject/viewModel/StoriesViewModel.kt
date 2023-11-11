package alex.lop.io.alexProject.viewModel

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
class StoriesViewModel @Inject constructor(
    private val repository : MarvelRepository
): ViewModel() {

    private val _storiesList =
        MutableStateFlow<ResourceState<StoriesModelResponse>>(ResourceState.Loading())
    val storiesList : StateFlow<ResourceState<StoriesModelResponse>> = _storiesList

    init {
        fetch()
    }

    private fun fetch() = viewModelScope.launch {
        safeFetch()
    }

    private suspend fun safeFetch() {
        try {
            val response = repository.stories()
            _storiesList.value = handleResponse(response)
        } catch (t: Throwable) {
            when(t) {
                is IOException -> _storiesList.value =
                    ResourceState.Error(R.string.error_internet_connection.toString())

                else -> _storiesList.value =
                    ResourceState.Error(R.string.error_data_conversion_failure.toString())
            }
        }
    }
    private fun handleResponse(response : Response<StoriesModelResponse>) : ResourceState<StoriesModelResponse> {
        if (response.isSuccessful) {
            response.body()?.let { values ->
                return ResourceState.Success(values)
            }
        }
        return ResourceState.Error(response.message())
    }

}