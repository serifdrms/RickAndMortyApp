package com.isteserif.rickandmortyapp.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.GridLayoutManager
import com.isteserif.rickandmortyapp.databinding.FragmentCharacterListBinding // Birazdan oluşturacağız
import com.isteserif.rickandmortyapp.ui.adapter.CharacterAdapter
import com.isteserif.rickandmortyapp.viewmodel.CharacterViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class CharacterListFragment : Fragment() {

    // 1. ViewBinding: XML'e güvenli erişim
    private var _binding: FragmentCharacterListBinding? = null
    private val binding get() = _binding!!

    // 2. ViewModel (Genel Müdür): 'by viewModels()' eklentisi ile bağlıyoruz.
    private val viewModel: CharacterViewModel by viewModels()

    // 3. Adapter (Akıllı Taşıyıcı):
    private lateinit var characterAdapter: CharacterAdapter

    // 4. onCreateView: Fragment'ın XML (Görünüm) oluşturduğu yer
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // XML'i (FragmentCharacterListBinding) 'inflate' et (şişir/oluştur)
        _binding = FragmentCharacterListBinding.inflate(inflater, container, false)
        return binding.root // Oluşturulan görünümün kökünü döndür
    }

    // 5. onViewCreated: Görünüm (XML) oluşturulduktan hemen sonra çağrılır.
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupAdapter()
        setupRecyclerView()
        observeCharacters()
    }

    private fun setupAdapter() {
        characterAdapter = CharacterAdapter()
    }

    private fun setupRecyclerView() {
        binding.rvCharacters.apply {
            adapter = characterAdapter
            layoutManager = GridLayoutManager(requireContext(), 2)
        }
    }

    private fun observeCharacters() {
        // 6. VERİYİ DİNLEME (En Önemli Kısım)
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {

                // 7. 'viewModel.characters.collectLatest'
                // "Genel Müdür'ün (ViewModel) 'characters' akışını (Flow) dinle."
                viewModel.characters.collectLatest { pagingData ->
                    // 8. "Akıllı Taşıyıcıya (Adapter) yeni gelen veriyi (pagingData) gönder."
                    characterAdapter.submitData(pagingData)
                }
            }
        }
    }

    // 9. onDestroyView: Fragment yok edilirken çağrılır.
    //    'binding' değişkenini 'null' yaparak hafıza sızıntısını (memory leak) önleriz.
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}