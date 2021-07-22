package kg.unicapp.weatherapi.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable
import kg.unicapp.weatherapi.repo.WeatherRepo

class MainViewModel(private val repo: WeatherRepo): ViewModel() {

    private val compositeDisposable = CompositeDisposable()

    private val isLoading = MutableLiveData<Boolean>()
    val _isLoading: LiveData<Boolean>
    get() = isLoading

    init {
        getWeatherFromApi()
    }


    fun getWeatherFromApi(){
        isLoading.value = true

        compositeDisposable.add(
            repo.getWeatherFromApi()
                .doOnTerminate { isLoading.value = false}
                .doOnDispose{ isLoading.value = false }
                .subscribe({
                           isLoading.value =false
                }, {
                    isLoading.value =false
                }))


    }

    fun showLoading(){
        isLoading.value = true
    }

    private fun hideLoading(){
        isLoading.value = false
    }
    fun getForeCastAsLive() = repo.getForeCastFromDbAsLive()

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}


