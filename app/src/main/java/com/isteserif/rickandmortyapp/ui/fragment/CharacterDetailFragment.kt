package com.isteserif.rickandmortyapp.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.isteserif.rickandmortyapp.databinding.FragmentCharacterDetailBinding
import com.isteserif.rickandmortyapp.viewmodel.DetailViewModel

class CharacterDetailFragment : Fragment() {

    private var _binding: FragmentCharacterDetailBinding? = null
    private val binding get() = _binding!!

    // 1. Yeni "Genel Müdür"ümüzü (DetailViewModel) çağırıyoruz
    private val viewModel: DetailViewModel by viewModels()

    // 2. "Safe Args" ile gelen argümanları (characterId) alıyoruz
    private val args: CharacterDetailFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCharacterDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 3. "Genel Müdür"e (ViewModel) "Bana bu ID'li karakteri getir" diyoruz
        viewModel.getCharacter(args.characterId)

        // 4. "Genel Müdür"ün bize vereceği 'character' verisini dinlemeye başlıyoruz
        // 4. "Genel Müdür"ün bize vereceği 'character' verisini dinlemeye başlıyoruz
        viewModel.character.observe(viewLifecycleOwner) { character ->
            // 5. Veri geldiği an, XML'deki 'TextView' ve 'ImageView'leri doldur

            // Glide ile resmi bas
            Glide.with(requireContext())
                .load(character.image)
                .into(binding.imgCharacterDetail)

            // 'binding' ile XML'e ulaşıp text'leri doldur
            binding.tvNameDetail.text = character.name
            binding.tvStatusDetail.text = character.status
            binding.tvSpeciesDetail.text = character.species

            // --- YENİ EKLENEN 3 SATIR ---
            binding.tvGenderDetail.text = character.gender
            // 'origin' ve 'location' nesnelerinin 'name' özelliğini alıyoruz
            binding.tvOriginDetail.text = "Köken: ${character.origin?.name}"
            binding.tvLocationDetail.text = "Konum: ${character.location?.name}"
        }

        // 6. Hata olursa dinle (opsiyonel ama güzel)
        viewModel.error.observe(viewLifecycleOwner) { errorMessage ->
            Toast.makeText(requireContext(), errorMessage, Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}