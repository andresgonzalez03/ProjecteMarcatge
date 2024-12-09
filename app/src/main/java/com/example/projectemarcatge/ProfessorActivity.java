package com.example.projectemarcatge;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class ProfessorActivity extends AppCompatActivity {

    private EditText fechaEditText;
    private Spinner asignaturaSpinner, grupoSpinner;
    private Button consultarButton, pasarListaButton;
    private TableLayout resultTableLayout;
    private TextView errorLayout;

    private List<Alumno> alumnosFicticios;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_professor);

        fechaEditText = findViewById(R.id.fecha);
        asignaturaSpinner = findViewById(R.id.asignatura);
        grupoSpinner = findViewById(R.id.grupo);
        consultarButton = findViewById(R.id.consultarBtn);
        pasarListaButton = findViewById(R.id.pasarListaBtn);
        resultTableLayout = findViewById(R.id.resultTable);
        errorLayout = findViewById(R.id.error);

        alumnosFicticios = new ArrayList<>();
        alumnosFicticios.add(new Alumno("Arman", 1));
        alumnosFicticios.add(new Alumno("Bea", 2));
        alumnosFicticios.add(new Alumno("Carlos", 3));
        alumnosFicticios.add(new Alumno("David", 4));
        alumnosFicticios.add(new Alumno("Eva", 5));

        resultTableLayout.setVisibility(View.GONE);
        pasarListaButton.setVisibility(View.GONE);

        consultarButton.setOnClickListener(v -> consultarAsistencia());
        pasarListaButton.setOnClickListener(v -> pasarLista());
    }

    private void consultarAsistencia() {
        String fecha = fechaEditText.getText().toString();
        String asignatura = asignaturaSpinner.getSelectedItem() != null ? asignaturaSpinner.getSelectedItem().toString() : "";
        String grupo = grupoSpinner.getSelectedItem() != null ? grupoSpinner.getSelectedItem().toString() : "";


        if (fecha.isEmpty() || asignatura.isEmpty() || grupo.isEmpty()) {
            errorLayout.setVisibility(View.VISIBLE);
            errorLayout.setText("Per favor, omple tots els camps.");
        } else {
            errorLayout.setVisibility(View.GONE);
            mostrarTabla();
        }
    }

    private void mostrarTabla() {
        resultTableLayout.removeAllViews();
        resultTableLayout.setVisibility(View.VISIBLE);
        pasarListaButton.setVisibility(View.VISIBLE);

        TableRow headerRow = new TableRow(this);
        TextView nombreHeader = new TextView(this);
        nombreHeader.setText("Nom de l'Alumne");
        headerRow.addView(nombreHeader);

        TextView presentHeader = new TextView(this);
        presentHeader.setText("Present");
        headerRow.addView(presentHeader);

        TextView absentHeader = new TextView(this);
        absentHeader.setText("Absent");
        headerRow.addView(absentHeader);

        resultTableLayout.addView(headerRow);

        for (Alumno alumno : alumnosFicticios) {
            TableRow row = new TableRow(this);

            TextView nombreTextView = new TextView(this);
            nombreTextView.setText(alumno.getNombre());
            row.addView(nombreTextView);

            CheckBox presentCheckBox = new CheckBox(this);
            row.addView(presentCheckBox);

            CheckBox absentCheckBox = new CheckBox(this);
            row.addView(absentCheckBox);

            resultTableLayout.addView(row);
        }
    }
    private void pasarLista() {
        Toast.makeText(this, "Llista passada", Toast.LENGTH_SHORT).show();
        resultTableLayout.setVisibility(View.GONE);
        pasarListaButton.setVisibility(View.GONE);
    }
    private static class Alumno {
        private String nombre;
        private int id;

        public Alumno(String nombre, int id) {
            this.nombre = nombre;
            this.id = id;
        }

        public String getNombre() {
            return nombre;
        }

        public int getId() {
            return id;
        }
    }
}
