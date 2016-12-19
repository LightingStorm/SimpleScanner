package lightingstorm.io.simplescanner;

import android.app.Activity;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;

import lightingstorm.io.simplescanner.process.ImageViewHelper_Effect;

public class EffectActivity extends Activity {

    //public ImageView original = (ImageView) this.findViewById(R.id.imageView);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_effect);

    }

    public void btn_original_Click(View view){
        Button btn_grayscale = (Button) this.findViewById(R.id.btn_grayscale);
        Drawable d1 = getResources().getDrawable(R.drawable.ic_filter_drama_black_24dp);
        btn_grayscale.setCompoundDrawablesWithIntrinsicBounds(null,d1,null,null);
        btn_grayscale.setTextColor(getResources().getColor(R.color.BlackRusian));

        Button btn_original = (Button) this.findViewById(R.id.btn_original);
        Drawable d2 = getResources().getDrawable(R.drawable.ic_photo_white_24dp);
        btn_original.setCompoundDrawablesWithIntrinsicBounds(null,d2,null,null);
        btn_original.setTextColor(getResources().getColor(R.color.WhiteSmoke));

        Button btn_blackwhite = (Button) this.findViewById(R.id.btn_blackwhite);
        Drawable d3 = getResources().getDrawable(R.drawable.ic_tonality_black_24dp);
        btn_blackwhite.setCompoundDrawablesWithIntrinsicBounds(null,d3,null,null);
        btn_blackwhite.setTextColor(getResources().getColor(R.color.BlackRusian));

        //Process
        ImageView iv = (ImageView) this.findViewById(R.id.imageView);
        BitmapDrawable ob = new BitmapDrawable(getResources(), (new ImageViewHelper_Effect().convertToOriginal(iv,this)));
        iv.setBackgroundDrawable(ob);

    }

    public void btn_grayscale_Click(View view){
        Button btn_grayscale = (Button) this.findViewById(R.id.btn_grayscale);
        Drawable d = getResources().getDrawable(R.drawable.ic_filter_drama_white_24dp);
        btn_grayscale.setCompoundDrawablesWithIntrinsicBounds(null,d,null,null);
        btn_grayscale.setTextColor(getResources().getColor(R.color.WhiteSmoke));

        Button btn_original = (Button) this.findViewById(R.id.btn_original);
        d = getResources().getDrawable(R.drawable.ic_photo_size_select_actual_black_24dp);
        btn_original.setCompoundDrawablesWithIntrinsicBounds(null,d,null,null);
        btn_original.setTextColor(getResources().getColor(R.color.BlackRusian));

        Button btn_blackwhite = (Button) this.findViewById(R.id.btn_blackwhite);
        d = getResources().getDrawable(R.drawable.ic_tonality_black_24dp);
        btn_blackwhite.setCompoundDrawablesWithIntrinsicBounds(null,d,null,null);
        btn_blackwhite.setTextColor(getResources().getColor(R.color.BlackRusian));

        //Process
        ImageView iv = (ImageView) this.findViewById(R.id.imageView);
        BitmapDrawable ob = new BitmapDrawable(getResources(), (new ImageViewHelper_Effect().convertToGrayScale(iv,this)));
        iv.setBackgroundDrawable(ob);

    }

    public void btn_blackwhite_Click(View view){
        Button btn_grayscale = (Button) this.findViewById(R.id.btn_grayscale);
        Drawable d = getResources().getDrawable(R.drawable.ic_filter_drama_black_24dp);
        btn_grayscale.setCompoundDrawablesWithIntrinsicBounds(null,d,null,null);
        btn_grayscale.setTextColor(getResources().getColor(R.color.BlackRusian));

        Button btn_original = (Button) this.findViewById(R.id.btn_original);
        d = getResources().getDrawable(R.drawable.ic_photo_size_select_actual_black_24dp);
        btn_original.setCompoundDrawablesWithIntrinsicBounds(null,d,null,null);
        btn_original.setTextColor(getResources().getColor(R.color.BlackRusian));

        Button btn_blackwhite = (Button) this.findViewById(R.id.btn_blackwhite);
        d = getResources().getDrawable(R.drawable.ic_tonality_white_24dp);
        btn_blackwhite.setCompoundDrawablesWithIntrinsicBounds(null,d,null,null);
        btn_blackwhite.setTextColor(getResources().getColor(R.color.WhiteSmoke));

        ImageView iv = (ImageView) this.findViewById(R.id.imageView);

        BitmapDrawable ob = new BitmapDrawable(getResources(),(new ImageViewHelper_Effect()).convertToBlackAndWhite(iv,this));
        iv.setBackgroundDrawable(ob);
    }

    public void btn_rotateleft(View view){
        ImageView iv = (ImageView) this.findViewById(R.id.imageView);
        //iv.setImageBitmap(new ImageViewHelper_Effect().rotateImage(iv,this,270));

        BitmapDrawable ob = new BitmapDrawable(getResources(),(new ImageViewHelper_Effect()).rotateleftImage(iv,this));
        iv.setBackgroundDrawable(null);
        iv.setBackgroundDrawable(ob);
    }

    public void btn_rotateright(View view){
        ImageView iv = (ImageView) this.findViewById(R.id.imageView);
        //iv.setImageBitmap(new ImageViewHelper_Effect().rotateImage(iv,this,270));

        BitmapDrawable ob = new BitmapDrawable(getResources(),(new ImageViewHelper_Effect()).rotaterightImage(iv,this));
        iv.setBackgroundDrawable(null);
        iv.setBackgroundDrawable(ob);
    }

}
