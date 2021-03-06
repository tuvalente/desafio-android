package com.picpay.desafio.android

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.picpay.desafio.android.adapters.HeaderAdapter
import com.picpay.desafio.android.adapters.UserListAdapter
import com.picpay.desafio.android.api.PicPayService
import com.picpay.desafio.android.data.User
import kotlinx.android.synthetic.main.activity_main.*
import okhttp3.Cache
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity(R.layout.activity_main) {

    private val gson: Gson by lazy { GsonBuilder().create() }
    private lateinit var okHttp : OkHttpClient
    private lateinit var retrofit : Retrofit
    private lateinit var service : PicPayService
    var cacheSize = 10 * 1024 * 1024

    private val adapter by lazy {
        UserListAdapter()
    }

    private val headerAdapter by lazy {
        HeaderAdapter()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupHttpService()
    }

    override fun onResume() {
        super.onResume()
        setAdapter()
        refreshData()
    }

    private fun setupHttpService() {
        val cache = Cache(cacheDir, cacheSize.toLong())
        okHttp = OkHttpClient.Builder()
            .cache(cache)
            .addInterceptor {chain ->
                var request = chain.request()
                request = if (Utils.isConnectedToInternet(applicationContext))
                    request.newBuilder().header("Cache-Control", "public, max-age=" + 5).build()
                else
                    request.newBuilder().header("Cache-Control", "public, only-if-cached, max-stale=" + 60 * 60 * 24 * 7).build()

                chain.proceed(request)
            }
            .build()
        retrofit = Retrofit.Builder()
            .baseUrl((application as PicPayApp).getUrl())
            .client(okHttp)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
        service = retrofit.create(PicPayService::class.java)
    }

    private fun setAdapter() {
        recycler_view.layoutManager = LinearLayoutManager(this)
        adapter.stateRestorationPolicy = RecyclerView.Adapter.StateRestorationPolicy.PREVENT
        val concatAdapter = ConcatAdapter(headerAdapter, adapter)
        recycler_view.adapter = concatAdapter
    }

    private fun showProgressBar() {
        progress_bar.visibility = View.VISIBLE
    }
    private fun hideProgressBar() {
        progress_bar.visibility = View.GONE
    }
    private fun showErrorMessage() {
        val message = getString(R.string.error)

        progress_bar.visibility = View.GONE
        recycler_view.visibility = View.GONE

        Toast.makeText(this@MainActivity, message, Toast.LENGTH_SHORT)
            .show()
    }
    private fun refreshData() {
        showProgressBar()
        service.getUsers()
            .enqueue(object : Callback<List<User>> {
                override fun onFailure(call: Call<List<User>>, t: Throwable) {
                    showErrorMessage()
                }

                override fun onResponse(call: Call<List<User>>, response: Response<List<User>>) {
                    hideProgressBar()
                    try {
                        adapter.users = response.body()!!
                        adapter.stateRestorationPolicy = RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY
                    } catch (e: Exception) {
                        showErrorMessage()
                        e.printStackTrace()
                    }
                }
            })
    }
}
