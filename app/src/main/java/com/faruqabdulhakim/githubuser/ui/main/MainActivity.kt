package com.faruqabdulhakim.githubuser.ui.main

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.SearchView
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.recyclerview.widget.LinearLayoutManager
import com.faruqabdulhakim.githubuser.ui.detail.DetailActivity
import com.faruqabdulhakim.githubuser.data.remote.response.User
import com.faruqabdulhakim.githubuser.adapter.ListUserAdapter
import com.faruqabdulhakim.githubuser.R
import com.faruqabdulhakim.githubuser.databinding.ActivityMainBinding
import com.google.android.material.elevation.SurfaceColors
import com.faruqabdulhakim.githubuser.data.Result
import com.faruqabdulhakim.githubuser.ui.ViewModelFactory
import com.faruqabdulhakim.githubuser.ui.favorites.FavoritesActivity
import com.faruqabdulhakim.githubuser.ui.setting.SettingActivity
import com.google.android.material.snackbar.Snackbar

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "setting")

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val viewModel by viewModels<MainViewModel>() {
        ViewModelFactory.getInstance(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)

        val color = SurfaceColors.SURFACE_5.getColor(this)
        window.statusBarColor = color

        val layoutManager = LinearLayoutManager(this)
        binding.rvUserList.layoutManager = layoutManager

        viewModel.searchUser("andi").observe(this) { resultHandler(it) }
        viewModel.getIsDarkMode().observe(this) { isDarkMode ->
            AppCompatDelegate.setDefaultNightMode(if (isDarkMode) AppCompatDelegate.MODE_NIGHT_YES else AppCompatDelegate.MODE_NIGHT_NO)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)

        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = menu.findItem(R.id.search).actionView as SearchView

        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView.queryHint = resources.getString(R.string.search_hint)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                viewModel.searchUser(query).observe(this@MainActivity) { resultHandler(it) }
                searchView.clearFocus()
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                return false
            }
        })
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_favorite -> {
                val favoritesIntent = Intent(this@MainActivity, FavoritesActivity::class.java)
                startActivity(favoritesIntent)
            }
            R.id.action_setting -> {
                val settingIntent = Intent(this@MainActivity, SettingActivity::class.java)
                startActivity(settingIntent)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun resultHandler(result: Result<List<User>>) {
        when (result) {
            is Result.Loading -> {
                showLoading(true)
            }
            is Result.Success -> {
                showLoading(false)
                showRecyclerView(result.data)
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

    private fun showRecyclerView(listUser: List<User>) {
        binding.tvNoData.visibility = if (listUser.isNotEmpty()) {
            View.INVISIBLE
        } else {
            View.VISIBLE
        }

        val adapter = ListUserAdapter(listUser)
        adapter.setOnItemClickCallback(object : ListUserAdapter.OnItemClickCallback {
            override fun onItemClicked(user: User) {
                val detailIntent = Intent(this@MainActivity, DetailActivity::class.java)
                detailIntent.putExtra(DetailActivity.EXTRA_USERNAME, user.login)
                startActivity(detailIntent)
            }
        })
        binding.rvUserList.adapter = adapter
    }

}