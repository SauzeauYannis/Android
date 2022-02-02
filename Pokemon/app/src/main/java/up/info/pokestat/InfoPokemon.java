package up.info.pokestat;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

public class InfoPokemon extends AppCompatActivity {

    private static final String URL = "https://www.pokepedia.fr/";

    private String name;
    private TextView pokemon_name;
    private TextView pokemon_type;
    private TextView pokemon_size;
    private TextView pokemon_weight;
    private ImageView imageView;
    private Set<String> searchedPokemonName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_pokemon);

        name = savedInstanceState != null ?
                (String) savedInstanceState.getSerializable(getString(R.string.key_name)) :
                getIntent().getExtras().getString(getString(R.string.key_name));

        pokemon_name = findViewById(R.id.txt_name);
        pokemon_type = findViewById(R.id.txt_type);
        pokemon_size = findViewById(R.id.txt_size);
        pokemon_weight = findViewById(R.id.txt_weight);
        imageView = findViewById(R.id.img);

        findViewById(R.id.btn_close).setOnClickListener(view -> finish());

        InfoPokemon.this.reload_historic();
        InfoPokemon.this.display_historic();

        PokeRequest pokeRequest = new PokeRequest(name);
        pokeRequest.execute();
    }

    public void viewOnWeb(View view) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(URL + name));
        startActivity(intent);
    }

    public void add_historic(String info) {
        SharedPreferences sharedPref =  getSharedPreferences("historicPokemon", Context.MODE_PRIVATE);
        searchedPokemonName.add(info);
        sharedPref.edit().putStringSet("historyPokemonName", searchedPokemonName).apply();
    }

    public void write_historic_in_file() {
        File folder = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
        File fileout = new File(folder, "pokestat_historic.txt");
        try (FileOutputStream fos = new FileOutputStream(fileout)) {
            PrintStream ps = new PrintStream(fos);
            ps.println("Start of my historic");

            for (String item : searchedPokemonName)
                ps.println(item);

            ps.close();
        } catch (FileNotFoundException e) {
            Log.e(MainActivity.LOG,"File not found",e);
            e.printStackTrace();
        } catch (IOException e) {
            Log.e(MainActivity.LOG,"Error I/O",e);
            e.printStackTrace();
        }

    }

    public void reload_historic() {
        SharedPreferences sharedPref =  getSharedPreferences("historicPokemon", Context.MODE_PRIVATE);
        searchedPokemonName = sharedPref.getStringSet("historyPokemonName", new TreeSet<String>());
    }

    public void display_historic() {
        Log.d(MainActivity.LOG,"Historique ("+ (new Date())+ ") size= " + searchedPokemonName.size() + ":  ");
        for (String item : searchedPokemonName)
            Log.d(MainActivity.LOG,"\t- " + item);
    }

    public class PokeRequest extends AsyncTask<Void, Integer, Void> {
        private String name;
        private String restype;
        private String resname;
        private String resweight;
        private String ressize;
        private String imageUrl;

        public PokeRequest(String name) {
            this.name = name;
            restype = "<none>";
            resname = "<none>";
            resweight = "<none>";
            ressize = "<none>";
        }

        @Override
        protected Void doInBackground(Void... voids) { // Se fait en background du thread UI
            try {
                Document doc = Jsoup.connect(URL + name).get();
                Element tableinfo = doc.selectFirst("table.tableaustandard");

                Element img = tableinfo.select("img").first();
                imageUrl = URL + img.attr("src");

                assert tableinfo != null;
                Elements names = tableinfo.select("th.entêtesection");
                for (Element e : names) {
                    resname = e.ownText();
                    Log.v(MainActivity.LOG,"Entete section: " + resname);
                }

                Log.v(MainActivity.LOG,"=====>>>>>  FINAL Entete section: " + resname);

                Elements rows = tableinfo.select("tr");
                for (Element row : rows) {
                    Log.v(MainActivity.LOG,"=====>>>>>  new line. ");
                    if(row.select("a[title*=taille]").size() > 0) {
                        Element target = row.selectFirst("td");
                        if(target != null) {
                            ressize = target.ownText();
                            Log.v(MainActivity.LOG,"=====>>>>>  Find a size: " + ressize);
                        }
                        else
                            Toast.makeText(InfoPokemon.this, R.string.error_no_dom_entity, Toast.LENGTH_LONG).show();
                    }

                    if(row.select("a[title*=poids]").size() > 0) {
                        Element target = row.selectFirst("td");
                        if(target != null) {
                            resweight = target.ownText();
                            Log.v(MainActivity.LOG,"=====>>>>>  Find a weight: " + resweight);
                        }
                        else
                            Toast.makeText(InfoPokemon.this,R.string.error_no_dom_entity, Toast.LENGTH_LONG).show();
                    }

                }

                Elements elems = tableinfo.select("a[title*=type]");
                ArrayList<String> types = new ArrayList<>();
                for (Element e: elems) {
                    if(!e.attr("title").equalsIgnoreCase("Type")) {
                        String rawtype = e.attr("title");
                        String type = rawtype.replace(" (type)","");
                        types.add(type);
                        Log.v(MainActivity.LOG,"\tFind type: " +type);
                    }
                }
                restype = types.stream().collect(Collectors.joining(" - "));
            } catch (IOException e) {
                Log.e(MainActivity.LOG,"Error during connection...",e);
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            // Inutile ici, cf doc
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(Void aVoid) { // S'exécute sur le ThreadUI après doInBackground
            super.onPostExecute(aVoid);
            // ATTENTION, il faut adapter le code ci-dessous avec vos controles graphiques.
            InfoPokemon.this.pokemon_name.append(resname);
            InfoPokemon.this.pokemon_type.append(restype);
            InfoPokemon.this.pokemon_size.append(ressize);
            InfoPokemon.this.pokemon_weight.append(resweight);
            Glide.with(InfoPokemon.this).load(imageUrl).into(imageView);
            Toast.makeText(InfoPokemon.this, R.string.end_request, Toast.LENGTH_SHORT).show();

            // c'est ici que vous devrez ajouter l'écriture de votre fichier en FIN de sujet!!!
            String info = "Name: " + resname + "\n" +
                          "Type: " + restype + "\n" +
                          "Size: " + ressize + "\n" +
                          "Weight:  " + resweight + "\n";
            InfoPokemon.this.add_historic(info);
            InfoPokemon.this.reload_historic();
            InfoPokemon.this.display_historic();
            InfoPokemon.this.write_historic_in_file();
        }
    }
}