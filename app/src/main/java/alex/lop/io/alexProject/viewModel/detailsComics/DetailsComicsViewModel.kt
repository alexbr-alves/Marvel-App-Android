package alex.lop.io.alexProject.viewModel.detailsComics

import alex.lop.io.alexProject.data.model.comic.ComicModel
import alex.lop.io.alexProject.repository.MarvelRepository
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsComicsViewModel @Inject constructor(
    private val repository : MarvelRepository
) : ViewModel() {

    private val _searchComic =
        MutableLiveData<Boolean>()
    val searchComic : LiveData<Boolean> = _searchComic

    fun insert(comicModel : ComicModel) = viewModelScope.launch {
        //repository.insert(ComicModel)
    }

    fun delete(comicModel : ComicModel) = viewModelScope.launch {
        //repository.delete(comicModel)
    }

    fun searchFavorite(id : Int) = viewModelScope.launch {
        val response = repository.searchFavorite(id)
        _searchComic.value = response
    }


}