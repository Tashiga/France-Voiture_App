package fr.tashiga.france_voiture_app

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ArticleAdapter (var items: List<Article>) : RecyclerView.Adapter<ArticleAdapter.ArticleViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.article, parent, false)
        return ArticleViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        val article = items[position]
        holder.bind(article)
    }

    override fun getItemCount() = items.size

   inner class ArticleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        var titre: TextView
        var description: TextView
        var image: ImageView
        var prix: TextView
        var vendeur: TextView
        var categorie: TextView

        init {
            titre = itemView.findViewById(R.id.TextView_article_nom)
            description = itemView.findViewById(R.id.TextView_description)
            image = itemView.findViewById(R.id.imageView_Article)
            prix = itemView.findViewById(R.id.TextView_prixArticle)
            vendeur = itemView.findViewById(R.id.TextView_nomVendeur)
            categorie = itemView.findViewById(R.id.TextView_nomCategorie)
        }

        fun bind(article: Article) {
            titre.text = article.titre
            vendeur.text = article.vendeur
            description.text = article.description
            categorie.text = article.categorie
            prix.text = article.prix
            image.setImageResource(article.image)
        }
    }
}


