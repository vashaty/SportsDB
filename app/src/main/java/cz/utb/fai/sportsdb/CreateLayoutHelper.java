package cz.utb.fai.sportsdb;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

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
        return text;
    }
    public View CreateView ()
    {
        View view = new View(context);
        LinearLayout.LayoutParams viewParams = new LinearLayout.LayoutParams(0,0,1);
        view.setLayoutParams(viewParams);
        return view;
    }

//        upcomingLayout.addView(matchLayout);
//
//
//        matchLayout.addView(matchLayout1Line);
//
//
//
//        matchLayout1Line.addView(homeTeamText);
//
//    View view = new View(this);
//    LinearLayout.LayoutParams viewParams = new LinearLayout.LayoutParams(0,0,1);
//        matchLayout1Line.addView(view,viewParams);

//    TextView dateText = new TextView(this);
//    LinearLayout.LayoutParams dateTextParams = new LinearLayout.LayoutParams(
//            LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
//        dateText.setLayoutParams(dateTextParams);
//        dateText.setText("10 Dec 20");
//        dateText.setTextColor(Color.parseColor("#FFFFFF"));
//
//        matchLayout1Line.addView(dateText);
}
