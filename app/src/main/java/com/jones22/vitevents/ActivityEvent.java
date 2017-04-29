package com.jones22.vitevents;

import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.Calendar;

public class ActivityEvent extends AppCompatActivity {

    //@BindView(R.id.event_name)
    TextView eventName;
    //@BindView(R.id.event_date)
    TextView eventDate;
    //@BindView(R.id.event_time)
    TextView eventTime;
    //@BindView(R.id.event_venue)
    TextView eventVenue;
    //@BindView(R.id.event_fee)
    TextView eventFee;
    //@BindView(R.id.event_desc)
    TextView eventDesc;
    TextView description;
    TextView imageCaption;
    TextView clubName;
    ImageView imageView;

    private String register;
    private String knowMore;
    private String imgURL;

    private Toolbar toolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);
        //ButterKnife.bind(this);
        toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        eventName = (TextView) findViewById(R.id.event_name);
        eventDate = (TextView) findViewById(R.id.event_date);
        eventDesc = (TextView) findViewById(R.id.event_desc);
        eventVenue = (TextView) findViewById(R.id.event_venue);
        eventFee = (TextView) findViewById(R.id.event_fee);
        eventTime = (TextView) findViewById(R.id.event_time);
        description = (TextView) findViewById(R.id.desc);
        imageCaption = (TextView) findViewById(R.id.PosterTitle);
        imageView = (ImageView) findViewById(R.id.imageView);
        clubName = (TextView) findViewById(R.id.clubName1);
        description.setTypeface(null, Typeface.BOLD);
        imageCaption.setTypeface(null, Typeface.BOLD);

        java.util.Date subdate;
        java.util.Date todaysDate = Calendar.getInstance().getTime();
        Log.i("Date", todaysDate.toString());

        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if (extras == null) {

            } else {
                eventName.setText(extras.getString("name"));
                eventVenue.setText(extras.getString("venue"));
                eventDate.setText(extras.getString("date").substring(0, 10));
                eventTime.setText(extras.getString("time"));
                eventFee.setText("" + extras.getInt("fee"));
                eventDesc.setText(extras.getString("desc"));
                clubName.setText(extras.getString("clubName"));
                knowMore = extras.getString("knowMore");
                //Log.i("Register",extras.getString(""))
                register = extras.getString("register");
                imgURL = extras.getString("image");
                Glide.with(this).load(imgURL).into(imageView);

                //Log.i("RegiserEvent",register);
                //extras.get("image");
                /*
                byte[] data = extras.getByteArray("image");
                Bitmap bmp = BitmapFactory.decodeByteArray(data,0,data.length);
                imageView.setImageBitmap(bmp);*/
                String date = extras.getString("date");
                Log.i("Given Date", date.substring(0, 10));
            }
        } else {
            eventName.setText((String) savedInstanceState.getSerializable("name"));
            eventVenue.setText((String) savedInstanceState.getSerializable("venue"));
            eventDate.setText((String) savedInstanceState.getSerializable("date"));
            eventTime.setText((String) savedInstanceState.getSerializable("time"));
            eventFee.setText("" + (int) savedInstanceState.getSerializable("fee"));
            eventDesc.setText((String) savedInstanceState.getSerializable("desc"));
        }

    }

    public void knowMore(View view) {
        Intent i = new Intent();
        i.setAction(Intent.ACTION_VIEW);
        i.addCategory(Intent.CATEGORY_BROWSABLE);
        i.setData(Uri.parse(knowMore));
        startActivity(i);
    }

    public void Register(View view) {
        Log.i("Register", "Logging " + register);
        if (register != null) {
            Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(register));
            startActivity(i);
        }
    }
/*
    public static Drawable loadImageFromUrl(String url) {
        InputStream inputStream;
        AsyncImageLoader async=new AsyncImageLoader();

        Drawable cachedImage = async.loadDrawable(
                url, new ImageCallback() {
                    public void imageLoaded(Drawable imageDrawable,
                                            String imageUrl) {
                        imageView.setImageDrawable(imageDrawable);
                    }
                });
        imageView.setImageDrawable(cachedImage);
        return cachedImage;
    }
    */
}
