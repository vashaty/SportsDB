package cz.utb.fai.sportsdb;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

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

public class SearchActivity extends MenuActivity {
    Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        context = this;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        EditText teamSearch = (EditText)  findViewById(R.id.search_team_text);
        Button button = findViewById(R.id.search_button);
        button.setEnabled(false);
        teamSearch.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean b) {
                if(b) button.setEnabled(true);
            }
        });
    }

    public void searchButtonClick(View v)
    {
        LinearLayout layout = findViewById(R.id.activity_searchLayout);
        layout.removeAllViews();

        InputMethodManager inputManager = (InputMethodManager)
                getSystemService(Context.INPUT_METHOD_SERVICE);

        inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                InputMethodManager.HIDE_NOT_ALWAYS);

        RequestQueue queue = Volley.newRequestQueue(this);
        EditText teamSearch = (EditText)  findViewById(R.id.search_team_text);

        CreateLayoutHelper createLayoutHelper = new CreateLayoutHelper(layout, context);

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

                            JSONArray teams = jsonObject.getJSONArray("teams");
                            for (int i = 0; i<teams.length();i++){

                                JSONObject team =teams.getJSONObject(i);

                                String teamName = team.getString("strTeam");

                                String teamID = team.getString("idTeam");

                                String teamLogo = team.getString("strTeamBadge");

                                TextView text = createLayoutHelper.CreateTextFavs(teamName, teamID);
                                text.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        Intent intent = new Intent(context, TeamActivity.class);
                                        Bundle b = new Bundle();

                                        b.putString("teamName", teamName);
                                        b.putString("teamID", teamID);
                                        b.putString("teamLogo", teamLogo);

                                        intent.putExtras(b);
                                        startActivity(intent);
                                    }
                                });
                                layout.addView(text);
                            }
                        }
                        catch (JSONException e)
                        {
                            Toast.makeText(context, "No results found", Toast.LENGTH_SHORT).show();
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
                        Toast.makeText(context, "No internet connection", Toast.LENGTH_SHORT).show();
                    }
                });

        // Add the request to the RequestQueue.
        queue.add(stringRequest);
    }
}
