package com.faruqabdulhakim.githubuser.ui.favorites

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.faruqabdulhakim.githubuser.adapter.ListUserAdapter
import com.faruqabdulhakim.githubuser.data.remote.response.User
import com.faruqabdulhakim.githubuser.databinding.ActivityFavoritesBinding
import com.faruqabdulhakim.githubuser.ui.ViewModelFactory
import com.faruqabdulhakim.githubuser.ui.detail.DetailActivity
import com.google.android.material.elevation.SurfaceColors

class FavoritesActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFavoritesBinding
    private val viewModel by viewModels<FavoritesViewModel>() { ViewModelFactory.getInstance(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityFavoritesBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val color = SurfaceColors.SURFACE_5.getColor(this)
        window.statusBarColor = color

        viewModel.getFavorites().observe(this) {
            showRecyclerView(it.map { favorite ->
                User(favorite.username, favorite.avatarUrl)
            })
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) finish()
        return super.onOptionsItemSelected(item)
    }

    private fun showRecyclerView(listUser: List<User>) {
        binding.tvNoData.visibility = if (listUser.isNotEmpty()) {
            View.INVISIBLE
        } else {
            View.VISIBLE
        }

        binding.rvFavorites.apply {
            layoutManager = LinearLayoutManager(this@FavoritesActivity)
            adapter = ListUserAdapter(listUser).also {
                it.setOnItemClickCallback(object: ListUserAdapter.OnItemClickCallback {
                    override fun onItemClicked(user: User) {
                        val detailIntent = Intent(this@FavoritesActivity, DetailActivity::class.java)
                        detailIntent.putExtra(DetailActivity.EXTRA_USERNAME, user.login)
                        startActivity(detailIntent)
                    }
                })
            }
        }
    }
}