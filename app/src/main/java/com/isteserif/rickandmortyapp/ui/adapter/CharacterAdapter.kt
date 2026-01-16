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

    // Paging Kütüp deki veriyi alır xml'lerin içine yansıtır.

    // 2 Secenegim vardı PagingDataAdapter-RecyclerView.Adapter ben
    // pagingDataAdapter kullandım cünkü  Paging 3 sayesinde cok büyük veriyi
    // hafıza yormadan sadece ekranda görünenleri yükleyerek yönetmemizi sağlıyor

    inner class CharacterViewHolder(val binding: ItemCharacterBinding) :
        RecyclerView.ViewHolder(binding.root) { // performans icin tutucu kullandim


            //Burada karakter verisi ile XML tasarımını birbirine bağlıyoruz
        fun bind(character: RickMortyCharacter?) {
            if (character == null) return

            binding.tvCharacterName.text = character.name

                // glide burda resmi indirip önbellege aliyor ve
                // imageview'da gösteriyor önbellek netten tasarruf saglar
            Glide.with(binding.root.context)
                .load(character.image)
                .into(binding.imgCharacter)

            // 'itemView' (yani kartın kendisinr) tıklandiginde ne olacagi burda
            binding.root.setOnClickListener {
                // Tıklanan karakterin ID'sini al (null değilse)
                character.id?.let { id ->

                    // Safe Args sayesinde detay fragmentine ID gönderirken
                    // null veya yablis tip gönderme riski sifirlaniyor
                    val action =
                        CharacterListFragmentDirections
                            .actionCharacterListFragmentToCharacterDetailFragment(id)

                    it.findNavController().navigate(action)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterViewHolder {
        val binding =
            ItemCharacterBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CharacterViewHolder(binding) //bos kart olusturduk
    }

    override fun onBindViewHolder(holder: CharacterViewHolder, position: Int) {
        holder.bind(getItem(position)) //kartı doldurttuk
    }


    // Bu asagidaki kısım performans ve kullanici deneyimi acisindan önemli
    // liste güncellendiginde areItemsTheSame ID check eder ayni kisi mi diye
    // eger ayni kisiyse areContentsTheSame ise icindeki bilgileri de kontrol eder
    // yani sadece degisenler güncellendigi icin kaydırma yaparken lag olmaz
    object CharacterComparator : DiffUtil.ItemCallback<RickMortyCharacter>() {
        override fun areItemsTheSame(oldItem: RickMortyCharacter, newItem: RickMortyCharacter): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: RickMortyCharacter, newItem: RickMortyCharacter): Boolean {
            return oldItem == newItem
        }
    }
}