package kg.unicapp.weatherapi.network

import io.reactivex.Single
import kg.unicapp.weatherapi.models.ForeCast
import retrofit2.http.GET
import retrofit2.http.Query


interface WeatherApi {

    @GET("onecall")
    fun fetchWeather(
        @Query("lat") lat: Double = 42.4907,
        @Query("lon") lon: Double = 78.3936,
        @Query("exclude") exclude: String = "minutely",
        @Query("appid") appid: String = "c3e48a8a5b0c893badc50cc525fa776f",
        @Query("lang") lang: String = "ru",
        @Query("units") units: String = "metric"
    ): Single<ForeCast>
}