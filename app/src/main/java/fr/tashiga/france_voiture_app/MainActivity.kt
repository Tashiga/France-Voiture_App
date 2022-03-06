package fr.tashiga.france_voiture_app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import java.sql.ResultSet

class MainActivity : AppCompatActivity() {
    internal val mysql = MySQLDB.MySQLDB()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val textView = findViewById<TextView>(R.id.text_view_id) as TextView
        val errorText = findViewById<TextView>(R.id.error_text_id) as TextView
        val show = findViewById<Button>(R.id.button_id) as Button

        show.setOnClickListener {
//                var result: ResultSet? = mysql.executeQuery("select * from article")
//                var valeur:String = ""
//                textView.text = valeur
//                println("--- LES ARTICLES ---")
//                while(result!!.next()) {
//                    valeur +=  "id de l'article : " + result.getString("idArticle") + ", nom : " + result.getString("nom").toUpperCase() + ", categorie : " +result.getString("categorie") + ", prix :  " + result.getString("prix") + " euros, date :  " + result.getString("dateCreation") + " "
//                    valeur += "description : " + result.getString("description") + " stock : " + result.getString("nbStock") + "\n"
//                }
//                result!!.close()
            textView.text = "hello"
        }

    }




}