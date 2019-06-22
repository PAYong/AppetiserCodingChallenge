package com.appetiser.itunesapp.data

import android.content.SharedPreferences
import android.util.Log
import com.google.gson.Gson

/**
 * @author Ronna Faye Jamola
 */
class SaveFileItems{

    /**
     * This function will save all the iTunes search results via SharedPreferences
     * @param item  The SearchResult object to be saved to SharedPreferences
     * @param mPrefs The SharePreferences instance from the MaintActivity
     * @throws Exception
     */
    fun saveFile( item: SearchResult?, mPrefs: SharedPreferences?){

        try {
            val editor = mPrefs!!.edit()
            val gson: Gson  = Gson();
            val json: String = gson.toJson(item);
            editor.putString("SearchResult_FaveItem", json);
            editor.commit();
        }catch (ex: Exception){
            Log.d("Error", ex.message.toString())
        }
    }

    /**
     * Retrieves all the saved iTunes search results via SharedPreferences
     * @param mPrefs The SharePreferences instance from the MaintActivity
     * @return The SearchResult object that saved from saveFile()
     * @throws Exception
     */
     fun retrieveFile(mPrefs: SharedPreferences?): SearchResult?{
         var ret: SearchResult? = null
         try {
             val gson = Gson()
             val json = mPrefs?.getString("SearchResult_FaveItem", "Does not exist")
             ret = gson.fromJson<SearchResult>(json, SearchResult::class.java)
             Log.d("Result:", ret.toString())
             return ret
         }catch(ex:Exception){
             Log.d("Error", ex.message.toString())
         }
         return ret
     }
}