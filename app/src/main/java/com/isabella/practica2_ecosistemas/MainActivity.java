package com.isabella.practica2_ecosistemas;

import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.view.View.OnClickListener;
import java.util.Locale;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    private TextView matchChallenge;
    private EditText plainTextRespuesta;
    private Button buttonResponder;
    private TextView textViewPuntaje;
    private String preguntaActual;
    private int respuestaActual;
    private TextView textViewNumeros;
    private int puntaje;
    private TextView textViewnumerotiempo;
    private int limite;
    private boolean stopped;
    private int contador;
    private boolean start;
    private Button buttonintentardenuevo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        generateQuestion();
        puntaje=0;
        limite=0;
        contador=30;

        matchChallenge = findViewById(R.id.matchChallenge);
        plainTextRespuesta = findViewById(R.id.plainTextRespuesta);
        buttonResponder = findViewById(R.id.buttonResponder);
        textViewPuntaje = findViewById(R.id.textViewPuntaje);
        textViewNumeros = findViewById(R.id.textViewNumeros);
        textViewnumerotiempo = findViewById(R.id.textViewnumerotiempo);
        buttonintentardenuevo = findViewById(R.id.buttonintentardenuevo);

        textViewPuntaje.setText("Puntaje: "+puntaje);
        textViewNumeros.setText(preguntaActual);

        buttonResponder.setOnClickListener(
                (v) -> {
                    if(plainTextRespuesta.getText().toString().trim().equals(respuestaActual+"")){
                        puntaje+=15;
                        generateQuestion();
                        textViewPuntaje.setText("Puntaje: "+puntaje);
                        textViewNumeros.setText(preguntaActual);
                    }
                });
        buttonintentardenuevo.setOnClickListener(
                (v) -> {
                    contador=30;
                    stopped=false;
                    generateQuestion();
                    startThread();
                    runOnUiThread(()->{
                        buttonintentardenuevo.setVisibility(View.INVISIBLE);
                    });
                    runOnUiThread(()->{
                        textViewNumeros.setText(preguntaActual);
                    });
                });
        startThread();

        plainTextRespuesta.setOnClickListener(
                (v) -> {
                    String Respuesta = plainTextRespuesta.getText().toString();
                    Toast.makeText(this, Respuesta, Toast.LENGTH_LONG).show();
                }
        );
    }

    public void startThread(){
        new Thread(()->{
            while (!stopped){
                try {
                    if(contador==limite){
                        stopped=true;
                        runOnUiThread(()->{
                            buttonintentardenuevo.setVisibility(View.VISIBLE);
                        });
                    }
                    else{
                        contador--;
                    }
                    runOnUiThread(()->{
                        textViewnumerotiempo.setText(contador+"");
                    });
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public void generateQuestion(){
        Random rand = new Random();
        int n1 = rand.nextInt(21);
        int n2 = rand.nextInt(21);
        int operacion = rand.nextInt(4);
        switch (operacion){
            case 0:
                preguntaActual = n1 +"+"+n2;
                respuestaActual = n1+n2;
                break;
            case 1:
                preguntaActual = n1 +"-"+n2;
                respuestaActual = n1-n2;
                break;
            case 2:
                preguntaActual = n1 +"*"+n2;
                respuestaActual = n1*n2;
                break;
            case 3:
                preguntaActual = n1 +"/"+n2;
                respuestaActual = (int)(n1/n2);
                break;
        }
    }
}