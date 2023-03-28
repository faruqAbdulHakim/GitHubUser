package com.faruqabdulhakim.githubuser.ui.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.bumptech.glide.Glide
import com.faruqabdulhakim.githubuser.R
import com.faruqabdulhakim.githubuser.databinding.ActivityDetailBinding
import com.faruqabdulhakim.githubuser.ui.ViewModelFactory
import com.google.android.material.elevation.SurfaceColors
import com.google.android.material.tabs.TabLayoutMediator
import com.faruqabdulhakim.githubuser.data.Result
import com.faruqabdulhakim.githubuser.data.local.entity.DetailUser
import com.google.android.material.snackbar.Snackbar

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding
    private val viewModel by viewModels<DetailViewModel> {
        ViewModelFactory.getInstance(this)
    }
    private var detailUser: DetailUser? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        val color = SurfaceColors.SURFACE_2.getColor(this)
        window.statusBarColor = color

        val username = intent.getStringExtra(EXTRA_USERNAME) ?: ""

        val tabs = binding.tabs
        val viewPager = binding.viewPager
        val pagerAdapter = PagerAdapter(this)
        pagerAdapter.username = username
        viewPager.adapter = pagerAdapter
        TabLayoutMediator(tabs, viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()

        viewModel.getDetailUser(username).observe(this) { result ->
            when (result) {
                is Result.Loading -> showLoading(true)
                is Result.Success -> {
                    showLoading(false)
                    detailUser = result.data
                    showDetailUser()
                }
                is Result.Error -> {
                    showLoading(false)
                    Snackbar.make(binding.root, getString(R.string.error), Snackbar.LENGTH_SHORT).show()
                }
            }
        }

        binding.btnFavorite.setOnClickListener {
            detailUser?.let {
                viewModel.toggleFavorite(it.login, it.avatarUrl, it.isFavorite)
                it.isFavorite = !it.isFavorite
                showDetailUser()
            }
        }

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) finish()
        return super.onOptionsItemSelected(item)
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) {
            View.VISIBLE
        } else {
            View.INVISIBLE
        }
    }

    private fun showDetailUser() {
        detailUser?.let { detail ->
            Glide.with(this@DetailActivity)
                .load(detail.avatarUrl)
                .into(binding.ivAvatar)
            binding.apply {
                tvName.text = detail.name
                tvUsername.text = detail.login
                tvFollow.text = resources.getString(
                    R.string.follow,
                    detail.followers,
                    detail.following
                )
                btnFavorite.visibility = View.VISIBLE
                btnFavorite.setImageDrawable(
                    ContextCompat.getDrawable(
                        this@DetailActivity,
                        if (detail.isFavorite) {
                            R.drawable.ic_favorite_24
                        } else {
                            R.drawable.ic_favorite_border_24
                        }
                    )
                )
            }
        }


    }

    private inner class PagerAdapter(activity: AppCompatActivity) : FragmentStateAdapter(activity) {
        var username = ""

        override fun createFragment(position: Int): Fragment {
            val fragment = FollowFragment()
            fragment.arguments = Bundle().apply {
                putInt(FollowFragment.ARG_POSITION, position + 1)
                putString(FollowFragment.ARG_USERNAME, username)
            }

            return fragment
        }

        override fun getItemCount(): Int = 2
    }

    companion object {
        const val EXTRA_USERNAME = "extra_username"

        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.followers,
            R.string.following
        )
    }
}