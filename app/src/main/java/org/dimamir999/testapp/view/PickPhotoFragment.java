package org.dimamir999.testapp.view;

import android.app.Fragment;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import org.dimamir999.testapp.R;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by dimamir999 on 19.01.16.
 */
public class PickPhotoFragment extends Fragment {

    private final static int PICK_PHOTO_REQUEST = 1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_photo_picker, container, false);
        Button fromGalleryButton = (Button) view.findViewById(R.id.from_gallery_button);
        fromGalleryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.v("dimamir999", "handle click on from gallery button");
                loadPhotoFromGallery();
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
        Bitmap photo = null;
        InputStream stream = null;
        photo = BitmapFactory.decodeStream(stream);
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

    //improve this method
    public Bitmap scalePhoto(Bitmap src){
        return Bitmap.createScaledBitmap(src, 512, 512, true);
    }

    public void setPhotoToView(Bitmap photo){
        ImageView imageView = (ImageView) getActivity().findViewById(R.id.photoView);
        imageView.setImageBitmap(photo);
    }
}
