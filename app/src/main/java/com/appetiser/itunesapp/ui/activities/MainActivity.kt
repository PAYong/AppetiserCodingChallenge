/*
 * Copyright (c) 2018 Razeware LLC
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * Notwithstanding the foregoing, you may not use, copy, modify, merge, publish,
 * distribute, sublicense, create a derivative work, and/or sell copies of the
 * Software in any work that is designed, intended, or marketed for pedagogical or
 * instructional purposes related to programming, coding, application development,
 * or information technology.  Permission for such use, copying, modification,
 * merger, publication, distribution, sublicensing, creation of derivative works,
 * or sale is expressly withheld.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package com.appetiser.itunesapp.ui.activities

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.net.ConnectivityManager
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import com.appetiser.itunesapp.R
import com.appetiser.itunesapp.api.RepositoryRetriever
import com.appetiser.itunesapp.data.SearchResult
import com.appetiser.itunesapp.ui.adapters.RepoListAdapter
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.doAsync
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : Activity() {

  private val repoRetriever = RepositoryRetriever()

  private val callback = object : Callback<SearchResult> {
    override fun onFailure(call: Call<SearchResult>?, t: Throwable?) {
      Log.e("MainActivity", "Problem calling Github API", t)
    }

    override fun onResponse(call: Call<SearchResult>, response: Response<SearchResult>) {
      response?.isSuccessful.let {
        val ret: SearchResult
        repoList.adapter = RepoListAdapter(response.body())
      }
    }
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

    repoList.layoutManager = LinearLayoutManager(this)

    buttonSearch.setOnClickListener(){
      startSearch();
    }

  }

  private fun isNetworkConnected(): Boolean{
    val connectivity = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val networkInfo = connectivity.activeNetworkInfo
    return networkInfo!= null && networkInfo.isConnected
  }

  private fun startSearch(){
    try {
      if (isNetworkConnected()) {
        doAsync {
          repoRetriever.getRepositories(callback)
        }
      } else {
        AlertDialog.Builder(this).setTitle("No Internet Connection")
                .setMessage("Please check your internet connection and try again")
                .setPositiveButton(android.R.string.ok) { _, _ -> }
                .setIcon(android.R.drawable.ic_dialog_alert).show()
      }
    }catch(ex: Exception){
      Log.d("Error:" , ex.toString())
    }
  }


}
