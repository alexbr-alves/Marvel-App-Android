package alex.lop.io.alexProject.util

import alex.lop.io.alexProject.data.model.FavoriteModel
import alex.lop.io.alexProject.data.model.ThumbnailModel
import alex.lop.io.alexProject.data.model.character.CharacterModel
import alex.lop.io.alexProject.data.model.comic.ComicModel
import alex.lop.io.alexProject.data.model.event.EventModel

class Converts {

    fun characterToFavorite(characterModel : CharacterModel) : FavoriteModel {
        return FavoriteModel(
            characterModel.id,
            characterModel.name,
            characterModel.description,
            Constants.CHARACTER,
            thumbnailModel = ThumbnailModel(
                path = characterModel.thumbnailModel.path,
                extension = characterModel.thumbnailModel.extension
            )
        )
    }

    fun favoriteToCharacter(favoriteModel : FavoriteModel) : CharacterModel {
        return CharacterModel(
            favoriteModel.id,
            favoriteModel.title,
            favoriteModel.description,
            ThumbnailModel(
                favoriteModel.thumbnailModel.path,
                favoriteModel.thumbnailModel.extension
            )
        )
    }

    fun favoriteToComic(favoriteModel : FavoriteModel) : ComicModel {
        return ComicModel(
            favoriteModel.id,
            favoriteModel.title,
            favoriteModel.description,
            ThumbnailModel(
                favoriteModel.thumbnailModel.path,
                favoriteModel.thumbnailModel.extension
            )
        )
    }

    fun favoriteToEvent(favoriteModel : FavoriteModel) : EventModel {
        return EventModel(
            favoriteModel.id,
            favoriteModel.title,
            favoriteModel.description,
            ThumbnailModel(
                favoriteModel.thumbnailModel.path,
                favoriteModel.thumbnailModel.extension
            )
        )
    }

    fun ComicToFavorite(comicModel : ComicModel) : FavoriteModel {
        return FavoriteModel(
            comicModel.id,
            comicModel.title,
            comicModel.description,
            Constants.COMIC,
            ThumbnailModel(
                comicModel.thumbnailModel.path,
                comicModel.thumbnailModel.extension
            )
        )
    }

    fun eventToFavorite(eventModel : EventModel) : FavoriteModel {
        return FavoriteModel(
            eventModel.id,
            eventModel.title,
            eventModel.description,
            Constants.COMIC,
            ThumbnailModel(
                eventModel.thumbnailModel.path,
                eventModel.thumbnailModel.extension
            )
        )
    }

}