package com.rijo.umbrella.services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.rijo.umbrella.Utilities;
import com.rijo.umbrella.model.Conditions;
import com.rijo.umbrella.model.Const;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class FetchWeatherService extends Service {
    ExecutorService executor;
    public FetchWeatherService() {
    }

    @Override
    public void onCreate() {
        executor= Executors.newCachedThreadPool();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        fetchConditionAndPutToDB();
        return START_REDELIVER_INTENT;
    }

    private void fetchConditionAndPutToDB() {
        Conditions conditions=null;
        Future<Conditions> imageFuture=executor.submit(new Callable<Conditions>() {
            @Override
            public Conditions call() throws Exception {
                return getConditionsfromUrl(Const.CONDITION_API);
            }
        });
        try {
            conditions=imageFuture.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        if(conditions!=null) {
            putConditionsToDB();
        } else {
            stopSelf();
        }

    }

    private Conditions getConditionsfromUrl(String conditionApi) {
        String imagesString= Utilities.downloadJsonFromUrl(conditionApi);
        if(imagesString!=null) {
            Conditions images = Utilities.getClassFromJSONString(imagesString, Conditions.class);
            return images;
        }
        else {
            return null;
        }
    }

    private void putConditionsToDB() {
    }
}
