package alex.lop.io.alexProject.viewModel

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
class ComicViewModel @Inject constructor(
    private val repository : MarvelRepository
) : ViewModel() {

    private val _comicList =
        MutableStateFlow<ResourceState<ComicModelResponse>>(ResourceState.Loading())
    val comicList : StateFlow<ResourceState<ComicModelResponse>> = _comicList

    init {
        fetch()
    }

    fun fetch(titleStartsWith : String? = null) = viewModelScope.launch {
        safeFetch(titleStartsWith)
    }



    private suspend fun safeFetch(titleStartsWith : String? = null) {
        try {
            val response = repository.comics(titleStartsWith)
            _comicList.value = handleResponse(response)
        } catch (t: Throwable) {
            when (t) {
                is IOException -> _comicList.value =
                    ResourceState.Error(R.string.error_internet_connection.toString())

                else -> _comicList.value =
                    ResourceState.Error(R.string.error_data_conversion_failure.toString())
            }
        }
    }

    private fun handleResponse(response : Response<ComicModelResponse>) : ResourceState<ComicModelResponse> {
        if (response.isSuccessful) {
            response.body()?.let { values ->
                return ResourceState.Success(values)
            }
        }
        return  ResourceState.Error(response.message())
    }

}