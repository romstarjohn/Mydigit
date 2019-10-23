package com.project.android.finanzm.services;

import android.app.Service;
import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;



public class MyJobService extends JobService {
    private final static String TAG = "JobService";

    public MyJobService() {
    }

    @Override
    public boolean onStartJob(final JobParameters params) {
        Log.i(TAG, "onStartJob" + params.getJobId());

        Runnable r = new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                jobFinished(params, false);
            }
        };

        Thread thread = new Thread(r);
        thread.start();

        return true;
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        Log.i(TAG, "onStopJob" + params.getJobId());

        return false;
    }

}
