package com.example.guessthenumber;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;

public class GameActivity extends AppCompatActivity {
    private TextView textViewLast,textViewRight,textViewHint;
    private Button buttonConfirm;
    private EditText editTextGuess;
    boolean twoDigits,threeDigits,fourDigits;
    Random r=new Random();
    int num,tries=10;
    ArrayList<Integer> guessesList=new ArrayList<>();
    int userAttempts=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        textViewHint=findViewById(R.id.textViewHint);
        textViewLast=findViewById(R.id.textViewLast);
        textViewRight=findViewById(R.id.textViewRight);
        buttonConfirm=findViewById(R.id.buttonConfirm);
        editTextGuess=findViewById(R.id.editTextGuess);
        twoDigits=getIntent().getBooleanExtra("two",false);
        threeDigits=getIntent().getBooleanExtra("three",false);
        fourDigits=getIntent().getBooleanExtra("four",false);
        if(twoDigits)
        {
            num=r.nextInt(90)+10;

        }
        else if(threeDigits)
        {
            num=r.nextInt(900)+100;
        }
        else if(fourDigits)
        {
            num=r.nextInt(9000)+1000;
        }
        buttonConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String guess=editTextGuess.getText().toString();
                if(guess.equals(""))
                {
                    Toast.makeText(GameActivity.this,"Please guess a number",Toast.LENGTH_LONG).show();
                }
                else
                {
                    textViewLast.setVisibility(view.VISIBLE);
                    textViewHint.setVisibility(view.VISIBLE);
                    textViewRight.setVisibility(view.VISIBLE);
                    userAttempts++;
                    tries--;
                    int userGuess=Integer.parseInt(guess);
                    guessesList.add(userGuess);
                    textViewLast.setText("Your last guess is : "+guess);
                    textViewRight.setText("Your remaining tries are : "+tries);
                    if(num==userGuess)
                    {
                        AlertDialog.Builder builder=new AlertDialog.Builder(GameActivity.this);
                        builder.setTitle("Number guessing game");
                        builder.setCancelable(false);
                        builder.setMessage("Congrats the number was : "+num+"\n\n"+"You know my number in "+userAttempts+" attempts"+"\n\n"+"Your guesses : "+guessesList+"\n\nWould you like to play again?");
                        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Intent intent=new Intent(GameActivity.this,MainActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        });
                        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                moveTaskToBack(true);
                                android.os.Process.killProcess(android.os.Process.myPid());
                                System.exit(1);
                            }
                        });
                        builder.create().show();
                    }
                    else if(num<userGuess)
                    {
                        textViewHint.setText("Decrease your guess");
                    }
                    else if(num>userGuess)
                    {
                        textViewHint.setText("Increase your guess");
                    }
                    else if(tries==0)
                    {
                        AlertDialog.Builder builder=new AlertDialog.Builder(GameActivity.this);
                        builder.setTitle("Number guessing game");
                        builder.setCancelable(false);
                        builder.setMessage("Sorry the tries are over : "+num+"\n\n"+"Your guesses : "+guessesList+"\n\nWould you like to play again?");
                        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Intent intent=new Intent(GameActivity.this,MainActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        });
                        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                moveTaskToBack(true);
                                android.os.Process.killProcess(android.os.Process.myPid());
                                System.exit(1);
                            }
                        });
                        builder.create().show();

                    }
                    editTextGuess.setText("");
                }
            }
        });
    }
}