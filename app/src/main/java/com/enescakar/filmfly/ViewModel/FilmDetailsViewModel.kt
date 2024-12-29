package com.enescakar.filmfly.ViewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.enescakar.filmfly.Model.Category
import com.enescakar.filmfly.Model.Movie
import com.enescakar.filmfly.Model.MovieList
import com.enescakar.filmfly.Model.ProductionCompany
import com.enescakar.filmfly.Model.Video
import com.enescakar.filmfly.Service.Service
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers

class FilmDetailsViewModel: ViewModel() {
    private val service = Service()
    private val disposable = CompositeDisposable()

    val movie = MutableLiveData<Movie>()
    val movieVideos = MutableLiveData<Video>()
    val benzerFilmler = MutableLiveData<List<Movie>>()

    fun accessData(id: Long) {
        disposable.add(
            service.getMovie(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<Movie>(){
                    override fun onSuccess(t: Movie) {
                        movie.value = t
                    }

                    override fun onError(e: Throwable) {
                        println(e.localizedMessage)
                    }

                })
        )

        disposable.add(
            service.getMovieVideo(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object: DisposableSingleObserver<Video>(){
                    override fun onSuccess(t: Video) {
                        movieVideos.value = t
                    }

                    override fun onError(e: Throwable) {
                        println(e.localizedMessage)
                    }

                })
        )
        disposable.add(
            service.getBenzer(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object: DisposableSingleObserver<MovieList>(){
                    override fun onSuccess(t: MovieList) {
                        benzerFilmler.value = t.results
                    }

                    override fun onError(e: Throwable) {
                        println(e.localizedMessage)
                    }

                })
        )
    }
}