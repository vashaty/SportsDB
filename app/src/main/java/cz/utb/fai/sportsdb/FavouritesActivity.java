package cz.utb.fai.sportsdb;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Map;

public class FavouritesActivity extends MenuActivity {
    Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourites);
        LinearLayout layout = findViewById(R.id.activity_favouritesLayout);
        context = this;
        CreateLayoutHelper layoutHelper = new CreateLayoutHelper(layout, context);

        String SHARED_PREFERENCES_FILE_NAME = "appFileInternal";
        SharedPreferences sharedPreferences = this.getSharedPreferences(SHARED_PREFERENCES_FILE_NAME, MODE_PRIVATE);

        Map<String, ?> allEntries = sharedPreferences.getAll();
        for (Map.Entry<String, ?> entry : allEntries.entrySet()) {
            String teamName = entry.getValue().toString().split(";")[0];
            TextView text = layoutHelper.CreateTextFavs(teamName, entry.getKey());
            text.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, TeamActivity.class);
                    Bundle b = new Bundle();

                    b.putString("teamName", teamName);
                    b.putString("teamID", entry.getKey());
                    b.putString("teamLogo", entry.getValue().toString().split(";")[1]);

                    intent.putExtras(b);
                    startActivity(intent);
                }
            });
            layout.addView(text);
        }
    }

}