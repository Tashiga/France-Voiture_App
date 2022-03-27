package fr.tashiga.france_voiture_app

import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.sql.*
import java.util.ArrayList


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loadBoutique()
    }

    fun loadAccueil() {
        setContentView(R.layout.activity_main)
        var boutonArticle = findViewById<Button>(R.id.btn_article) as Button
        var boutonPageConnexion = findViewById<Button>(R.id.connexion) as Button
        var citroen = findViewById<ImageView>(R.id.imageCitroen) as ImageView
        var peugeot = findViewById<ImageView>(R.id.imagePeugeot) as ImageView
        var renault = findViewById<ImageView>(R.id.imageRenault) as ImageView
        var erreur = findViewById<TextView>(R.id.error_text_id) as TextView
        boutonArticle.setOnClickListener {
            loadArticle("")
        }
        boutonPageConnexion.setOnClickListener {
            loadConnexion()
        }
        citroen.setOnClickListener {
            loadArticle("citroen")
        }
        peugeot.setOnClickListener {
            loadArticle("peugeot")
        }
        renault.setOnClickListener {
            loadArticle("renault")
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

    fun loadArticle(marque:String){
        setContentView(R.layout.display_articles)
        var searchView = findViewById<SearchView>(R.id.idSearchBar) as SearchView
        var listeView = findViewById<ListView>(R.id.list_item) as ListView
        var retour = findViewById<TextView>(R.id.displayArticle_retour) as TextView
        try {
            MySQL.MySQL("user", "mdp")
            var connection:Connection? = MySQL.connectMySQL()
            val resultSet:ResultSet?
            if(marque.equals("citroen")) {
               resultSet = MySQL.executeRequete(connection!!, "select * from article natural join peut_convenir_avec natural join voiture where voiture.marque="+ marque)
            }
            else if(marque.equals("renault")) {
                resultSet = MySQL.executeRequete(connection!!, "select * from article natural join peut_convenir_avec natural join voiture where voiture.marque="+ marque)
            }
            else if(marque.equals("peugeot")) {
                resultSet = MySQL.executeRequete(connection!!, "select * from article natural join peut_convenir_avec natural join voiture where voiture.marque="+ marque)
            }
            else {
                resultSet = MySQL.executeRequete(connection!!, "select * from article")
            }

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

    fun getListOfArticles(query: String) : ArrayList<Article>?{
        MySQL.MySQL("user", "mdp")
        var connection:Connection? = MySQL.connectMySQL()
        val resultSet:ResultSet? = MySQL.executeRequete(connection!!, query)
        var article: Article
        val items = ArrayList<Article>()
        while (resultSet!!.next()) {
            var nomVendeur = resultSet.getString("nom")+" "+resultSet.getString("prenom")
            var image: Int
            if(resultSet.getString("categorie").equals("moteur")) {
                image = R.drawable.moteur
            }
            else if(resultSet.getString("categorie").equals("pneu")) {
                image = R.drawable.pneu
            }
            else {
                image = R.drawable.autre
            }
            var description:String
            if(resultSet.getString("description").isNullOrEmpty()) {
                description = ""
            }
            else {
                description = resultSet.getString("description")
            }

            article = Article( resultSet.getString("article"),
                    description,
                    image,
                    resultSet.getString("prix"),
                    nomVendeur,
                    resultSet.getString("categorie")
            )
            items.add(article)
        }
        return items

    }

    fun loadBoutique() {
        setContentView(R.layout.boutique)
        var recyclerView = findViewById<RecyclerView>(R.id.RecyclerViewBoutique) as RecyclerView

        val items = getListOfArticles("select article.nom as article, article.description, article.prix, article.categorie, vendeur.nom, vendeur.prenom from article inner join ajouter on article.idArticle = ajouter.idArticle inner join vendeur on ajouter.idVendeur = vendeur.idVendeur")

        recyclerView.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = ArticleAdapter(items!!)
        }

    }

}