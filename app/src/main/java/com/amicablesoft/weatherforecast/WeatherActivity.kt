package com.amicablesoft.weatherforecast

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.util.Log
import android.view.MenuItem
import com.amicablesoft.weatherforecast.model.Forecast
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

/**
 * Created by olha on 3/21/17.
 */
class WeatherActivity : AppCompatActivity(), WeatherView {

    private lateinit var adapter: WeatherAdapter
    private lateinit var presenter: WeatherPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_weather)

        val toolbar = findViewById(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)
        val city = intent.getStringExtra("city")
        supportActionBar?.title = city

        val gridLayoutManager = GridLayoutManager(this, 2)
        val recyclerView = findViewById(R.id.recycler_view) as RecyclerView
        recyclerView.layoutManager = gridLayoutManager as RecyclerView.LayoutManager?
        adapter = WeatherAdapter(this)
        recyclerView.adapter = adapter

        val latitude = intent.getDoubleExtra("latitude", 0.0)
        val longitude = intent.getDoubleExtra("longitude", 0.0)

        presenter = WeatherPresenter(this, latitude, longitude)
        presenter.onCreate()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.home -> {
                finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun show(forecasts: List<Forecast>) {
        adapter.setForecasts(forecasts)
    }

    override fun showError(error: Throwable) {
        Snackbar.make(findViewById(R.id.activity_weather), R.string.error_message, Snackbar.LENGTH_LONG).show()
    }


}

interface WeatherView {
    fun show(forecasts: List<Forecast>)
    fun showError(error: Throwable)
}

class WeatherPresenter(val view: WeatherView, val latitude: Double, val longitude: Double){

    val repository:ForecastRepository = ForecastRepository()

    fun onCreate() {
        repository.fetchForecastFor(latitude, longitude)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ forecasts ->
                    view.show(forecasts)
                }, { error ->
                    Log.e("TAG", "Fail to load forecasts", error)
                    view.showError(error)
                })
    }
}
