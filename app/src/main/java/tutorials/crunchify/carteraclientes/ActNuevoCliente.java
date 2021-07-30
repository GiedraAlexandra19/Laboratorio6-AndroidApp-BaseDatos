package tutorials.crunchify.carteraclientes;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import tutorials.crunchify.carteraclientes.BaseDatos.DatosOpenHelper;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import tutorials.crunchify.carteraclientes.databinding.ActNuevoClienteBinding;

public class ActNuevoCliente extends AppCompatActivity {
    private EditText edtNombre;
    private EditText edtDireccion;
    private EditText edtEmail;
    private EditText edtTelefono;

    private SQLiteDatabase conexion;
    private DatosOpenHelper datosOpenHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_nuevo_cliente);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        edtNombre = (EditText) findViewById(R.id.edtNombre);
        edtDireccion = (EditText) findViewById(R.id.edtDireccion);
        edtEmail = (EditText) findViewById(R.id.edtEmail);
        edtTelefono = (EditText) findViewById(R.id.edtTelefono);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_act_nuevo_cliente, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.action_ok:
                if(bCamposCorrectos()) {
                    try {
                        datosOpenHelper = new DatosOpenHelper(this);
                        conexion = datosOpenHelper.getWritableDatabase();
                        StringBuilder sql = new StringBuilder();
                        sql.append("INSERT INTO CLIENTE (NOMBRE, DIRECCION, EMAIL, TELEFONO) VALUES ('");
                        sql.append(edtNombre.getText().toString().trim() + "' , '");
                        sql.append(edtDireccion.getText().toString().trim() + "' , '");
                        sql.append(edtEmail.getText().toString().trim() + "' , '");
                        sql.append(edtTelefono.getText().toString().trim() + "' )");
                        conexion.execSQL(sql.toString());
                        conexion.close();
                        finish();

                    } catch(Exception ex){
                        AlertDialog.Builder dlg = new AlertDialog.Builder(this);
                        dlg.setTitle("Aviso");
                        dlg.setMessage(ex.getMessage());
                        dlg.setNeutralButton("OK", null);
                        dlg.show();

                    }
                }
                else{
                    AlertDialog.Builder dlg = new AlertDialog.Builder(this);
                    dlg.setTitle("Aviso");
                    dlg.setMessage("Existen campos vacios");
                    dlg.setNeutralButton("OK", null);
                    dlg.show();
                }
                //Toast.makeText(this,"Boton Ok seleccionado", Toast.LENGTH_SHORT).show();
                break;

            case R.id.action_cancelar:
                //Toast.makeText(this,"Boton Cancelar seleccionado",Toast.LENGTH_SHORT).show();
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private boolean bCamposCorrectos() {
        boolean res = true;
        if (edtNombre.getText().toString().trim().isEmpty()) {
            edtNombre.requestFocus();
            res = false;
        }
        if (edtDireccion.getText().toString().trim().isEmpty()) {
            edtNombre.requestFocus();
            res = false;
        }
        if (edtEmail.getText().toString().trim().isEmpty()) {
            edtNombre.requestFocus();
            res = false;
        }
        if (edtTelefono.getText().toString().trim().isEmpty()) {
            edtNombre.requestFocus();
            res = false;
        }
        return res;
    }
}