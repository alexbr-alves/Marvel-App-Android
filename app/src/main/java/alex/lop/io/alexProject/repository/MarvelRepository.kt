package alex.lop.io.alexProject.repository

import alex.lop.io.alexProject.data.local.MarvelDao
import alex.lop.io.alexProject.data.model.character.CharacterModel
import alex.lop.io.alexProject.data.remote.ServiceApi
import javax.inject.Inject

class MarvelRepository @Inject constructor(
    private val api : ServiceApi,
    private val dao: MarvelDao
) {
    suspend fun characters(nameStartsWith : String? = null) = api.characters(nameStartsWith)
    suspend fun comics() = api.comics()
    suspend fun creators() = api.creators()
    suspend fun events() = api.events()
    suspend fun series() = api.series()
    suspend fun getComicsCharacter(characterId : Int) = api.getComicsCharacter(characterId)

    suspend fun insert(characterModel : CharacterModel) = dao.insert(characterModel)
    fun getAll() = dao.getAll()
    suspend fun delete(characterModel : CharacterModel) = dao.delete(characterModel)
}