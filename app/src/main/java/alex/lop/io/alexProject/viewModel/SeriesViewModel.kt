package alex.lop.io.alexProject.viewModel

import alex.lop.io.alexProject.R
import alex.lop.io.alexProject.data.model.serie.SeriesModelResponse
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
class SeriesViewModel @Inject constructor(
    private val repository : MarvelRepository
) : ViewModel() {

    private val _seriesList =
        MutableStateFlow<ResourceState<SeriesModelResponse>>(ResourceState.Loading())
    val seriesList : StateFlow<ResourceState<SeriesModelResponse>> = _seriesList

    init {
        fetch()
    }

    fun fetch(titleStartsWith : String? = null) = viewModelScope.launch {
        safeFetch(titleStartsWith)
    }



    private suspend fun safeFetch(titleStartsWith : String? = null) {
        try {
            val response = repository.series(titleStartsWith)
            _seriesList.value = handleResponse(response)
        } catch (t: Throwable) {
            when (t) {
                is IOException -> _seriesList.value =
                    ResourceState.Error(R.string.error_internet_connection.toString())

                else -> _seriesList.value =
                    ResourceState.Error(R.string.error_data_conversion_failure.toString())
            }
        }
    }

    private fun handleResponse(response : Response<SeriesModelResponse>) : ResourceState<SeriesModelResponse> {
        if (response.isSuccessful) {
            response.body()?.let { values ->
                return ResourceState.Success(values)
            }
        }
        return ResourceState.Error(response.message())
    }

}