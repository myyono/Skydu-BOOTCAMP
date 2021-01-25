package skydu.android.instaclone.ui.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import skydu.android.instaclone.data.repository.UserRepository

open class BaseViewModel: ViewModel() {
    protected val userRepository: UserRepository = UserRepository()

    protected val triggerLogOut = MutableLiveData<Unit>()

    val loggedOutEvent = triggerLogOut.switchMap {
        userRepository.doLogout()
    }

    fun triggerLogout() {
        triggerLogOut.value = Unit
    }
}