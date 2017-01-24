package lightingstorm.io.simplescanner.process;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.io.ByteArrayOutputStream;
import java.util.concurrent.ExecutionException;

import lightingstorm.io.simplescanner.EffectActivity;
import lightingstorm.io.simplescanner.R;

/**
 * Created by Shin on 12/11/2016.
 */

public class ImageViewHelper_Effect extends AsyncTask<Bitmap,Bitmap,Bitmap>{

    private static ImageViewHelper_Effect instance = new ImageViewHelper_Effect();
    public static int status = -1;
    public static int rotate_left=0;
    public static int rotate_right=0;
    public static Bitmap ori;
    public static Bitmap gc;
    public static Bitmap bw;
    public static Bitmap bw_left;

    public ImageViewHelper_Effect(){}


    @Override
    protected Bitmap doInBackground(Bitmap... params) {
        if (gc==null){
            Bitmap bmpOriginal =params[0];

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

            status = 1;
            gc = Bitmap.createBitmap(bmpGrayscale,0,0,width,height);
        }
        if (bw==null) {
            Bitmap src  = params[0];
            double value = 90;

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
            for (int x = 0; x < width; x++) {
                for (int y = 0; y < height; y++) {
                    // get pixel color
                    pixel = src.getPixel(x, y);
                    A = Color.alpha(pixel);
                    // apply filter contrast for every channel R, G, B
                    R = Color.red(pixel);
                    R = (int) (((((R / 255.0) - 0.5) * contrast) + 0.5) * 255.0);
                    if (R < 0) {
                        R = 0;
                    } else if (R > 255) {
                        R = 255;
                    }

                    G = Color.red(pixel);
                    G = (int) (((((G / 255.0) - 0.5) * contrast) + 0.5) * 255.0);
                    if (G < 0) {
                        G = 0;
                    } else if (G > 255) {
                        G = 255;
                    }

                    B = Color.red(pixel);
                    B = (int) (((((B / 255.0) - 0.5) * contrast) + 0.5) * 255.0);
                    if (B < 0) {
                        B = 0;
                    } else if (B > 255) {
                        B = 255;
                    }

                    // set new pixel color to output bitmap
                    bmOut.setPixel(x, y, Color.argb(A, R, G, B));
                }
            }

            //return bmOut;

            Matrix matrix = new Matrix();
            //matrix.setRotate(-90);
            status = 2;
            bw = Bitmap.createBitmap(bmOut, 0, 0, width, height);
            if (width<height)
            {
                bw_left = Bitmap.createBitmap(bw,0,0,width,height,matrix,true);
                bw_left = Bitmap.createScaledBitmap(bw_left,(int)(height*((float)width/height)),width,true);
                //bw_left = Bitmap.createBitmap(bw,0,0,(int)(height*((float)width/height)),width,matrix,true);
            }

            else
                bw_left = Bitmap.createBitmap(bw,0,0,(int)(width*((float)height/width)),height,matrix,true);
            return bw;
        }
        return null;
    }


    public static ImageView original_save ;


    public ImageViewHelper_Effect getInstance(){
        return instance;
    }

    public Bitmap convertToOriginal(ImageView iv,EffectActivity ea){
        if (ori==null){
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

            Matrix matrix = new Matrix();;
            //matrix.setRotate(-90*rotate_left + 90*rotate_right);
            status = 0;
            ori=Bitmap.createBitmap(out,0,0,width,height,matrix,true);
            return ori;
        }
        else{
            Matrix matrix = new Matrix();
            status = 0;
            return Bitmap.createBitmap(ori, 0, 0, ori.getWidth(), ori.getHeight(), matrix, true);
        }

    }

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

            Matrix matrix = new Matrix();
            status = 1;
            gc = Bitmap.createBitmap(bmpGrayscale,0,0,width,height,matrix,true);
            return gc;
    }

    public Bitmap convertToBlackAndWhite(ImageView iv , EffectActivity ea){
    if (bw==null || bw_left==null) {

        iv.buildDrawingCache(true);
        Bitmap src = iv.getDrawingCache(true);

        double value = 100;

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
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                // get pixel color
                pixel = src.getPixel(x, y);
                A = Color.alpha(pixel);
                // apply filter contrast for every channel R, G, B
                R = Color.red(pixel);
                R = (int) (((((R / 255.0) - 0.5) * contrast) + 0.5) * 255.0);
                if (R < 0) {
                    R = 0;
                } else if (R > 255) {
                    R = 255;
                }

                G = Color.red(pixel);
                G = (int) (((((G / 255.0) - 0.5) * contrast) + 0.5) * 255.0);
                if (G < 0) {
                    G = 0;
                } else if (G > 255) {
                    G = 255;
                }

                B = Color.red(pixel);
                B = (int) (((((B / 255.0) - 0.5) * contrast) + 0.5) * 255.0);
                if (B < 0) {
                    B = 0;
                } else if (B > 255) {
                    B = 255;
                }

                // set new pixel color to output bitmap
                bmOut.setPixel(x, y, Color.argb(A, R, G, B));
            }
        }

        Matrix matrix = new Matrix();
        status = 2;
        bw = Bitmap.createBitmap(bmOut, 0, 0, width, height);
        if (width<height)
            bw_left = Bitmap.createBitmap(bw,0,0,(int)(height*((float)width/height)),width,matrix,true);
        else
            bw_left = Bitmap.createBitmap(bw,0,0,(int)(width*((float)height/width)),height,matrix,true);
        return bw;
        }
        else{
            Matrix matrix = new Matrix();
            int temp = (rotate_right-rotate_left)%4;
            if (temp%2==0){
                status=2;
                Bitmap bitmap = Bitmap.createBitmap(bw, 0, 0, bw.getWidth(), bw.getHeight(), matrix, true);
                return bitmap;
            }
            else {
                if (temp == 1 || temp == -3){
                    status = 2;
                    Bitmap bitmap = Bitmap.createBitmap(bw_left, 0, 0, bw_left.getWidth(),bw_left.getHeight(), matrix, true);
                    return bitmap;

                }
                if (temp == -1|| temp == 3){
                    status = 2;
                    Bitmap bitmap = Bitmap.createBitmap(bw_left, 0, 0,bw_left.getWidth(),bw_left.getHeight(), matrix, true);
                    return bitmap;
                }
                else{
                    status = 2;
                    Bitmap bitmap = Bitmap.createBitmap(bw_left, 0, 0,bw_left.getWidth(),bw_left.getHeight(), matrix, true);
                    return bitmap;
                }
            }
        }
    }
    public Uri getImageUri(Context inContext, Bitmap inImage) {
        Bitmap bitmapResized = Bitmap.createBitmap(inImage);
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        bitmapResized.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), bitmapResized, "Title",null);
        return Uri.parse(path);
    }
}


