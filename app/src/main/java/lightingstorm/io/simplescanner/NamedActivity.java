package lightingstorm.io.simplescanner;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
//import android.media.Image;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;


import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.Image;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.Scanner;

import com.itextpdf.text.Anchor;
import com.itextpdf.text.BadElementException;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chapter;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.List;
import com.itextpdf.text.ListItem;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Section;
import com.itextpdf.text.pdf.PdfImportedPage;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfWriter;

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.widget.TextView;

import lightingstorm.io.simplescanner.process.Var;

public class NamedActivity extends Activity {
    private static String FILE;
    private static File FOLDER_PDF_SCANNER;
    TextView txtFileName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_named);


        //Load imageview
        ImageView iv = (ImageView) this.findViewById(R.id.imageViewNamed);
        iv.setImageBitmap(convertToBitmap(Var.iv_tranfer.getBackground()));

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

            for (ImageView imgv :
                    Var.list_iv) {
                Bitmap img = convertToBitmap(imgv.getBackground());
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                img.compress(Bitmap.CompressFormat.JPEG, 100, stream);

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

    }

    public  void openFile(){
        File filesDir = getFilesDir();
        Scanner input = new Scanner(FILE);
    }

    public void goToMain(){
        
    }

}
