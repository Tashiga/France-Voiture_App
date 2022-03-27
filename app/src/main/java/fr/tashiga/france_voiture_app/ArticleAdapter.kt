package fr.tashiga.france_voiture_app

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ArticleAdapter (var items: List<Article>) : RecyclerView.Adapter<ArticleAdapter.ArticleViewHolder>(), Filterable {

    var articlesFilteredList : List<Article> = ArrayList<Article>()

    init {
        articlesFilteredList = items
    }
    
    //bar de recherche filtrer
    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(p0: CharSequence?): FilterResults {
                var charSearch = p0.toString()
                if(charSearch.isEmpty()) {
                    articlesFilteredList = items
                }
                else {
                    articlesFilteredList = items.filter { article -> article.titre.lowercase().contains(charSearch.lowercase())
                            || article.categorie.lowercase().contains(charSearch.lowercase())
                            || article.prix.lowercase().contains(charSearch.lowercase())
                            || article.vendeur.lowercase().contains(charSearch.lowercase()) }

                }
                val filterResults = FilterResults()
                filterResults.values = articlesFilteredList
                return filterResults
            }

            override fun publishResults(p0: CharSequence?, p1: FilterResults?) {
                articlesFilteredList = p1?.values as ArrayList<Article>
                notifyDataSetChanged() //rafraichir les données
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.article, parent, false)
        return ArticleViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        val article = articlesFilteredList[position]
        holder.bind(article)
    }

    override fun getItemCount() = articlesFilteredList.size

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


