package com.example.android.sunshine;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;


public class signup extends ActionBarActivity implements View.OnClickListener
{
    String UserName;
    String PassWord;
    EditText username;
    EditText password;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        Parse.initialize(getApplicationContext(), "N62UdspJn6aJiNRqJdocMVBj9V2rjM3MFMeEhiAL", "KVQn4BWZNEvy8ygdL9xvprnbX7pe1VafLZmpUjuc");
        Button LoginButton = (Button)findViewById(R.id.loginbutton);
        LoginButton.setOnClickListener(this);
        Button SignUpButton = (Button)findViewById(R.id.signUpButton);
        SignUpButton.setOnClickListener(this);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_signup, menu);
        return true;
    }

    @Override
    protected void onPause()
    {
        super.onPause();
        //finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings)
        {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v)
    {
        if(v.getId()==R.id.loginbutton){
            username = (EditText) findViewById(R.id.UserName);
            UserName = username.getText().toString();
            password = (EditText) findViewById(R.id.password);
            PassWord = password.getText().toString();

// Send data to Parse.com for verification
            ParseUser.logInInBackground(UserName, PassWord,
                    new LogInCallback() {
                        public void done(ParseUser user, ParseException e) {
                            if (user != null) {
// If user exist and authenticated, send user to Welcome.class
                                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                                startActivity(intent);
                                Toast.makeText(getApplicationContext(),
                                        "Successfully Logged in",
                                        Toast.LENGTH_LONG).show();
                                finish();
                            } else {
                                Toast.makeText(
                                        getApplicationContext(),
                                        "No such user exist, please signup",
                                        Toast.LENGTH_LONG).show();
                            }
                        }
                    });
        }
        else
        {
            if (v.getId() == R.id.signUpButton)
            {
                username = (EditText) findViewById(R.id.UserName);
                UserName = username.getText().toString();
                password = (EditText) findViewById(R.id.password);
                PassWord = password.getText().toString();
                Log.d("UserName", UserName);
                if (UserName.isEmpty() || PassWord.isEmpty())
                {
                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setMessage("Please make sure you entered all the fields correctly.")
                            .setTitle("Oops!")
                            .setPositiveButton(android.R.string.ok, null);
                    AlertDialog dialog = builder.create();
                    dialog.show();

                    return;
                }
                ParseUser User = new ParseUser();
                User.setUsername(UserName);
                User.setPassword(PassWord);
                username.setText("");
                password.setText("");
                Log.d("Signup", "Before");
                User.signUpInBackground(new SignUpCallback()
                {
                    @Override
                    public void done(ParseException e)
                    {
                          if (e != null)
                          {
                               Toast.makeText(getApplicationContext(),"Saving User Failed",Toast.LENGTH_SHORT).show();
                              Log.d("Signup","Between");
                                Log.w("hi", "Error : " + e.getMessage() + ":::" + e.getCode());
                                if (e.getCode() == 202)
                                {
                                    Toast.makeText(getApplicationContext(),"Username already taken. \n" + " Please choose another username.",Toast.LENGTH_SHORT).show();
                                }
                          }
                          else
                          {
                              Toast.makeText(getApplicationContext(),"User Saved",Toast.LENGTH_SHORT).show();
                    /*Do some things here if you want to.*/
                          }

                    }
                }
                );}
        }
    }
}

