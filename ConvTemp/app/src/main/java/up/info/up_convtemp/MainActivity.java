package up.info.up_convtemp;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.switchmaterial.SwitchMaterial;

public class MainActivity extends AppCompatActivity {

    public static String TAG = "UPConvTemp"; // Identifiant pour les messages de log

    private EditText editInputTemp;    // Boite de saisie de la température
    private Spinner input_S;
    private Spinner output_S;
    private TextView dispResult;       // Le TextView qui affichera le résultat
    private boolean autoConvert;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        editInputTemp = findViewById(R.id.input_temp);
        input_S = findViewById(R.id.in_spinner);
        output_S = findViewById(R.id.out_spinner);
        dispResult = findViewById(R.id.output_result);
        SwitchMaterial convertSwitch = findViewById(R.id.auto_convert);

        autoConvert = convertSwitch.isChecked();

        findViewById(R.id.button_convert).setOnClickListener(this::action_convert);

        editInputTemp.setOnKeyListener((view, i, keyEvent) -> { auto_convert(view); return false; });

        input_S.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {}
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                auto_convert(view);
            }
        });

        output_S.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {}
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                auto_convert(view);
            }
        });

        convertSwitch.setOnCheckedChangeListener((compoundButton, b) -> {
            autoConvert = b;
            if (autoConvert) auto_convert(compoundButton);
        });
    }

    public void auto_convert (View v) {
        if (autoConvert) action_convert(v);
    }

    public void action_convert(View v) {
        try {
            double temp_val = Double.parseDouble(editInputTemp.getText().toString());
            double result = temp_val;
            switch (input_S.getSelectedItemPosition()) {
                case 0:
                    if (output_S.getSelectedItemPosition() == 1) result = temp_val * 9 / 5 + 32;
                    if (output_S.getSelectedItemPosition() == 2) result = temp_val + 273.15;
                    break;
                case 1:
                    if (output_S.getSelectedItemPosition() == 0) result = (temp_val - 32) * 5 / 9;
                    if (output_S.getSelectedItemPosition() == 2) result = (temp_val + 459.73) / 1.8;
                    break;
                case 2:
                    if (output_S.getSelectedItemPosition() == 0) result = temp_val - 273.15;
                    if (output_S.getSelectedItemPosition() == 1) result = temp_val * 1.8 - 459.67;
                    break;
            }
            dispResult.setText(String.valueOf(result));
            //vibrate(100);
        } catch (Exception e) {
            toast("Error");
            Log.v(TAG, "Empty input");
        }
    }

    public void vibrate(long duration_ms) {
        Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        if (duration_ms < 1) duration_ms = 1;
        if (v != null && v.hasVibrator()) {
            // Attention changement comportement avec API >= 26 (cf doc)
            if (Build.VERSION.SDK_INT >= 26) {
                v.vibrate(VibrationEffect.createOneShot(duration_ms,
                        VibrationEffect.DEFAULT_AMPLITUDE));
            } else {
                v.vibrate(duration_ms);
            }
        }
        // sinon il n'y a pas de mécanisme de vibration
    }

    public void toast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
}