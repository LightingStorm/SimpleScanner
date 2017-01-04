package lightingstorm.io.simplescanner;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Window;
import android.widget.ImageView;

import com.itextpdf.text.Document;
import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.PdfImportedPage;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.File;
import java.io.FileOutputStream;
//import android.media.Image;

public class PdfviewActivity extends Activity {
    private static String INPUTFILE = NamedActivity.FILE;
    private static String OUTPUTFILE = NamedActivity.FOLDER_PDF_SCANNER.getPath() + "test.pdf";

    public ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        showPdfInWebview();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if ((keyCode == KeyEvent.KEYCODE_BACK))
        {
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }




    public void showPdf() {
        try {
            Document document = new Document();

            PdfWriter writer = PdfWriter.getInstance(document,
                    new FileOutputStream(OUTPUTFILE));
            document.open();
            PdfReader reader = new PdfReader(INPUTFILE);
            int n = reader.getNumberOfPages();
            PdfImportedPage page;
            // Go through all pages
            for (int i = 1; i <= n; i++) {
                // Only page number 2 will be included
                if (i == 1) {
                    page = writer.getImportedPage(reader, i);

                    Image instance = Image.getInstance(page);
                    Bitmap bitmap = Bitmap.createBitmap(Math.round(instance.getWidth()), Math.round(instance.getHeight()), Bitmap.Config.ARGB_8888);
                    // here you can show image on your phone
                    writer.add(instance);
                    imageView = (ImageView) this.findViewById(R.id.imgv_pdfView);
                    imageView.setImageBitmap(bitmap);
                }
            }
            document.close();

        } catch (Exception e) {
            e.fillInStackTrace();
        }

    }

    public void showPdfInWebview() {
        File file = new File(INPUTFILE);

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
}
