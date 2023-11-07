package alex.lop.io.alexProject.viewModel

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
class CreatorViewModel @Inject constructor(
    private val repository : MarvelRepository
) : ViewModel() {

    private val _creatorList = MutableStateFlow<ResourceState<CreatorModelResponse>>(ResourceState.Loading())
    val creatorList : StateFlow<ResourceState<CreatorModelResponse>> = _creatorList

    init {
        fetch()
    }

    private fun fetch() = viewModelScope.launch {
        safeFetch()
    }

    private suspend fun safeFetch() {
        try {
            val response = repository.creators()
            _creatorList.value = handleResponse(response)
        } catch (t: Throwable) {
            when (t) {
                is IOException -> _creatorList.value =
                    ResourceState.Error(R.string.error_internet_connection.toString())

                else -> _creatorList.value =
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
        return  ResourceState.Error(response.message())
    }


}