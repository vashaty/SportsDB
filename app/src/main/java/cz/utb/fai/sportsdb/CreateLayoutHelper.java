package cz.utb.fai.sportsdb;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;

public class CreateLayoutHelper extends AppCompatActivity {
    LinearLayout upcomingLayout;
    Context context;
    public CreateLayoutHelper(LinearLayout upcomingLayout, Context context){
        this.upcomingLayout = upcomingLayout;
        this.context= context;
    }
    public LinearLayout CreateUpcomingLayout ()
    {
        //////////UPCOMING MATCH LAYOUT///////////////////
        LinearLayout matchLayout = new LinearLayout(context);
        matchLayout.setOrientation(LinearLayout.VERTICAL);
        LinearLayout.LayoutParams matchParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        matchParams.setMargins(0,0,0,10);
        matchLayout.setLayoutParams(matchParams);
        /////////////////////////////////////////
        return matchLayout;
    }
    public LinearLayout CreateLineLayout ()
    {
        //////////1 LINE OF UPCOMING MATCH LAYOUT///////////////////
        LinearLayout matchLayoutLine = new LinearLayout(context);
        LinearLayout.LayoutParams matchParamsLine = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        matchLayoutLine.setOrientation(LinearLayout.HORIZONTAL);
        matchLayoutLine.setBackgroundColor(Color.parseColor("#0c3114"));
        matchParamsLine.gravity = Gravity.BOTTOM;
        matchLayoutLine.setLayoutParams(matchParamsLine);
        //////////////////////////////////////////////////////////
        return matchLayoutLine;
    }
    public TextView CreateText (String textString)
    {
        TextView text = new TextView(context);
        LinearLayout.LayoutParams homeTeamTextParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        text.setLayoutParams(homeTeamTextParams);
        text.setText(textString);
        text.setTextColor(Color.parseColor("#FFFFFF"));
        text.setPadding(10,4,10,4);
        text.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 16.5F);
        return text;
    }

    public TextView CreateTextFavs (String textString, String id)
    {
        TextView text = new TextView(context);
        LinearLayout.LayoutParams homeTeamTextParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        text.setLayoutParams(homeTeamTextParams);
        text.setText(textString);
        text.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 25);
        text.setBackground(AppCompatResources.getDrawable(context, R.drawable.stroke));
        text.setTextColor(Color.parseColor("#FFFFFF"));

        return text;
    }

    public View CreateView ()
    {
        View view = new View(context);
        LinearLayout.LayoutParams viewParams = new LinearLayout.LayoutParams(0,0,1);
        view.setLayoutParams(viewParams);
        return view;
    }

}
