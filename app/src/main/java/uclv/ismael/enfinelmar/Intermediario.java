package uclv.ismael.enfinelmar;

/**
 * Created by SERGIO-PC on 15/04/2019.
 */

import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.webkit.JavascriptInterface;
import android.widget.Toast;

/**
 * Created by SERGIO-PC on 08/02/2019.
 *  < com.github.barteksc.pdfviewer.PDFView
 android:id="@+id/pdfView"
 android:layout_width="368dp"
 android:layout_height="495dp"
 tools:layout_editor_absoluteY="8dp"
 tools:layout_editor_absoluteX="8dp" /-->


 */

public class Intermediario {
    MainActivity mContext;
    Intermediario(MainActivity c) {
        mContext = c;
    }

    @JavascriptInterface
    public void showToast(String pano) {
        Toast.makeText(mContext, pano, Toast.LENGTH_SHORT).show();
    }

    @JavascriptInterface
    public void showInfo(String pano) {
        sendMessage("info", pano);
    }

    @JavascriptInterface
    public void showScreen(String pano) {
        sendMessage("screen", pano);
    }

    @JavascriptInterface
    public void showPDF(String pano) {
        sendMessage("pdf", pano);
    }

    // Send an Intent with an action named "my-event".
    private void sendMessage(String type, String pano) {
        Intent intent = new Intent("my-event");
        // add data
        intent.putExtra("type", type);
        intent.putExtra("message", pano);

        LocalBroadcastManager.getInstance(mContext).sendBroadcast(intent);
    }

}