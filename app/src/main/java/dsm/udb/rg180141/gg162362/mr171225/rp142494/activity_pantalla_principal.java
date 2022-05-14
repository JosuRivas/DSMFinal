package dsm.udb.rg180141.gg162362.mr171225.rp142494;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.GridLayout;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

public class activity_pantalla_principal extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    //VARIABLES

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    GridLayout gridLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantalla_principal);

        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView=findViewById(R.id.nav_view);
        gridLayout = (GridLayout) findViewById(R.id.layoutGrid);

        toolbar = (Toolbar) findViewById(R.id.toolbarMenu);
        setSupportActionBar(toolbar);


        navigationView.bringToFront();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this , drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed(){
        if (drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem){
        return false;
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
                Intent intent = new Intent(activity_pantalla_principal.this, MainActivity.class);
                startActivity(intent);
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    public void onClickTarjeta(View vista){
        String idTarjeta = String.valueOf(getResources().getResourceEntryName(vista.getId()));
        Intent intent = new Intent(activity_pantalla_principal.this,Lista_Negocios.class);
        switch (idTarjeta){
            case "tarjetaMedico":
                intent.putExtra("tipo","medico");
                break;
            case "tarjetaMecanico":
                intent.putExtra("tipo","mecanico");
                break;
            case "tarjetaGrua":
                intent.putExtra("tipo","grua");
                break;
            case "tarjetaFontanero":
                intent.putExtra("tipo","fontanero");
                break;
            case "tarjetaCerrajero":
                intent.putExtra("tipo","cerrajero");
                break;
            case "tarjetaElectricista":
                intent.putExtra("tipo","electricista");
                break;
            case "tarjetaEstilista":
                intent.putExtra("tipo","estilista");
                break;
            case "tarjetaJardinero":
                intent.putExtra("tipo","jardinero");
                break;
            case "tarjetaMascotas":
                intent.putExtra("tipo","mascotas");
                break;
            case "tarjetaSastre":
                intent.putExtra("tipo","sastreria");
                break;
        }
        startActivity(intent);
    }

}