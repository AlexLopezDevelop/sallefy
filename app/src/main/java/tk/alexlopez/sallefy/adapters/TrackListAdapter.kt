
package tk.alexlopez.sallefy.adapters
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import tk.alexlopez.sallefy.R
import tk.alexlopez.sallefy.activities.TrackOptionsActivity
import tk.alexlopez.sallefy.models.Track
import java.util.*

class TrackListAdapter(private val mContext: Context, private val mTracks: ArrayList<Track>?) : RecyclerView.Adapter<TrackListAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        Log.d(TAG, "onCreateViewHolder: called.")
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.playlist_item, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val track = mTracks!![position]
        holder.tvTitle.text = track.name
        holder.tvAuthor.text = track.userLogin
        if (track.thumbnail != null) {
            Glide.with(mContext)
                    .asBitmap() //.placeholder(R.drawable.ic_audiotrack)
                    .load(track.thumbnail)
                    .into(holder.ivPicture)
        }
        holder.ibOptions.setOnClickListener {
            val intent = Intent(mContext.applicationContext, TrackOptionsActivity::class.java)
            //
            val bundle = Bundle()
            bundle.putSerializable("trackData", track)
            intent.putExtras(bundle)
            mContext.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return mTracks?.size ?: 0
    }

    fun updateTrackLikeStateIcon(position: Int, isLiked: Boolean) {
        mTracks!![position].isLiked = isLiked
        notifyDataSetChanged()
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tvTitle: TextView = itemView.findViewById<TextView>(R.id.track_title)
        var tvAuthor: TextView = itemView.findViewById(R.id.track_author)
        var ivPicture: ImageView = itemView.findViewById(R.id.track_img)
        var ibOptions: ImageButton = itemView.findViewById(R.id.track_options)

    }

    companion object {
        private const val TAG = "TrackListAdapter"
    }

}