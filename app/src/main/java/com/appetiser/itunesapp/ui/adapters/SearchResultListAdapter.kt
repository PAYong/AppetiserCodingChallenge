
package com.appetiser.itunesapp.ui.adapters

import android.app.Activity
import android.app.Dialog
import android.content.SharedPreferences
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import com.appetiser.itunesapp.R
import com.appetiser.itunesapp.data.SaveFileItems
import com.appetiser.itunesapp.data.SearchItems
import com.appetiser.itunesapp.data.SearchResult
import com.appetiser.itunesapp.extensions.ctx
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.dialog_layout.*
import kotlinx.android.synthetic.main.item_repo.view.*

/**
 * The custom adapter for SearchResult
 * @param repoList Response from the search result from iTunes which is to be displayed in the list.
 * @param activity Used in displaying the detailed view.
 * @param mPrefs The SharedPreferences instance used when saving the SearchResult object.
 */
class SearchResultListAdapter(val repoList: SearchResult?, val activity: Activity, val mPrefs: SharedPreferences? ) : RecyclerView.Adapter<SearchResultListAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.ctx).inflate(R.layout.item_repo, parent, false)

        val saveFile: SaveFileItems = SaveFileItems();
        saveFile.saveFile(repoList, mPrefs)
        return MyViewHolder(view, activity, repoList)
    }


    /**
     * Binds the list into the view via MyViewHolder
     * @param holder The custom view holder
     * @param position The position of the item into the list
     */
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        if (repoList != null) {
            holder.bindRepo(repoList?.results[position])
        }
    }

    /**
     * Returns the total items in repoList
     * @return Returns the item count
     */
    override fun getItemCount(): Int {
        if (repoList != null)
            return repoList.results.size
        return 0
    }

    /**
     * Describes an item view and metadata about its place within the RecyclerView and returns the View with the set values
     * @param view The view which the item will be displayed
     * @param repoList The data to be displayed on the RecyclerView
     * @return Returns the view with the set values
     */
    class MyViewHolder(view: View, activity: Activity, repoList: SearchResult?) : RecyclerView.ViewHolder(view) {
        fun bindRepo(repo: SearchItems?) {

            with(repo) {
                itemView.trackname.text = repo?.trackName.orEmpty()
                itemView.primaryGenreName.text = repo?.primaryGenreName.orEmpty()
                itemView.trackPrice.text = repo?.trackPrice.orEmpty()
                Picasso.get().load(repo?.artworkUrl100).error(R.drawable.ic_round_error_outline_24px).into(itemView.icon)
            }
        }

        init {
            // binds views
            view.setOnClickListener() {
                val adapterPosition = getAdapterPosition()
                if (adapterPosition >= 0) {
                    if (repoList != null)
                        showDialog(activity, repoList?.results[adapterPosition])
                }
            }
        }

        /**
         * Shows the detailed info of the selected item in the list via dialog.
         * @param activity The activity
         * @param item Selected item from the list to be displayed in the dialog.
         */
        fun showDialog(activity: Activity, item: SearchItems) {
            val dialog = Dialog(activity)
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog.setCancelable(false)
            dialog.setContentView(R.layout.dialog_layout)
            dialog.dialog_trackname.setText(item.trackName)
            dialog.dialog_primaryGenreName.setText(item.primaryGenreName)
            dialog.dialog_trackPrice.setText(item.trackPrice)
            dialog.description.setText(item.longDescription)
            Picasso.get().load(item.artworkUrl100).error(R.drawable.ic_round_error_outline_24px).into(dialog.dialog_icon)
            dialog.setCancelable(true)
            dialog.show()
        }

    }
}