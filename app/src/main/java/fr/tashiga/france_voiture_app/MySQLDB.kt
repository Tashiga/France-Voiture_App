package fr.tashiga.france_voiture_app


import android.database.SQLException
import java.sql.Connection
import java.sql.DriverManager
import java.sql.ResultSet
import java.sql.Statement
import java.util.*

object MySQLDB {
    internal var conn: Connection? = null

    fun MySQLDB():MySQLDB {
        getConnection("user", "mdp")
        return this
    }

    /**
     * This method makes a connection to MySQL Server
     * In this example, MySQL Server is running in the local host (so 127.0.0.1)
     * at the standard port 3306
     */
    fun getConnection(username: String, password: String): Connection? {
        val connectionProps = Properties()
        connectionProps.put("user", username)
        connectionProps.put("password", password)
        try {
//            Class.forName("com.mysql.jdbc.Driver").newInstance()
            this.conn = DriverManager.getConnection(
                "jdbc:" + "mysql" + "://" +
                        "192.168.1.42" + ":3306" + "/" +
                        "francevoiture",
                connectionProps)
            return conn!!
        } catch (ex: SQLException) {
            // handle any errors
            ex.printStackTrace()
            return null
        } catch (ex: Exception) {
            // handle any errors
            ex.printStackTrace()
            return null
        }
    }

    fun executeQuery() {
        var stmt: Statement? = null
        var resultset: ResultSet? = null

        getConnection("user", "mdp")

        println("avant execution " + stmt + " connection : " + conn)
        try {
            stmt = conn!!.createStatement()
            resultset = stmt!!.executeQuery("SHOW DATABASES;")

            if (stmt.execute("SHOW DATABASES;")) {
                resultset = stmt.resultSet
            }
            println("--- VOICI LES BASES ---")
            while (resultset!!.next()) {
                println(resultset.getString("Database"))
            }
            println("-------------------------\n")

            resultset = stmt!!.executeQuery("SHOW TABLES;")
            println("--- VOICI LES TABLES ---")
            while(resultset!!.next()) {
                println(resultset.getString("Tables_in_francevoiture"))
            }
            println("-------------------------\n")

            resultset = stmt!!.executeQuery("select * from client;")
            println("--- VOICI LES CLIENTS ---")
            while(resultset!!.next()) {
                println(resultset.getString("civilite") + ". " + resultset.getString("nom").toUpperCase() + " " + resultset.getString("prenom"))
            }
            println("-------------------------\n")

            resultset = stmt!!.executeQuery("select * from vendeur;")
            println("--- VOICI LES VENDEURS ---")
            while(resultset!!.next()) {
                println(resultset.getString("civilite") + ". " + resultset.getString("nom").toUpperCase() + " " + resultset.getString("prenom"))
            }
            println("-------------------------\n")

        }catch (ex: SQLException) {
            // handle any errors
            ex.printStackTrace()
        }
//        finally {
//            // release resources
//            if (resultset != null) {
//                try {
//                    resultset.close()
//                } catch (sqlEx: SQLException) {
//                }
//                resultset = null
//            }
//            if (stmt != null) {
//                try {
//                    stmt.close()
//                } catch (sqlEx: SQLException) {
//                }
//                stmt = null
//            }
//
////            if (conn != null) {
////                try {
////                    conn!!.close()
////                } catch (sqlEx: SQLException) {
////                }
////                conn = null
////            }
//
//        }
    }

    //execute n'importe quel requete et renvoie les resultats
    fun executeQuery(query: String):ResultSet? {
        var stmt: Statement? = null
        var resultset: ResultSet? = null
        try {
            stmt = this.conn!!.createStatement()
            resultset = stmt!!.executeQuery(query)
            if (stmt.execute(query)) {
                resultset = stmt.resultSet
            }
        } catch (ex: SQLException) {
            println("erreur lors de l'exuction de la requete SQL")
        }
        return resultset
    }

    fun getConn(): Connection? {
        try {
            return this.conn!!
        }catch (e: Exception) {
            e.printStackTrace()
            return null
        }
    }

//    Exemple d'un main fonctionnel

    @JvmStatic fun main(args: Array<String>){

//  Base de donnee : insertion d'articles
//  categorie enum('pneus','moteur','freinage','systeme electrique', 'amortisseur','echappement', 'suspension', 'piece allumage', 'climatisation', 'carrosserie', 'alternateur', 'filtre', 'direction') not null
//  INSERT INTO article (nom, prix, description, nbStock, dateCreation, categorie) VALUES('Un pneu', '56.23', null, 2, (select CURRENT_DATE ()), 'pneus');

        val mysql:MySQLDB = MySQLDB()
//      executer une requete
        mysql.executeQuery()
        var resultats: ResultSet? = mysql.executeQuery("select * from vendeur;")
        println("--- NOTRE RESULTAT DE EXECUTEQUERY() ---")
        var lign: Int = 1
        while(resultats!!.next()) {
            println( "" + lign + ". " + resultats.getString("civilite") + ". " + resultats.getString("nom").toUpperCase() + " " + resultats.getString("prenom"))
            println("\tMdp : " + resultats.getString("mdp") + "\n\tRaison social : " + resultats.getString("raison_social") + "\n\temail : " + resultats.getString("email") )
            lign++
        }
        println("-------------------------\n")
        resultats.close()
        resultats = mysql.executeQuery("select * from article")
        println("--- LES ARTICLES ---")
        lign = 1
        while(resultats!!.next()) {
            println( "" + lign + ". " + resultats.getString("nom").toUpperCase() + "[" +resultats.getString("categorie") + "] " + resultats.getString("prix") + " euros, ajouté le " + resultats.getString("dateCreation"))
            println("\tDescription : " + resultats.getString("description") + "\n\tStock : " + resultats.getString("nbStock") )
            lign++
        }
        resultats.close()
    }

}