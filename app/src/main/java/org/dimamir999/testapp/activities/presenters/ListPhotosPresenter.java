package org.dimamir999.testapp.activities.presenters;

import android.app.ActivityManager;
import android.content.Context;
import android.os.AsyncTask;
import android.text.format.Time;
import android.util.Log;


import org.dimamir999.testapp.activities.views.ListPhotoView;
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
    private ArrayList<PhotoWithGeoTag> viewedPhotos;


    public ListPhotosPresenter(ListPhotoView view) {
        this.view = view;
        photoWithGeoTagDAO = new PhotoWithGeoTagDAO(view.getContextActivity());
    }

    public ArrayList<PhotoWithGeoTag> getListData(){
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
        viewedPhotos = photoWithGeoTagDAO.getBetweenDates(startDate, endDate);
        return photoWithGeoTagDAO.getBetweenDates(startDate, endDate);
    }

    public void deletePhoto(int position){
        PhotoWithGeoTag photoObject = viewedPhotos.remove(position);
        AsyncRemover remover = new AsyncRemover();
        remover.execute(photoObject.getId());
    }

    public class AsyncRemover extends AsyncTask<Long, Void, Void> {

        @Override
        protected Void doInBackground(Long... ids) {
            photoWithGeoTagDAO.delete(ids[0]);
            return null;
        }
    }
}
