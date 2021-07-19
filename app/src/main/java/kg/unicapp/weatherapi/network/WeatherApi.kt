package kg.unicapp.weatherapi.network

import kg.unicapp.weatherapi.models.ForeCast
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query
import java.util.*

interface WeatherApi {
    @GET("https://api.openweathermap.org/data/2.5/onecall?lat=42.4907&lon=78.3936&exclude=hourly,daily&appid=c3e48a8a5b0c893badc50cc525fa776f&lang=ru&units=metric&=")
    fun fetchWeather():Observable<ForeCast>

    @GET("oneCall")
    fun fetWeatherUsingQuery(
        @Query("lat") lat: Double = 42.4907,
        @Query("lon") lon: Double = 78.3936,
        @Query("exclude") exclude: String = "hourly,daily",
        @Query("appid") appid: String = "c3e48a8a5b0c893badc50cc525fa776f",
        @Query("lang") lang: String = "ru",
        @Query("units") units: String = "metric"

    ): Call<ForeCast>
}