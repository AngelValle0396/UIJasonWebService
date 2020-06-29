package com.example.uijasonwebservice;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.wifi.p2p.WifiP2pManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.service.voice.VoiceInteractionService;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.uijasonwebservice.Retrofit.ResponseRetrofit;
import com.example.uijasonwebservice.Retrofit.ServicesMain;
import com.example.uijasonwebservice.WebService.Asynchtask;
import com.example.uijasonwebservice.WebService.WebService;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity /*implements Asynchtask*/ {
    private EditText txtNombre, txtPASS;
    private TextView txtBancos;
    private Bundle b;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txtNombre=(EditText)findViewById(R.id.txtNombre);
        txtPASS= (EditText)findViewById(R.id.txtPass);
        b = this.getIntent().getExtras();
        //Cargar lista de bancos con Retrofit
        getService();

        //WebService method Cargar lista de bancos-Practica
        /* Map<String, String> datos = new HashMap<String, String>();
        WebService ws= new WebService("https://api-uat.kushkipagos.com/transfer-subscriptions/v1/bankList",
                datos,  this, this);
        ws.execute("GET","Public-Merchant-Id","84e1d0de1fbf437e9779fd6a52a9ca18"); */   }

        public void btEnviar(View view){
        Intent intent = new Intent(MainActivity.this, ValidarLogin.class);
        b = new Bundle();
        if (txtNombre.getText().length()==0||txtPASS.getText().length()==0){
            Toast.makeText(getApplicationContext(), "Debe ingresar datos en ambos campos",Toast.LENGTH_LONG).show();
        }
        else   {
            b.putString("usr",   txtNombre.getText().toString());
            b.putString("pass", txtPASS.getText().toString());
            intent.putExtras(b);
            startActivity(intent);
        }
    }

    //funcion para parsear el json y mostrar en el textview-Practica
   /* @Override
    public void processFinish(String result) throws JSONException {

         txtBancos = (TextView)findViewById(R.id.txtListaBancos);
        String lstBancos="";
        JSONArray JSONlista =  new JSONArray(result);
        for(int i = 0; i< JSONlista.length(); i++){
            JSONObject banco=  JSONlista.getJSONObject(i);
            lstBancos= lstBancos +"\n" +banco.getString("name").toString();
        }
        //txtBancos.setText("Respuesta WS: " + lstBancos);
        //txtBancos.setText("Respuesta WS: " + result);
        //bundle.getString("NOMBRE")+ "\n CLAVE:"+bundle.getString("PASS"));
    }
*/
    private void getService() {
        String url ="https://api-uat.kushkipagos.com/transfer-subscriptions/v1/";
        txtBancos = (TextView)findViewById(R.id.txtListaBancos);
        Retrofit retrofit= new Retrofit.Builder().baseUrl(url).addConverterFactory(GsonConverterFactory.create()).build();
        ServicesMain servicios = retrofit.create(ServicesMain.class);
        Call<List<ResponseRetrofit>> response= servicios.getNamesBanks("84e1d0de1fbf437e9779fd6a52a9ca18");
        response.enqueue(new Callback<List<ResponseRetrofit>>() {
            @Override
            public void onResponse(Call<List<ResponseRetrofit>> call, Response<List<ResponseRetrofit>> response) {
                if (!response.isSuccessful()){
                    txtBancos.setText("Código: "+ response.code());
                return;}
                String contenido="";
                for (ResponseRetrofit lista:response.body()) {
                    contenido =contenido+ lista.getName()+"\n";
                }
                txtBancos.setText("Con Retrofit\n"+ contenido);
            }
            @Override
            public void onFailure(Call<List<ResponseRetrofit>> call, Throwable t) {
                txtBancos.setText(t.getMessage());
            }
        });
    }
}