package tk.alexlopez.sallefy.access

import android.view.View
import android.widget.VideoView
import androidx.databinding.BindingAdapter
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel

class LoginViewModel : ViewModel() {

    val username = MutableLiveData<String>()
    val password = MutableLiveData<String>()
    val path = MutableLiveData<String>()

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
                    // TODO: we need do the call api
                }
            }
        }
    }

    fun isVideoVisible(): Int {
        path.value?.let {
            return if (it.isNotEmpty()) View.VISIBLE else View.GONE
        }

        return View.GONE
    }

}