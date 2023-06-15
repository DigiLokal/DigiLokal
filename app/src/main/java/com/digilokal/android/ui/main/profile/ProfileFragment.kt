package com.digilokal.android.ui.main.profile

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.digilokal.android.R
import com.digilokal.android.data.repository.UserPreference
import com.digilokal.android.databinding.FragmentProfileBinding
import com.digilokal.android.di.ViewModelFactory
import com.digilokal.android.helper.dataStore
import com.digilokal.android.ui.authentication.welcome.WelcomeActivity
import com.digilokal.android.ui.main.profile.editprofile.EditProfileActivity
import com.digilokal.android.ui.main.profile.liked.LikedActivity

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private val EDIT_PROFILE_REQUEST_CODE = 1

    private val profileViewModelFactory: ViewModelFactory by lazy {
        ViewModelFactory(UserPreference.getInstance(requireContext().dataStore), requireContext())
    }

    private val profileViewModel: ProfileViewModel by lazy {
        ViewModelProvider(this, profileViewModelFactory).get(ProfileViewModel::class.java)
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        val root: View = binding.root

        setupAction()

        profileViewModel.getProfile()
        showData()

        profileViewModel.isLoading.observe(viewLifecycleOwner) {
            showLoading(it)
        }
        return root
    }

    private fun showData() {
        profileViewModel.profile.observe(viewLifecycleOwner) { profile ->

            Glide.with(binding.ivUser)
                .load("https://avatars.githubusercontent.com/u/1573?v=4")
                .into(binding.ivUser)

            binding.name.text = profile.firstOrNull()?.nama ?: ""
            binding.username.text = profile.firstOrNull()?.username ?: ""
            binding.tvEmail.text = profile.firstOrNull()?.email ?: ""
//            binding.tvType.text = profile.firstOrNull()?.tipe ?: ""
            // demo
            binding.tvType.setText(R.string.warung_maknyus)
            binding.tvDetail.text = profile.firstOrNull()?.detail ?: ""
        }
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == EDIT_PROFILE_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            // Refresh the profile data here
            profileViewModel.getProfile()
        }
    }

    private fun setupAction() {
        binding.btSignout.setOnClickListener{
            profileViewModel.logout()
            val intent = Intent(requireActivity(), WelcomeActivity::class.java)
            startActivity(intent)
        }
        binding.editProfileLayout.setOnClickListener{
            val intent = Intent(requireActivity(), EditProfileActivity::class.java)
            startActivityForResult(intent, EDIT_PROFILE_REQUEST_CODE)
        }
        binding.likedInfluencerLayout.setOnClickListener{
            val intent = Intent(requireActivity(), LikedActivity::class.java)
            startActivity(intent)
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressbar.visibility = if (isLoading) View.VISIBLE else View.GONE
        binding.bgProgressbar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        profileViewModel.isLoading.removeObservers(viewLifecycleOwner)
        profileViewModel.profile.removeObservers(viewLifecycleOwner)
        _binding = null
    }
}