package alex.lop.io.alexProject.data.remote

import alex.lop.io.alexProject.data.model.character.CharacterModelResponse
import alex.lop.io.alexProject.data.model.comic.ComicModelResponse
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

    //Character details

    @GET("characters/{characterId}/comics")
    suspend fun getComicsCharacter(
        @Path(
            value = "characterId",
            encoded = true
        ) characterId : Int
    ) : Response<ComicModelResponse>

    @GET("characters/{characterId}/events")
    suspend fun getEventsCharacter(
        @Path(
            value = "characterId",
            encoded = true
        ) characterId : Int
    ) : Response<EventModelResponse>

    @GET("characters/{characterId}/series")
    suspend fun getSeriesCharacter(
        @Path(
            value = "characterId",
            encoded = true
        ) characterId : Int
    ) : Response<SeriesModelResponse>

    @GET("characters/{characterId}/stories")
    suspend fun getStoriesCharacter(
        @Path(
            value = "characterId",
            encoded = true
        ) characterId : Int
    ) : Response<StoriesModelResponse>

    //Comic details

    @GET("comics/{comicId}/characters")
    suspend fun getCharacterComics(
        @Path(
            value = "comicId",
            encoded = true
        ) comicId : Int
    ) : Response<CharacterModelResponse>

    @GET("comics/{comicId}/events")
    suspend fun getEventComics(
        @Path(
            value = "comicId",
            encoded = true
        ) comicId : Int
    ) : Response<EventModelResponse>

    @GET("comics/{comicId}/stories")
    suspend fun getStoriesComics(
        @Path(
            value = "comicId",
            encoded = true
        ) comicId : Int
    ) : Response<StoriesModelResponse>

    //Event details

    @GET("events/{eventId}/characters")
    suspend fun getCharacterEvent(
        @Path(
            value = "eventId",
            encoded = true
        ) eventId : Int
    ) : Response<CharacterModelResponse>

    @GET("events/{eventId}/comics")
    suspend fun getComicEvent(
        @Path(
            value = "eventId",
            encoded = true
        ) eventId : Int
    ) : Response<ComicModelResponse>

    @GET("events/{eventId}/series")
    suspend fun getSeriesEvent(
        @Path(
            value = "eventId",
            encoded = true
        ) eventId : Int
    ) : Response<SeriesModelResponse>

    @GET("events/{eventId}/stories")
    suspend fun getStoriesEvent(
        @Path(
            value = "eventId",
            encoded = true
        ) eventId : Int
    ) : Response<StoriesModelResponse>

}