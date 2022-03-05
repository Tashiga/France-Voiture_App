package fr.tashiga.france_voiture_app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import java.sql.DriverManager
import java.util.*

class MainActivity : AppCompatActivity() {
    internal val mysql = MySQLDB.MySQLDB()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
//        mysql.executeQuery()
//        val textView = findViewById<TextView>(R.id.text_view_id) as TextView
//        val errorText = findViewById<TextView>(R.id.error_text_id) as TextView
//        val show = findViewById<Button>(R.id.button_id) as Button
//
//        show.setOnClickListener ( View.OnClickListener {
//            @Override
//            fun onCLick(view: View) {
//
//            }
//        })

    }




}