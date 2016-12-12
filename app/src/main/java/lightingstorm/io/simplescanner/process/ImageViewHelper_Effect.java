package lightingstorm.io.simplescanner.process;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import lightingstorm.io.simplescanner.EffectActivity;
import lightingstorm.io.simplescanner.R;

/**
 * Created by Shin on 12/11/2016.
 */

public class ImageViewHelper_Effect {

    public Bitmap convertToGrayScale(ImageView iv,EffectActivity ea){
        iv.buildDrawingCache();
        Bitmap bmpOriginal = iv.getDrawingCache();
        //ColorMatrix matrix = new ColorMatrix();
        //matrix.setSaturation(0);  //0 means grayscale
        //ColorMatrixColorFilter cf = new ColorMatrixColorFilter(matrix);
        //iv.setColorFilter(cf);
        //iv.setAlpha(128);   // 128 = 0.5

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
}
