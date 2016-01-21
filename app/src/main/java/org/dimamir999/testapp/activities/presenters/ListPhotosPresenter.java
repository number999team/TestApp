package org.dimamir999.testapp.activities.presenters;

import android.database.Cursor;
import android.graphics.Bitmap;
import android.text.format.DateUtils;
import android.text.format.Time;
import android.widget.Adapter;
import android.widget.ListAdapter;
import android.widget.SimpleAdapter;
import android.widget.SimpleCursorAdapter;

import org.dimamir999.testapp.R;
import org.dimamir999.testapp.activities.views.ListPhotoView;
import org.dimamir999.testapp.db.DBHelper;
import org.dimamir999.testapp.db.PhotoWithGeoTagDAO;
import org.dimamir999.testapp.model.PhotoWithGeoTag;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Created by dimamir999 on 21.01.16.
 */
public class ListPhotosPresenter {

    private static final String ATTRIBUTE_NAME_TEXT = "text";
    private static final String ATTRIBUTE_NAME_IMAGE = "image";

    private ListPhotoView view;
    private PhotoWithGeoTagDAO photoWithGeoTagDAO;
    private Cursor cursor;
    private SimpleCursorAdapter adapter;

    public ListPhotosPresenter(ListPhotoView view) {
        this.view = view;
        photoWithGeoTagDAO = new PhotoWithGeoTagDAO(view.getContextActivity());
    }

    public ListAdapter createListViewAdapter(){
        // get date current date(start of the day and end of the day)


        Time time = new Time(Time.getCurrentTimezone());
        time.setToNow();
        Time startTime = new Time();
        startTime.set(time.monthDay, time.month, time.year);
        Time endTime = new Time();
        time.set(time.toMillis(true) + TimeUnit.DAYS.toMillis(1));
        endTime.set(time.monthDay, time.month, time.year);

        Date startDate = new Date(startTime.toMillis(true));
        Date endDate = new Date(endTime.toMillis(true));

        ArrayList<PhotoWithGeoTag> photosList = photoWithGeoTagDAO.getBetweenDates(startDate, endDate);

        Bitmap[] photos = new Bitmap[photosList.size()];
        String[] dates = new String[photosList.size()];

        for(int i = 0;i < photosList.size();i++){
            PhotoWithGeoTag photoObject = photosList.get(i);
            photos[i] = photoObject.getPhoto();
            dates[i] = photoObject.getDate().toString();
        }

        ArrayList<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
        Map<String, Object> m;
        for (int i = 0; i < dates.length; i++) {
            m = new HashMap<String, Object>();
            m.put(ATTRIBUTE_NAME_TEXT, dates[i]);
            m.put(ATTRIBUTE_NAME_IMAGE, photos);
            data.add(m);
        }
        String[] from = { ATTRIBUTE_NAME_TEXT,
                ATTRIBUTE_NAME_IMAGE };
        int[] to = { R.id.date_text_view,  R.id.photo_item_view};
        SimpleAdapter adapter = new SimpleAdapter(view.getContextActivity(), data, R.layout.photo_list_item,
                from, to);
        return adapter;
    }

}
