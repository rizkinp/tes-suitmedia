package com.example.appc_suitmedia_test.ui
//first screen
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.appc_suitmedia_test.R
import com.google.android.material.textfield.TextInputEditText

class FirstScreen : AppCompatActivity() {

    //initialize component
    private lateinit var edName: TextInputEditText
    private lateinit var edPolindrome: TextInputEditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_first)

        edName = findViewById(R.id.edName)
        edPolindrome = findViewById(R.id.edPolindrome)
        val btnCheckPolindrome = findViewById<Button>(R.id.btnCheckPolindrome)
        val btnNextPage = findViewById<Button>(R.id.btnNextPage)

        btnNextPage.setOnClickListener {
            // get value from edName
            val name = edName.text.toString().trim()

            //Check if edName is not null
            if (!TextUtils.isEmpty(name)) {
                //go to second page
                val intent = Intent(this@FirstScreen, SecondScreen::class.java)
                //send value of name
                intent.putExtra("name", name)
                startActivity(intent)
            } else {
                Toast.makeText(this, "Please field name column", Toast.LENGTH_LONG).show()
            }
        }

        btnCheckPolindrome.setOnClickListener {
            // get value edPolindrome
            val inputPolindrome = edPolindrome.text.toString().trim()

            //chek value null or not
            if (!TextUtils.isEmpty(inputPolindrome)) {
                // check polindrome or not
                val isPalindrome = checkPalindrome(inputPolindrome)
                // showing message
                showMessageDialog(isPalindrome)
            } else {
                Toast.makeText(this, "Please field polindrome column", Toast.LENGTH_LONG).show()
            }
        }
    }

    //Function to check polindrome or not
    private fun checkPalindrome(input: String): Boolean {
        val reversed = input.reversed()
        return input == reversed
    }

    private fun showMessageDialog(isPalindrome: Boolean) {
        // Make message to return polindrome or not
        val message = if (isPalindrome) "That is polindrome" else "That's Not a Palindrome!"

        // Showing dialog message
        AlertDialog.Builder(this)
            .setMessage(message)
            .setPositiveButton("OK") { dialog, _ ->
                // Do nothing, close dialog
                dialog.dismiss()
            }
            .create()
            .show()
    }
}
