package org.dimamir999.testapp.activities.views;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import org.dimamir999.testapp.R;
import org.dimamir999.testapp.activities.presenters.ListPhotosPresenter;
import org.dimamir999.testapp.model.PhotoWithGeoTag;
import org.dimamir999.testapp.services.LocationControlService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ListPhotosActivity extends Activity implements ListPhotoView{

    private static final int DELETE_ITEM_ID = 1;
    private static final String ATTRIBUTE_NAME_TEXT = "text";
    private static final String ATTRIBUTE_NAME_IMAGE = "image";

    private ListPhotosPresenter presenter;
    private ListView photosListView;
    private ArrayList<Map<String, Object>> data;
    private SimpleAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_photos);
        presenter = new ListPhotosPresenter(this);
        photosListView = (ListView) findViewById(R.id.photos_list);

        ArrayList<PhotoWithGeoTag> photosList = presenter.getListData();
        data = new ArrayList<Map<String, Object>>();

        for (int i = 0; i < photosList.size(); i++) {
            Map<String, Object> itemMap = new HashMap<String, Object>();
            PhotoWithGeoTag photoObject = photosList.get(i);
            itemMap.put(ATTRIBUTE_NAME_TEXT, photoObject.getDate().toString());
            itemMap.put(ATTRIBUTE_NAME_IMAGE, photoObject.getPath());
            data.add(itemMap);
        }
        String[] from = { ATTRIBUTE_NAME_TEXT,
                ATTRIBUTE_NAME_IMAGE };
        int[] to = { R.id.date_text_view,  R.id.photo_item_view};
        adapter = new SimpleAdapter(this, data, R.layout.photo_list_item,
                from, to);
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
        if(presenter.isServiceRunning(LocationControlService.class)) {
            stopService(new Intent(this, LocationControlService.class));
            ((TextView) view).setText("Start scan my location");
        } else {
            startService(new Intent(this, LocationControlService.class));
            ((TextView) view).setText("Stop scan my location");
        }
    }


}
