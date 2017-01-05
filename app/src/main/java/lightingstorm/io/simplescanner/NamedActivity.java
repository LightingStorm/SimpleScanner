package lightingstorm.io.simplescanner;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
//import android.media.Image;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;

import com.itextpdf.text.Document;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.Image;

import java.util.Scanner;
import android.os.Environment;
import android.widget.TextView;

import lightingstorm.io.simplescanner.process.Count;
import lightingstorm.io.simplescanner.process.ImageViewHelper_Effect;
import lightingstorm.io.simplescanner.process.Var;

public class NamedActivity extends Activity {
    public static String FILE;
    public static File FOLDER_PDF_SCANNER;
    TextView txtFileName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_named);


        //Load imageview
        ImageView iv = (ImageView) this.findViewById(R.id.imageViewNamed);
        //Var.iv_tranfer.buildDrawingCache(true);
        iv.setImageURI(Var._uri);
        iv.setRotation(Var.iv_tranfer.getRotation());

        if (!createFolderScanner()) {

        }
    }

    //Create folder simpleScanner: folder chứa file pdf.
    public static Boolean createFolderScanner() {
        NamedActivity.FOLDER_PDF_SCANNER = new File(Environment.getExternalStorageDirectory() +
                File.separator + "SimpleScanner");
        boolean success = true;
        if (!FOLDER_PDF_SCANNER.exists()) {
            success = FOLDER_PDF_SCANNER.mkdirs();
        }
        return success;
    }

    private static void addEmptyLine(Paragraph paragraph, int number) {
        for (int i = 0; i < number; i++) {
            paragraph.add(new Paragraph(" "));
        }
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

    //Hàm save file pdf
    public void saveFilePdf() {
        try {
            Document document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream(FILE));
            document.open();

            int count= 0;
            //for (ImageView imgv :
            //      Var.list_iv) {
            ImageView iv = (ImageView) this.findViewById(R.id.imageViewNamed);
            //Var.iv_tranfer.buildDrawingCache(true);
            iv.buildDrawingCache(true);
            Bitmap img = iv.getDrawingCache(true);
            // Thêm phần xoay hình
            Matrix matrix = new Matrix();
            matrix.postRotate(-90* Count.rotate_left + 90 * Count.rotate_right);
            Bitmap scaledBitmap = Bitmap.createScaledBitmap(img,iv.getWidth(),iv.getHeight(),true);
            Bitmap rotatedBitmap = Bitmap.createBitmap(scaledBitmap , 0, 0, iv.getWidth(), iv.getHeight(), matrix, true);
            count++;
            //
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            rotatedBitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);

            Image myImg = Image.getInstance(stream.toByteArray());
            myImg.setAlignment(Image.MIDDLE);

            document.add(myImg);
            //}
            Count.rotate_left=0;
            Count.rotate_right=0;
            setNull();
            document.close();


        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void btn_checkNamed_Click(View view) {
        txtFileName = (TextView) findViewById(R.id.txt_name_file);
        NamedActivity.FILE = FOLDER_PDF_SCANNER + File.separator + txtFileName.getText().toString() + ".pdf";
        saveFilePdf();
        startActivity( (new Intent(this,MainActivity.class)).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
        //goToPdfView();
    }

    public  void openFile(){
        File filesDir = getFilesDir();
        Scanner input = new Scanner(FILE);
    }

    public void goToPdfView(){
        Intent intent = new Intent(this,PdfviewActivity.class);
        startActivity(intent);
        return;
    }
    public void goToMain(){
        
    }

    public Uri getImageUri(Context inContext, Bitmap inImage) {
        Bitmap bitmapResized = Bitmap.createBitmap(inImage);
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        bitmapResized.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), bitmapResized, "Title",null);
        return Uri.parse(path);
    }

    public void setNull(){
        ImageViewHelper_Effect.bw=null;
        ImageViewHelper_Effect.bw_left=null;
        ImageViewHelper_Effect.rotate_left=0;
        ImageViewHelper_Effect.rotate_right=0;
        ImageViewHelper_Effect.gc=null;
        ImageViewHelper_Effect.ori=null;
        ImageViewHelper_Effect.original_save=null;
        ImageViewHelper_Effect.status = -1;
    }
}
