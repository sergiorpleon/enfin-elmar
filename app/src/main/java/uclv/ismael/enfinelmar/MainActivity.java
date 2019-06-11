package uclv.ismael.enfinelmar;

import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Toast;

import com.github.barteksc.pdfviewer.PDFView;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    WebView myBrowser;
    String actual = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();


        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        /*
        PDFView pdf = (PDFView) findViewById(R.id.pdfView);

        pdf.fromAsset("file.pdf").load();
        */
        myBrowser = (WebView)findViewById(R.id.webview);

        //activamos javascript
        WebSettings settings = myBrowser.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setDomStorageEnabled(true);

        myBrowser.loadUrl("file:///android_asset/site/index.html");
        actual = "file:///android_asset/site/index.html";

        myBrowser.addJavascriptInterface(new Intermediario(this), "Android");


    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            if( actual.equalsIgnoreCase("file:///android_asset/site/index.html")){
                super.onBackPressed();
            }else{
                myBrowser.loadUrl("file:///android_asset/site/index.html");

            }
            actual = "file:///android_asset/site/index.html";

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            crearDialogoAlerta();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_inicio) {
            // Handle the camera action
            myBrowser.loadUrl("file:///android_asset/site/index.html");
            actual = "file:///android_asset/site/index.html";
        } else if (id == R.id.nav_manglar) {
            // Handle the camera action
            myBrowser.loadUrl("file:///android_asset/site/page1.html");
            actual = "lolo";

        } else if (id == R.id.nav_pasto) {
            myBrowser.loadUrl("file:///android_asset/site/page2.html");
            actual = "lolo";

        } else if (id == R.id.nav_arrecife) {
            myBrowser.loadUrl("file:///android_asset/site/page3.html");
            actual = "lolo";

        } else if (id == R.id.nav_enfrentamiento) {
            myBrowser.loadUrl("file:///android_asset/site/page4.html");
            actual = "lolo";

        } else if (id == R.id.nav_corredores) {
            myBrowser.loadUrl("file:///android_asset/site/page12.html");
            actual = "lolo";

        } else if (id == R.id.nav_relaciones) {
            Intent info = new Intent(this, ViewActivity.class);
            info.putExtra("page","relaciones-entre-los-ecosistemas-marinos-y-costeros");
            startActivity(info);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onResume() {
        super.onResume();

        // Register mMessageReceiver to receive messages.
        LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiver,
                new IntentFilter("my-event"));
    }

    @Override
    protected void onPause() {
        // Unregister since the activity is not visible
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mMessageReceiver);
        super.onPause();
    }

    // handler for received Intents for the "my-event" event
    private BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            // Extract data included in the Intent
            String type = intent.getStringExtra("type");
            String message = intent.getStringExtra("message");

            switch (type){
                case "screen":
                    //Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
                    myBrowser.loadUrl("file:///android_asset/site/"+message+".html");
                    actual = "file:///android_asset/site/"+message+".html";
                    break;

                case "info":
                    //Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
                    //myBrowser.loadUrl("file:///android_asset/web/preview/"+message+".html");

                    Intent info = new Intent(context, ViewActivity.class);
                    info.putExtra("page",message);
                    startActivity(info);
                    break;

                case "pdf":
                    //Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
                    //myBrowser.loadUrl("file:///android_asset/web/preview/"+message+".html");
                    Intent pdf = new Intent(context, PDFActivity.class);
                    pdf.putExtra("pdf",message);
                    startActivity(pdf);
                    break;

                default:
                    Log.d("receiver", "Got message: " + message);
                    //myBrowser.loadUrl("file:///android_asset/biblioteca.html");
                    break;
            }


        }
    };

    private void crearDialogoAlerta() {
        //Creacion del Dialogo
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        //inflando el contenido con un layout;
        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.layout_creditos,(ViewGroup) findViewById(R.id.lytLayout));
        builder.setView(layout);

        builder.setPositiveButton("Regresar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        Dialog dialogo = builder.create();
        dialogo.show();
    }
}
