package proxiad.com.commercesratp;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
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
import com.android.volley.toolbox.JsonObjectRequest;

import java.util.ArrayList;

import proxiad.com.commercesratp.model.Commerces;

public class MainActivity extends Activity {

    private String urlJsonObj = "https://data.ratp.fr/api/records/1.0/search/?dataset=liste-des-commerces-de-proximite-agrees-ratp&rows=100&sort=code_postal";

    private static String TAG = MainActivity.class.getSimpleName();

    private Button btnShowMapActivity;

    // Progress dialog
    private ProgressDialog pDialog;

    private TextView txtResponse;

    // temporary string to show the parsed response
    private String jsonResponse;

    //liste des commerces
    ArrayList<Commerces> listCommerces ;

    private RecyclerView.Adapter adapter;

    private RecyclerView.LayoutManager layoutManager;

    private static RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Bouton pour afficher la carte.
        btnShowMapActivity = (Button)findViewById(R.id.showMapActivity) ;
        //Afficher les données dans recycleview
        recyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        listCommerces = new ArrayList<Commerces>();

        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Veuillez patienter...");
        pDialog.setCancelable(false);

        //Appel de l'api RATP et le parsing des objets JSON
        makeJsonObjectRequest();

        adapter = new CustomAdapter(listCommerces);
        recyclerView.setAdapter(adapter);

        btnShowMapActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,MapActivity.class);
                intent.putExtra("List", listCommerces);
                startActivity(intent);
            }
        });

    }


    private void makeJsonObjectRequest() {

        showpDialog();

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Method.GET,
                urlJsonObj, null, new Response.Listener<JSONObject>() {

            //Utilisation de l'api Volley pour les requettes HTTP
            @Override
            public void onResponse(JSONObject response) {
                Log.d(TAG, response.toString());

                try {
                    // Parsing json
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
                        String codePostal = fieldsObjects.getString("code_postal");
                        String coordonnesJSON = fieldsObjects.getString("coord_geo");
                        JSONArray coordonnes = new JSONArray(coordonnesJSON);
                        Double x = coordonnes.getDouble(0);
                        Double y = coordonnes.getDouble(1);

                        commerce = new Commerces(label, city,codePostal, x,y);

                        listCommerces.add(commerce);

                    }

                    //Mise à jour la liste dans recycleView
                    adapter.notifyDataSetChanged();

                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(),
                            "Erreur: " + e.getMessage(),
                            Toast.LENGTH_LONG).show();
                }
                hidepDialog();
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Erreur: " + error.getMessage());

                // Masquer la boite de dialogue
                hidepDialog();
            }
        });


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