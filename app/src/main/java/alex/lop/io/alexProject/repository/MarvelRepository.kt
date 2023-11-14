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
    suspend fun events() = api.events()
    suspend fun series(titleStartsWith : String? = null) = api.series(titleStartsWith)
    suspend fun stories() = api.stories()

    //Character details

    suspend fun getComicsCharacter(characterId : Int) = api.getComicsCharacter(characterId)
    suspend fun getEventsCharacter(characterId : Int) = api.getEventsCharacter(characterId)
    suspend fun getSeriesCharacter(characterId : Int) = api.getSeriesCharacter(characterId)
    suspend fun getStoriesCharacter(characterId : Int) = api.getStoriesCharacter(characterId)

    //Comic details

    suspend fun getCharacterComics(comicId : Int) = api.getCharacterComics(comicId)
    suspend fun getEventComics(comicId : Int) = api.getEventComics(comicId)
    suspend fun getStoriesComics(comicId : Int) = api.getStoriesComics(comicId)

    //Events details

    suspend fun getCharacterEvent(eventId: Int) = api.getCharacterEvent(eventId)
    suspend fun getComicEvent(eventId: Int) = api.getComicEvent(eventId)
    suspend fun getSeriesEvent(eventId: Int) = api.getSeriesEvent(eventId)
    suspend fun getStoriesEvent(eventId: Int) = api.getStoriesEvent(eventId)


    suspend fun insert(characterModel : CharacterModel) = dao.insert(characterModel)
    fun getAll() = dao.getAll()
    suspend fun delete(characterModel : CharacterModel) = dao.delete(characterModel)
    suspend fun searchFavorite(id : Int) = dao.searchFavorite(id)
}