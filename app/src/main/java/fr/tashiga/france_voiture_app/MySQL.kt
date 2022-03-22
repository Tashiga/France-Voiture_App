package fr.tashiga.france_voiture_app

import android.os.StrictMode
import android.os.StrictMode.ThreadPolicy
import java.sql.*

object MySQL {

    private var user:String = ""
    private var pass: String = ""

    fun MySQL(user:String, pass:String) {
        this.pass = pass
        this.user = user
    }

    /**
     * connection à MySQL
     */
    fun connectMySQL(): Connection? {
        var cn: Connection?
        try {
            Class.forName("com.mysql.jdbc.Driver")
            val policy = StrictMode.ThreadPolicy.Builder()
                .permitAll().build()
            StrictMode.setThreadPolicy(policy)
            cn = DriverManager.getConnection("jdbc:mysql://192.168.1.42/francevoiture",
                this.user, this.pass)
            //en partage de connexion : 172.20.10.8
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
    fun executeRequete(cn: Connection, sql:String) : ResultSet?{
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
    fun fermerStatement(ps: Statement) {
        if (ps != null) {
            ps!!.close()
        }
    }

    /**
     * fermer la connection de la base de données
     */
    fun deconnexionMySQL (cn: Connection) {
        if (cn != null) {
            cn.close()
        }
    }

}