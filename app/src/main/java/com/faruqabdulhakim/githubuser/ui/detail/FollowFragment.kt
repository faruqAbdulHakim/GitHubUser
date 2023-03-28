package com.faruqabdulhakim.githubuser.ui.detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.faruqabdulhakim.githubuser.R
import com.faruqabdulhakim.githubuser.data.remote.response.User
import com.faruqabdulhakim.githubuser.adapter.ListUserAdapter
import com.faruqabdulhakim.githubuser.databinding.FragmentFollowBinding
import com.faruqabdulhakim.githubuser.ui.ViewModelFactory
import com.faruqabdulhakim.githubuser.data.Result
import com.google.android.material.snackbar.Snackbar

class FollowFragment : Fragment() {
    private var _binding: FragmentFollowBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModels<FollowViewModel>() {
        ViewModelFactory.getInstance(requireActivity())
    }

    companion object {
        const val ARG_POSITION = "arg_position"
        const val ARG_USERNAME = "arg_username"
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFollowBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val layoutManager = LinearLayoutManager(requireActivity())
        binding.rvFollow.layoutManager = layoutManager

        arguments?.let { bundle ->
            val position = bundle.getInt(ARG_POSITION, 1)
            val username = bundle.getString(ARG_USERNAME) ?: ""
            when (position) {
                1 -> viewModel.getFollowerList(username)
                    .observe(viewLifecycleOwner) {
                        resultHandler(it)
                    }

                2 -> viewModel.getFollowingList(username)
                    .observe(viewLifecycleOwner) {
                        resultHandler(it)
                    }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun resultHandler(result: Result<List<User>>) {
        when (result) {
            is Result.Loading -> showLoading(true)
            is Result.Success -> {
                showLoading(false)
                showFollow(result.data)
            }
            is Result.Error -> {
                showLoading(false)
                Snackbar.make(binding.root, getString(R.string.error), Snackbar.LENGTH_SHORT).show()
            }
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) {
            View.VISIBLE
        } else {
            View.INVISIBLE
        }
    }

    private fun showFollow(followList: List<User>) {
        binding.tvNoData.visibility = if (followList.isNotEmpty()) {
            View.INVISIBLE
        } else {
            View.VISIBLE
        }

        val adapter = ListUserAdapter(followList)
        binding.rvFollow.adapter = adapter
    }
}