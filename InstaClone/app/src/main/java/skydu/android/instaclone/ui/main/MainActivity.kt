package skydu.android.instaclone.ui.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import skydu.android.instaclone.R
import skydu.android.instaclone.ui.home.HomeActivity
import skydu.android.instaclone.ui.login.LoginActivity

class MainActivity : AppCompatActivity() {

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.navigationPage.observe(this) {
            if(it == MainViewModel.NavigationPage.LOGIN) {
                Intent(this, LoginActivity::class.java).run {
                    startActivity(this)
                }
            } else {
                Intent(this, HomeActivity::class.java).run {
                    startActivity(this)
                }
            }
            finish()
        }
    }
}