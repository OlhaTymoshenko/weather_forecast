package com.amicablesoft.weatherforecast.ui

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.amicablesoft.weatherforecast.R
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.common.api.Status
import com.google.android.gms.location.places.Place
import com.google.android.gms.location.places.Places
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment
import com.google.android.gms.location.places.ui.PlaceSelectionListener

/**
 * Created by olha on 3/22/17.
 */
class MainActivity : AppCompatActivity(), GoogleApiClient.OnConnectionFailedListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val googleApiClient = GoogleApiClient.Builder(this)
                .addApi(Places.GEO_DATA_API)
                .enableAutoManage(this, this)
                .build()
        val autocompleteFragment = fragmentManager.findFragmentById(R.id.place_autocomplete_fragment) as PlaceAutocompleteFragment

        autocompleteFragment.setOnPlaceSelectedListener(object : PlaceSelectionListener {
            override fun onPlaceSelected(place: Place) {
                val intent = Intent(this@MainActivity, WeatherActivity::class.java)
                intent.putExtra("latitude", place.latLng.latitude)
                intent.putExtra("longitude", place.latLng.longitude)
                intent.putExtra("city", place.name.toString())
                startActivity(intent)
                Log.i("TAG", "Place: " + place.name)
            }

            override fun onError(status: Status) {
                Snackbar.make(findViewById(R.id.activity_main), R.string.error_load_places, Snackbar.LENGTH_LONG).show()
                Log.i("TAG", "An error occurred: " + status)
            }
        })
    }

    override fun onConnectionFailed(connectionResult: ConnectionResult) {
        Snackbar.make(findViewById(R.id.activity_main), R.string.error_connection, Snackbar.LENGTH_LONG).show()
    }
}
