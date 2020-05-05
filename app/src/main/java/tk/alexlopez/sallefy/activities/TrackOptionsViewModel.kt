package tk.alexlopez.sallefy.activities

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import tk.alexlopez.sallefy.network.manager.TrackManager

class TrackOptionsViewModel : ViewModel() {

    val trackId = MutableLiveData<Int>()
    val userLikeTrack = MutableLiveData<Boolean>()

    lateinit var trackManager: TrackManager

    fun likeTrack() {
        trackManager.userLikeTrack(8)
                .subscribe({
                    Log.d("TEST", "msg ${it.liked}")
                }, {
                    it.let { e ->
                        Log.d("TEST", e.message)
                    }
                })
    }
}