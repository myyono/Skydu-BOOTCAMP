package skydu.android.instaclone.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import skydu.android.instaclone.data.remote.NetworkService
import skydu.android.instaclone.data.remote.Networking
import skydu.android.instaclone.data.repository.model.DataResult
import skydu.android.instaclone.data.repository.model.Profile

class ProfileRepository {
    private val networkService: NetworkService = Networking.service

    fun fetchProfile(username: String): LiveData<DataResult<Profile>> =
        liveData {
            emit(DataResult<Profile>(DataResult.State.LOADING, null, null))

            val result =
                try {
                    networkService.getProfile(username).convertToDataResult()
                } catch (e: Exception) {
                    e.convertExceptionToError()
                }

            if (result.state == DataResult.State.SUCCESS && result.data != null) {
                Profile(
                    result.data.username,
                    result.data.name,
                    result.data.email,
                    result.data.user_image_url ?: "",
                    result.data.posts.map {
                        Profile.Post(it.id, it.image_url)
                    }
                ).run {
                    emit(DataResult(DataResult.State.SUCCESS, this, null))
                }
            } else {
                emit(DataResult<Profile>(result.state, null, result.errorMessage))
            }
        }
}