package com.digilokal.dilo.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.digilokal.dilo.R
import com.digilokal.dilo.databinding.FragmentHomeBinding
import com.digilokal.dilo.ui.InfluencerAdapter
import com.digilokal.dilo.ui.ServicesAdapter
import com.digilokal.dilo.ui.custom.ItemDecoration

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        setupAction()

        setupInfluencerRv()

        homeViewModel.influencer.observe(requireActivity()) {
            binding.rvInfluencer.adapter = InfluencerAdapter(it)
        }

        setupServicesRv()

        homeViewModel.services.observe(requireActivity()) {
            binding.rvServices.adapter = ServicesAdapter(it)
        }

        return root
    }

    private fun setupInfluencerRv() {
        val layoutManager = LinearLayoutManager(requireActivity(), LinearLayoutManager.HORIZONTAL, false)
        binding.rvInfluencer.layoutManager = layoutManager
//        val itemDecoration = DividerItemDecoration(requireActivity(), layoutManager.orientation)
        val spacing = resources.getDimensionPixelSize(R.dimen.item_spacing)
        val itemDecoration = ItemDecoration(spacing)
        binding.rvInfluencer.addItemDecoration(itemDecoration)
    }

    private fun setupServicesRv() {
        val layoutManager = LinearLayoutManager(requireActivity(), LinearLayoutManager.HORIZONTAL, false)
        binding.rvServices.layoutManager = layoutManager
//        val itemDecoration = DividerItemDecoration(requireActivity(), layoutManager.orientation)
        val spacing = resources.getDimensionPixelSize(R.dimen.item_spacing)
        val itemDecoration = ItemDecoration(spacing)
        binding.rvServices.addItemDecoration(itemDecoration)
    }


    private fun setupAction() {
        binding.showMoreInfluencer.setOnClickListener{

        }
        binding.showMoreServices.setOnClickListener{

        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}