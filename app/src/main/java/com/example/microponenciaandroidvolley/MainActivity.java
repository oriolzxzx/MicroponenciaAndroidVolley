package com.example.microponenciaandroidvolley;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.text.Html;
import android.text.Spanned;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private String BASEURL = "https://api.unsplash.com/search/photos/?";
    private String KEY = "Tienes que introducir aqui tu key. O a caso te piensas que te voy a dejar la mia?";

    private String TAG_QUERY = "query=";
    private String TAG_KEY = "client_id=";
    private String TAG_NUMELEMENTS = "per_page=";
    private String TAG_PAGE = "page=";
    private String AND = "&";

    RecyclerView re;
    EditText editText;
    ArrayList<ImageInfo> Urls = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ImageButton im =  findViewById(R.id.imageButton);
        editText = findViewById(R.id.editText);
        re = findViewById(R.id.recyclerview);
        RecyckerView_ImageAdapter adapter = new RecyckerView_ImageAdapter(Urls);
        adapter.setListener(ImageClickListener);
        re.setAdapter(adapter);
        re.setLayoutManager(new GridLayoutManager(getApplicationContext(),calculateNoOfColumns(getApplicationContext(),120)));

        im.setOnClickListener(BuscarListener);

    }

    private void CargarImagenes(String PalabraMagica) {
        String url = BASEURL + TAG_KEY + KEY + AND + TAG_PAGE + 1 + AND + TAG_NUMELEMENTS + 30 + AND + TAG_QUERY + PalabraMagica;
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, ListenerRespuesta, ListenerErrorRespuesta);

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(request);
    }

    public static ArrayList<ImageInfo> getImageList(JSONObject response)
    {
        ArrayList<ImageInfo> values = new ArrayList<>();
        try {
            JSONArray lista = response.getJSONArray("results");

            for(int cnt = 0; cnt < lista.length(); cnt++)
            {
                ImageInfo img = new ImageInfo(lista.getJSONObject(cnt));
                values.add(img);
            }

        } catch (JSONException e) {
            e.printStackTrace();

        }
        return values;
    }

    Response.Listener ListenerRespuesta = new Response.Listener() {
        @Override
        public void onResponse(Object response) {
            ArrayList<ImageInfo>  lista = getImageList((JSONObject) response);
            if(lista.isEmpty())
            {
                re.setVisibility(View.VISIBLE);
                Toast.makeText(getApplicationContext(),"No hay resultados para la busqueda",Toast.LENGTH_LONG).show();
            }
            else
            {
                Urls.clear();
                Urls.addAll(lista);
                re.getAdapter().notifyDataSetChanged();
                re.setVisibility(View.VISIBLE);
            }

        }
    };

    Response.ErrorListener ListenerErrorRespuesta = new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
            error.printStackTrace();
        }
    };

    View.OnClickListener BuscarListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(editText.getText().toString().isEmpty())
            {
                Toast.makeText(getApplicationContext(), "Introduce un valor de busqueda", Toast.LENGTH_SHORT).show();
            }
            else
            {
                re.setVisibility(View.GONE);
                CargarImagenes(editText.getText().toString());
            }
        }
    };

    View.OnClickListener ImageClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int num = re.getChildLayoutPosition(v);
            ImageInfo item = Urls.get(num);
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            //builder.setTitle(item.Description);

            Spanned sp = Html.fromHtml(item.GetAlertFormat(), Html.FROM_HTML_MODE_COMPACT);
            builder.setMessage(sp);
            AlertDialog dialog = builder.create();
            dialog.show();
        }
    };




    public int calculateNoOfColumns(Context context, float columnWidthDp) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        float screenWidthDp = displayMetrics.widthPixels / displayMetrics.density;
        int noOfColumns = (int) (screenWidthDp / columnWidthDp + 0.5);
        return noOfColumns;
    }



}