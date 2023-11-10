package alex.lop.io.alexProject.data.remote

import alex.lop.io.alexProject.data.model.character.CharacterModelResponse
import alex.lop.io.alexProject.data.model.comic.ComicModelResponse
import alex.lop.io.alexProject.data.model.creator.CreatorModelResponse
import alex.lop.io.alexProject.data.model.event.EventModelResponse
import alex.lop.io.alexProject.data.model.serie.SeriesModelResponse
import alex.lop.io.alexProject.data.model.stories.StoriesModelResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ServiceApi {

    @GET("characters")
    suspend fun characters(
        @Query("nameStartsWith") nameStartsWith : String? = null,
        @Query("limit") limit : Int = 100
    ) : Response<CharacterModelResponse>

    @GET("comics")
    suspend fun comics(
        @Query("limit") limit : Int = 100
    ) : Response<ComicModelResponse>

    @GET("creators")
    suspend fun creators(
        @Query("limit") limit : Int = 100
    ) : Response<CreatorModelResponse>

    @GET("events")
    suspend fun events(
        @Query("limit") limit : Int = 100
    ) : Response<EventModelResponse>

    @GET("series")
    suspend fun series(
        @Query("titleStartsWith") titleStartsWith : String? = null,
        @Query("limit") limit : Int = 100
    ) : Response<SeriesModelResponse>

    @GET("stories")
    suspend fun stories(
        @Query("limit") limit : Int = 100
    ) : Response<StoriesModelResponse>

    @GET("characters/{characterId}/comics")
    suspend fun getComicsCharacter(
        @Path(
            value = "characterId",
            encoded = true
        ) characterId : Int
    ) : Response<ComicModelResponse>
}