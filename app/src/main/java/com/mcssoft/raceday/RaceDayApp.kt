package com.mcssoft.raceday

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import org.greenrobot.eventbus.EventBus

@HiltAndroidApp
class RaceDayApp : Application() {

    override fun onCreate() {
        super.onCreate()

        // https://greenrobot.org/eventbus/documentation/subscriber-index/
        EventBus.builder().addIndex(MyEventBusIndex()).installDefaultEventBus()
    }
}
/*
Codelab doco:
-------------
@HiltAndroidApp triggers Hilt's code generation including a base class for your application that
can use dependency injection. The application container is the parent container of the app, which
means that other containers can access the dependencies that it provides.
 */