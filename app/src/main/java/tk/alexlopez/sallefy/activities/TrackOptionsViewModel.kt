package tk.alexlopez.sallefy.activities

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import tk.alexlopez.sallefy.R
import tk.alexlopez.sallefy.network.manager.TrackManager
import java.lang.Exception

class TrackOptionsViewModel : ViewModel() {

    val trackId = MutableLiveData<Int>()
    val userLikeTrack = MutableLiveData<Boolean>()
    val errorMessage = MutableLiveData<Int>()
    lateinit var trackManager: TrackManager

    fun load(trackManager: TrackManager, idTrack: Int) {
        this.trackManager = trackManager

        trackManager.getTrackLike(idTrack)
                .subscribe({
                    userLikeTrack.value = it.liked.toBoolean()
                }, {

                    Log.e("TrackOptionsViewModel", "unexpected Alex error ${it.message}")
                })
    }

    fun likeTrack() {
        trackManager.userLikeTrack(8)
                .subscribe({
                    Log.d("TrackOptionsViewModel", " Modify liked ${it.liked}")
                    userLikeTrack.value = it.liked.toBoolean()
                }, {

                })
    }

}