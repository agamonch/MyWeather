package kg.unicapp.weatherapi.ui

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kg.unicapp.weatherapi.R
import kg.unicapp.weatherapi.WeatherClient
import kg.unicapp.weatherapi.format
import kg.unicapp.weatherapi.models.Constants
import kg.unicapp.weatherapi.models.ForeCast
import kg.unicapp.weatherapi.storage.ForeCastDatabase
import kg.unicapp.weatherapi.ui.rv.DailyForeCastAdapter
import java.util.*
import kotlin.math.roundToInt

@SuppressLint("CheckResult")
class MainActivity : AppCompatActivity() {

    private val db by lazy {
        ForeCastDatabase.getInstance(applicationContext)
    }

    private lateinit var dailyForeCastAdapter: DailyForeCastAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupViews()
        setupRecyclerViews()
        getWeatherFromApi()
        subscribeToLiveData()
    }

    private fun setupViews() {
        val refresh = findViewById<TextView>(R.id.refresh)
        refresh.setOnClickListener {
            showLoading()
            getWeatherFromApi()
        }
    }

    private fun setupRecyclerViews() {
        val rv_daily_forecast = findViewById<RecyclerView>(R.id.rv_daily_forecast)
        dailyForeCastAdapter = DailyForeCastAdapter()
        rv_daily_forecast.adapter = dailyForeCastAdapter

    }

    private fun showLoading() {
        val progress = findViewById<View>(R.id.progress)
        progress.visibility = View.VISIBLE
    }

    private fun hideLoading() {
        val progress = findViewById<View>(R.id.progress)
        progress.visibility = View.GONE
    }

    private fun getWeatherFromApi(it: ForeCast) {
        WeatherClient.weatherApi.fetchWeather()
            .subscribeOn(Schedulers.io())
            .map {
                db.forecastDao().insert(it)
                it
            }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                hideLoading()
            },
                {
                    hideLoading()
                    Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()
                })
    }

    private fun subscribeToLiveData(it: ForeCast) {
        db.forecastDao().getAll().observe(this, Observer {
            it?.let{
                setValuesToViews(it)
                loadWeatherIcon(it)
                it.daily?.let { dailyList -> dailyForeCastAdapter.setItems(dailyList) }
            }
        })
    }


    private fun setValuesToViews(it: ForeCast) {
        val tv_temp = findViewById<TextView>(R.id.tv_temperature)
        val tv_date = findViewById<TextView>(R.id.tv_date)
        val tv_temp_max = findViewById<TextView>(R.id.tv_temp_max)
        val tv_temp_min = findViewById<TextView>(R.id.tv_temp_min)
        val tv_feels_like = findViewById<TextView>(R.id.tv_feels_like)
        val tv_weather = findViewById<TextView>(R.id.tv_weather)
        val sunrise = findViewById<TextView>(R.id.tv_sunrise)
        val sunset = findViewById<TextView>(R.id.tv_sunset)
        val humidity = findViewById<TextView>(R.id.tv_humidity)

        tv_temp.text = it.current?.temp?.roundToInt().toString()
        tv_date.text = it.current?.date.format()
        tv_temp_max.text = it.daily?.get(0)?.temp?.max?.roundToInt()?.toString()
        tv_temp_min.text = it.daily?.get(0)?.temp?.min?.roundToInt()?.toString()
        tv_feels_like.text = it.current?.feels_like?.roundToInt()?.toString()
        tv_weather = it.current?.weather?.get(0)?.description
        sunrise.text = it.current?.sunrise.format("hh:mm")
        sunset.text = it.current?.sunset.format("hh:mm")
        humidity.text = "${it.current?.humidity?.toString()} %"
    }

    private fun loadWeatherIcon(it: ForeCast) {
        val iv_weather_icon = findViewById<ImageView>(R.id.iv_weather_icon)
        it.current?.weather?.get(0)?.icon?.let { icon ->
            Glide.with(this)
                .load("${Constants.iconUri}${icon}${Constants.iconFormat}")
                .into(iv_weather_icon)
        }
    }
}




