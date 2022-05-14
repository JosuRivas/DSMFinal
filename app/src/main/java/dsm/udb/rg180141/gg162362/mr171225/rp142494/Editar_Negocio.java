package dsm.udb.rg180141.gg162362.mr171225.rp142494;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import dsm.udb.rg180141.gg162362.mr171225.rp142494.modelos.Negocio;

public class Editar_Negocio extends AppCompatActivity {

    private Spinner spinnerDepartamentos,spinnerTipoNegocio;
    private Button btnConfirmarCambios;
    private EditText nombreNegocioEDT,municipioEDT,direccionEDT,horarioEDT,telefonoEDT,rangoPreciosEDT;
    private DatabaseReference miDatabaseNegocios;
    private String idNegocio;
    private ArrayAdapter<CharSequence> adapter;
    private ArrayAdapter<CharSequence> adapterNegocios;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_negocio);

        Bundle datos = getIntent().getExtras();
        idNegocio = datos.getString("idNegocio");

        inicializarVistas();

        miDatabaseNegocios = FirebaseDatabase.getInstance().getReference("negocios");

        spinnerDepartamentos = (Spinner) findViewById(R.id.editarDepartamentoSpinner);
        adapter = ArrayAdapter.createFromResource(this,R.array.array_departamentos, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerDepartamentos.setAdapter(adapter);

        spinnerTipoNegocio = (Spinner) findViewById(R.id.editarTipoNegocioSpinner);
        adapterNegocios = ArrayAdapter.createFromResource(this,R.array.lista_negocios, android.R.layout.simple_spinner_item);
        adapterNegocios.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerTipoNegocio.setAdapter(adapterNegocios);

        cargarDatos();

        btnConfirmarCambios.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Negocio negocio = new Negocio(nombreNegocioEDT.getText().toString(),spinnerDepartamentos.getSelectedItem().toString(),
                        municipioEDT.getText().toString(),telefonoEDT.getText().toString(),direccionEDT.getText().toString(),
                        rangoPreciosEDT.getText().toString(),horarioEDT.getText().toString(),spinnerTipoNegocio.getSelectedItem().toString()
                );

                miDatabaseNegocios.child(idNegocio).setValue(negocio).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(getApplicationContext(),"Datos editados correctamente",Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(Editar_Negocio.this,Dashboard_Negocio.class);
                        startActivity(intent);
                    }
                });
            }
        });
    }

    private void cargarDatos() {
        miDatabaseNegocios.child(idNegocio).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (task.isSuccessful()){
                    Negocio negocio = task.getResult().getValue(Negocio.class);
                    nombreNegocioEDT.setText(negocio.getNombre());
                    municipioEDT.setText(negocio.getMunicipio());
                    direccionEDT.setText(negocio.getDireccion());
                    horarioEDT.setText(negocio.getHorario());
                    telefonoEDT.setText(negocio.getTelefono());
                    rangoPreciosEDT.setText(negocio.getRangoPrecios());
                    spinnerDepartamentos.setSelection(adapter.getPosition(negocio.getDepartamento()));
                    spinnerTipoNegocio.setSelection(adapterNegocios.getPosition(negocio.getTipo()));
                    spinnerTipoNegocio.setEnabled(false);

                }
            }
        });
    }

    private void inicializarVistas() {
        btnConfirmarCambios = findViewById(R.id.btnConfirmarCambios);
        nombreNegocioEDT = findViewById(R.id.editarNombreNegocio);
        municipioEDT = findViewById(R.id.editarMunicipio);
        direccionEDT = findViewById(R.id.editarDireccion);
        horarioEDT = findViewById(R.id.editarHorario);
        telefonoEDT = findViewById(R.id.editarTelefono);
        rangoPreciosEDT = findViewById(R.id.editarRangoPrecios);
    }
}