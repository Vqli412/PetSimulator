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
    private lateinit var usersReference: DatabaseReference
    private lateinit var user: User

    //class for the user object for the firebase database
    @IgnoreExtraProperties
    data class User(
        var email: String = "",
        var password: String = "",
        var pet: Int = 0
    )

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
        user = User(regEmail.text.toString(), regPass.text.toString())
        var regListener: RegisterListener = RegisterListener()
        usersReference.orderByChild("email").equalTo(regEmail.text.toString())
            .addListenerForSingleValueEvent(regListener)
    }

    fun login() {
        var logListener: LoginListener = LoginListener()
        usersReference.orderByChild("email").equalTo(logEmail.text.toString())
            .addListenerForSingleValueEvent(logListener)

        //need to implement a check to see if the user has pulled before:
        //if not pulled, bring to gacha
        //else if pulled, bring to pet homescreen

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
                if (regEmail.text.length < 1 || regPass.text.length < 1) {
                    regError.text = "Credentials must be at least 1 character long"
                } else {
                    val newUserRef = usersReference.push()
                    newUserRef.setValue(user)
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
                var foundUser = foundInstance.getValue(User::class.java)
                if (foundUser != null) {
                    var userPass = foundUser.password
                    if (userPass == logPass.text.toString()) {
                        Log.w("MainActivity", "Password Matches")
                        user = foundUser
                        // if password matches, then check if the user has rolled a pet already
                        //if user does not have a pet, represented as 0, go to the gacha page, else go to home page
                        if (foundUser.pet == 0) {
                            var intent : Intent = Intent(this@MainActivity, GachaActivity::class.java)
                            startActivity(intent)
                        } else {
                            var intent : Intent = Intent(this@MainActivity, PethomeActivity::class.java)
                            startActivity(intent)
                        }
                    } else {
                        Log.w("MainActivity", "Password does not matches")
                        logError.text = "Wrong Password!"
                    }
                } else {
                    Log.w("MainActivity", "Password matches")
                }
            } else {
                Log.w("MainActivity", "Email exists")
                logError.text = "User does not exist!"
            }
        }

        override fun onCancelled(error: DatabaseError) {
            Log.w("MainActivity", "error")
        }

    }
}