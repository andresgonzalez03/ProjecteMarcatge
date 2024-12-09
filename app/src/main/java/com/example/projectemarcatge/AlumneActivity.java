package com.example.projectemarcatge;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

public class AlumneActivity extends AppCompatActivity {

    private EditText etIdAlumno, etFecha;
    private Button consultarBtn;
    private CardView resultContainer;
    private TextView errorText;
    private TableLayout resultTable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alumne);

        etIdAlumno = findViewById(R.id.etIdAlumno);
        etFecha = findViewById(R.id.etFecha);
        consultarBtn = findViewById(R.id.consultarBtn);
        resultContainer = findViewById(R.id.resultContainer);
        errorText = findViewById(R.id.error);
        resultTable = findViewById(R.id.resultTable);

        consultarBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String idAlumno = etIdAlumno.getText().toString().trim();
                String fecha = etFecha.getText().toString().trim();

                if (idAlumno.isEmpty() || fecha.isEmpty()) {
                    Toast.makeText(AlumneActivity.this, "Por favor ingresa todos los campos", Toast.LENGTH_SHORT).show();
                    return;
                }
                consultarAsistencia(idAlumno, fecha);
            }
        });
    }
    private void consultarAsistencia(String idAlumno, String fecha) {
        boolean foundResults = true;
        if (foundResults) {
            resultContainer.setVisibility(View.VISIBLE);
            errorText.setVisibility(View.GONE);
            resultTable.removeAllViews();
            String[][] fakeResults = {
                    {"Lunes", "10:00 AM", "12:00 PM", "Asistido", "Aula 101", "Matemáticas"},
                    {"Martes", "11:00 AM", "1:00 PM", "Asistido", "Aula 102", "Física"},
                    {"Miércoles", "9:00 AM", "11:00 AM", "Asistido", "Aula 103", "Química"}
            };
            for (String[] rowData : fakeResults) {
                TableRow row = new TableRow(this);

                for (String data : rowData) {
                    TextView textView = new TextView(this);
                    textView.setText(data);
                    textView.setPadding(10, 10, 10, 10);
                    row.addView(textView);
                }

                resultTable.addView(row);
            }
        } else {
            resultContainer.setVisibility(View.GONE);
            errorText.setVisibility(View.VISIBLE);
        }
    }
}