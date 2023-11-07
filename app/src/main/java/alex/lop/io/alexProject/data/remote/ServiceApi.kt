package alex.lop.io.alexProject.data.remote

import alex.lop.io.alexProject.data.model.character.CharacterModelResponse
import alex.lop.io.alexProject.data.model.comic.ComicModelResponse
import alex.lop.io.alexProject.data.model.comicsCharacter.ComicsCharacterModelResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ServiceApi {

    @GET("characters")
    suspend fun characters(
        @Query("nameStartsWith") nameStartsWith: String? = null,
    ): Response<CharacterModelResponse>

    @GET("comics")
    suspend fun comics(
        @Query("nameStartsWith") nameStartsWith: String? = null,
    ): Response<ComicModelResponse>

    @GET("characters/{characterId}/comics")
    suspend fun getComicsCharacter(
        @Path(
            value = "characterId",
            encoded = true
        ) characterId: Int
    ): Response<ComicsCharacterModelResponse>
}