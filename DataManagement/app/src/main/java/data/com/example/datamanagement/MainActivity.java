package data.com.example.datamanagement;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

public class MainActivity extends AppCompatActivity {

    private final String TAG = "MainActivity";
    private final String file_name = "text.txt";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.layout);

        if (!getFileStreamPath(file_name).exists()){
            try{
                writeToFile();
            }catch (FileNotFoundException ex){
                Log.i(TAG, "File not found");
            }
        }

        try{
            readFile(linearLayout);
        }catch (IOException ex){
            Log.i(TAG, "IOException");
        }
    }


    private void writeToFile() throws FileNotFoundException{

        FileOutputStream fileOutputStream = openFileOutput(file_name, MODE_PRIVATE);
        PrintWriter printWriter = new PrintWriter(new BufferedWriter(new OutputStreamWriter(fileOutputStream)));
        printWriter.println("Sample text 1");
        printWriter.println("Sample text 2");
        printWriter.println("Sample text 3");

        printWriter.close();
    }

    private void readFile(LinearLayout linearLayout) throws IOException{

        FileInputStream fileInputStream = openFileInput(file_name);
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(fileInputStream));

        String val = "";

        while (null != (val = bufferedReader.readLine())){
            TextView textView = new TextView(this);
            textView.setText(val);
            textView.setTextSize(20);
            linearLayout.addView(textView);
        }
    }
}
