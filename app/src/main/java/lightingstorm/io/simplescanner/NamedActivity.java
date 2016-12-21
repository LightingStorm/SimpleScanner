package lightingstorm.io.simplescanner;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import lightingstorm.io.simplescanner.process.Var;

public class NamedActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_named);

        //Load imageview
        ImageView iv = (ImageView) this.findViewById(R.id.imageViewNamed);
        iv.setImageBitmap(convertToBitmap(Var.iv_tranfer.getBackground()));
    }

    //Hàm Convert Drawable -> Bitmap
    public Bitmap convertToBitmap(Drawable drawable) {
        BitmapDrawable bd = (BitmapDrawable) drawable;
        Bitmap mutableBitmap = Bitmap.createBitmap(bd.getBitmap().getWidth(), bd.getBitmap().getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(mutableBitmap);
        drawable.setBounds(0, 0, bd.getBitmap().getWidth(), bd.getBitmap().getHeight());
        drawable.draw(canvas);
        return mutableBitmap;
    }
}
