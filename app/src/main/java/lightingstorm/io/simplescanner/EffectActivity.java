package lightingstorm.io.simplescanner;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.io.ByteArrayOutputStream;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ThreadFactory;

import lightingstorm.io.simplescanner.process.ImageViewHelper_Effect;
import lightingstorm.io.simplescanner.process.Var;

public class EffectActivity extends Activity {

    private boolean first=false;
    private double scalepercent;
    private int width=0;
    private int height=0;
    private boolean isA4 = false;
    //public ImageView original = (ImageView) this.findViewById(R.id.imageView);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_effect);

        ImageView iv = (ImageView) this.findViewById(R.id.imageView);
        //iv.setBackgroundDrawable(Var.iv_tranfer.getBackground());
        iv.setImageURI(Var._uri);

        width=((ImageView)this.findViewById(R.id.imageView)).getWidth();
        height=((ImageView)this.findViewById(R.id.imageView)).getHeight();

    }

    public void btn_original_Click(View view) throws ExecutionException, InterruptedException {

        ImageView iv = (ImageView) this.findViewById(R.id.imageView);
        iv.buildDrawingCache();
        Bitmap bmp = iv.getDrawingCache();
        Bitmap test = (new ImageViewHelper_Effect().execute(bmp).get());

        if (!first)
        {
            first = !first;
            Thread.sleep(500);
        }



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

        BitmapDrawable ob = new BitmapDrawable(getResources(), (new ImageViewHelper_Effect().convertToOriginal(iv,this)));
        iv.setBackgroundDrawable(ob);
        iv.setImageURI(null);

    }

    public void btn_grayscale_Click(View view) throws InterruptedException, ExecutionException {


        ImageView iv = (ImageView) this.findViewById(R.id.imageView);
        iv.buildDrawingCache();
        Bitmap bmp = iv.getDrawingCache();
        Bitmap test = (new ImageViewHelper_Effect().execute(bmp).get());
        if (!first)
        {
            first = !first;
            Thread.sleep(500);
        }


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

        BitmapDrawable ob = new BitmapDrawable(getResources(), (new ImageViewHelper_Effect().convertToGrayScale(iv,this)));
        iv.setBackgroundDrawable(ob);
        iv.setImageURI(null);
        //iv.setImageURI((new ImageViewHelper_Effect()).getImageUri(this,(new ImageViewHelper_Effect()).convertToGrayScale(iv,this)));
    }

    public void btn_blackwhite_Click(View view) throws InterruptedException, ExecutionException {

        ImageView iv = (ImageView) this.findViewById(R.id.imageView);
        iv.buildDrawingCache();
        Bitmap bmp = iv.getDrawingCache();
        Bitmap test = (new ImageViewHelper_Effect().execute(bmp).get());
        if (!first)
        {
            first = !first;
            Thread.sleep(500);
        }

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



        BitmapDrawable ob = new BitmapDrawable(getResources(),(new ImageViewHelper_Effect()).convertToBlackAndWhite(iv,this));
        iv.setBackgroundDrawable(ob);
        iv.setImageURI(null);
    }

    public void btn_rotateleft(View view) throws ExecutionException, InterruptedException {


        if (!first)
        {
            first = !first;
            btn_original_Click(view);
        }

        ImageView iv = (ImageView) this.findViewById(R.id.imageView);
        ImageViewHelper_Effect.rotate_left++;
        iv.setRotation(-90*ImageViewHelper_Effect.rotate_left+ 90*ImageViewHelper_Effect.rotate_right);

        //BitmapDrawable ob = new BitmapDrawable(getResources(),(new ImageViewHelper_Effect()).rotateleftImage(iv,this));
        //iv.setBackgroundDrawable(null);
        //iv.setBackgroundDrawable(ob);

    }

    public void btn_rotateright(View view) throws ExecutionException, InterruptedException {
        if (!first)
        {
            first = !first;
            btn_original_Click(view);
        }

        ImageView iv = (ImageView) this.findViewById(R.id.imageView);
        ImageViewHelper_Effect.rotate_right++;
        iv.setRotation(-90*ImageViewHelper_Effect.rotate_left+ 90*ImageViewHelper_Effect.rotate_right);

        //BitmapDrawable ob = new BitmapDrawable(getResources(),(new ImageViewHelper_Effect()).rotaterightImage(iv,this));
        //iv.setBackgroundDrawable(null);
        //iv.setBackgroundDrawable(ob);
    }

    public void scaleToA4(View view){
        ImageView iv = (ImageView) this.findViewById(R.id.imageView);
        Button btn_A4 = (Button)this.findViewById(R.id.btn_a4);
        if (isA4) {
            isA4 = !isA4;
            btn_A4.setTextColor(getResources().getColor(R.color.WhiteSmoke));
            btn_A4.setBackgroundColor(Color.TRANSPARENT);

        }
        else{
            isA4 = !isA4;
            height = (int) ((double)iv.getHeight()/scalepercent);
            btn_A4.setTextColor(getResources().getColor(R.color.MidnightExpress));
            btn_A4.setBackgroundColor(getResources().getColor(R.color.WhiteSmoke));
        }
    }

    public void btn_back(View view){
        ImageView iv = (ImageView) this.findViewById(R.id.imageView);

        //for (ImageView item:Var.list_iv) {
        //   if (item.getBackground() == iv.getBackground())
        //    Log.d("OK","OK");
        //}

        ImageViewHelper_Effect.bw=null;
        ImageViewHelper_Effect.bw_left=null;
        ImageViewHelper_Effect.rotate_left=0;
        ImageViewHelper_Effect.rotate_right=0;
        ImageViewHelper_Effect.gc=null;
        ImageViewHelper_Effect.ori=null;
        ImageViewHelper_Effect.original_save=null;
        ImageViewHelper_Effect.status = -1;

        finish();

        //Intent goToNextActivity = new Intent(getApplicationContext(), CropActivity.class);
        //startActivity(goToNextActivity);
    }

    public void btn_checkmark(View view){
        ImageView iv = (ImageView) this.findViewById(R.id.imageView);

        if (!isA4) {
            width = iv.getWidth();
            height = iv.getHeight();

        }
        else{
            double scalepercent = (double)iv.getWidth() / 595.0;
            width = 595;
            height = (int) ((double)iv.getHeight()/scalepercent);
        }

        Drawable dr = iv.getBackground();
        Bitmap b = ((BitmapDrawable)dr).getBitmap();
        Bitmap bitmapResized = Bitmap.createScaledBitmap(b, width, height, true);
        dr = new BitmapDrawable(bitmapResized);

        Var.iv_tranfer = new ImageView(this);
        Var.iv_tranfer.setBackgroundDrawable(dr);
        Var.iv_tranfer.setRotation(iv.getRotation());
        Var.list_iv.add(Var.iv_tranfer);

        Intent intent = new Intent(this,NamedActivity.class);
        startActivity(intent);
    }

    public Uri getImageUri(Context inContext, Bitmap inImage) {
        Bitmap bitmapResized = Bitmap.createBitmap(inImage);
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        bitmapResized.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), bitmapResized, "Title",null);
        return Uri.parse(path);
    }



}
