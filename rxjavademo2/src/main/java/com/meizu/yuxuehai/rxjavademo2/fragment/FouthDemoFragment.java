package com.meizu.yuxuehai.rxjavademo2.fragment;

import android.app.Fragment;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.github.lzyzsd.circleprogress.ArcProgress;
import com.meizu.yuxuehai.rxjavademo2.R;
import com.rey.material.widget.Button;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;
import rx.subjects.PublishSubject;

/**
 * Created by yuxuehai on 17-9-29.
 */

public class FouthDemoFragment extends Fragment implements View.OnClickListener{


    private Button mButton;
    private ArcProgress mArcProgress;


    private PublishSubject<Integer> mDownloadProgress = PublishSubject.create();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_download, container, false);
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mButton = view.findViewById(R.id.button_download);
        mArcProgress = view.findViewById(R.id.arc_progress);

        mButton.setOnClickListener(this);

    }


    @Override
    public void onClick(View view) {
        mButton.setText("Downloading");
        mButton.setClickable(false);

        mDownloadProgress
                .distinct()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Integer>() {
                    @Override
                    public void onCompleted() {
                        Toast.makeText(getActivity(), "onCompleted!", Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Toast.makeText(getActivity(), "something wrong!", Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onNext(Integer integer) {
                        mArcProgress.setProgress(integer);
                    }
                });

        final String destination = "/sdcard/softboy.avi";

        obserbableDownload("http://archive.blender.org/fileadmin/movies/softboy.avi", destination)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Boolean>() {
                    @Override
                    public void call(Boolean aBoolean) {
                        FouthDemoFragment.this.resetDownloadButton();
                        Intent intent = new Intent(Intent.ACTION_VIEW);
                        File file = new File(destination);
                        intent.setDataAndType(Uri.fromFile(file), "video/avi");
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        FouthDemoFragment.this.startActivity(intent);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        Toast.makeText(FouthDemoFragment.this.getActivity(), "Something went south", Toast.LENGTH_SHORT).show();
                        FouthDemoFragment.this.resetDownloadButton();
                    }
                });


    }


    private void resetDownloadButton() {
        mButton.setText("Download");
        mButton.setClickable(true);
        mArcProgress.setProgress(0);
    }

    private Observable<Boolean> obserbableDownload(final String source, final String destination) {
        return Observable.create(new Observable.OnSubscribe<Boolean>() {
            @Override
            public void call(Subscriber<? super Boolean> subscriber) {
                try {
                    boolean result = FouthDemoFragment.this.downloadFile(source, destination);
                    if (result) {
                        subscriber.onNext(true);
                        subscriber.onCompleted();
                    } else {
                        subscriber.onError(new Throwable("Download failed."));
                    }
                } catch (Exception e) {
                    subscriber.onError(e);
                }
            }
        });
    }

    private boolean downloadFile(String source, String destination) {
        boolean result = false;
        InputStream input = null;
        OutputStream output = null;
        HttpURLConnection connection = null;
        try {
            URL url = new URL(source);
            connection = (HttpURLConnection) url.openConnection();
            connection.connect();

            if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                return false;
            }

            int fileLength = connection.getContentLength();

            input = connection.getInputStream();
            output = new FileOutputStream(destination);

            byte data[] = new byte[4096];
            long total = 0;
            int count;
            while ((count = input.read(data)) != -1) {
                total += count;

                if (fileLength > 0) {
                    int percentage = (int) (total * 100 / fileLength);
                    mDownloadProgress.onNext(percentage);
                }
                output.write(data, 0, count);
            }
            mDownloadProgress.onCompleted();
            result = true;
        } catch (Exception e) {
            mDownloadProgress.onError(e);
        } finally {
            try {
                if (output != null) {
                    output.close();
                }
                if (input != null) {
                    input.close();
                }
            } catch (IOException e) {
                mDownloadProgress.onError(e);
            }

            if (connection != null) {
                connection.disconnect();
                mDownloadProgress.onCompleted();
            }
        }
        return result;
    }
}
