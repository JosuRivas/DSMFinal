package dsm.udb.rg180141.gg162362.mr171225.rp142494;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Dashboard_Negocio extends AppCompatActivity {

    androidx.appcompat.widget.Toolbar toolbar;
    TextView tvBienvenida;
    Button btnEditarNegocio;
    DatabaseReference miDatabaseUsers,miDatabaseNegocios;
    String user;
    String idNegocio;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard_negocio);

        toolbar = (Toolbar) findViewById(R.id.toolbarMenu);
        toolbar.setTitle("Smart Services");
        setSupportActionBar(toolbar);
        tvBienvenida = (TextView) findViewById(R.id.txtUser);
        btnEditarNegocio = (Button) findViewById(R.id.btnEditarInfoNegocio);
        miDatabaseUsers = FirebaseDatabase.getInstance().getReference("usuarios");
        miDatabaseNegocios = FirebaseDatabase.getInstance().getReference("negocios");
        Bundle datos = getIntent().getExtras();
        user = datos.getString("user");
        setTextoBienvenida();

        btnEditarNegocio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Dashboard_Negocio.this,Editar_Negocio.class);
                intent.putExtra("idNegocio",idNegocio);
                startActivity(intent);
            }
        });




    }

    private void setTextoBienvenida() {

        miDatabaseUsers.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    if (dataSnapshot.child("email").getValue().toString().equals(user)){
                        idNegocio = dataSnapshot.child("idNegocio").getValue().toString();

                        miDatabaseNegocios.child(idNegocio).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<DataSnapshot> task) {
                                if (task.isSuccessful()){
                                    tvBienvenida.setText(task.getResult().child("nombre").getValue().toString());
                                }
                            }
                        });
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_inicio,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){

            case R.id.itemCerrarSesion: {
                Intent intent = new Intent(Dashboard_Negocio.this, MainActivity.class);
                startActivity(intent);
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }

}