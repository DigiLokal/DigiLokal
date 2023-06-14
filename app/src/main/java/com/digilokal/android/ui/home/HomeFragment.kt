package com.digilokal.android.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.digilokal.android.R
import com.digilokal.android.data.UserPreference
import com.digilokal.android.databinding.FragmentHomeBinding
import com.digilokal.android.helper.ViewModelFactory
import com.digilokal.android.helper.dataStore
import com.digilokal.android.ui.InfluencerAdapter
import com.digilokal.android.ui.ServicesAdapter
import com.digilokal.android.ui.custom.ItemDecoration
import com.digilokal.android.ui.influencer.InfluencerActivity
import com.digilokal.android.ui.profile.ProfileViewModel

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private val homeViewModelFactory: ViewModelFactory by lazy {
        ViewModelFactory(UserPreference.getInstance(requireContext().dataStore), requireContext())
    }

    private val homeViewModel: HomeViewModel by lazy {
        ViewModelProvider(this, homeViewModelFactory).get(HomeViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

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

        homeViewModel.isLoading.observe(requireActivity()) {
            showLoading(it)
        }

        return root
    }

    private fun setupInfluencerRv() {
        val layoutManager = LinearLayoutManager(requireActivity(), LinearLayoutManager.HORIZONTAL, false)
        binding.rvInfluencer.layoutManager = layoutManager
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
            val intent = Intent(requireActivity(), InfluencerActivity::class.java)
            startActivity(intent)
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressbar.visibility = if (isLoading) View.VISIBLE else View.GONE
        binding.bgProgressbar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}