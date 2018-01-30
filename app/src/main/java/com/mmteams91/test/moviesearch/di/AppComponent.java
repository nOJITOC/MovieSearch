package com.mmteams91.test.moviesearch.di;

import android.app.Application;
import android.content.Context;

import com.mmteams91.test.moviesearch.App;
import com.mmteams91.test.moviesearch.MainActivity;
import com.mmteams91.test.moviesearch.MainContract;
import com.mmteams91.test.moviesearch.data.network.RestApi;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjector;
import dagger.android.support.AndroidSupportInjectionModule;
import retrofit2.Retrofit;


@Singleton
@Component(modules = {
        ApplicationModule.class,
        NetworkModule.class,
        StorageModule.class,
        ScreenBindingModule.class,
        MainActivity.Module.class,
        AndroidSupportInjectionModule.class,
})
public interface AppComponent extends AndroidInjector<App> {

    RestApi getRest();

    Context getContext();

    MainContract.Presenter getMainPresenter();

    // Gives us syntactic sugar. we can then do DaggerAppComponent.builder().application(this).build().inject(this);
    // never having to instantiate any modules or say which module we are passing the application to.
    // Application will just be provided into our app graph now.
    @Component.Builder
    interface Builder {

        @BindsInstance
        AppComponent.Builder application(Application application);

        @BindsInstance
        AppComponent.Builder retrofit(Retrofit retrofit);

        AppComponent build();
    }
}
