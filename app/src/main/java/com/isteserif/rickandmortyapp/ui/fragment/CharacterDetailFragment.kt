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

    // viewmodel oluşturup fragmentin yasamdöngüsüne dahil ediyoruz burada
    private val viewModel: DetailViewModel by viewModels()

    // Diğer sayfadan gönderilen characterId'ye, sanki bu sınıfın bir değişkeni gibi
    // ulaşabiliyoruz. Bunu Safe Args sayesinde yapabildik
    private val args: CharacterDetailFragmentArgs by navArgs()

    // inflater kullanarak fragment_character_detail.xml dosyasını 'inflate' ediyoruz
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCharacterDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // ViewModel'e istedigimiz ID'li karakteri getirmesi icin bu kısım
        viewModel.getCharacter(args.characterId)

        viewModel.character.observe(viewLifecycleOwner) { character ->
            // ve veri geldiği an, XML'deki 'TextView' ve 'ImageView'leri dolduruyor

            // Glide ile resmi bas
            Glide.with(requireContext())
                .load(character.image)
                .into(binding.imgCharacterDetail)

            // 'binding' ile XML'e ulaşıp text'leri doldurtuyoz
            binding.tvNameDetail.text = character.name
            binding.tvStatusDetail.text = character.status
            binding.tvSpeciesDetail.text = character.species


            binding.tvGenderDetail.text = character.gender
            // 'origin' ve 'location' nesnelerinin 'name' özelliğini alıyoruz
            binding.tvOriginDetail.text = "Köken: ${character.origin?.name}"
            binding.tvLocationDetail.text = "Konum: ${character.location?.name}"
        }


        viewModel.error.observe(viewLifecycleOwner) { errorMessage ->
            Toast.makeText(requireContext(), errorMessage, Toast.LENGTH_SHORT).show()
        }
    }

    // Bu kısım fragment yok edildiğinde binding'i temizler, opsiyonel ama
    //verimlilik, güvenlik ve memory leak açısından profosyonelce bir kullanim.
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}