package alex.lop.io.alexProject.activity

import alex.lop.io.alexProject.R
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import alex.lop.io.alexProject.databinding.ActivityMainBinding
import alex.lop.io.alexProject.util.setGone
import alex.lop.io.alexProject.util.setVisible
import android.view.View
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



    private fun setupToolbar(textVisible : Boolean, title : String = "") =
        with(binding) {
            toolbar.back.setOnClickListener {
                onBackPressed()
            }
            val toolbarInclude : View = findViewById(R.id.toolbar)
            if (textVisible) {
                toolbarInclude.setVisible()
                toolbar.text.setVisible()
                toolbar.text.text = title
                toolbar.back.setVisible()
            } else {
                toolbarInclude.setGone()
            }
        }

    private fun setupToolbar() {
        navHostFragment.navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.homeFragment -> setupToolbar(
                    false
                )

                R.id.comicsFragment -> setupToolbar(
                    true,
                    getString(R.string.title_fragment_comic)
                )

                R.id.favoriteCharacterFragment -> setupToolbar(
                    true,
                    getString(R.string.title_fragment_favorites)
                )

                R.id.timelineFragment -> setupToolbar(
                    true,
                    getString(R.string.feed)
                )

                R.id.eventFragment -> setupToolbar(
                    true,
                    getString(R.string.title_fragment_event)
                )

                R.id.detailsCharacterFragment -> setupToolbar(
                    true,
                    getString(R.string.title_fragment_detail)
                )

                R.id.CharacterFragment -> setupToolbar(
                    true,
                    getString(R.string.title_fragment_characters)
                )

                R.id.detailsComicFragment -> setupToolbar(
                    true,
                    getString(R.string.title_fragment_detail)
                )

                R.id.detailsEventFragment -> setupToolbar(
                    true,
                    getString(R.string.title_fragment_event)
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