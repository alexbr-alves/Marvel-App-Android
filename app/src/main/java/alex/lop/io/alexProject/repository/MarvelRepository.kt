package alex.lop.io.alexProject.repository

import alex.lop.io.alexProject.data.remote.ServiceApi
import javax.inject.Inject

class MarvelRepository @Inject constructor(
    private val api : ServiceApi
) {
    suspend fun list(nameStartsWith : String? = null) = api.list(nameStartsWith)
    suspend fun getComics(characterId : Int) = api.getComics(characterId)
}