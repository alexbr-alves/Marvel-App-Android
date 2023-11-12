package alex.lop.io.alexProject.repository

import alex.lop.io.alexProject.data.local.MarvelDao
import alex.lop.io.alexProject.data.model.character.CharacterModel
import alex.lop.io.alexProject.data.remote.ServiceApi
import javax.inject.Inject

class MarvelRepository @Inject constructor(
    private val api : ServiceApi,
    private val dao : MarvelDao
) {
    suspend fun characters(nameStartsWith : String? = null) = api.characters(nameStartsWith)
    suspend fun comics() = api.comics()
    suspend fun creators() = api.creators()
    suspend fun events() = api.events()
    suspend fun series(titleStartsWith : String? = null) = api.series(titleStartsWith)
    suspend fun stories() = api.stories()
    suspend fun getComicsCharacter(characterId : Int) = api.getComicsCharacter(characterId)

    suspend fun getEventsCharacter(characterId : Int) = api.getEventsCharacter(characterId)
    suspend fun getSeriesCharacter(characterId : Int) = api.getSeriesCharacter(characterId)
    suspend fun getStoriesCharacter(characterId : Int) = api.getStoriesCharacter(characterId)




    suspend fun insert(characterModel : CharacterModel) = dao.insert(characterModel)
    fun getAll() = dao.getAll()
    suspend fun delete(characterModel : CharacterModel) = dao.delete(characterModel)
    suspend fun searchFavorite(id : Int) = dao.searchFavorite(id)
}