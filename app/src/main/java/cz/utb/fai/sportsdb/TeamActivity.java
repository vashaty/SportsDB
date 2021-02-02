package cz.utb.fai.sportsdb;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

public class TeamActivity extends AppCompatActivity{
    Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        context = this;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team);

        Bundle b = getIntent().getExtras();

        String teamID = b.getString("teamID");
        String teamName = b.getString("teamName");
        String teamLogo = b.getString("teamLogo");


        LinearLayout upcomingLayout = findViewById(R.id.activity_team_view_upcoming);
        LinearLayout latestLayout = findViewById(R.id.activity_team_view_latest);
        TextView teamNameLabel = findViewById(R.id.activity_team_label);
        ImageView teamLogoView = findViewById(R.id.activity_team_image);
        ImageButton buttonFav = findViewById(R.id.activity_team_fav_button);
        teamNameLabel.setText(teamName);
        Picasso.get().load(teamLogo).into(teamLogoView);

        String SHARED_PREFERENCES_FILE_NAME = "appFileInternal";
        SharedPreferences sharedPreferences = this.getSharedPreferences(SHARED_PREFERENCES_FILE_NAME, MODE_PRIVATE);


        CreateLayoutHelper createLayoutHelper = new CreateLayoutHelper(upcomingLayout,this);


        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);
        String urlUpcoming ="https://www.thesportsdb.com/api/v1/json/1/eventsnext.php?id=" + teamID;
        String urlLatest ="https://www.thesportsdb.com/api/v1/json/1/eventslast.php?id=" + teamID;

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
        if(isInFav (teamID,sharedPreferences)){
            buttonFav.setColorFilter(Color.parseColor("#FFD700"));
        } else {
            buttonFav.setColorFilter(Color.parseColor("#FFFFFF"));
        }

        buttonFav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                favouritesButton(teamID,teamName,buttonFav,sharedPreferences, teamLogo);
            }
        });
    }

    private boolean isInFav (String id,SharedPreferences sharedPreferences){
        Map<String, ?> allEntries = sharedPreferences.getAll();
        for (Map.Entry<String, ?> entry : allEntries.entrySet()) {
            if (entry.getKey().equals(id)){
                return true;
            }
        }
        return false;
    }

    public void favouritesButton(String id, String teamName, ImageButton button,SharedPreferences sharedPreferences, String teamLogo) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        if(!isInFav(id, sharedPreferences)){
            editor.putString(id, teamName +";"+ teamLogo);
            button.setColorFilter(Color.parseColor("#FFD700"));
            editor.apply();
        } else {
            editor.remove(id);
            editor.apply();
            button.setColorFilter(Color.parseColor("#FFFFFF"));
        }
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
                Intent intentFav = new Intent(context, FavouritesActivity.class);
                startActivity(intentFav);
                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }
}
