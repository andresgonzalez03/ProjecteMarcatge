package com.example.projectemarcatge;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class AlumneActivity extends AppCompatActivity {

    private EditText etNombreAlumno, etFecha;
    private Button consultarBtn;
    private CardView resultContainer;
    private TextView errorText;
    private TableLayout resultTable;
    private List<Asistencia> listaAsistencias;
    private List<String> diasConAsistencia;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alumne);

        etNombreAlumno = findViewById(R.id.etNombreAlumno);
        etFecha = findViewById(R.id.etFecha);
        consultarBtn = findViewById(R.id.consultarBtn);
        resultContainer = findViewById(R.id.resultContainer);
        errorText = findViewById(R.id.error);
        resultTable = findViewById(R.id.resultTable);

        listaAsistencias = new ArrayList<>();
        diasConAsistencia = new ArrayList<>();
        cargarDiasConAsistencia();
        cargarDatosFalsos();

        etFecha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mostrarDatePicker();
            }
        });

        consultarBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nombreAlumno = etNombreAlumno.getText().toString().trim();
                String fecha = etFecha.getText().toString().trim();

                if (nombreAlumno.isEmpty() || fecha.isEmpty()) {
                    Toast.makeText(AlumneActivity.this, "Si us plau ingresa tots els camps", Toast.LENGTH_SHORT).show();
                    return;
                }

                cargarDatosAsistencia(nombreAlumno, fecha);
            }
        });
    }
    private void mostrarDatePicker() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                etFecha.setText(year + "-" + (month + 1) + "-" + dayOfMonth);
            }
        }, year, month, dayOfMonth);

        datePickerDialog.show();
    }

    private void cargarDatosAsistencia(String nombreAlumno, String fecha) {
        resultTable.removeAllViews();

        TableRow headerRow = new TableRow(this);
        agregarCelda(headerRow, "Nom", true);
        agregarCelda(headerRow, "Assignatura", true);
        agregarCelda(headerRow, "Entrada", true);
        agregarCelda(headerRow, "Sortida", true);
        agregarCelda(headerRow, "Estat", true);
        resultTable.addView(headerRow);

        boolean foundResults = false;
        for (Asistencia asistencia : listaAsistencias) {
            if (asistencia.getNombre().equalsIgnoreCase(nombreAlumno) && asistencia.getFecha().equals(fecha)) {
                foundResults = true;

                TableRow row = new TableRow(this);

                agregarCelda(row, asistencia.getNombre(), false);
                agregarCelda(row, asistencia.getAsignatura(), false);
                agregarCelda(row, asistencia.getHoraEntrada(), false);
                agregarCelda(row, asistencia.getHoraSalida(), false);
                agregarCeldaConColorEstado(row, asistencia.getEstado(), false);

                resultTable.addView(row);
            }
        }

        if (!foundResults) {
            resultContainer.setVisibility(View.GONE);
            errorText.setVisibility(View.VISIBLE);
            errorText.setText("No s'ha trobat asistència per l'alumne i la data sel·leccionada");
        } else {
            resultContainer.setVisibility(View.VISIBLE);
            errorText.setVisibility(View.GONE);
        }
    }

    private void agregarCelda(TableRow row, String texto, boolean esEncabezado) {
        TextView textView = new TextView(this);
        textView.setText(texto);
        textView.setPadding(10, 10, 10, 10);

        if (esEncabezado) {
            textView.setTextSize(18);
            textView.setTypeface(null, android.graphics.Typeface.BOLD);
        }

        row.addView(textView);
    }

    private void agregarCeldaConColorEstado(TableRow row, String estado, boolean esEncabezado) {
        TextView textView = new TextView(this);
        textView.setText(estado);
        textView.setPadding(10, 10, 10, 10);

        if (estado.equalsIgnoreCase("Present")) {
            textView.setBackgroundColor(ContextCompat.getColor(this, android.R.color.holo_green_light));
        } else if (estado.equalsIgnoreCase("Absent")) {
            textView.setBackgroundColor(ContextCompat.getColor(this, android.R.color.holo_red_light));
        } else if (estado.equalsIgnoreCase("Retard")) {
            textView.setBackgroundColor(ContextCompat.getColor(this, android.R.color.holo_orange_light));
        }

        row.addView(textView);
    }
    private void cargarDiasConAsistencia() {
        diasConAsistencia.add("1");
        diasConAsistencia.add("2");
        diasConAsistencia.add("3");
        diasConAsistencia.add("4");
        diasConAsistencia.add("5");
        diasConAsistencia.add("6");
        diasConAsistencia.add("9");
        diasConAsistencia.add("10");
        diasConAsistencia.add("11");
        diasConAsistencia.add("12");
        diasConAsistencia.add("13");
        diasConAsistencia.add("16");
        diasConAsistencia.add("17");
        diasConAsistencia.add("18");
        diasConAsistencia.add("19");
        diasConAsistencia.add("20");
    }

    private void cargarDatosFalsos() {
        String[] alumnos = {"Arman", "Bea", "Carlos", "David", "Eva"};
        String[] asignaturas = {"M7", "M6", "M8", "M12", "M9"};
        String[] horasEntrada = {"9:00 AM", "10:00 AM", "11:00 AM", "1:00 PM", "9:00 AM"};
        String[] horasSalida = {"11:00 AM", "12:00 PM", "1:00 PM", "3:00 PM", "11:00 AM"};
        String[] estados = {"Present", "Absent", "Present", "Retard", "Retard"};

        for (String alumno : alumnos) {
            for (String dia : diasConAsistencia) {
                for (int i = 0; i < asignaturas.length; i++) {
                    listaAsistencias.add(new Asistencia(
                            alumno,
                            "2024-12-" + dia,
                            horasEntrada[i],
                            horasSalida[i],
                            estados[i],
                            asignaturas[i]
                    ));
                }
            }
        }
    }

    public static class Asistencia {
        private String nombre;
        private String fecha;
        private String horaEntrada;
        private String horaSalida;
        private String estado;
        private String asignatura;

        public Asistencia(String nombre, String fecha, String horaEntrada, String horaSalida, String estado, String asignatura) {
            this.nombre = nombre;
            this.fecha = fecha;
            this.horaEntrada = horaEntrada;
            this.horaSalida = horaSalida;
            this.estado = estado;
            this.asignatura = asignatura;
        }

        public String getNombre() {
            return nombre;
        }

        public String getFecha() {
            return fecha;
        }

        public String getHoraEntrada() {
            return horaEntrada;
        }

        public String getHoraSalida() {
            return horaSalida;
        }

        public String getEstado() {
            return estado;
        }

        public String getAsignatura() {
            return asignatura;
        }
    }
}