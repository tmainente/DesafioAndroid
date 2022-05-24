package com.example.desafioandroid.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.desafioandroid.R
import com.example.desafioandroid.databinding.ActivityMainBinding
import com.example.desafioandroid.ui.adapter.RepoListAdapter
import com.example.desafioandroid.util.UIState
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val mainViewModel: MainViewModel by viewModel()
    private lateinit var adapter: RepoListAdapter
    private var language = "language:kotlin"
    private var sort = "stars"
    private var page: Int = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        adapter = RepoListAdapter()
        binding.rRepo.adapter = adapter
        binding.rRepo.layoutManager = LinearLayoutManager(this)
        binding.rRepo.addItemDecoration(
            DividerItemDecoration(
                baseContext,
                DividerItemDecoration.VERTICAL
            )

        )

        mainViewModel.state.observe(this, Observer {
            when (it) {
                is UIState.Initial -> {
                    mainViewModel.getRepo(language, sort, page)
                }
                is UIState.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                }
                is UIState.Success ->{
                    binding.progressBar.visibility = View.GONE

                    adapter.submitList(it.result.items)
                }
                is UIState.Error ->{
                    val message = it.errorMessage
                    binding.rRepo.visibility = View.GONE
                    binding.progressBar.visibility = View.GONE

                    Toast.makeText(this@MainActivity, message, Toast.LENGTH_SHORT)
                        .show()
                }
            }
        })
    }

}