package skydu.android.instaclone.ui.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.switchMap
import skydu.android.instaclone.data.repository.ProfileRepository
import skydu.android.instaclone.data.repository.model.DataResult
import skydu.android.instaclone.data.repository.model.Profile
import skydu.android.instaclone.ui.base.BaseViewModel

class ProfileViewModel(val username: String) : BaseViewModel() {
    private val profileRepository = ProfileRepository()

    val profile: LiveData<DataResult<Profile>> =
        profileRepository.fetchProfile(username).switchMap {
            liveData {
                if(it.state == DataResult.State.UNAUTHORIZED) {
                    triggerLogout()
                } else {
                    emit(it)
                }
            }
        }
}