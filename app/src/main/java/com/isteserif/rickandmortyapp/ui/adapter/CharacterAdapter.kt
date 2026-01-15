package com.isteserif.rickandmortyapp.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.isteserif.rickandmortyapp.databinding.ItemCharacterBinding
import com.isteserif.rickandmortyapp.model.RickMortyCharacter
import com.isteserif.rickandmortyapp.ui.fragment.CharacterListFragmentDirections // YENİ (Safe Args)

class CharacterAdapter : PagingDataAdapter<RickMortyCharacter, CharacterAdapter.CharacterViewHolder>(CharacterComparator) {

    inner class CharacterViewHolder(val binding: ItemCharacterBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(character: RickMortyCharacter?) {
            if (character == null) return

            binding.tvCharacterName.text = character.name
            Glide.with(binding.root.context)
                .load(character.image)
                .into(binding.imgCharacter)

            // YENİ - TIKLAMA İŞLEMİ:
            // 'itemView' (yani kartın kendisi) tıklandığında:
            binding.root.setOnClickListener {
                // 1. Tıklanan karakterin ID'sini al (null değilse)
                character.id?.let { id ->
                    // 2. Safe Args ile "aksiyonu" (koridoru) oluştur
                    val action =
                        CharacterListFragmentDirections
                            .actionCharacterListFragmentToCharacterDetailFragment(id)

                    // 3. 'findNavController()' ile "haritayı" bul ve
                    //    'navigate(action)' ile o koridora gir (ekranı değiştir)
                    it.findNavController().navigate(action)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterViewHolder {
        val binding =
            ItemCharacterBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CharacterViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CharacterViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    object CharacterComparator : DiffUtil.ItemCallback<RickMortyCharacter>() {
        override fun areItemsTheSame(oldItem: RickMortyCharacter, newItem: RickMortyCharacter): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: RickMortyCharacter, newItem: RickMortyCharacter): Boolean {
            return oldItem == newItem
        }
    }
}