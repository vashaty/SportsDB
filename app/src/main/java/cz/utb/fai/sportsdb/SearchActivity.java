package cz.utb.fai.sportsdb;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
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

public class SearchActivity extends AppCompatActivity {
    Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        context = this;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
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
    public void searchButtonClick(View v)
    {
        RequestQueue queue = Volley.newRequestQueue(this);
        EditText teamSearch = (EditText)  findViewById(R.id.search_team_text);
        String urlSearch ="https://www.thesportsdb.com/api/v1/json/1/searchteams.php?t=" + teamSearch.getText().toString();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, urlSearch,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response)
                    {
                        // ZPRACOVANI JSONu:
                        try
                        {
                            JSONObject jsonObject = new JSONObject(response);

                            // 2. Z PROMENNE jsonObject ZISKAME "responseData" (viz struktura JSONu odpovedi)
                            JSONArray teams = jsonObject.getJSONArray("teams");
                            JSONObject team =teams.getJSONObject(0);
                            String teamName = team.getString("strTeam");
                            String teamID = team.getString("idTeam");
                            String teamLogo = team.getString("strTeamLogo");

                            Intent intent = new Intent(context, TeamActivity.class);
                            Bundle b = new Bundle();

                            b.putString("teamName", teamName);
                            b.putString("teamID", teamID);
                            b.putString("teamLogo", teamLogo);

                            intent.putExtras(b);
                            startActivity(intent);


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
        queue.add(stringRequest);
    }
}
