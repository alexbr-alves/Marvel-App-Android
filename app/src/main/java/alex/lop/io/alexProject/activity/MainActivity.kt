package alex.lop.io.alexProject.activity

import alex.lop.io.alexProject.R
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import alex.lop.io.alexProject.databinding.ActivityMainBinding
import alex.lop.io.alexProject.util.hide
import alex.lop.io.alexProject.util.show
import android.os.Build
import android.text.Layout
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding
    private lateinit var navHostFragment : NavHostFragment

    override fun onCreate(savedInstanceState : Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initViews(binding)
        setupToolbar()
        supportActionBar?.hide()
        binding.toolbar.back.setOnClickListener { onBackPressed() }
    }

    private fun setupTitleToolbar(textVisible : Boolean, title : String = "") =
        with(binding.toolbar) {
            if (textVisible) {
                text.show()
                image.hide()
                text.text = title
                back.show()
            } else {
                text.hide()
                image.show()
                back.hide()
            }
        }

    override fun onBackPressed() {
        super.onBackPressed()
    }

    private fun setupToolbar() {
        navHostFragment.navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.homeFragment -> setupTitleToolbar(
                    false)
                R.id.comicsFragment -> setupTitleToolbar(
                    true,
                    getString(R.string.title_fragment_comic)
                )

                R.id.favoriteCharacterFragment -> setupTitleToolbar(
                    true,
                    getString(R.string.title_fragment_favorites)
                )

                R.id.searchCharacterFragment -> setupTitleToolbar(
                    true,
                    getString(R.string.title_fragment_search)
                )

                R.id.eventFragment -> setupTitleToolbar(
                    true,
                    getString(R.string.title_fragment_event)
                )

                R.id.creatorFragment -> setupTitleToolbar(
                    true,
                   getString(R.string.title_fragment_creator)
                )

                R.id.detailsCharacterFragment -> setupTitleToolbar(
                    true,
                    getString(R.string.title_fragment_detail)
                )
                R.id.ListCharacterFragment -> setupTitleToolbar(
                    true,
                    getString(R.string.title_fragment_characters)
                )
            }
        }
    }

    private fun initViews(binding : ActivityMainBinding) {
        navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragment_container_view) as NavHostFragment
        val navController = navHostFragment.navController

        binding.bottomNavigation.apply {
            setupWithNavController(navController)

        }
    }


}