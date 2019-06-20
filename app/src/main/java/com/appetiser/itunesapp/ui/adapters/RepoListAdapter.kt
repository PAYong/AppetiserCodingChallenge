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

package com.appetiser.itunesapp.ui.adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.appetiser.itunesapp.R
import com.appetiser.itunesapp.data.SearchItems
import com.appetiser.itunesapp.data.SearchResult
import com.appetiser.itunesapp.extensions.ctx
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_repo.view.*


class RepoListAdapter(private val repoList: SearchResult?) : RecyclerView.Adapter<RepoListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.ctx).inflate(R.layout.item_repo, parent, false) //2
        return ViewHolder(view)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if(repoList!=null)
            holder.bindRepo(repoList?.results[position]) //3
    }

    override fun getItemCount():Int{
        if(repoList!=null)
                return repoList.results.size //4)
        return 0
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bindRepo(repo: SearchItems?) { //5
            with(repo) {
                itemView.trackname.text = repo?.trackName.orEmpty() //6
                itemView.primaryGenreName.text = repo?.primaryGenreName.orEmpty() //7
                itemView.trackPrice.text = repo?.trackPrice.orEmpty()
                Picasso.get().load(repo?.artworkUrl100).into(itemView.icon)
            }
        }
    }
}