package com.example.appc_suitmedia_test.ui
//SecondScreen
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.example.appc_suitmedia_test.R
import com.example.appc_suitmedia_test.ui.ThirdScreen.Companion.REQUEST_CODE_SECOND_SCREEN

class SecondScreen : AppCompatActivity() {

    //initialize component
    private lateinit var tvShowName: TextView
    private lateinit var tvSelectedUserName: TextView
    private lateinit var btnChooseUser: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)

        // Initialize Toolbar
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        // Declare tvName and replace with intent from the first screen
        tvShowName = findViewById(R.id.tvName)
        val intentData = intent
        val name = intentData.getStringExtra("name")
        tvShowName.text = name

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        // Initialize button choose user
        btnChooseUser = findViewById(R.id.btnChooseUser)
        tvSelectedUserName = findViewById(R.id.tvSelectedName)

        // Listener for button choose user
        btnChooseUser.setOnClickListener {
            //open third screen
            val intentNextPage = Intent(this, ThirdScreen::class.java)
            startActivityForResult(intentNextPage, REQUEST_CODE_SECOND_SCREEN)

        }
    }

    // button back handler on toolbar
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    // Handle result from third scren to show in Textview selectedName
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == ThirdScreen.REQUEST_CODE_SECOND_SCREEN && resultCode == RESULT_OK) {
            val firstName = data?.getStringExtra("first_name")
            val lastName = data?.getStringExtra("last_name")

            tvSelectedUserName.text = "$firstName $lastName"
        }
    }

    companion object {
        const val REQUEST_CODE_THIRD_SCREEN = 123
    }
}
