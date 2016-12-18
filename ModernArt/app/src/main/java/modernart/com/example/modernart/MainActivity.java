package modernart.com.example.modernart;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private EditText mEditText1;
    private EditText mEditText2;
    private EditText mEditText3;
    private EditText mEditText4;
    private EditText mEditText5;
    private SeekBar  mSeekBar;
    private TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mEditText1 = (EditText) findViewById(R.id.editText1);
        mEditText2 = (EditText) findViewById(R.id.editText2);
        mEditText3 = (EditText) findViewById(R.id.editText3);
        mEditText4 = (EditText) findViewById(R.id.editText4);
        mEditText5 = (EditText) findViewById(R.id.editText5);
        mTextView = (TextView) findViewById(R.id.textView);

        mSeekBar = (SeekBar) findViewById(R.id.seekBar);
        mSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress,boolean fromUser) {
                mEditText1.setBackgroundColor(Color.parseColor("#FFFFFF") + progress);
                mEditText2.setBackgroundColor(Color.parseColor("#6495ED") + progress);
                mEditText3.setBackgroundColor(Color.parseColor("#8B0000") + progress);
                mEditText4.setBackgroundColor(Color.parseColor("#E6E6FA") + progress);
                mEditText5.setBackgroundColor(Color.parseColor("#4B0082") + progress);
                mTextView.setText(String.valueOf(progress));
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.options_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.moreInformation:
                MoreInformationDialog dialogfragment = new MoreInformationDialog();
                dialogfragment.show(getFragmentManager(),"MoreInformationDialog");
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public static class MoreInformationDialog extends DialogFragment {

        public MoreInformationDialog(){

        }
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setMessage(R.string.message)
                    .setPositiveButton(R.string.visitMOMA, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            //user clicked on 'visit' Moma button.
                            String url = "http://www.MoMA.org";
                            Intent i = new Intent(Intent.ACTION_VIEW);
                            i.setData(Uri.parse(url));
                            startActivity(i);
                        }
                    })
                    .setNegativeButton(R.string.notNow, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            // User cancelled the dialog
                            dialog.dismiss();
                        }
                    });
           //return alertdialog builder
            return builder.create();
        }
    }

}
