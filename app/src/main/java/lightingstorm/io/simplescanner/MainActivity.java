package lightingstorm.io.simplescanner;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import lightingstorm.io.simplescanner.process.Var;

public class MainActivity extends AppCompatActivity {

    private Context context;
    final String dir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + "/ImgTmp/";
    int TAKE_PHOTO_CODE = 0;
    public static int count = 0;
    boolean check=false;
    String path ;
    ArrayList<String> listitem_path = new ArrayList<>();


    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {

        super.onCreate(savedInstanceState);

        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);


        // Here, we are making a folder named picFolder to store
        // pics taken by the camera using this application.

        File newdir = new File(dir);
        newdir.mkdirs();


        LoadFile_PDF();
        ListView lv = (ListView) this.findViewById(R.id.listpdf);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String value = listitem_path.get(position);

                File file = new File(value);

                if (file.exists()) {
                    Uri path = Uri.fromFile(file);
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setDataAndType(path, "application/pdf");
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    try {
                        startActivity(intent);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        FloatingActionButton myFab = (FloatingActionButton) findViewById(R.id.btn_take_photo);
        myFab.setOnClickListener(new View.OnClickListener() {
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

    void btn_more_CLick(View v){
        //Load lại danh sách file pdf
        ListView lv = (ListView) this.findViewById(R.id.listpdf);
        File[] list_pdf = null;
        list_pdf = (new File(Environment.getExternalStorageDirectory() + File.separator + "SimpleScanner")).listFiles();

        listitem_path = new ArrayList<>();
        String[] file_name = new String[list_pdf.length];
        int count = 0;

        for (File f:
                list_pdf) {
            file_name[count] = f.getName();
            listitem_path.add(f.getAbsolutePath());
            count ++;
        }
        count = 0;
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(lv.getContext(),android.R.layout.simple_list_item_1,file_name);
        lv.setAdapter(adapter);

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


    public void LoadFile_PDF(){
        ListView lv = (ListView) this.findViewById(R.id.listpdf);
        File[] list_pdf = null;
        list_pdf = (new File(Environment.getExternalStorageDirectory() + File.separator + "SimpleScanner")).listFiles();

        listitem_path = new ArrayList<>();
        String[] file_name = new String[list_pdf.length];
        int count = 0;

        for (File f:
                list_pdf) {
            file_name[count] = f.getName();
            listitem_path.add(f.getAbsolutePath());
            count ++;
        }
        count = 0;

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(lv.getContext(),android.R.layout.simple_list_item_1,file_name);
        lv.setAdapter(adapter);

    }

    public void showPdfInWebview() {

    }

}
