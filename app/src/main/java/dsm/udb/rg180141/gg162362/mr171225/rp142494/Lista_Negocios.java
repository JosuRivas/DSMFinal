package dsm.udb.rg180141.gg162362.mr171225.rp142494;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import dsm.udb.rg180141.gg162362.mr171225.rp142494.adapters.Recycler_Negocios_Adapter;
import dsm.udb.rg180141.gg162362.mr171225.rp142494.modelos.Negocio;

public class Lista_Negocios extends AppCompatActivity implements Recycler_Negocios_Adapter.OnItemListener {

    RecyclerView recyclerViewNegocios;
    Recycler_Negocios_Adapter negociosAdapter;
    ArrayList<Negocio> listaNegocios;
    DatabaseReference miDatabase;
    Toolbar toolbar;
    TextView idNegocio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_negocios);

        Bundle datos = getIntent().getExtras();
        String tipoNegocio = datos.getString("tipo");
        recyclerViewNegocios = findViewById(R.id.recyclerViewNegocios);

        toolbar = (Toolbar) findViewById(R.id.toolbarMenu);
        setSupportActionBar(toolbar);

        listaNegocios = new ArrayList<Negocio>();
        miDatabase = FirebaseDatabase.getInstance().getReference("negocios");

        recyclerViewNegocios.setLayoutManager(new LinearLayoutManager(this));


        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this,DividerItemDecoration.VERTICAL);
        recyclerViewNegocios.addItemDecoration(dividerItemDecoration);

        miDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    Negocio negocio = dataSnapshot.getValue(Negocio.class);
                    negocio.setId(dataSnapshot.getKey());
                    if (negocio.getTipo().toLowerCase().equals(tipoNegocio.toLowerCase())){
                        listaNegocios.add(negocio);
                    }
                }
                if (listaNegocios.isEmpty()){
                    Toast.makeText(getApplicationContext(),"No se encontraron negocios en esta categoría",Toast.LENGTH_LONG).show();
                }
                negociosAdapter = new Recycler_Negocios_Adapter(getApplicationContext(),listaNegocios,Lista_Negocios.this);
                recyclerViewNegocios.setAdapter(negociosAdapter);
                negociosAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_busqueda,menu);
        MenuItem item = menu.findItem(R.id.barraBusqueda);
        SearchView searchView = (SearchView) item.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                negociosAdapter.getFilter().filter(newText);
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public void onItemClick(int posicion) {
        String id = listaNegocios.get(posicion).getId();
        Intent intent = new Intent(Lista_Negocios.this,contacto_negocio.class);
        intent.putExtra("id",id);
        startActivity(intent);
    }
}