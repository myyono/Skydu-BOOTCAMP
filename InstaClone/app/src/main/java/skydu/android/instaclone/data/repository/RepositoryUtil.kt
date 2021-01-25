package skydu.android.instaclone.data.repository

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import retrofit2.Response
import skydu.android.instaclone.R
import skydu.android.instaclone.data.remote.common.ApiResponse
import skydu.android.instaclone.data.repository.model.DataResult
import skydu.android.instaclone.data.remote.common.ErrorResponse
import skydu.android.instaclone.utils.ResourceUtil
import java.net.UnknownHostException
import javax.net.ssl.HttpsURLConnection


fun <T> Response<ApiResponse<T>>.convertToDataResult(): DataResult<T> {
    try {
        if (isSuccessful) {
            val body = body()
            if (body != null) return DataResult(DataResult.State.SUCCESS, body.data, null)
        } else {
            when (this.code()) {
                HttpsURLConnection.HTTP_UNAUTHORIZED -> {
                    return DataResult(DataResult.State.UNAUTHORIZED, null, ResourceUtil.getString(R.string.server_connection_error))
                }
                HttpsURLConnection.HTTP_UNAVAILABLE ->
                    return DataResult(DataResult.State.ERROR, null, ResourceUtil.getString(R.string.network_server_not_available))
                422 -> {
                    val gson = Gson()
                    val type = object : TypeToken<ErrorResponse>() {}.type
                    val errorResponse: ErrorResponse = gson.fromJson(errorBody()?.charStream(), type)
                    return DataResult(DataResult.State.ERROR, null, errorResponse.message)
                }
                else -> {
                    return DataResult(DataResult.State.ERROR, null, ResourceUtil.getString(R.string.network_internal_error))
                }
            }
        }
        return DataResult(DataResult.State.ERROR, null, "")
    } catch (e: Exception) {
        return e.convertExceptionToError()
    }
}

fun <T> Exception.convertExceptionToError(): DataResult<T> {
    return if(this is UnknownHostException) {
        DataResult(
            DataResult.State.ERROR,
            null,
            ResourceUtil.getString(R.string.network_connection_error)
        )
    } else {
        DataResult(
            DataResult.State.ERROR,
            null,
            ResourceUtil.getString(R.string.network_default_error)
        )
    }
}


