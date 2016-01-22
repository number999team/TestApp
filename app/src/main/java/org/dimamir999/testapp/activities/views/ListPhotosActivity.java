package org.dimamir999.testapp.activities.views;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import org.dimamir999.testapp.R;
import org.dimamir999.testapp.activities.presenters.ListPhotosPresenter;
import org.dimamir999.testapp.model.PhotoWithGeoTag;
import org.dimamir999.testapp.services.LocationControlService;
import org.dimamir999.testapp.services.PhotoScaler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ListPhotosActivity extends Activity implements ListPhotoView{

    private static final int DELETE_ITEM_ID = 1;
    private static final String ATTRIBUTE_NAME_TEXT = "text";
    private static final String ATTRIBUTE_NAME_IMAGE = "image";
    private static final int LIST_PHOTO_HEIGHT = 250;
    private static final int LIST_PHOTO_WIDTH = 250;

    private ListPhotosPresenter presenter;
    private ListView photosListView;
    private ArrayList<Map<String, Object>> data;
    private SimpleAdapter adapter;
    private PhotoScaler photoScaler = new PhotoScaler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_photos);
        presenter = new ListPhotosPresenter(this);
        if(LocationControlService.isRunning) {
            stopService(new Intent(this, LocationControlService.class));
            ((TextView) findViewById(R.id.turn_service_button)).setText("Start scan my location");
        } else {
            startService(new Intent(this, LocationControlService.class));
            ((TextView) findViewById(R.id.turn_service_button)).setText("Stop scan my location");
        }
        photosListView = (ListView) findViewById(R.id.photos_list);

        ArrayList<PhotoWithGeoTag> photosList = presenter.getListData();
        data = new ArrayList<Map<String, Object>>();

        for (int i = 0; i < photosList.size(); i++) {
            Map<String, Object> itemMap = new HashMap<String, Object>();
            PhotoWithGeoTag photoObject = photosList.get(i);
            itemMap.put(ATTRIBUTE_NAME_TEXT, photoObject.getDate().toString());
            Bitmap photo = photoObject.getPhoto();
            if(photo.getHeight() != LIST_PHOTO_HEIGHT || photo.getWidth() != LIST_PHOTO_WIDTH) {
                photo = photoScaler.scaleForList(photo, LIST_PHOTO_HEIGHT, LIST_PHOTO_WIDTH);
            }
            itemMap.put(ATTRIBUTE_NAME_IMAGE, photo);
            data.add(itemMap);
        }
        String[] from = { ATTRIBUTE_NAME_TEXT,
                ATTRIBUTE_NAME_IMAGE };
        int[] to = { R.id.date_text_view,  R.id.photo_item_view};
        adapter = new SimpleAdapter(this, data, R.layout.photo_list_item,
                from, to);
        adapter.setViewBinder(new ScalePhotoBinder());
        photosListView.setAdapter(adapter);
        registerForContextMenu(photosListView);
    }

    public void toPickPhotoActivity(View view){
        Intent intent = new Intent(this, PhotoPickActivity.class);
        startActivity(intent);
    }

    public void toMapActivity(View view){
        Intent intent = new Intent(this, MapActivity.class);
        startActivity(intent);
    }

    public Activity getContextActivity(){
        return this;
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.add(0, DELETE_ITEM_ID, 0, "Удалить запись");
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        if (item.getItemId() == DELETE_ITEM_ID) {
            AdapterView.AdapterContextMenuInfo menuInfo = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
            data.remove(menuInfo.position);
            presenter.deletePhoto(menuInfo.position);
            adapter.notifyDataSetChanged();
            return true;
        }
        return super.onContextItemSelected(item);
    }

    public void changeGeoLocationServiceStatus(View view){
        if(LocationControlService.isRunning) {
            stopService(new Intent(this, LocationControlService.class));
            ((TextView) view).setText("Start scan my location");
        } else {
            startService(new Intent(this, LocationControlService.class));
            ((TextView) view).setText("Stop scan my location");
        }
    }

    private class ScalePhotoBinder implements SimpleAdapter.ViewBinder {

        @Override
        public boolean setViewValue(View view, Object data,
                                    String textRepresentation) {
            int i = 0;
            if (view.getId() == R.id.photo_item_view) {
                Log.v("dimamir999", "photo loaded");
                ((ImageView) view).setImageBitmap((Bitmap) data);
                return true;
            }
            return false;
        }
    }
}
