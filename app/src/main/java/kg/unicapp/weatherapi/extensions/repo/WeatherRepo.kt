package kg.unicapp.weatherapi.extensions.repo


import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers


import kg.unicapp.weatherapi.models.ForeCast
import kg.unicapp.weatherapi.network.WeatherApi
import kg.unicapp.weatherapi.storage.ForeCastDatabase

class WeatherRepo (
    private val db: ForeCastDatabase,
    private val weatherApi: WeatherApi
){
    fun getWeatherFromApi(it: ForeCast): Single<ForeCast> {
        return weatherApi.fetchWeather()
            .subscribeOn(Schedulers.io())
            .map {
                db.forecastDao().insert(it)
                it
            }
            .observeOn(AndroidSchedulers.mainThread())

    }
    fun getForeCastFromDbAsLive() = db.forecastDao().getAll()
}