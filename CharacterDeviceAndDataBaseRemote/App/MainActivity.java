package com.example.ferne_000.requeriments;

import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestHandle;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class MainActivity extends AppCompatActivity {


    private TextView tv1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv1 = (TextView)findViewById(R.id.tv1);
    }

    @Override
    protected void onResume() {
        super.onResume();
        conToServerAndProcees();
    }
    public void conToServerAndProcees(){

        AsyncHttpClient myClient = new AsyncHttpClient();
        String url="http://systemxlr.96.lt/Server/theRequeriments.php";
        RequestParams requestParams = new RequestParams();
        // DATOS QUE SE SENVIAR AL SERVIDOR
        //PRIMER PARAMETRO COMO ESTA EN PHP EL VALOS EN POST, GET REQUEST
        //SEGUNDO PARAMETRO EL VALOR QUE ENVIO
        requestParams.add("sendSerial", Build.SERIAL);
        requestParams.add("sendBrand", Build.BRAND);
        requestParams.add("sendDevice", Build.DEVICE);
        requestParams.add("sendID", Build.ID);
        requestParams.add("sendManf",Build.MANUFACTURER);
        requestParams.add("sendProduct",Build.PRODUCT);



        RequestHandle post = myClient.post(url, requestParams, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String respuesta;
                if (statusCode == 200) { //CODIGO DIFERENTE DEL 404 QUE ENCUENTRA LA PAGINA
                    try{
                        JSONObject o = new JSONObject(new String(responseBody));
                        //tye foreach
                        //OBETENGO LA RESPUESTA DEL SERVIDOR
                        respuesta = o.getString("aceptado"); //OBJETO JSON 'ACEPTADO' CON VALOR SI O NO
                        if(respuesta.equals("VALIDO")) { // SI ES SI IMPRIMIR CARACTERSITICAS
                            tv1.setText("VALIDO\n\nSerial: " + Build.SERIAL + "\n" +
                                        "Brand: "  + Build.BRAND + "\n" +
                                        "Device: " +Build.DEVICE + "\n" +
                                        "ID: "     + Build.ID + "\n" +
                                        "Manufacturer: " + Build.MANUFACTURER + "\n" +
                                        "Product: "+ Build.PRODUCT);
                        }else{
                            tv1.setText("ACCESO DENEGADO ");
                        }
                    }catch(JSONException e){
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }
        });

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
