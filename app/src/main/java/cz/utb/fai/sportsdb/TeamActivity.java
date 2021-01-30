package cz.utb.fai.sportsdb;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class TeamActivity extends AppCompatActivity{
    Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        context = this;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team);

        Bundle b = getIntent().getExtras();

        LinearLayout upcomingLayout = findViewById(R.id.activity_team_view_upcoming);
        LinearLayout latestLayout = findViewById(R.id.activity_team_view_latest);
        TextView teamName = findViewById(R.id.activity_team_label);
        teamName.setText(b.getString("teamName"));

        CreateLayoutHelper createLayoutHelper = new CreateLayoutHelper(upcomingLayout,this);

        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);
        String urlUpcoming ="https://www.thesportsdb.com/api/v1/json/1/eventsnext.php?id=" + b.getString("teamID");
        String urlLatest ="https://www.thesportsdb.com/api/v1/json/1/eventslast.php?id=" + b.getString("teamID");

        // Request a string response from the provided URL.
        StringRequest stringRequestUpcoming = new StringRequest(Request.Method.GET, urlUpcoming,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response)
                    {
                        // ZPRACOVANI JSONu:
                        try
                        {
                            //1. Z DAT, KTERA JSME OBDRZELI VYTVORIME JSONObject
                            JSONObject jsonObject = new JSONObject(response);

                            // 2. Z PROMENNE jsonObject ZISKAME "responseData" (viz struktura JSONu odpovedi)
                            JSONArray array = jsonObject.getJSONArray("events");
                            JSONObject event;
                            for (int j = 0; j < array.length();j++ ) {
                                LinearLayout matchLayout = createLayoutHelper.CreateUpcomingLayout();
                                upcomingLayout.addView(matchLayout);
                                event =array.getJSONObject(j);


                                for (int i = 0; i < 2; i++) {
                                    LinearLayout matchLayout1Line = createLayoutHelper.CreateLineLayout();
                                    matchLayout.addView(matchLayout1Line);

                                    if(i == 0) {
                                        TextView teamText = createLayoutHelper.CreateText(event.getString("strHomeTeam"));
                                        matchLayout1Line.addView(teamText);

                                        View view = createLayoutHelper.CreateView();
                                        matchLayout1Line.addView(view);

                                        TextView dateText = createLayoutHelper.CreateText(event.getString("dateEvent"));
                                        matchLayout1Line.addView(dateText);
                                    } else{
                                        TextView teamText = createLayoutHelper.CreateText(event.getString("strAwayTeam"));
                                        matchLayout1Line.addView(teamText);

                                        View view = createLayoutHelper.CreateView();
                                        matchLayout1Line.addView(view);

                                        TextView dateText = createLayoutHelper.CreateText(event.getString("strTime") + " UTC");
                                        matchLayout1Line.addView(dateText);
                                    }
                                }
                            }
                        }
                        catch (JSONException e)
                        {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error)
                    {
//                        textView.setText("That didn't work!");
                    }
                });

        // Add the request to the RequestQueue.
        queue.add(stringRequestUpcoming);

        StringRequest stringRequestLatest = new StringRequest(Request.Method.GET, urlLatest,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response)
                    {
                        // ZPRACOVANI JSONu:
                        try
                        {
                            //1. Z DAT, KTERA JSME OBDRZELI VYTVORIME JSONObject
                            JSONObject jsonObject = new JSONObject(response);

                            // 2. Z PROMENNE jsonObject ZISKAME "responseData" (viz struktura JSONu odpovedi)
                            JSONArray array = jsonObject.getJSONArray("results");
                            JSONObject event;
                            for (int j = 0; j < array.length();j++ ) {
                                LinearLayout matchLayout = createLayoutHelper.CreateUpcomingLayout();
                                latestLayout.addView(matchLayout);
                                event =array.getJSONObject(j);


                                for (int i = 0; i < 2; i++) {
                                    LinearLayout matchLayout1Line = createLayoutHelper.CreateLineLayout();
                                    matchLayout.addView(matchLayout1Line);

                                    if(i == 0) {
                                        TextView teamText = createLayoutHelper.CreateText(event.getString("strHomeTeam"));
                                        matchLayout1Line.addView(teamText);

                                        View view = createLayoutHelper.CreateView();
                                        matchLayout1Line.addView(view);

                                        TextView score = createLayoutHelper.CreateText(event.getString("intHomeScore"));
                                        matchLayout1Line.addView(score);

                                    } else{
                                        TextView teamText = createLayoutHelper.CreateText(event.getString("strAwayTeam"));
                                        matchLayout1Line.addView(teamText);

                                        View view = createLayoutHelper.CreateView();
                                        matchLayout1Line.addView(view);

                                        TextView score = createLayoutHelper.CreateText(event.getString("intAwayScore"));
                                        matchLayout1Line.addView(score);

                                    }
                                }
                            }
                        }
                        catch (JSONException e)
                        {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error)
                    {
//                        textView.setText("That didn't work!");
                    }
                });

        // Add the request to the RequestQueue.
        queue.add(stringRequestLatest);


//        //////////UPCOMING MATCH LAYOUT///////////////////
//        LinearLayout matchLayout = new LinearLayout(this);
//        matchLayout.setOrientation(LinearLayout.VERTICAL);
//        LinearLayout.LayoutParams matchParams = new LinearLayout.LayoutParams(
//                LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
//        matchParams.setMargins(0,0,0,10);
//        matchLayout.setLayoutParams(matchParams);
//        /////////////////////////////////////////
//        upcomingLayout.addView(matchLayout);
//
//        //////////1 LINE OF UPCOMING MATCH LAYOUT///////////////////
//        LinearLayout matchLayout1Line = new LinearLayout(this);
//        LinearLayout.LayoutParams matchParams1Line = new LinearLayout.LayoutParams(
//                LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
//        matchLayout1Line.setOrientation(LinearLayout.HORIZONTAL);
//        matchLayout1Line.setBackgroundColor(Color.parseColor("#0c3114"));
//        matchParams1Line.gravity = Gravity.BOTTOM;
//        matchLayout1Line.setLayoutParams(matchParams1Line);
//        //////////////////////////////////////////////////////////
//        matchLayout.addView(matchLayout1Line);
//
//        TextView homeTeamText = new TextView(this);
//        LinearLayout.LayoutParams homeTeamTextParams = new LinearLayout.LayoutParams(
//                LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
//        homeTeamText.setLayoutParams(homeTeamTextParams);
//        homeTeamText.setText("home team");
//        homeTeamText.setTextColor(Color.parseColor("#FFFFFF"));
//
//        matchLayout1Line.addView(homeTeamText);
//
//        View view = new View(this);
//        LinearLayout.LayoutParams viewParams = new LinearLayout.LayoutParams(0,0,1);
//        matchLayout1Line.addView(view,viewParams);
//
//        TextView dateText = new TextView(this);
//        LinearLayout.LayoutParams dateTextParams = new LinearLayout.LayoutParams(
//                LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
//        dateText.setLayoutParams(dateTextParams);
//        dateText.setText("10 Dec 20");
//        dateText.setTextColor(Color.parseColor("#FFFFFF"));
//
//        matchLayout1Line.addView(dateText);






    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_search:
                // User chose the "Settings" item, show the app settings UI...
                Intent intent = new Intent(context, SearchActivity.class);
                startActivity(intent);
                return true;

            case R.id.action_favorite:
                // User chose the "Favorite" action, mark the current item
                // as a favorite...
                Intent intent2 = new Intent(context, TeamActivity.class);
                startActivity(intent2);
                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }
}
