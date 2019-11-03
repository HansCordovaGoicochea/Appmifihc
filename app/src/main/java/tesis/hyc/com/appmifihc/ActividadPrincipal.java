package tesis.hyc.com.appmifihc;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import tesis.hyc.com.appmifihc.Clases.Author;

public class ActividadPrincipal extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actividad_principal);

        Author author = new Author("isbn123", 19, 55);
        author.save();
    }
}
