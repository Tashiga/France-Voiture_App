package fr.tashiga.france_voiture_app.Model

class Client {

    private var idClient: Int = 0
    private var nom: String = ""
    private var prenom : String = ""
    private var mdp: String = ""
    private var email: String =""
    private var civilite: Civilite = Civilite.M

    fun Client(idClient: Int, nom: String, prenom: String, mdp: String, email: String, civilite: Civilite) {
        this.idClient = idClient
        this.nom = nom
        this.prenom = prenom
        this.mdp = mdp
        this.email = email
        this.civilite = civilite
    }

//    GETTERS AND SETTERS
    fun getIdCLient(): Int = this.idClient
    fun getNom(): String = this.nom
    fun getPrenom(): String = this.prenom
    fun getMdp(): String = this.mdp
    fun getEmail(): String = this.email
    fun getCivilite(): Civilite = this.civilite
    fun getListAttributs(): Array<String> = arrayOf("idClient", "nom", "prenom", "mdp", "email", "civilite")

    fun setIdClient(id: Int) {
        this.idClient = id
    }
    fun setNom(nom: String) {
        this.nom = nom
    }
    fun setPrenom(prenom: String) {
        this.prenom = prenom
    }
    fun setMdp(mdp: String) {
        this.mdp = mdp
    }
    fun setEmail(email: String) {
        this.email = email
    }
    fun setCivilite(civilite: Civilite) {
        this.civilite = civilite
    }

    override fun toString(): String {
        return "Client [idClient : " + this.idClient +
                " , nom : " + this.nom + " , prenom : " + this.prenom + " , civilite : " +
                this.civilite + " , mdp : " + this.mdp + " , email : " + this.email
    }
}