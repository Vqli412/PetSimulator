package com.example.finalproject

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.IgnoreExtraProperties
import com.example.finalproject.DBUser
import com.google.firebase.database.ValueEventListener
import org.w3c.dom.Text

class MainActivity : AppCompatActivity() {
    private lateinit var regEmail: EditText
    private lateinit var regPass: EditText
    private lateinit var regSubmit: Button
    private lateinit var logEmail: EditText
    private lateinit var logPass: EditText
    private lateinit var logSubmit: Button
    private lateinit var regError: TextView
    private lateinit var logError: TextView
    private lateinit var firebase: FirebaseDatabase
    private lateinit var user: DBUser

    //class for the user object for the firebase database

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //instantiate the views
        regEmail = findViewById(R.id.registerEmail)
        regPass = findViewById(R.id.registerPassword)
        regSubmit = findViewById(R.id.registerSubmit)
        regError = findViewById(R.id.registerError)
        logEmail = findViewById(R.id.loginEmail)
        logPass = findViewById(R.id.loginPassword)
        logSubmit = findViewById(R.id.loginSubmit)
        logError = findViewById(R.id.loginError)
        firebase = FirebaseDatabase.getInstance()
        usersReference = firebase.getReference("users")
        //set register button listener
        regSubmit.setOnClickListener{register()}
        logSubmit.setOnClickListener{login()}
    }

    fun register() {
        //creates new user to add
        email = regEmail.text.toString()
        password = regPass.text.toString()
        user = DBUser(email, password, -1)
        var regListener: RegisterListener = RegisterListener()
        //get objects by the emails
        usersReference.orderByChild("email").equalTo(email)
            .addListenerForSingleValueEvent(regListener)
    }

    fun login() {
        email = logEmail.text.toString()
        password = logPass.text.toString()
        var logListener: LoginListener = LoginListener()
        usersReference.orderByChild("email").equalTo(email)
            .addListenerForSingleValueEvent(logListener)
    }

    //checks if the user exists in the database
    inner class RegisterListener: ValueEventListener {
        override fun onDataChange(snapshot: DataSnapshot) {
            if (snapshot.exists()) {
                regError.text = "User exists!"
                Log.w("MainActivity", "Email exists")
            } else {
                //add the user if the user does not exist
                //generates a new unique key for the new user object
                val newUserRef = usersReference.push()
                newUserRef.setValue(user)
                if (regEmail.text.length < 1 || regPass.text.length < 1) {
                    regError.text = "Credentials must be at least 1 character long"
                } else {
                    Log.w("MainActivity", "User added")
                    //goes to the gacha page when account first created
                    var intent: Intent = Intent(this@MainActivity, GachaActivity::class.java)
                    startActivity(intent)
                }
            }
        }

        override fun onCancelled(error: DatabaseError) {
            Log.w("MainActivity", "error")
        }

    }

    inner class LoginListener(): ValueEventListener {
        override fun onDataChange(snapshot: DataSnapshot) {
            if (snapshot.exists()) {
                var foundInstance = snapshot.children.first() //Get the first child
                var foundUser = foundInstance.getValue(DBUser::class.java)
                if (foundUser != null) {
                    var userPass = foundUser.password
                    if (userPass == logPass.text.toString()) {
                        Log.w("MainActivity", "Password Matches")
                        user = foundUser
                        // if password matches, then check if the user has rolled a pet already
                        //if user does not have a pet, represented as 0, go to the gacha page, else go to home page
                        if (foundUser.pet == -1) {
                            var intent : Intent = Intent(this@MainActivity, GachaActivity::class.java)
                            startActivity(intent)
                        } else {
                            val intent = Intent(this@MainActivity, PethomeActivity::class.java).apply {
                                putExtra("capyResId", foundUser.pet)
                            }
                            startActivity(intent)
                        }
                    } else {
                        Log.w("MainActivity", "Password does not matches")
                        logError.text = "Wrong Password!"
                    }
                }
            } else {
                Log.w("MainActivity", "Email does not exists")
                logError.text = "User does not exist!"
            }
        }

        override fun onCancelled(error: DatabaseError) {
            Log.w("MainActivity", "error")
        }
    }

    companion object {
        public lateinit var usersReference: DatabaseReference
        public lateinit var email: String
        public lateinit var password: String
    }
}