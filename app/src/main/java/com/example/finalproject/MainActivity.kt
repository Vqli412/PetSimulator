package com.example.finalproject

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.IgnoreExtraProperties
import com.google.firebase.database.ValueEventListener

class MainActivity : AppCompatActivity() {
    private lateinit var regEmail: EditText
    private lateinit var regPass: EditText
    private lateinit var regSubmit: Button
    private lateinit var logEmail: EditText
    private lateinit var logPass: EditText
    private lateinit var logSubmit: Button
    private lateinit var firebase: FirebaseDatabase
    private lateinit var usersReference: DatabaseReference
    private lateinit var user: User

    //class for the user object for the firebase database
    @IgnoreExtraProperties
    data class User(
        var email: String = "",
        var password: String = ""
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //instantiate the views
        regEmail = findViewById(R.id.registerEmail)
        regPass = findViewById(R.id.registerPassword)
        regSubmit = findViewById(R.id.registerSubmit)
        logEmail = findViewById(R.id.loginEmail)
        logPass = findViewById(R.id.loginPassword)
        logSubmit = findViewById(R.id.loginSubmit)
        firebase = FirebaseDatabase.getInstance()
        usersReference = firebase.getReference("users")
        //set register button listener
        regSubmit.setOnClickListener{register()}
        logSubmit.setOnClickListener {login()}
    }

    fun register() {
        //creates new user to add
        user = User(regEmail.text.toString(), regPass.text.toString())
        var regListener: RegisterListener = RegisterListener()
        usersReference.orderByChild("email").equalTo(regEmail.text.toString())
            .addListenerForSingleValueEvent(regListener)

        var intent : Intent = Intent(this, GachaActivity::class.java)
        startActivity(intent)
    }

    fun login() {
        var logListener: LoginListener = LoginListener()
        usersReference.orderByChild("email").equalTo(regEmail.text.toString())
            .addListenerForSingleValueEvent(logListener)
    }

    //checks if the user exists in the database
    inner class RegisterListener: ValueEventListener {
        override fun onDataChange(snapshot: DataSnapshot) {
            if (snapshot.exists()) {
                Log.w("MainActivity", "Email exists")
            } else {
                //add the user if the user does not exist
                //generates a new unique key for the new user object
                val newUserRef = usersReference.push()
                newUserRef.setValue(user)
                Log.w("MainActivity", "User added")
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

                    } else {
                        Log.w("MainActivity", "Password Matches")
                    }
                } else {
                    Log.w("MainActivity", "Password Matches")
                }
            } else {
                //add the user if the user does not exist
                //generates a new unique key for the new user object
                Log.w("MainActivity", "Email exists")
            }
        }

        override fun onCancelled(error: DatabaseError) {
            Log.w("MainActivity", "error")
        }

    }
}