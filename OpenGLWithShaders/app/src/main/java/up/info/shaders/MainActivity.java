package up.info.shaders;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends Activity
{
    /**
     * TAG for logging errors
     */
    private static final String LOG_TAG = "Applications 3D";
    /**
     * View where OpenGL can draw
     */
    private MyGLSurfaceView glview;
    /**
     * Reference to the Scene environment
     */
    private Scene scene;

    /**
     * Creation of the surface view and the scene
     * @param savedInstanceState
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        log("Starting "+ getString(R.string.app_name) +"...");

        // Create a GLSurfaceView instance and set it
        // as the ContentView for this Activity
        this.scene =new Scene();
        this.glview = new MyGLSurfaceView(this,this.scene);
        setContentView(this.glview);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
    }

    /**
     * Pause of the application. To do...
     */
    @Override
    protected void onPause() {
        super.onPause();
        log("Pausing "+ getString(R.string.app_name) +".");
        this.glview.onPause();
    }

    /**
     * Resume of the application. To do...
     */
    @Override
    protected void onResume() {
        super.onResume();
        log("Resuming "+ getString(R.string.app_name) +".");
        glview.onResume();
    }

    /**
     * Method used to send message to the log console
     * @param message message to display in log
     */
    static public void log(String message)
    {
        Log.i(LOG_TAG, message);
    }
}
