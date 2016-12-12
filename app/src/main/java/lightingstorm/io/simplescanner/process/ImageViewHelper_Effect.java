package lightingstorm.io.simplescanner.process;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageView;

import lightingstorm.io.simplescanner.EffectActivity;
import lightingstorm.io.simplescanner.R;

/**
 * Created by Shin on 12/11/2016.
 */

public class ImageViewHelper_Effect {

    private static ImageViewHelper_Effect instance = new ImageViewHelper_Effect();

    public ImageViewHelper_Effect(){}

    public ImageViewHelper_Effect getInstance(){
        return instance;
    }

    public static ImageView original_save ;

    public Bitmap convertToGrayScale(ImageView iv,EffectActivity ea){

        if (original_save==null){
            original_save = new ImageView(ea);
            original_save = iv;
        }

        iv.buildDrawingCache();
        Bitmap bmpOriginal = iv.getDrawingCache();


        int width, height;
        height = bmpOriginal.getHeight();
        width = bmpOriginal.getWidth();



        Bitmap bmpGrayscale = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(bmpGrayscale);
        Paint paint = new Paint();
        ColorMatrix cm = new ColorMatrix();
        cm.setSaturation(0);
        ColorMatrixColorFilter f = new ColorMatrixColorFilter(cm);
        paint.setColorFilter(f);
        c.drawBitmap(bmpOriginal, 0, 0, paint);



        return bmpGrayscale;
    }


    public Bitmap convertToOriginal(ImageView iv,EffectActivity ea){
        if (original_save==null){
            original_save = new ImageView(ea);
            original_save = iv;
        }

        original_save.buildDrawingCache();
        Bitmap bmpOriginal = original_save.getDrawingCache();

        int width, height;
        height = bmpOriginal.getHeight();
        width = bmpOriginal.getWidth();

        Bitmap out = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(out);
        Paint paint = new Paint();
        ColorMatrix cm = new ColorMatrix();
        cm.setSaturation(1);
        ColorMatrixColorFilter f = new ColorMatrixColorFilter(cm);
        paint.setColorFilter(f);
        c.drawBitmap(bmpOriginal, 0, 0, paint);
        return out;
    }

    public Bitmap convertToBlackAndWhite(ImageView iv , EffectActivity ea){
        if (original_save==null){
            original_save = new ImageView(ea);
            original_save = iv;
        }

        iv.buildDrawingCache(true);
        Bitmap src = iv.getDrawingCache(true);

        double value = 20;

        int width = src.getWidth();
        int height = src.getHeight();
        // create output bitmap
        Bitmap bmOut = Bitmap.createBitmap(width, height, src.getConfig());
        // color information
        int A, R, G, B;
        int pixel;
        // get contrast value
        double contrast = Math.pow((100 + value) / 100, 2);

        // scan through all pixels
        for(int x = 0; x < width; x++) {
            for(int y = 0; y < height; y++) {
                // get pixel color
                pixel = src.getPixel(x, y);
                A = Color.alpha(pixel);
                // apply filter contrast for every channel R, G, B
                R = Color.red(pixel);
                R = (int)(((((R / 255.0) - 0.5) * contrast) + 0.5) * 255.0);
                if(R < 0) { R = 0; }
                else if(R > 255) { R = 255; }

                G = Color.red(pixel);
                G = (int)(((((G / 255.0) - 0.5) * contrast) + 0.5) * 255.0);
                if(G < 0) { G = 0; }
                else if(G > 255) { G = 255; }

                B = Color.red(pixel);
                B = (int)(((((B / 255.0) - 0.5) * contrast) + 0.5) * 255.0);
                if(B < 0) { B = 0; }
                else if(B > 255) { B = 255; }

                // set new pixel color to output bitmap
                bmOut.setPixel(x, y, Color.argb(A, R, G, B));
            }
        }

        return bmOut;
    }
}

