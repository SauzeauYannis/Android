package up.info.pokestat;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class InfoPokemon extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_pokemon);

        if (savedInstanceState != null)
            ((TextView) findViewById(R.id.txt_name)).append((String) savedInstanceState.getSerializable(getString(R.string.key_name)));
        else
            ((TextView) findViewById(R.id.txt_name)).append(getIntent().getExtras().getString(getString(R.string.key_name)));
    }
}