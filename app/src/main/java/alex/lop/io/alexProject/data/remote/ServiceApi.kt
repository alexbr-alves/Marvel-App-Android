package alex.lop.io.alexProject.data.remote

import alex.lop.io.alexProject.data.model.character.CharacterModelResponse
import alex.lop.io.alexProject.data.model.comic.ComicModelResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ServiceApi {

    @GET("characters")
    suspend fun list(
        @Query("nameStartsWith") nameStartsWith: String? = null
    ): Response<CharacterModelResponse>

    @GET("characters/{characterId}/comics")
    suspend fun getComics(
        @Path(
            value = "characterId",
            encoded = true
        ) characterId: Int
    ): Response<ComicModelResponse>
}