package layout.com.example.helloandroidwithmenu;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView textView = (TextView) findViewById(R.id.textview);

        //long press on textview will invoke ContextMenu
        registerForContextMenu(textView);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.top_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case(R.id.item1):
                Toast.makeText(getApplicationContext(),"You have been helped",Toast.LENGTH_SHORT).show();
                return true;
            case(R.id.item2):
                Toast.makeText(getApplicationContext(),"You have been helped again", Toast.LENGTH_SHORT).show();
                return true;
            case(R.id.item3):
                return true;
            default:
                super.onOptionsItemSelected(item);
                return true;
        }
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo){
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.context_menu, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item){
        switch (item.getItemId()){
            case(R.id.contextItem):
                Toast.makeText(getApplicationContext(), "Context Menu is shown. Please use Options Menu.", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return true;
        }
    }
}
