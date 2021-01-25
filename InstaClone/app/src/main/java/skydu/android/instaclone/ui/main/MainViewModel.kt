package skydu.android.instaclone.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import skydu.android.instaclone.data.repository.UserRepository

class MainViewModel : ViewModel() {
    private val userRepository = UserRepository()

    val navigationPage: LiveData<NavigationPage> = userRepository.isUserLoggedIn().map {
        if(it) {
            NavigationPage.HOME
        } else {
            NavigationPage.LOGIN
        }
    }

    enum class NavigationPage {
        LOGIN,
        HOME
    }
}