package fr.tashiga.france_voiture_app

import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import java.sql.*
import java.util.ArrayList


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loadAccueil()
    }

    fun loadAccueil() {
        setContentView(R.layout.activity_main)
        var boutonArticle = findViewById<Button>(R.id.btn_article) as Button
        var boutonPageConnexion = findViewById<Button>(R.id.connexion) as Button
        var erreur = findViewById<TextView>(R.id.error_text_id) as TextView
        boutonArticle.setOnClickListener {
            loadArticle()
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

    fun loadArticle(){
        setContentView(R.layout.display_articles)
        var searchView = findViewById<SearchView>(R.id.idSearchBar) as SearchView
        var listeView = findViewById<ListView>(R.id.list_item) as ListView
        var retour = findViewById<TextView>(R.id.displayArticle_retour) as TextView
        try {
            MySQL.MySQL("user", "mdp")
            var connection:Connection? = MySQL.connectMySQL()
            val resultSet = MySQL.executeRequete(connection!!, "select * from article")
            val addText: ArrayList<String> = ArrayList()
            while (resultSet!!.next()) {
                addText!!.add(resultSet.getString("nom") + " "+ resultSet.getString("categorie") + " "+ resultSet.getString("prix") + " euros")
            }
            val resultSetVendeur = MySQL.executeRequete(connection!!, "select * from vendeur")
            var str:String = ""
            while (resultSetVendeur!!.next()) {
                str = resultSetVendeur.getString("civilite") + " "+ resultSetVendeur.getString("nom") + " "+ resultSetVendeur.getString("prenom") + " "+ resultSetVendeur.getString("raison_social")
                addText!!.add(str)
            }
            var adapter: ArrayAdapter<String>  = ArrayAdapter(this, android.R.layout.simple_list_item_1, addText!!)
            listeView.adapter = adapter
            
            searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(p0: String?): Boolean {
                    searchView.clearFocus()
                    if(addText!!.contains(p0)) {
                        adapter.filter.filter(p0)
                    }
                    else {
                        Toast.makeText(applicationContext, "Article non trouvé", Toast.LENGTH_LONG).show()
                    }
                    return false
                }
                override fun onQueryTextChange(p0: String?): Boolean {
                    adapter.filter.filter(p0)
                    return false
                }
            })
        }catch (e: java.lang.Exception) {
            e.printStackTrace()
        }
        retour.setOnClickListener {
            setContentView(R.layout.activity_main)
            loadAccueil()
        }
    }

}