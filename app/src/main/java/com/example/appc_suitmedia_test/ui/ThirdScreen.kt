package com.example.appc_suitmedia_test.ui
//thirdscreen
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.appc_suitmedia_test.R
import com.example.appc_suitmedia_test.api.ApiService
import com.example.appc_suitmedia_test.model.User
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ThirdScreen : AppCompatActivity() {
    //initialize component
    private lateinit var toolbar: Toolbar
    private lateinit var recyclerView: RecyclerView
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout
    private lateinit var endOfListTextView: TextView
    private lateinit var userAdapter: UserAdapter
    private var userList: MutableList<User> = mutableListOf()
    private var page = 1
    private val perPage = 10
    private var isLoading = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_third)

        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        recyclerView = findViewById(R.id.recyclerView)
        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout)
        endOfListTextView = findViewById(R.id.endOfListTextView)

        userAdapter = UserAdapter(userList)

        userAdapter.setOnItemClickListener { user ->
            // Handle list item when click
            val intent = Intent()
            intent.putExtra("first_name", user.first_name)
            intent.putExtra("last_name", user.last_name)
            setResult(RESULT_OK, intent)
            finish()
        }

        recyclerView.adapter = userAdapter

        val layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager

        swipeRefreshLayout.setOnRefreshListener {
            userList.clear()
            page = 1
            loadUsers()
        }
        //listener for recycelerview
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val visibleItemCount = layoutManager.childCount
                val totalItemCount = layoutManager.itemCount
                val firstVisibleItem = layoutManager.findFirstVisibleItemPosition()

                if (!isLoading && (visibleItemCount + firstVisibleItem) >= totalItemCount) {
                    page++
                    loadUsers()
                }

                if (!isLoading && totalItemCount < perPage) {
                    endOfListTextView.visibility = View.VISIBLE
                }
            }
        })

        loadUsers()
    }
    //function to load user from API
    private fun loadUsers() {
        isLoading = true

        val retrofit = Retrofit.Builder()
            .baseUrl("https://reqres.in/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val apiService = retrofit.create(ApiService::class.java)

        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = apiService.getUsers(page, perPage)
                withContext(Dispatchers.Main) {
                    if (response.isSuccessful) {
                        val userResponse = response.body()
                        if (userResponse != null) {
                            userList.addAll(userResponse.data)
                            userAdapter.notifyDataSetChanged()
                        }
                    }
                    isLoading = false
                    swipeRefreshLayout.isRefreshing = false
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    isLoading = false
                    swipeRefreshLayout.isRefreshing = false
                    // Handle error
                }
            }
        }
    }

    // Handle button back in toolbar
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    companion object {
        const val REQUEST_CODE_SECOND_SCREEN = 456
    }
}
