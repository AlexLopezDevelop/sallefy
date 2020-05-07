package tk.alexlopez.sallefy.activities.access

import android.util.Log
import android.view.View
import android.widget.VideoView
import androidx.databinding.BindingAdapter
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import tk.alexlopez.sallefy.models.UserToken
import tk.alexlopez.sallefy.network.manager.UserManager

class LoginViewModel : ViewModel() {

    val username = MutableLiveData<String>()
    val password = MutableLiveData<String>()
    val path = MutableLiveData<String>()
    val userToken = MutableLiveData<UserToken>()
    val doSignUp = MutableLiveData<Boolean>()

    lateinit var userManager: UserManager

    companion object {

        @JvmStatic
        @BindingAdapter("path")
        fun setVideo(view: VideoView, path: String?) {
            path?.let {
                if (it.isNotEmpty()) {
                    view.setVideoPath(path)
                    view.start()
                }
            }
        }
    }


    fun doLogin() {
        username.value?.let { user ->
            password.value?.let { pwd ->
                if (user.isNotEmpty() && pwd.isNotEmpty()) {
                    userManager.loginAttempt(user, pwd)
                            .subscribe({
                                userToken.value = it
                            }, {
                                Log.e("LoginViewModel", "${it.message}")
                            })
                }
            }
        }
    }

    fun goToSignUp() {
        doSignUp.postValue(true)
    }

    fun isVideoVisible(): Int {
        path.value?.let {
            return if (it.isNotEmpty()) View.VISIBLE else View.GONE
        }

        return View.GONE
    }

}