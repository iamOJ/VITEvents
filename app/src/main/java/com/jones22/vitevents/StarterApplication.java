package com.jones22.vitevents;

import android.app.Application;
import android.util.Log;

import com.parse.FindCallback;
import com.parse.Parse;
import com.parse.ParseACL;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.List;

public class StarterApplication extends Application {

    private static final String APPLN_ID = "eventsVIT", CLIENT_KEY = "asdlkkwugvpshnoioih745a9fa5";

    @Override
    public void onCreate() {
        super.onCreate();

        // Enable Local Datastore.
        Parse.enableLocalDatastore(this);

        // Add your initialization code here
        Parse.initialize(new Parse.Configuration.Builder(getApplicationContext())
                .applicationId("eventsVIT")
                .clientKey(null)
                .server("http://eventsvit.herokuapp.com/parse/")
                .build()
        );
/*
      ParseObject gameScore = new ParseObject("GameScore");
      gameScore.put("score", 1337);
      gameScore.put("playerName", "Sean Plott");
      gameScore.put("cheatMode", false);
      gameScore.saveInBackground(new SaveCallback() {
          public void done(ParseException e) {
              if (e == null) {
                  Log.i("Parse", "Save Succeeded");
              } else {
                  Log.i("Parse", "Save Failed");
              }
          }
      });
*//*
        ParseQuery<ParseObject> query = ParseQuery.getQuery("GameScore");
        query.getInBackground("QDb9uzycC1", new GetCallback<ParseObject>() {
            @Override
            public void done(ParseObject object, ParseException e) {
                if(e==null) {
                    object.put("score",2000);
                    object.saveInBackground(new SaveCallback() {
                        @Override
                        public void done(ParseException e) {
                            if(e==null){
                                Log.i("Parse", "Save Succeeded");
                            } else {
                                Log.i("Parse", "Save Failed2");
                            }
                        }
                    });
                }
                else{
                    Log.i("Parse", "Save Fail");
                    e.printStackTrace();
                }
            }
        });

*/
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Events");
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if(e==null){
                    Log.i("Checking",objects.toString());
                    Log.i("Values", (String) objects.get(0).get("EventName"));
                }
                else{
                    Log.i("Checking","Fail");
                    e.printStackTrace();
                }
            }
        });/*
        query.getInBackground("QDb9uzycC1", new GetCallback<ParseObject>() {
            @Override
            public void done(ParseObject object, ParseException e) {
                if(e==null) {
                    object.put("score",2000);
                    object.saveInBackground(new SaveCallback() {
                        @Override
                        public void done(ParseException e) {
                            if(e==null){
                                Log.i("Parse", "Save Succeeded");
                            } else {
                                Log.i("Parse", "Save Failed2");
                            }
                        }
                    });
                }
                else{
                    Log.i("Parse", "Save Fail");
                    e.printStackTrace();
                }
            }
        });*/
        ParseUser.enableAutomaticUser();
        ParseACL defaultACL = new ParseACL();
        // Optionally enable public read access.
        // defaultACL.setPublicReadAccess(true);
        defaultACL.setPublicReadAccess(true);
        ParseACL.setDefaultACL(defaultACL, true);
    }
}
