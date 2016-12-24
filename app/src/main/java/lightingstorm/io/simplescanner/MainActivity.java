package lightingstorm.io.simplescanner;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.hardware.*;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.isseiaoki.simplecropview.CropImageView;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.util.Timer;
import java.util.TimerTask;

import lightingstorm.io.simplescanner.process.Var;

import static lightingstorm.io.simplescanner.R.id.imageView;

public class MainActivity extends AppCompatActivity {
    
    private Context context;
    final String dir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + "/ImgTmp/";
    int TAKE_PHOTO_CODE = 0;
    public static int count = 0;
    boolean check=false;
    String path ;


    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Here, we are making a folder named picFolder to store
        // pics taken by the camera using this application.

        File newdir = new File(dir);
        newdir.mkdirs();

        Button capture = (Button) findViewById(R.id.btn_take_photo);
        capture.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                //Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                //startActivityForResult(cameraIntent, TAKE_PHOTO_CODE);
                // Here, the counter will be incremented each time, and the
                // picture taken by camera will be stored as 1.jpg,2.jpg
                // and likewise.

                count++;
                String file = dir + count + ".jpg";
                File newfile = new File(file);
                try {
                    newfile.createNewFile();
                } catch (IOException e) {
                }

                Uri outputFileUri = Uri.fromFile(newfile);
                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri);
                path = file;
                startActivityForResult(cameraIntent, TAKE_PHOTO_CODE);
            }
        });



    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == TAKE_PHOTO_CODE && resultCode == Activity.RESULT_OK) {
            //Đọc file từ path , chuyển file sang dạng Bitmap
            File image = new File(path);
            BitmapFactory.Options bmOptions = new BitmapFactory.Options();
            Bitmap bitmap = BitmapFactory.decodeFile(image.getAbsolutePath(),bmOptions);
            //delete
            image.deleteOnExit();

            //Lưu Bitmap vào Var (biến tạm)
            Var.iv_tranfer = new ImageView(this);
            Var.iv_tranfer.setImageBitmap(bitmap);
            Drawable bg = new BitmapDrawable(bitmap);
            Var.iv_tranfer.setBackgroundDrawable(bg);

            //Chuyển sang màn hình Crop
            Intent intent = new Intent(this,CropActivity.class);
            startActivity(intent);
        }
    }




}
