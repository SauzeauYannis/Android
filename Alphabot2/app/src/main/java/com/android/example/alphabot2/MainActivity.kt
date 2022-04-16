package com.android.example.alphabot2

import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.jcraft.jsch.ChannelExec
import com.jcraft.jsch.JSch
import java.io.ByteArrayOutputStream
import java.util.*
import android.net.Uri


class MainActivity : AppCompatActivity() {

    private var robotLaunched: Boolean = false

    private val duration: Int = Toast.LENGTH_SHORT

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_IMMERSIVE
                or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_FULLSCREEN)


        findViewById<ImageView>(R.id.playStopImage).setOnClickListener {
            imageClicked()
        }
    }


    private fun imageClicked() {
        if (robotLaunched) {
            Toast.makeText(applicationContext, "Arrêt du robot...", duration).show()
            stopRobot()
            Toast.makeText(applicationContext, "Robot arrêté", duration).show()
        } else {
            Toast.makeText(applicationContext, "Mise en route du robot...", duration).show()
            launchRobot()
            Toast.makeText(applicationContext, "Robot en état de marche", duration).show()
        }
    }

    private fun launchRobot() {
        //SshTask("python ~/AlphaBot2/Web-Control/main.py").execute()
        goToWebSite()
        findViewById<ImageView>(R.id.playStopImage).setImageDrawable(getDrawable(android.R.drawable.ic_delete))
        findViewById<TextView>(R.id.playStopText).setText(R.string.stopText)
        robotLaunched = true
    }


    private fun stopRobot() {
        SshTask("ls").execute()
        findViewById<ImageView>(R.id.playStopImage).setImageDrawable(getDrawable(android.R.drawable.ic_media_play))
        findViewById<TextView>(R.id.playStopText).setText(R.string.playText)
        robotLaunched = false
    }

    private fun goToWebSite() {
        val uriUrl: Uri = Uri.parse("http:192.168.6.234:8000")
        val launchBrowser = Intent(Intent.ACTION_VIEW, uriUrl)
        startActivity(launchBrowser)
    }

    class SshTask(command: String) : AsyncTask<Void, Void, String>() {

        private var cmd = command

        override fun doInBackground(vararg params: Void?): String {
            val output = executeSshCommand("ubuntu", "ubuntu", "192.168.1.30", 22, cmd)
            Log.i("MainActivity", output)
            print(output)
            return output
        }
    }

}

fun executeSshCommand(username: String,
                      password: String,
                      hostname: String,
                      port: Int,
                      command: String): String {
    val jcs = JSch()
    val session = jcs.getSession(username, hostname, port)
    session.setPassword(password)

    val properties = Properties()
    properties["StrictHostKeyChecking"] = "no"
    session.setConfig(properties)

    session.connect()

    val sshChanel = session.openChannel("exec") as ChannelExec
    val outputStream = ByteArrayOutputStream()
    sshChanel.outputStream = outputStream

    sshChanel.setCommand(command)
    sshChanel.connect()

    Thread.sleep(1_000)
    sshChanel.disconnect()

    session.disconnect()

    return outputStream.toString()
}
