package proxiad.com.commercesratp;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.Point;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request.Method;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

import proxiad.com.commercesratp.model.Commerces;

public class MainActivity extends Activity {

    // json object response url
    //  private String urlJsonObj = "http://api.androidhive.info/volley/person_object.json";

    // json array response url
    // private String urlJsonArry = "http://api.androidhive.info/volley/person_array.json";
    private String urlJsonObj = "https://data.ratp.fr/api/records/1.0/search/?dataset=liste-des-commerces-de-proximite-agrees-ratp&rows=100&sort=code_postal";

    private static String TAG = MainActivity.class.getSimpleName();
    private Button btnMakeObjectRequest, btnMakeArrayRequest;

    // Progress dialog
    private ProgressDialog pDialog;

    private TextView txtResponse;

    // temporary string to show the parsed response
    private String jsonResponse;

    //liste des commerces
    ArrayList<Commerces> listCommerces ;

    private static RecyclerView.Adapter adapter;

    private RecyclerView.LayoutManager layoutManager;

    private static RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        listCommerces = new ArrayList<Commerces>();

        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Please wait...");
        pDialog.setCancelable(false);

        makeJsonObjectRequest();

        //makeJsonArrayRequest();

        adapter = new CustomAdapter(listCommerces);
        recyclerView.setAdapter(adapter);


    }

    /**
     * Method to make json object request where json response starts wtih {
     * */
    private void makeJsonObjectRequest() {

        showpDialog();

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Method.GET,
                urlJsonObj, null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                Log.d(TAG, response.toString());

                try {
                    // Parsing json object response
                    // response will be a json object
                    String records = response.getString("records");
                    JSONArray mainObject = new JSONArray(records);

                    Commerces commerce;

                    for(int i=0; i<mainObject.length();i++)
                    {
                        JSONObject data = (JSONObject)mainObject.get(i);
                        String fields = data.getString("fields");
                        JSONObject fieldsObjects = new JSONObject(fields);
                        String label = fieldsObjects.getString("tco_libelle");
                        String city = fieldsObjects.getString("ville");
                        String codePostale = fieldsObjects.getString("code_postal");
                        String coordonnesJSON = fieldsObjects.getString("coord_geo");
                        JSONArray coordonnes = new JSONArray(coordonnesJSON);
                        Double x = coordonnes.getDouble(0);
                        Double y = coordonnes.getDouble(1);

                        LatLng point = new LatLng(x,y);
                        commerce = new Commerces(label, city, point);

                        listCommerces.add(commerce);

                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(),
                            "Error: " + e.getMessage(),
                            Toast.LENGTH_LONG).show();
                }
                hidepDialog();
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_SHORT).show();
                // hide the progress dialog
                hidepDialog();
            }
        });

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(jsonObjReq);
    }


    /**
     * Method to make json array request where response starts with [
     * */
    private void makeJsonArrayRequest() {

    }
    private void showpDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hidepDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }


}