package alex.lop.io.alexProject.adapters


import alex.lop.io.alexProject.data.model.character.CharacterModel

import alex.lop.io.alexProject.databinding.ItemCharacterBinding

import alex.lop.io.alexProject.util.loadImage
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

class CharacterAdapter : RecyclerView.Adapter<CharacterAdapter.CharacterViewHolder>() {

    inner class CharacterViewHolder(val binding : ItemCharacterBinding) :
        RecyclerView.ViewHolder(binding.root)


    private val differCallback = object : DiffUtil.ItemCallback<CharacterModel>() {
        override fun areItemsTheSame(oldItem : CharacterModel, newItem : CharacterModel) : Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }

        override fun areContentsTheSame(
            oldItem : CharacterModel,
            newItem : CharacterModel
        ) : Boolean {
            return oldItem.id == newItem.id &&
                    oldItem.name == newItem.name &&
                    oldItem.description == newItem.description &&
                    oldItem.thumbnailModel.path == newItem.thumbnailModel.path &&
                    oldItem.thumbnailModel.extension == newItem.thumbnailModel.extension
        }

    }

    private val differ = AsyncListDiffer(this, differCallback)

    var characters : List<CharacterModel>
        get() = differ.currentList
        set(value) = differ.submitList(value)

    override fun getItemCount() : Int = characters.size
    override fun onCreateViewHolder(parent : ViewGroup, viewType : Int) : CharacterViewHolder {
        return CharacterViewHolder(
            ItemCharacterBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder : CharacterViewHolder, position : Int) {
        val character = characters[position]
        holder.binding.apply {
            tvNameCharacter.text = character.name

            loadImage(
                imgCharacter,
                character.thumbnailModel.path,
                character.thumbnailModel.extension
            )
        }
        holder.itemView.setOnClickListener {
            onItemClickListener?.let {
                it(character)
            }
        }
    }

    private var onItemClickListener : ((CharacterModel) -> Unit)? = null

    fun setOnClickListener(listener : (CharacterModel) -> Unit) {
        onItemClickListener = listener
    }

    fun getCharacterPosition(position : Int) : CharacterModel {
        return characters[position]
    }
}