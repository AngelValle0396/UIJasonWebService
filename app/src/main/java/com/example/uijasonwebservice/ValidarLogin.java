package com.example.uijasonwebservice;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.uijasonwebservice.WebService.Asynchtask;
import com.example.uijasonwebservice.WebService.WebService;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ValidarLogin extends AppCompatActivity implements Asynchtask {
    private TextView txtSaludo;
    private  Bundle bundle;
    private RequestQueue queue ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_validar_login);
        bundle = this.getIntent().getExtras();
        queue = Volley.newRequestQueue(this);
        LgVolley();
       /* Map<String, String> datos = new HashMap<String, String>();
         WebService ws= new WebService("http://uealecpeterson.net/ws/login.php?usr=" + bundle.getString("usr") + "&pass=" +
                 bundle.getString("pass"), datos, ValidarLogin.this, ValidarLogin.this);
         ws.execute("GET");*/
             }
    @Override
    public void processFinish(String result) throws JSONException {
        //txtSaludo = (TextView)findViewById(R.id.lblMensaje);
        //txtSaludo.setText("Respuesta WS: " + result);//bundle.getString("NOMBRE")+ "\n CLAVE:"+bundle.getString("PASS"));
    }
    private void LgVolley(){
        String urllg="http://uealecpeterson.net/ws/login.php?usr="+bundle.getString("usr")+"&pass="+bundle.getString("pass");

        StringRequest stringRequest = new StringRequest(Request.Method.GET, urllg, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
            txtSaludo=(TextView)findViewById(R.id.lblMensaje);
            txtSaludo.setText(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error)
            {
                txtSaludo=(TextView)findViewById(R.id.lblMensaje);
                txtSaludo.setText("Error: " + error.toString());
            }
        });
        queue.add(stringRequest);
    }
}
