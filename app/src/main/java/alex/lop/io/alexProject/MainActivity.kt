package alex.lop.io.alexProject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import alex.lop.io.alexProject.databinding.ActivityMainBinding
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController

@Suppress("DEPRECATION")
class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding
    private lateinit var navHostFragment : NavHostFragment

    override fun onCreate(savedInstanceState : Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initViews(binding)
    }

    private fun initViews(binding : ActivityMainBinding) {
        navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragment_container_view) as NavHostFragment
        val navController = navHostFragment.navController

        binding.bottomNavigation.apply {
            setupWithNavController(navController)
            setOnNavigationItemReselectedListener {

            }
        }
    }
}