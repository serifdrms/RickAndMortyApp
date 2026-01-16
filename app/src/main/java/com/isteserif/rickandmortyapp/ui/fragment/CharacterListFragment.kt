package com.isteserif.rickandmortyapp.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.GridLayoutManager
import com.isteserif.rickandmortyapp.databinding.FragmentCharacterListBinding
import com.isteserif.rickandmortyapp.ui.adapter.CharacterAdapter
import com.isteserif.rickandmortyapp.viewmodel.CharacterViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class CharacterListFragment : Fragment() {

    private var _binding: FragmentCharacterListBinding? = null
    private val binding get() = _binding!!

    private val viewModel: CharacterViewModel by viewModels()
    private lateinit var characterAdapter: CharacterAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCharacterListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupAdapter()
        setupRecyclerView()
        setupSearchView()
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

    private fun setupSearchView() {
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let { viewModel.searchCharacters(it) }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                newText?.let { viewModel.searchCharacters(it) }
                return true
            }
        })
    }

    private fun observeCharacters() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.characters.collectLatest { pagingData ->
                    characterAdapter.submitData(pagingData)
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}