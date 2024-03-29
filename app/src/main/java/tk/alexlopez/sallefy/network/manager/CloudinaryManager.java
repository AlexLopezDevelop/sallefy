package tk.alexlopez.sallefy.network.manager;

import android.content.Context;
import android.net.Uri;

import androidx.appcompat.app.AppCompatActivity;

import com.cloudinary.android.MediaManager;
import com.cloudinary.android.callback.ErrorInfo;
import com.cloudinary.android.callback.UploadCallback;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import tk.alexlopez.sallefy.fragments.UploadFragment;
import tk.alexlopez.sallefy.models.Genre;
import tk.alexlopez.sallefy.models.Track;
import tk.alexlopez.sallefy.network.callback.TrackCallback;
import tk.alexlopez.sallefy.utils.CloudinaryParam;

public class CloudinaryManager extends AppCompatActivity {

    private static CloudinaryManager sManager;
    private Context mContext;
    private String mFileName;
    private Genre mGenre;
    private TrackCallback mCallback;

    public static CloudinaryManager getInstance(UploadFragment context, TrackCallback callback) {
        if (sManager == null) {
            sManager = new CloudinaryManager(context.getActivity(), callback);
        }
        return sManager;
    }

    public CloudinaryManager(Context context, TrackCallback callback) {
        mContext = context;
        mCallback = callback;
        MediaManager.init(mContext, CloudinaryParam.getConfigurations());
    }

    public synchronized void uploadAudioFile(Uri fileUri,Uri fileIMG, String fileName, Genre genre) {
        mGenre = genre;
        mFileName = fileName;
        Map<String, Object> options = new HashMap<>();
        options.put("public_id", fileName);
        options.put("folder", "sallefy/songs/mobile");
        options.put("resource_type", "video");

        MediaManager.get().upload(fileUri)
                .unsigned(fileName)
                .options(options)
                .callback(new CloudinaryCallback())
                .dispatch();

        Map<String, Object> optionsIMG = new HashMap<>();
        optionsIMG.put("public_id", fileName+"_img");
        optionsIMG.put("folder", "sallefy/img/mobile");
        optionsIMG.put("resource_type", "image");

        MediaManager.get().upload(fileIMG)
                .unsigned(fileName+"_img")
                .options(optionsIMG)
                .callback(new CloudinaryCallback())
                .dispatch();
        }

    private class CloudinaryCallback implements UploadCallback {

        @Override
        public void onStart(String requestId) {
        }
        @Override
        public void onProgress(String requestId, long bytes, long totalBytes) {
            Double progress = (double) bytes/totalBytes;
            //System.out.println(progress);
        }
        @Override
        public void onSuccess(String requestId, Map resultData) {
            Track track = new Track();
            track.setId(null);
            track.setName(mFileName);
            track.setUrl((String) resultData.get("url"));
            track.setThumbnail((String) resultData.get("url_img"));
            ArrayList<Genre> genres = new ArrayList<>();
            genres.add(mGenre);
            track.setGenres(genres);
            TrackManager.getInstance(mContext).createTrack(track, mCallback);
        }
        @Override
        public void onError(String requestId, ErrorInfo error) {
        }
        @Override
        public void onReschedule(String requestId, ErrorInfo error) {}
    }


}
