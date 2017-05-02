package com.jones22.vitevents;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentEvents#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentEvents extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private static List<ParseObject> allObjects = new ArrayList<ParseObject>();
    private RecyclerView listEvents;
    private AdapterEvents adapterEvents;
    private ProgressBar progressBar;
    private SwipeRefreshLayout swipeRefresh;
    public int bias;

    private static final String APPLN_ID = "eventsVIT", CLIENT_KEY = "asdlkkwugvpshnoioih745a9fa5";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentEvents.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentEvents newInstance(String param1, String param2) {
        FragmentEvents fragment = new FragmentEvents();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public FragmentEvents() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
            bias = 0;
        }

    }

    int skip = 0;

    FindCallback<ParseObject> getAllObjects() {

        progressBar.setVisibility(View.VISIBLE);
        listEvents.setVisibility(View.GONE);
        return new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if (e == null) {
                    allObjects.addAll(objects);
                    int limit = 1000;
                    if (objects.size() == limit) {
                        skip = skip + limit;
                        ParseQuery query = new ParseQuery("EventsVIT");
                        query.setSkip(skip);
                        query.setLimit(limit);
                        query.findInBackground(getAllObjects());
                    } else {
                        //code to show only today's events

                        String subdate;
                        String todaysDate = Calendar.getInstance().getTime().toString();

                        //Toast.makeText(getActivity(),""+todaysDate,Toast.LENGTH_SHORT).show();
                        for (int i = 0; i < allObjects.size(); i++) {

                            try {
                                subdate = allObjects.get(i).get("date").toString();
                                if (!subdate.substring(0, 10).equals(todaysDate.substring(0, 10))) {
                                    allObjects.remove(i);
                                }
                                //String subdateStr = df.format(subdate);
                            } catch (Exception ex) {
                                ex.printStackTrace();
                            }
                            //Toast.makeText(getActivity(),""+  subdate.compareTo(todaysDate),Toast.LENGTH_SHORT).show();

                        }
                        adapterEvents.setListEvents(objects);
                        listEvents.setAdapter(adapterEvents);
                        progressBar.setVisibility(View.GONE);
                        listEvents.setVisibility(View.VISIBLE);
                    }
                } else {
                    Toast.makeText(getActivity(), "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.GONE);


                }
            }
        };
    }

    private EventsDataSource dataSource;

    @Override
    public void onResume() {
        dataSource.open();
        super.onResume();
    }

    @Override
    public void onPause() {
        dataSource.close();
        super.onPause();
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        dataSource = new EventsDataSource(getActivity());
        dataSource.open();
        Events event = null;
        List<Events> values = dataSource.getAllEvents();
        final ArrayAdapter<Events> adapter = new ArrayAdapter<Events>(getActivity(),
                android.R.layout.simple_list_item_1, values);
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_events, container, false);
        listEvents = (RecyclerView) view.findViewById(R.id.listEvents);
        progressBar = (ProgressBar) view.findViewById(R.id.progressBar);
        swipeRefresh = (SwipeRefreshLayout) view.findViewById(R.id.swipeRefresh);
        //progressBar.setVisibility(View.VISIBLE);
        //listEvents.setVisibility(View.GONE);

        swipeRefresh.setRefreshing(false);

        swipeRefresh.getViewTreeObserver()
                .addOnGlobalLayoutListener(
                        new ViewTreeObserver.OnGlobalLayoutListener() {
                            @Override
                            public void onGlobalLayout() {
                                swipeRefresh
                                        .getViewTreeObserver()
                                        .removeGlobalOnLayoutListener(this);
                                //swipeRefresh.setRefreshing(true);
                            }
                        });


        //refreshItems();


        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //swipeRefresh.setRefreshing(true);
                // Refresh items
                refreshItems();
            }
        });

        listEvents.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapterEvents = new AdapterEvents(getActivity(), adapter);
        listEvents.setAdapter(adapterEvents);


        final GestureDetector mGestureDetector = new GestureDetector(getActivity(), new GestureDetector.SimpleOnGestureListener() {

            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                return true;
            }

        });
        listEvents.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {

            @Override
            public boolean onInterceptTouchEvent(RecyclerView recyclerView, MotionEvent motionEvent) {
                View child = recyclerView.findChildViewUnder(motionEvent.getX(), motionEvent.getY());

                if (child != null && mGestureDetector.onTouchEvent(motionEvent)) {
                    Log.i("Child", child.toString());


                    List<ParseObject> tempObj;
                    final int position = recyclerView.getChildPosition(child);
                    Log.i("Child ID", String.valueOf(recyclerView.getChildPosition(child)));
                    ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("Events");
                    query.setLimit(1000).orderByAscending("date");
                    query.findInBackground(new FindCallback<ParseObject>() {
                        public void done(List<ParseObject> markers, ParseException e) {
                            if (e == null) {
                                Intent intent = new Intent(getActivity(), ActivityEvent.class);
                                List<Events> values = dataSource.getAllEvents(markers);
                                Events temp = values.get(position + bias);
                                Log.i("Bias Value", String.valueOf(bias));
                                try {
                                    intent.putExtra("name", temp.getName());
                                    intent.putExtra("date", temp.getDate().substring(0, 10));
                                    intent.putExtra("time", temp.getTime());
                                    intent.putExtra("venue", temp.getVenue());
                                    intent.putExtra("fee", temp.getFee());
                                    intent.putExtra("desc", temp.getDesc());
                                    intent.putExtra("clubName", temp.getClubName());
                                    intent.putExtra("register", temp.getRegister());
                                    //Log.i("Temp","getReg");
                                    intent.putExtra("knowMore", temp.getKnowMore());
                                    intent.putExtra("image", temp.getImage());
                                    startActivity(intent);
                                }
                                catch (Exception e1)
                                {
                                    e1.printStackTrace();
                                    Toast.makeText(getActivity(), "Server Error! Please try again later", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Log.i("Events", "Not");
                                e.printStackTrace();
                                Toast.makeText(getActivity(), "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                                progressBar.setVisibility(View.GONE);
                            }
                        }
                    });

                    //Intent intent = new Intent(Intent.ACTION_VIEW);
                    //intent.setData(Uri.parse(allObjects.get(recyclerView.getChildPosition(child)).getString("facebook")));
                    //startActivity(intent);
                    return true;

                }

                return false;
            }

            @Override
            public void onTouchEvent(RecyclerView recyclerView, MotionEvent motionEvent) {

            }

            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

            }
        });

        swipeRefresh.setRefreshing(false);
        //final ParseQuery parseQuery = new ParseQuery("Events");
        //parseQuery.setLimit(1000);
        //parseQuery.findInBackground(getAllObjects());
        return view;
    }

    void refreshItems() {

        ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("Events");
        query.setLimit(1000);
        Log.i("Events", "Refreshing");
        query.setLimit(1000).orderByAscending("date");
        query.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> markers, ParseException e) {
                if (e == null) {
                    /*
                    List<Events> values = dataSource.getAllEvents(markers);
                    final ArrayAdapter<Events> adapter = new ArrayAdapter<Events>(getActivity(),
                            android.R.layout.simple_list_item_1, values);
                    adapterEvents = new AdapterEvents(getActivity(),adapter);
                    listEvents.setAdapter(adapterEvents);
                    swipeRefresh.setRefreshing(false);
                    */
                    allObjects.clear();
                    allObjects = markers;
                    Log.i("Length", String.valueOf(allObjects.size()));
                    String subdate;
                    String todaysDate = Calendar.getInstance().getTime().toString();

                    //Toast.makeText(getActivity(),""+todaysDate,Toast.LENGTH_SHORT).show();
                    for (int i = 0; i < allObjects.size(); i++) {

                        try {
                            subdate = allObjects.get(i).get("date").toString();
                            /*Log.i("Date", subdate);
                            Log.i("Today", todaysDate);*/
                            if (!subdate.substring(0, 10).equals(todaysDate.substring(0, 10))) {
                                allObjects.remove(i);
                                Log.i("biasing",subdate.substring(4, 7));
                                if (Integer.parseInt(subdate.substring(8, 10)) < Integer.parseInt(todaysDate.substring(8, 10)) && subdate.substring(4, 7).equals(todaysDate.substring(4, 7))) {
                                    bias++;
                                    Log.i("EventBiasing",allObjects.get(i).getString("EventName"));
                                    Log.i("Bias Value",String.valueOf(bias));
                                }
                                --i;
                                //bias++;
                            }
                            //String subdateStr = df.format(subdate);
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    }

                    List<Events> values = dataSource.getAllEvents(allObjects);
                    final ArrayAdapter<Events> adapter = new ArrayAdapter<Events>(getActivity(),
                            android.R.layout.simple_list_item_1, values);
                    adapterEvents = new AdapterEvents(getActivity(), adapter);
                    listEvents.setAdapter(adapterEvents);
                    swipeRefresh.setRefreshing(false);
                    /*
                    allObjects.addAll(markers);
                    Log.i("Events",allObjects.toString());
                    int len = markers.size();
                    for(int i = 0;i<len;i++){
                        Log.i("Values", (String) markers.get(i).get("EventName"));
                    }
                    */

                    //today's events
                    /*
                    java.util.Date subdate;
                    java.util.Date todaysDate = Calendar.getInstance().getTime();
                    for(int i=0;i<markers.size();i++) {

                        try {
                            subdate = markers.get(i).getDate("date");
                        } catch (Exception ex) {
                            subdate = Calendar.getInstance().getTime();
                        }
                        if (subdate.compareTo(todaysDate) == -1) {
                            try {
                                markers.remove(i);
                            }catch (Exception er){
                                er.printStackTrace();
                            }
                            i--;
                        }
                    }
                    */

                    adapterEvents.setListEvents(markers);
                    listEvents.setAdapter(adapterEvents);
                    progressBar.setVisibility(View.GONE);
                    listEvents.setVisibility(View.VISIBLE);

                    swipeRefresh.setRefreshing(false);

                } else {
                    Log.i("Events", "Not");
                    e.printStackTrace();
                    Toast.makeText(getActivity(), "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.GONE);
                }
            }
        });

        onItemsLoadComplete();
/*
        List<Events> values = dataSource.getAllEvents();
        final ArrayAdapter<Events> adapter = new ArrayAdapter<Events>(getActivity(),
                android.R.layout.simple_list_item_1, values);
        adapterEvents = new AdapterEvents(getActivity(),adapter);
        listEvents.setAdapter(adapterEvents);
        swipeRefresh.setRefreshing(false);
        */
    }

    void onItemsLoadComplete() {

    }

}
