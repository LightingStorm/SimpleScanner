package lightingstorm.io.simplescanner;

import android.content.ActivityNotFoundException;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.AnyRes;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.OutputStream;
import java.io.PrintWriter;
import android.view.Menu;
import android.view.MenuItem;

import com.isseiaoki.simplecropview.CropImageView;
import com.isseiaoki.simplecropview.callback.CropCallback;
import com.isseiaoki.simplecropview.callback.LoadCallback;
import com.isseiaoki.simplecropview.callback.SaveCallback;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;

import lightingstorm.io.simplescanner.process.ImageViewHelper_Effect;
import lightingstorm.io.simplescanner.process.Var;

public class CropActivity extends AppCompatActivity {
    private Context context;

    Uri imageUri;
    ImageView hinh;
    CropImageView cr;

    Bitmap picBM;
    final int CAMERA_CAPTURE = 1;
    //xử lý button
    Button fill;
    Button rotateLeft;
    Button rotateRight;

    Button btn_com;
    Button btn_back;

    CropCallback cropCallback;
    SaveCallback saveCallback;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crop);

        context=this;

        //gọi vào image trong layout

        cr = (CropImageView) findViewById(R.id.cropImageView);
        //ánh xạ đến button_fll
        fill = (Button)findViewById(R.id.btn_fill);
        rotateLeft = (Button)findViewById(R.id.btn_roundleft);
        rotateRight = (Button)findViewById(R.id.btn_roundright);
        btn_com = (Button)findViewById(R.id.btn_complete);
        btn_back = (Button) findViewById(R.id.btn_back);


        //imageUri = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE +
         //       "://" + getResources().getResourcePackageName(R.drawable.tuananh)+
         //       '/'+ getResources().getResourceTypeName(R.drawable.tuananh)+
         //       '/'+ getResources().getResourceEntryName(R.drawable.tuananh));


        //test
        BitmapDrawable bd = (BitmapDrawable)Var.iv_tranfer.getDrawable();
        Bitmap bm = bd.getBitmap();
        imageUri = getImageUri(this, bm);
        //

        /*cr.setScaleType(ImageView.ScaleType.CENTER);*/
        cr.setImageURI(imageUri);
        cr.setInitialFrameScale(0.75f);
        cr.setScaleType(ImageView.ScaleType.FIT_XY);

        /*
        imageUri = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE +
                "://" + getResources().getResourcePackageName(R.drawable.tuananh)+
                '/'+ getResources().getResourceTypeName(R.drawable.tuananh)+
                '/'+ getResources().getResourceEntryName(R.drawable.tuananh));
        */

        cr.startLoad(imageUri,new LoadCallback() {
            @Override
            public void onSuccess() {}

            @Override
            public void onError() {}}
        );


        fill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //chỉnh full set cho crop image
                cr.startLoad(imageUri,new LoadCallback() {
                    @Override
                    public void onSuccess() {}

                    @Override
                    public void onError() {}}
                );
                cr.setInitialFrameScale(1.0f);
            }
        });

        rotateLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cr.rotateImage(CropImageView.RotateDegrees.ROTATE_M90D);
            }
        });

        rotateRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cr.rotateImage(CropImageView.RotateDegrees.ROTATE_90D);
            }
        });


        btn_com.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bitmap photo = cr.getCroppedBitmap();
                Drawable drawable = new BitmapDrawable(photo);

                Var._uri = getImageUri(context,photo);
                Var.iv_tranfer.setBackground(drawable);

                Intent intent = new Intent(context,EffectActivity.class);
                startActivity(intent);
 //
                //ByteArrayOutputStream stream = new ByteArrayOutputStream();
                //cr.getCroppedBitmap().compress(Bitmap.CompressFormat.PNG,100,stream);
                //byte[] bytes = stream.toByteArray();
//                intent.putExtra("ImageCrop",cr.getCroppedBitmap());

//                startActivity(intent);
            }
        });

        //back
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder alter = new AlertDialog.Builder(CropActivity.this);
                alter.setMessage("bạn có muốn bỏ ảnh này ?").setCancelable(false).
                        setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent = new Intent(CropActivity.this, MainActivity.class);
                                startActivity(intent);
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });
                AlertDialog change = alter.create();
                change.setTitle("Thông Báo");
                change.show();
            }
        });

    }


    private void Crop(){
        //đã có ảnh từ drawable
        try{
            ImageView iv = (ImageView) this.findViewById(R.id.cropImageView);
            Uri imageUri = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE +
                    "://" + getResources().getResourcePackageName(R.drawable.tuananh)+
                    '/'+ getResources().getResourceTypeName(R.drawable.tuananh)+
                    '/'+ getResources().getResourceEntryName(R.drawable.tuananh));

            //đang lỗi từ đây
            Intent intent = new Intent("com.android.camera.action.CROP");
            intent.setDataAndType(imageUri,"image/*");
            intent.putExtra("crop","true");
            intent.putExtra("aspectX",10);
            intent.putExtra("aspectY",10);

            intent.putExtra("outputX",140);
            intent.putExtra("outputY",140);
            intent.putExtra("return","true");
            startActivityForResult(intent,2);
        }
        catch (ActivityNotFoundException anfe){
            String errorMessage = "ko có phần mềm hỗ trợ!";
            Toast toast = Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT);
            toast.show();
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 2 ){
            if(resultCode == RESULT_OK && data != null){
                Bundle extras = data.getExtras();
                Bitmap image = extras.getParcelable("data");
                hinh.setImageBitmap(image);
            }
        }
    }

    public static final Uri getUri(@NonNull Context context, @AnyRes int ID){
        Uri imageUri = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE +
        "://" + context.getResources().getResourcePackageName(ID)+
        '/'+ context.getResources().getResourceTypeName(ID)+
        '/'+ context.getResources().getResourceEntryName(ID));
        return imageUri;
    }

    //Load URI từ bitmap
    public Uri getImageUri(Context inContext, Bitmap inImage) {
        Bitmap bitmapResized = Bitmap.createBitmap(inImage);
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        bitmapResized.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), bitmapResized, "Title",null);
        return Uri.parse(path);
    }

}
