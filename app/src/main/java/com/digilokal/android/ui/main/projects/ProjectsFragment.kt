package com.digilokal.android.ui.main.projects

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.digilokal.android.R
import com.digilokal.android.data.repository.UserPreference
import com.digilokal.android.databinding.FragmentProjectsBinding
import com.digilokal.android.di.ViewModelFactory
import com.digilokal.android.helper.dataStore
import com.digilokal.android.ui.adapters.OrderAdapter
import com.digilokal.android.ui.custom.ItemDecoration


class ProjectsFragment : Fragment() {

    private var _binding: FragmentProjectsBinding? = null

    private val binding get() = _binding!!

    private val projectsViewModelFactory: ViewModelFactory by lazy {
        ViewModelFactory(UserPreference.getInstance(requireContext().dataStore), requireContext())
    }

    private val projectsViewModel: ProjectsViewModel by lazy {
        ViewModelProvider(this, projectsViewModelFactory).get(ProjectsViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProjectsBinding.inflate(inflater, container, false)
        val root: View = binding.root


        setupOrderRV()

        projectsViewModel.isLoading.observe(viewLifecycleOwner) {
            showLoading(it)
        }

        return root
    }

    private fun setupOrderRV() {
        val layoutManager = LinearLayoutManager(requireContext())
        binding.rvOrder.layoutManager = layoutManager
        val spacing = resources.getDimensionPixelSize(R.dimen.item_spacing)
        val itemDecoration = ItemDecoration(spacing)
        binding.rvOrder.addItemDecoration(itemDecoration)

        projectsViewModel.order.observe(viewLifecycleOwner) {
            binding.rvOrder.adapter = OrderAdapter(it)
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressbar.visibility = if (isLoading) View.VISIBLE else View.GONE
        binding.bgProgressbar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        projectsViewModel.order.removeObservers(viewLifecycleOwner)
        projectsViewModel.isLoading.removeObservers(viewLifecycleOwner)
        _binding = null
    }

}