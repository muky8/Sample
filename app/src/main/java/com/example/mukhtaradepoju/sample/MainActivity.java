package com.example.mukhtaradepoju.sample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.apollographql.apollo.ApolloCall;
import com.apollographql.apollo.ApolloSubscriptionCall;
import com.apollographql.apollo.api.Response;
import com.apollographql.apollo.exception.ApolloException;
import com.apollographql.apollo.rx2.Rx2Apollo;

import java.util.List;

import javax.annotation.Nonnull;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subscribers.DisposableSubscriber;

import static android.content.ContentValues.TAG;

public class MainActivity extends AppCompatActivity {


    RecyclerView recyclerView;
    private Adapter adapter;
    List<AllTasksQuery.AllTask> modelArrayList;
    private final CompositeDisposable disposables = new CompositeDisposable();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = (RecyclerView) findViewById(R.id.recycler1);
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        updateui();
        subscriptionmethod();

    }


    void updateui() {

        MyApolloClient.getApolloClient().query(AllTasksQuery.builder().build()).enqueue(new ApolloCall.Callback<AllTasksQuery.Data>() {
            @Override
            public void onResponse(@Nonnull Response<AllTasksQuery.Data> response) {


                modelArrayList = response.data().allTasks();
                List<AllTasksQuery.AllTask> allTasks = response.data().allTasks();
                for (int i = 0; i < allTasks.size(); i++) {
                    boolean val = allTasks.get(i).done;
                    Log.i("val", String.valueOf(val));

                }
                Log.i("Response", response.data().allTasks().toString());
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        adapter = new Adapter(MainActivity.this, modelArrayList);
                        recyclerView.setAdapter(adapter);
                    }
                });


            }


            @Override
            public void onFailure(@Nonnull ApolloException e) {

            }
        });


    }


    void subscriptionmethod() {
        ApolloSubscriptionCall<SubscribetaskSubscription.Data> subscriptionCall =
                MyApolloClient.getApolloClient()
                        .subscribe(new SubscribetaskSubscription());


        disposables.add(Rx2Apollo.from(subscriptionCall)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(
                        new DisposableSubscriber<Response<SubscribetaskSubscription.Data>>() {
                            @Override
                            public void onNext(Response<SubscribetaskSubscription.Data> response) {
                                updateui();


                            }

                            @Override
                            public void onError(Throwable e) {

                                subscriptionmethod();
                                updateui();

                            }

                            @Override
                            public void onComplete() {
                                Log.d(TAG, "Subscription exhausted");
                            }
                        }
                )
        );
    }


}

