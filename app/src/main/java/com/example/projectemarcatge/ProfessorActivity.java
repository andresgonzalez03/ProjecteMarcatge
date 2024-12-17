package com.example.projectemarcatge;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class ProfessorActivity extends AppCompatActivity {
    private CardView container;
    private EditText fechaEditText;
    private Spinner asignaturaSpinner, grupoSpinner;
    private Button consultarButton, pasarListaButton;
    private TableLayout resultTableLayout;
    private TextView errorLayout;
    private PieChart pieChart;

    private List<Alumno> alumnosFicticios;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_professor);
        container = findViewById(R.id.container);
        fechaEditText = findViewById(R.id.fecha);
        asignaturaSpinner = findViewById(R.id.asignatura);
        grupoSpinner = findViewById(R.id.grupo);
        consultarButton = findViewById(R.id.consultarBtn);
        pasarListaButton = findViewById(R.id.pasarListaBtn);
        resultTableLayout = findViewById(R.id.resultTable);
        errorLayout = findViewById(R.id.error);
        pieChart= findViewById(R.id.pieChart);

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
        fechaEditText.setOnClickListener(v -> mostrarSelectorFecha());
    }

    private void consultarAsistencia() {
        String fecha = fechaEditText.getText().toString();
        String asignatura = asignaturaSpinner.getSelectedItem() != null ? asignaturaSpinner.getSelectedItem().toString() : "";
        String grupo = grupoSpinner.getSelectedItem() != null ? grupoSpinner.getSelectedItem().toString() : "";


        if (fecha.isEmpty() || asignatura.isEmpty() || grupo.isEmpty()) {
            errorLayout.setVisibility(View.VISIBLE);
            errorLayout.setText("Si us plau, omple tots els camps.");
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
        headerRow.setPadding(20, 20, 20, 20);

        TextView nombreHeader = new TextView(this);
        nombreHeader.setText("Nom de l'Alumne");
        nombreHeader.setTextSize(16);
        nombreHeader.setPadding(20, 20, 20, 20);
        headerRow.addView(nombreHeader);

        TextView presentHeader = new TextView(this);
        presentHeader.setText("Present");
        presentHeader.setTextSize(16);
        presentHeader.setPadding(20, 20, 20, 20);
        headerRow.addView(presentHeader);

        TextView retardHeader = new TextView(this);
        retardHeader.setText("Retard");
        retardHeader.setTextSize(16);
        retardHeader.setPadding(20, 20, 20, 20);
        headerRow.addView(retardHeader);

        TextView absentHeader = new TextView(this);
        absentHeader.setText("Absent");
        absentHeader.setTextSize(16);
        absentHeader.setPadding(20, 20, 20, 20);
        headerRow.addView(absentHeader);

        resultTableLayout.addView(headerRow);

        for (Alumno alumno : alumnosFicticios) {
            TableRow row = new TableRow(this);
            row.setPadding(20, 20, 20, 20);

            TextView nombreTextView = new TextView(this);
            nombreTextView.setText(alumno.getNombre());
            nombreTextView.setTextSize(14);
            nombreTextView.setPadding(20, 20, 20, 20);
            row.addView(nombreTextView);

            CheckBox presentCheckBox = new CheckBox(this);
            presentCheckBox.setButtonTintList(getColorStateList(android.R.color.holo_green_dark));
            row.addView(presentCheckBox);

            CheckBox retardCheckBox = new CheckBox(this);
            retardCheckBox.setButtonTintList(getColorStateList(R.color.yellow));
            row.addView(retardCheckBox);

            CheckBox absentCheckBox = new CheckBox(this);
            absentCheckBox.setButtonTintList(getColorStateList(android.R.color.holo_red_dark));
            row.addView(absentCheckBox);

            presentCheckBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
                if (isChecked) absentCheckBox.setChecked(false);
                if (isChecked) retardCheckBox.setChecked(false);
            });

            retardCheckBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
                if (isChecked) presentCheckBox.setChecked(false);
                if (isChecked) absentCheckBox.setChecked(false);
            });

            absentCheckBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
                if (isChecked) presentCheckBox.setChecked(false);
                if (isChecked) retardCheckBox.setChecked(false);
            });

            resultTableLayout.addView(row);

            View separator = new View(this);
            separator.setLayoutParams(new TableRow.LayoutParams(
                    TableRow.LayoutParams.MATCH_PARENT, 2));
            separator.setBackgroundColor(getResources().getColor(android.R.color.darker_gray));
            resultTableLayout.addView(separator);
        }
    }

    private void pasarLista() {
        int presentes = 0, retardados = 0, ausentes = 0;

        for (int i = 0; i < resultTableLayout.getChildCount(); i++) {
            View row = resultTableLayout.getChildAt(i);

            if (row instanceof TableRow) {
                TableRow tableRow = (TableRow) row;

                int childCount = tableRow.getChildCount();
                Log.d("PasarLista", "Fila " + i + " tiene " + childCount + " elementos.");

                if (childCount >= 4) {
                    try {
                        CheckBox presentCheckBox = (CheckBox) tableRow.getChildAt(1);
                        CheckBox retardCheckBox = (CheckBox) tableRow.getChildAt(2);
                        CheckBox absentCheckBox = (CheckBox) tableRow.getChildAt(3);

                        if (presentCheckBox.isChecked()) {
                            presentes++;
                        } else if (retardCheckBox.isChecked()) {
                            retardados++;
                        } else if (absentCheckBox.isChecked()) {
                            ausentes++;
                        }
                    } catch (ClassCastException e) {
                        Log.e("PasarLista", "Error al hacer cast de los elementos: " + e.getMessage());
                    }
                } else {
                    Log.w("PasarLista", "Fila " + i + " no tiene suficientes elementos.");
                }
            }
        }

        List<PieEntry> entries = new ArrayList<>();

        if (presentes > 0) {
            entries.add(new PieEntry(presentes, "Presents"));
        }
        if (retardados > 0) {
            entries.add(new PieEntry(retardados, "Retardats"));
        }
        if (ausentes > 0) {
            entries.add(new PieEntry(ausentes, "Absents"));
        }

        if (entries.isEmpty()) {
            Toast.makeText(this, "No hay registros para mostrar", Toast.LENGTH_SHORT).show();
            return;
        }

        PieDataSet dataSet = new PieDataSet(entries, "Assistència");
        dataSet.setColors(ColorTemplate.MATERIAL_COLORS);

        PieData data = new PieData(dataSet);

        pieChart.setCenterText("Assistència");
        pieChart.setCenterTextSize(24);
        data.setValueTextSize(20f);
        pieChart.setEntryLabelTextSize(18);
        pieChart.setUsePercentValues(true);
        pieChart.setEntryLabelColor(Color.BLACK);
        pieChart.setDrawEntryLabels(true);
        pieChart.setHoleColor(Color.WHITE);
        pieChart.setHoleRadius(40f);
        pieChart.setTransparentCircleRadius(50f);

        pieChart.setData(data);
        pieChart.invalidate();
        pieChart.animateY(1000);
        pieChart.setVisibility(View.VISIBLE);

        container.setVisibility(View.GONE);
        pasarListaButton.setVisibility(View.GONE);
        errorLayout.setVisibility(View.GONE);
        fechaEditText.setVisibility(View.GONE);
        asignaturaSpinner.setVisibility(View.GONE);
        grupoSpinner.setVisibility(View.GONE);
        consultarButton.setVisibility(View.GONE);

        Toast.makeText(this, "Llista passada", Toast.LENGTH_SHORT).show();
    }

    private void mostrarSelectorFecha() {
        final Calendar calendario = Calendar.getInstance();
        int anio = calendario.get(Calendar.YEAR);
        int mes = calendario.get(Calendar.MONTH);
        int dia = calendario.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                (view, year, month, dayOfMonth) -> {
                    String fechaSeleccionada = dayOfMonth + "/" + (month + 1) + "/" + year;
                    fechaEditText.setText(fechaSeleccionada);
                }, anio, mes, dia);
        datePickerDialog.show();
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