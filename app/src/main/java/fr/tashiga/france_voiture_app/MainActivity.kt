package fr.tashiga.france_voiture_app

import android.os.Bundle
import android.os.StrictMode
import android.os.StrictMode.ThreadPolicy
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.sql.*


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var text = findViewById<TextView>(R.id.text_view_id) as TextView
        var boutonArticle = findViewById<Button>(R.id.button_id) as Button
        var erreur = findViewById<TextView>(R.id.error_text_id) as TextView
        boutonArticle.setOnClickListener {
            try {
                var connection:Connection? = connectMySQL()
                var addText:String? = "Articles : " + "\n"
                val sql = "SELECT * FROM article"
                val resultSet = executeRequete(connection!!, sql)
                while (resultSet!!.next()) {
                    addText += resultSet.getString("idArticle") + "-" +
                            resultSet.getString("nom") + " (" +resultSet.getString("categorie") + ") \n" +
                            "Description : " + resultSet.getString("description") + "\n" +
                            "Stock : " + resultSet.getString("nbStock") + "\n" +
                            " Prix : " + resultSet.getString("prix") + " euros \n" +
                            "Date de création : " +resultSet.getString("dateCreation")  + "\n\n"
                }
                text.text = addText
            }catch (e:Exception) {
                e.printStackTrace()
                erreur.text = e.message
            }
        }
    }

    /**
     * connection à MySQL
     */
    fun connectMySQL(): Connection? {
        var cn: Connection?
        try {
            Class.forName("com.mysql.jdbc.Driver")
            val policy = ThreadPolicy.Builder()
                .permitAll().build()
            StrictMode.setThreadPolicy(policy)
            cn = DriverManager.getConnection("jdbc:mysql://192.168.1.42/francevoiture",
                "user", "mdp")
            return cn
        } catch (e: ClassNotFoundException) {
            e.printStackTrace()
            return null
        } catch (e: SQLException) {
            e.printStackTrace()
            return null
        }
    }

    /**
     * executer une requete SQL
     */
    fun executeRequete(cn:Connection, sql:String) : ResultSet?{
        try {
            val ps = cn.createStatement()
            val resultSet = ps!!.executeQuery(sql)
            return resultSet
        } catch (e: ClassNotFoundException) {
            e.printStackTrace()
            return null
        } catch (e: SQLException) {
            e.printStackTrace()
            return null
        }
    }

    /**
     * femmer le statement utiliser pour executer une requete
     */
    fun fermerStatement(ps:Statement) {
        if (ps != null) {
            ps!!.close()
        }
    }

    /**
     * fermer la connection de la base de données
     */
    fun deconnexionMySQL (cn:Connection) {
        if (cn != null) {
            cn.close()
        }
    }

}