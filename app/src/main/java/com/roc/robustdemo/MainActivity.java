package com.roc.robustdemo;

import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.meituan.robust.Patch;
import com.meituan.robust.PatchExecutor;
import com.meituan.robust.RobustCallBack;
import com.meituan.robust.patch.annotaion.Modify;
import com.roc.robustdemo.lib.robust.PatchManipulateImp;
import com.roc.robustdemo.retrofit.ApiManager;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        loadPatch();

    }

    @Modify
    private void initView() {
        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            @Modify
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "fixed", Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * 加载补丁
     */
    private void loadPatch() {

        String downloadUrl = "patch.jar";
        //noinspection ConstantConditions
        final File localPathFile = new File(getApplicationContext().getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath() + File.separator + "robust" + File.separator + "patch.jar");
        if (!localPathFile.exists()) {
            if (!localPathFile.getParentFile().exists()) {
                //noinspection ResultOfMethodCallIgnored
                localPathFile.getParentFile().mkdirs();
            }
        }

        ApiManager
                .getInstance()
                .download(downloadUrl)
                .concatMap(new Function<byte[], ObservableSource<String>>() {
                    @Override
                    public ObservableSource<String> apply(final byte[] bytes) throws Exception {
                        return Observable.create(new ObservableOnSubscribe<String>() {
                            @Override
                            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                                InputStream inputStream = new ByteArrayInputStream(bytes);
                                int len;
                                byte buffer[] = new byte[1024];
                                FileOutputStream outputStream = new FileOutputStream(localPathFile);
                                while ((len = inputStream.read(buffer)) != -1) {
                                    outputStream.write(buffer, 0, len);
                                    outputStream.flush();
                                }
                                inputStream.close();
                                outputStream.close();
                                emitter.onComplete();
                            }
                        });
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<String>() {
                                   @Override
                                   public void onNext(String patchPath) {
                                   }

                                   @Override
                                   public void onError(Throwable throwable) {

                                   }

                                   @Override
                                   public void onComplete() {
                                       startPatch();
                                   }
                               }
                );
    }

    private void startPatch() {
        new PatchExecutor(getApplicationContext(), new PatchManipulateImp(), new RobustCallBack() {
            @Override
            public void onPatchListFetched(boolean result, boolean isNet, List<Patch> patches) {
                Log.d("PrivateTag", "onPatchListFetched");
                Log.d("PrivateTag", "result == " + result);
                Log.d("PrivateTag", "isNet == " + isNet);
                Log.d("PrivateTag", "patches.size == " + patches.size());
            }

            @Override
            public void onPatchFetched(boolean result, boolean isNet, Patch patch) {
                Log.d("PrivateTag", "onPatchFetched");
                Log.d("PrivateTag", "result == " + result);
                Log.d("PrivateTag", "isNet == " + isNet);
                Log.d("PrivateTag", "patch == " + patch.getLocalPath());
            }

            @Override
            public void onPatchApplied(boolean result, Patch patch) {
                Log.d("PrivateTag", "onPatchApplied");
                Log.d("PrivateTag", "result == " + result);
                Log.d("PrivateTag", "patch == " + patch.getLocalPath());
            }

            @Override
            public void logNotify(String log, String where) {
                Log.d("PrivateTag", "logNotify");
                Log.d("PrivateTag", "log == " + log);
                Log.d("PrivateTag", "where == " + where);
            }

            @Override
            public void exceptionNotify(Throwable throwable, String where) {
                Log.d("PrivateTag", "exceptionNotify");
                Log.d("PrivateTag", "throwable == " + throwable.getMessage());
                Log.d("PrivateTag", "where == " + where);
                throwable.printStackTrace();
            }
        }).start();
    }
}
