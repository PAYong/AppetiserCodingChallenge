
package com.appetiser.itunesapp.ui.activities

import android.app.AlertDialog
import android.content.Context
import android.content.SharedPreferences
import android.net.ConnectivityManager
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import com.appetiser.itunesapp.api.RepositoryRetriever
import com.appetiser.itunesapp.data.SaveFileItems
import com.appetiser.itunesapp.data.SearchResult
import com.appetiser.itunesapp.ui.adapters.SearchResultListAdapter
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.doAsync
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * The main activity for this app.
 * @author Ronna Faye Jamola
 */
class MainActivity : AppCompatActivity() {

    private val repoRetriever = RepositoryRetriever()
    var mPrefs: SharedPreferences? = null

    /**
     * Callback for searchRepositories using Retrofit.
     * Responds if Successful or Error execution.
     * If successful, list will be displayed using SearchResultListAdapter
     */
    private val callback = object : Callback<SearchResult> {
        override fun onFailure(call: Call<SearchResult>?, t: Throwable?) {
            Log.e("MainActivity", "PProblem calling iTunes API", t)
        }

        override fun onResponse(call: Call<SearchResult>, response: Response<SearchResult>) {
            response?.isSuccessful.let {
                repoList.adapter = SearchResultListAdapter(response.body(), this@MainActivity, mPrefs)
            }
        }
    }

    /**
     * Initializes and sets the different layout in MainAcitivity.
     * Performs automatic search upon opening of the App.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.appetiser.itunesapp.R.layout.activity_main)
        repoList.layoutManager = LinearLayoutManager(this)

        repoListFaveItems.layoutManager = LinearLayoutManager(this)

        setSupportActionBar(findViewById(com.appetiser.itunesapp.R.id.my_toolbar))
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setIcon(com.appetiser.itunesapp.R.drawable.ic_outline_movie_filter_24px)

        mPrefs = this.getSharedPreferences("SearchResult" , Context.MODE_PRIVATE)
        startSearch();
    }

    /**
     * Menu items actions.
     * @param item MenutItem seletected.
     * @return Boolean
     */
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.getItemId()) {
            com.appetiser.itunesapp.R.id.action_refresh -> {
                startSearch();
                repoListFaveItems.visibility = View.GONE
                repoList.visibility = View.VISIBLE
                Toast.makeText(this, "List refresh",Toast.LENGTH_LONG).show()
            }
            com.appetiser.itunesapp.R.id.saved_items ->{
                retrieveSavedItems()
                repoListFaveItems.visibility = View.VISIBLE
                repoList.visibility = View.GONE
                Toast.makeText(this, "Showing saved items",Toast.LENGTH_LONG).show()
            }
            else -> {
            }
        }

        return true
    }

    /**
     * Inflates the menu Home, Refresh and Saved Items
     * @param The menu to be inflated
     * @return Boolean
     */
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(com.appetiser.itunesapp.R.menu.toolbar_menu, menu)
        return true
    }

    /**
     * Retrieves all the saved iTunes search results via SharedPreferences
     */
    private fun retrieveSavedItems(){
        val saveFile: SaveFileItems = SaveFileItems();
        val result: SearchResult? = saveFile.retrieveFile(mPrefs)
        repoListFaveItems.adapter = SearchResultListAdapter(result, this@MainActivity, mPrefs)
    }

    /**
     * Checks the network connection before performing a search in iTunes
     * @return Returns the network connection. True if connected, false otherwise.
     */
    private fun isNetworkConnected(): Boolean {
        val connectivity = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connectivity.activeNetworkInfo
        return networkInfo != null && networkInfo.isConnected
    }

    /**
     * Performs search in iTunes with specific search parameters.
     *  Uses Retrofit to call and retrieve into iTunes.
     *  @throws Exception
     */
    private fun startSearch() {
        try {
            if (isNetworkConnected()) {
                doAsync {
                    repoRetriever.getRepositories(callback)
                }
            } else {

                /// The Builder pattersn
                AlertDialog.Builder(this).setTitle("No Internet Connection")
                        .setMessage("Please check your internet connection and try again or you can check saved items in Menu -> Saved Items.")
                        .setPositiveButton(android.R.string.ok) { _, _ -> }
                        .setIcon(android.R.drawable.ic_dialog_alert).show()
            }
        } catch (ex: Exception) {
            Log.d("Error:", ex.toString())
        }
    }


}
