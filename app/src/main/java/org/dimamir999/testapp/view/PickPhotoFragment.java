package org.dimamir999.testapp.view;

import android.app.Fragment;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.dimamir999.testapp.R;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.concurrent.ExecutionException;

/**
 * Created by dimamir999 on 19.01.16.
 */
public class PickPhotoFragment extends Fragment {

    private final static int PICK_PHOTO_REQUEST = 1;
    private final static int MAX_PIXEL_LENGTH = 2048;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_photo_picker, container, false);
        Button fromGalleryButton = (Button) view.findViewById(R.id.from_gallery_button);
        Button fromURLButton = (Button) view.findViewById(R.id.from_url_button);
        final TextView urlField = (TextView) view.findViewById(R.id.url_field);
        fromGalleryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.v("dimamir999", "handle click on from gallery button");
                loadPhotoFromGallery();
            }
        });
        fromURLButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.v("dimamir999", "handle click on from URL button");
                loadPhotoFromURL(urlField.getText().toString());
            }
        });

        return view;
    }


    public void loadPhotoFromGallery() {
        Intent pickPhotoIntent = new Intent(Intent.ACTION_PICK);
        pickPhotoIntent.setType("image/*");
        startActivityForResult(pickPhotoIntent, PICK_PHOTO_REQUEST);
    }

    public void loadPhotoFromURL(String url) {
        ImageLoader imageLoader = new ImageLoader();
        imageLoader.execute(url);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Bitmap photo = null;
        if (requestCode == PICK_PHOTO_REQUEST && resultCode == getActivity().RESULT_OK) {
            Uri selectedPhoto = data.getData();
            try {
                // sometimes out of memory exeption maybe make some limit for size of photo
                photo = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), selectedPhoto);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (resultCode == getActivity().RESULT_CANCELED) {
            // Operation failed or cancelled. Handle in your own way.
        }
        else {
            //scale because open gl cant load photo more than 4096 px
            photo = scalePhoto(photo);
            setPhotoToView(photo);
        }
    }

    public Bitmap scalePhoto(Bitmap src){
        int height = src.getHeight(), width = src.getWidth();
        int max = Math.max(height, width);
        if(max > MAX_PIXEL_LENGTH){
            double coef = (double)MAX_PIXEL_LENGTH / max;
            return Bitmap.createScaledBitmap(src, (int)(width * coef), (int)(height * coef), true);
        }else {
            return src;
        }
    }

    public void setPhotoToView(Bitmap photo){
        ImageView imageView = (ImageView) getActivity().findViewById(R.id.photoView);
        imageView.setImageBitmap(photo);
    }

    public class ImageLoader extends AsyncTask<String, Void, Bitmap> {

        private static final int OK = 0;
        private static final int BIG_FILE = 1;
        private static final int INCORRECT_FORMAT = 2;
        private static final int MAX_SIZE = 20 * 1024;

        private int answerCode;

        private ImageView photoView = (ImageView) getActivity().findViewById(R.id.photoView);
        private ProgressBar uploadProgressBar = (ProgressBar) getActivity().findViewById(R.id.upload_progress_bar);

        @Override
        protected void onPreExecute() {
            uploadProgressBar.setVisibility(ProgressBar.VISIBLE);
        }

        @Override
        protected Bitmap doInBackground(String... urls) {
            try {
                URL url = new URL(urls[0]);
                URLConnection connection = url.openConnection();
                if(connection.getContentLength() > MAX_SIZE){
                    answerCode = BIG_FILE;
                    return null;
                } else {
                    Bitmap photo = BitmapFactory.decodeStream(connection.getInputStream());
                    if(photo == null)
                        answerCode = INCORRECT_FORMAT;
                    else {
                        answerCode = OK;
                    }
                    return photo;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Bitmap result) {
            uploadProgressBar.setVisibility(ProgressBar.INVISIBLE);
            switch (answerCode){
                case OK:
                    result = scalePhoto(result);
                    photoView.setImageBitmap(result);
                    break;
                case BIG_FILE:
                    Toast.makeText(getActivity(), "File size more than max size " + MAX_SIZE + "bytes",
                            Toast.LENGTH_LONG).show();
                    break;
                case INCORRECT_FORMAT:
                    Toast.makeText(getActivity(), "Incorrect file format", Toast.LENGTH_LONG).show();
                    break;
            }
        }

    }
}
