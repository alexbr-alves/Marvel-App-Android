package alex.lop.io.alexProject.viewModel.detailCharacter

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
class StoriesCharacterViewModel @Inject constructor(
    val repository: MarvelRepository
) : ViewModel() {

    private val _storyList =
        MutableStateFlow<ResourceState<StoriesModelResponse>>(ResourceState.Loading())
    val storyList: StateFlow<ResourceState<StoriesModelResponse>> = _storyList

    fun fetchStory(characterId: Int) = viewModelScope.launch {
        safeFetchStory(characterId)
    }

    private suspend fun safeFetchStory(characterId: Int) {
        _storyList.value = ResourceState.Loading()
        try {
            val response = repository.getStoriesCharacter(characterId)
            _storyList.value = handleStoryResponse(response)
        } catch (t: Throwable) {
            when (t) {
                is IOException -> _storyList.value =
                    ResourceState.Error(R.string.error_internet_connection.toString())

                else -> _storyList.value =
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
