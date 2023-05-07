package com.enescakar.filmfly.ViewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.enescakar.filmfly.Model.Category
import com.enescakar.filmfly.Model.CategoryList
import com.enescakar.filmfly.Model.Movie
import com.enescakar.filmfly.Model.MovieList
import com.enescakar.filmfly.Service.Service
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers

class HomeViewModel: ViewModel() {
    private val service = Service()
    private val disposable = CompositeDisposable()

    val inTheVision = MutableLiveData<List<Movie>>()
    val voteAverange = MutableLiveData<List<Movie>>()
    val categories = MutableLiveData<List<Category>>()
    val yakinda = MutableLiveData<List<Movie>>()


    fun getData() {
        disposable.add(
            service.getCategories()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<CategoryList>(){
                    override fun onSuccess(t: CategoryList) {
                        categories.value = t.genres
                    }

                    override fun onError(e: Throwable) {
                        println(e.localizedMessage)
                    }

                })
        )

        disposable.add(
            service.getInTheVisionMOvies()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<MovieList>(){
                    override fun onSuccess(t: MovieList) {
                        inTheVision.value = t.results
                    }

                    override fun onError(e: Throwable) {
                        println(e.localizedMessage)
                    }

                })

        )
        disposable.add(
            service.getVoteAverange()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<MovieList>(){
                    override fun onSuccess(t: MovieList) {
                        voteAverange.value = t.results
                    }

                    override fun onError(e: Throwable) {
                        println(e.localizedMessage)
                    }

                })

        )

        disposable.add(
            service.getYakinda()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<MovieList>(){
                    override fun onSuccess(t: MovieList) {
                        yakinda.value = t.results
                    }

                    override fun onError(e: Throwable) {
                        println(e.localizedMessage)
                    }

                })

        )
    }

}