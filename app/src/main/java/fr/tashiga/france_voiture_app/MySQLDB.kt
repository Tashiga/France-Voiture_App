package fr.tashiga.france_voiture_app

import java.sql.*
import java.util.*

object MySQLDB {
    internal var conn: Connection? = null
    internal var username = "user"
    internal var password = "mdp"

    fun MySQLDB():MySQLDB {
        getConnection()
        return this
    }

    /**
     * This method makes a connection to MySQL Server
     * In this example, MySQL Server is running in the local host (so 127.0.0.1)
     * at the standard port 3306
     */
    fun getConnection() {
        val connectionProps = Properties()
        connectionProps.put("user", username)
        connectionProps.put("password", password)
        try {
//            Class.forName("com.mysql.jdbc.Driver").newInstance()
            conn = DriverManager.getConnection(
                "jdbc:" + "mysql" + "://" +
                        "192.168.1.42" +"/" +
                        "francevoiture",
                connectionProps)

        } catch (ex: SQLException) {
            // handle any errors
            ex.printStackTrace()
        } catch (ex: Exception) {
            // handle any errors
            ex.printStackTrace()
        }
    }

    fun executeQuery() {
        var stmt: Statement? = null
        var resultset: ResultSet? = null

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

//            resultset = stmt!!.executeQuery("select * from client;")
//            println("--- VOICI LA REQUETE ---")
//            while(resultset!!.next()) {
//                println(resultset.getArray(resultset.row))
//            }
//            println("-------------------------\n")

        }catch (ex: SQLException) {
            // handle any errors
            ex.printStackTrace()
        }finally {
            // release resources
            if (resultset != null) {
                try {
                    resultset.close()
                } catch (sqlEx: SQLException) {
                }
                resultset = null
            }
            if (stmt != null) {
                try {
                    stmt.close()
                } catch (sqlEx: SQLException) {
                }
                stmt = null
            }

            if (conn != null) {
                try {
                    conn!!.close()
                } catch (sqlEx: SQLException) {
                }
                conn = null
            }

        }
    }

    @JvmStatic fun main(args: Array<String>){
        val mysql:MySQLDB = MySQLDB()
//        executer une requete
        mysql.executeQuery()
    }

}