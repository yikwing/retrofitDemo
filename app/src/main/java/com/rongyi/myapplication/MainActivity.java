package com.rongyi.myapplication;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.orhanobut.logger.Logger;
import com.rongyi.myapplication.bean.ListResult;
import com.rongyi.myapplication.utils.HttpMethods;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.result_TV)
    TextView resultTV;
    @BindView(R.id.click_me_BN)
    Button clickMeBN;
    private Observer<List<ListResult>> observer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.click_me_BN)
    public void onViewClicked() {
        initNetwork();
    }

    private void initNetwork() {
        observer = new Observer<List<ListResult>>() {


            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(List<ListResult> listResults) {
                for (ListResult listResult : listResults) {
                    Logger.d(listResult.getCityName());
                }
            }

            @Override
            public void onError(Throwable e) {
                Logger.d(e.getLocalizedMessage());
            }

            @Override
            public void onComplete() {
                Toast.makeText(MainActivity.this, "请求成功", Toast.LENGTH_SHORT).show();
            }
        };

        HttpMethods.getInstance().getLogin(observer, "0", "深圳");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
