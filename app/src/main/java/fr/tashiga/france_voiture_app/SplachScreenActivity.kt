package fr.tashiga.france_voiture_app

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler

class SplachScreenActivity : AppCompatActivity() {

    //variable de temps
    private val SPLACH_TIME_OUT:Long = 3000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splach_screen)

        Handler().postDelayed( {
            startActivity(Intent(this,MainActivity::class.java))
            finish()
        }, SPLACH_TIME_OUT)
    }
}