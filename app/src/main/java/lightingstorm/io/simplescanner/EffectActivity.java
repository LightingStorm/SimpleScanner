package lightingstorm.io.simplescanner;

import android.app.Activity;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

public class EffectActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_effect);

        Button btn_blackwhite = (Button)findViewById(R.id.btn_blackwhite);
        btn_blackwhite.setText("B&W");


    }

    public void btn_original_Click(View view){
        Button btn_grayscale = (Button) this.findViewById(R.id.btn_grayscale);
        Drawable d1 = getResources().getDrawable(R.drawable.ic_filter_drama_black_36dp);
        btn_grayscale.setCompoundDrawablesWithIntrinsicBounds(null,d1,null,null);
        btn_grayscale.setTextColor(Color.parseColor("#1A1C27"));

        Button btn_original = (Button) this.findViewById(R.id.btn_original);
        Drawable d2 = getResources().getDrawable(R.drawable.ic_photo_white_36dp);
        btn_original.setCompoundDrawablesWithIntrinsicBounds(null,d2,null,null);
        btn_original.setTextColor(Color.parseColor("#F2F2F2"));

        Button btn_blackwhite = (Button) this.findViewById(R.id.btn_blackwhite);
        Drawable d3 = getResources().getDrawable(R.drawable.ic_tonality_black_36dp);
        btn_blackwhite.setCompoundDrawablesWithIntrinsicBounds(null,d3,null,null);
        btn_blackwhite.setTextColor(Color.parseColor("#1A1C27"));
    }

    public void btn_grayscale_Click(View view){
        Button btn_grayscale = (Button) this.findViewById(R.id.btn_grayscale);
        Drawable d = getResources().getDrawable(R.drawable.ic_filter_drama_white_36dp);
        btn_grayscale.setCompoundDrawablesWithIntrinsicBounds(null,d,null,null);
        btn_grayscale.setTextColor(Color.parseColor("#F2F2F2"));

        Button btn_original = (Button) this.findViewById(R.id.btn_original);
        d = getResources().getDrawable(R.drawable.ic_photo_size_select_actual_black_36dp);
        btn_original.setCompoundDrawablesWithIntrinsicBounds(null,d,null,null);
        btn_original.setTextColor(Color.parseColor("#1A1C27"));

        Button btn_blackwhite = (Button) this.findViewById(R.id.btn_blackwhite);
        d = getResources().getDrawable(R.drawable.ic_tonality_black_36dp);
        btn_blackwhite.setCompoundDrawablesWithIntrinsicBounds(null,d,null,null);
        btn_blackwhite.setTextColor(Color.parseColor("#1A1C27"));
    }

    public void btn_blackwhite_Click(View view){
        Button btn_grayscale = (Button) this.findViewById(R.id.btn_grayscale);
        Drawable d = getResources().getDrawable(R.drawable.ic_filter_drama_black_36dp);
        btn_grayscale.setCompoundDrawablesWithIntrinsicBounds(null,d,null,null);
        btn_grayscale.setTextColor(Color.parseColor("#1A1C27"));

        Button btn_original = (Button) this.findViewById(R.id.btn_original);
        d = getResources().getDrawable(R.drawable.ic_photo_size_select_actual_black_36dp);
        btn_original.setCompoundDrawablesWithIntrinsicBounds(null,d,null,null);
        btn_original.setTextColor(Color.parseColor("#1A1C27"));

        Button btn_blackwhite = (Button) this.findViewById(R.id.btn_blackwhite);
        d = getResources().getDrawable(R.drawable.ic_tonality_white_36dp);
        btn_blackwhite.setCompoundDrawablesWithIntrinsicBounds(null,d,null,null);
        btn_blackwhite.setTextColor(Color.parseColor("#F2F2F2"));
    }
}
