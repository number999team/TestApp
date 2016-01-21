package org.dimamir999.testapp.activities.presenters;

import android.database.Cursor;
import android.text.format.DateUtils;
import android.text.format.Time;
import android.widget.Adapter;
import android.widget.ListAdapter;
import android.widget.SimpleCursorAdapter;

import org.dimamir999.testapp.R;
import org.dimamir999.testapp.activities.views.ListPhotoView;
import org.dimamir999.testapp.db.DBHelper;
import org.dimamir999.testapp.db.PhotoWithGeoTagDAO;
import org.dimamir999.testapp.model.PhotoWithGeoTag;

import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * Created by dimamir999 on 21.01.16.
 */
public class ListPhotosPresenter {

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
//        view.getContextActivity().startManagingCursor(cursor);
//        String[] from = new String[] { "path", "date" };
//        int[] to = new int[] { R.id.photo_item_view, R.id.date_text_view };
//        adapter = new SimpleCursorAdapter(view.getContextActivity(), R.layout.photo_list_item, cursor, from, to);
        return adapter;
    }

}
