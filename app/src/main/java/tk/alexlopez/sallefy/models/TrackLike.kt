package tk.alexlopez.sallefy.models

import com.google.gson.annotations.SerializedName
import java.io.Serializable

class TrackLike : Serializable {
    @SerializedName("liked")
    lateinit var liked: String

}