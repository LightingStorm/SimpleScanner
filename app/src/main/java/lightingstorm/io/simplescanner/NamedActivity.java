package lightingstorm.io.simplescanner;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
//import android.media.Image;
import android.os.Build;
import android.os.Bundle;
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
        iv.setImageBitmap(convertToBitmap(Var.iv_tranfer.getBackground()));
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
            for (ImageView imgv :
                    Var.list_iv) {
                Bitmap img = convertToBitmap(imgv.getBackground());
                // Thêm phần xoay hình
                Matrix matrix = new Matrix();
                matrix.postRotate(-90*Var.list_count.get(count).rotate_left + 90 * Var.list_count.get(count).rotate_right);
                Bitmap scaledBitmap = Bitmap.createScaledBitmap(img,img.getHeight(),img.getWidth(),true);
                Bitmap rotatedBitmap = Bitmap.createBitmap(scaledBitmap , 0, 0, scaledBitmap .getWidth(), scaledBitmap .getHeight(), matrix, true);
                count++;
                //
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                rotatedBitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);

                Image myImg = Image.getInstance(stream.toByteArray());
                myImg.setAlignment(Image.MIDDLE);

                document.add(myImg);
            }

            document.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void btn_checkNamed_Click(View view) {
        txtFileName = (TextView) findViewById(R.id.txt_name_file);
        NamedActivity.FILE = FOLDER_PDF_SCANNER + File.separator + txtFileName.getText().toString() + ".pdf";
        saveFilePdf();
        goToPdfView();
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

}
