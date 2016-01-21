package org.dimamir999.testapp.activities.views;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import org.dimamir999.testapp.R;
import org.dimamir999.testapp.activities.presenters.ListPhotosPresenter;

public class ListPhotosActivity extends Activity implements ListPhotoView{

    private ListPhotosPresenter presenter;
    private ListView photosList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_photos);
        presenter = new ListPhotosPresenter(this);
        photosList = (ListView) findViewById(R.id.photos_list);

        photosList.setAdapter(presenter.createListViewAdapter());
        registerForContextMenu(photosList);
    }

    public Activity getContextActivity(){
        return this;
    }

}
