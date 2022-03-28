package fr.tashiga.france_voiture_app.Controler

import fr.tashiga.france_voiture_app.Model.Article
import fr.tashiga.france_voiture_app.MySQL
import fr.tashiga.france_voiture_app.R
import java.sql.Connection
import java.sql.ResultSet
import java.util.ArrayList

object Functions {


    fun getListOfArticles(query: String) : ArrayList<Article>?{
        MySQL.MySQL("user", "mdp")
        var connection: Connection? = MySQL.connectMySQL()
        val resultSet: ResultSet? = MySQL.executeRequete(connection!!, query)
        var article: Article
        val items = ArrayList<Article>()
        while (resultSet!!.next()) {
            var nomVendeur = resultSet.getString("nom")+" "+resultSet.getString("prenom")
            var image: Int
            if(resultSet.getString("categorie").equals("moteur")) {
                image = R.drawable.moteur
            }
            else if(resultSet.getString("categorie").equals("pneus")) {
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
}