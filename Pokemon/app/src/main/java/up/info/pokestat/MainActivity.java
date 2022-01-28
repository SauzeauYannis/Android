package up.info.pokestat;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    public static final String LOG = "LOG";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.d(LOG, "OnCreate");

        findViewById(R.id.btn_exit).setOnClickListener(v -> finish());
    }

    public void search(View view) {
        Intent intent = new Intent(this, InfoPokemon.class);
        intent.putExtra(getString(R.string.key_name), ((EditText) findViewById(R.id.inp_name)).getText().toString());
        startActivity(intent);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(LOG, "OnCreate");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(LOG, "onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(LOG, "onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(LOG, "onStop");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(LOG, "onRestart");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(LOG, "onDestroy");
    }
}