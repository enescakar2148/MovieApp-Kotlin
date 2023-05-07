package com.enescakar.filmfly.View


import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.enescakar.filmfly.Adapters.CategoryRecyclerAdapter
import com.enescakar.filmfly.Adapters.InTheVisionRecyclerAdapter
import com.enescakar.filmfly.Adapters.TopRatedRecyclerAdapter
import com.enescakar.filmfly.Adapters.YakindaReycylerAdapter
import com.enescakar.filmfly.ViewModel.HomeViewModel
import com.enescakar.filmfly.databinding.ActivityHomeBinding

class Home : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding
    private lateinit var viewModel: HomeViewModel

    val adapter = CategoryRecyclerAdapter(arrayListOf())
    val adapterTodayPopuler = InTheVisionRecyclerAdapter(arrayListOf())
    val adapterVoteAverange = TopRatedRecyclerAdapter(arrayListOf())
    val adapterYakinda = YakindaReycylerAdapter(arrayListOf())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)


        // Initialize ViewModel
        viewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
        viewModel.getData()


        //Category List Text-Adapters/Recycler
        binding.categoryRecycler.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        binding.categoryRecycler.adapter = adapter

        //In The Vission Movies Section
        binding.inTheVisionMovies.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        binding.inTheVisionMovies.adapter = adapterTodayPopuler

        //Vote Averange Movies Section
        binding.voteAverangeRecycler.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        binding.voteAverangeRecycler.adapter = adapterVoteAverange

        //Yakinda Movies Section
        binding.yakindaRecycler.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        binding.yakindaRecycler.adapter = adapterYakinda


        observeData()

    }

    fun observeData() {
        viewModel.inTheVision.observe(this, Observer { movieList ->
            movieList.let {
                adapterTodayPopuler.updateInTheVisionAdapter(movieList)
            }
        })
        viewModel.voteAverange.observe(this, Observer { movieList ->
            movieList.let {
                adapterVoteAverange.updateVoteAverangeAdapter(movieList)
            }
        })
        viewModel.yakinda.observe(this, Observer { movieList ->
            movieList.let {
                adapterYakinda.updateYakindaAdapter(movieList)
            }
        })
        viewModel.categories.observe(this, Observer { categoryList ->
            categoryList.let {
                adapter.updateCategoryAdapter(categoryList)
            }
        })
    }
}