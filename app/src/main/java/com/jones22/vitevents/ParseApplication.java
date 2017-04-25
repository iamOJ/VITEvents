package com.jones22.vitevents;

import android.app.Application;

import com.parse.Parse;
import com.parse.ParseACL;
import com.parse.ParseUser;

public class ParseApplication extends Application {

    private static final String APPLN_ID = "eventsVIT", CLIENT_KEY = "asdlkkwugvpshnoioih745a9fa5";

    @Override
    public void onCreate() {
        super.onCreate();

        Parse.initialize(this, APPLN_ID, CLIENT_KEY);

        ParseUser.enableAutomaticUser();
        ParseACL defaultACL = new ParseACL();

        defaultACL.setPublicReadAccess(true);

        ParseACL.setDefaultACL(defaultACL, true);

    }

}
