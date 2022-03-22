package fr.tashiga.france_voiture_app

import android.os.Bundle
import android.widget.Button
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.sql.*


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loadHomePage()
    }

    fun loadHomePage() {
        setContentView(R.layout.home_page)
        var franceVoiture = findViewById<TextView>(R.id.textView_franceVoiture) as TextView
        franceVoiture.setOnClickListener { loadAccueil() }
    }

    fun loadAccueil() {
        setContentView(R.layout.activity_main)
        var text = findViewById<TextView>(R.id.text_view_id) as TextView
        var boutonArticle = findViewById<Button>(R.id.btn_article) as Button
        var boutonPageConnexion = findViewById<Button>(R.id.connexion) as Button
        var erreur = findViewById<TextView>(R.id.error_text_id) as TextView
        boutonArticle.setOnClickListener {
            try {
                MySQL.MySQL("user", "mdp")
                var connection:Connection? = MySQL.connectMySQL()
                var addText:String? = "\t" + "Articles : " + "\n\n"
                val sql = "SELECT * FROM article"
                val resultSet = MySQL.executeRequete(connection!!, sql)
                while (resultSet!!.next()) {
                    addText += resultSet.getString("idArticle") + " - " +
                            resultSet.getString("nom") + " (" +resultSet.getString("categorie") + ") \n" +
                            "Description : " + resultSet.getString("description") + "\n" +
                            "Stock : " + resultSet.getString("nbStock") + "\n" +
                            "Prix : " + resultSet.getString("prix") + " euros \n" +
                            "Date de création : " +resultSet.getString("dateCreation")  + "\n\n"
                }
                text.text = addText

            }catch (e:Exception) {
                e.printStackTrace()
                erreur.text = e.message
            }
        }
        boutonPageConnexion.setOnClickListener {
            loadConnexion()
        }
    }

    fun loadConnexion() {
        setContentView(R.layout.connexion)
        var retour = findViewById<TextView>(R.id.textView_retour) as TextView
        retour.setOnClickListener {
            setContentView(R.layout.activity_main)
            loadAccueil()
        }
    }

}