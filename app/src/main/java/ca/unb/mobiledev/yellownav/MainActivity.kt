package ca.unb.mobiledev.yellownav

import android.os.Bundle
import android.util.Log
import android.widget.Switch
import android.widget.Toast
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import ca.unb.mobiledev.yellownav.databinding.ActivityMainBinding
import com.google.android.gms.maps.GoogleMap


class MainActivity : AppCompatActivity(){
    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMainBinding
    var amActive: Boolean = false
    var pmActive: Boolean = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_map)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val alarmUtils = AlarmUtils(this)
        alarmUtils.initRepeatingAlarm()

        val switchAm = findViewById<Switch>(R.id.morning_switch)
        val switchPm = findViewById<Switch>(R.id.afternoon_switch)


        Log.e("pmActiveTest1", pmActive.toString())

        switchAm?.setOnCheckedChangeListener { _, isChecked ->
            val message = if (isChecked) "ON" else "OFF"
            Toast.makeText(
                this@MainActivity, message,
                Toast.LENGTH_SHORT
            ).show()
            amActive = isChecked
        }

        switchPm?.setOnCheckedChangeListener { _, isChecked ->
            Log.e("pmActiveTest", pmActive.toString())
            val message = if (isChecked) "ON" else "OFF"
            Toast.makeText(
                this@MainActivity, message,
                Toast.LENGTH_SHORT
            ).show()
            pmActive = isChecked

        }


        val navView: BottomNavigationView = binding.navView

        val navController = findNavController(R.id.nav_host_fragment_activity_main)

        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_map, R.id.navigation_home, R.id.navigation_feed
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }


}
