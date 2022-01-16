package up.info.up_convtemp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    public static String TAG = "UPConvTemp"; // Identifiant pour les messages de log

    private EditText editInputTemp;    // Boite de saisie de la température
    private RadioButton rbCelsius;     // Bouton radio indiquant si la saisie est en Celsius
    private RadioButton rbFahrenheit;  // Bouton radio indiquant si la saisie est en Fahrenheit
    private TextView dispResult;       // Le TextView qui affichera le résultat

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        editInputTemp = findViewById(R.id.input_temp);
        rbCelsius = findViewById(R.id.rb_celsius);
        rbFahrenheit = findViewById(R.id.rb_fahrenheit);
        dispResult = findViewById(R.id.output_result);

        findViewById(R.id.button_convert).setOnClickListener(this::action_convert);
    }

    @SuppressLint("DefaultLocale")
    public void action_convert(View v) {
        try {
            double temp_val = Double.parseDouble(editInputTemp.getText().toString());
            double result = Double.NaN;
            if (rbFahrenheit.isChecked()) result = (temp_val - 32) * 5 / 9;
            else if (rbCelsius.isChecked()) result = temp_val * 9 / 5 + 32;
            else Log.v(TAG, "No unit");
            dispResult.setText(Double.isNaN(result) ?
                    Resources.getSystem().getString(R.string.unit) :
                    String.format("%.2f", result) + "°" + (rbCelsius.isChecked() ? "F" : "C")
            );
            vibrate(100);
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