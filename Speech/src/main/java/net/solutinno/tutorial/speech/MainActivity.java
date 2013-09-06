package net.solutinno.tutorial.speech;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends ActionBarActivity implements Button.OnClickListener {

    private EditText mEditText;
    private Button mButtonTextToSpeech;
    private Button mButtonSpeechToText;
    private Button mButtonTTSlanguages;
    private Button mButtonSTTlanguages;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mEditText = (EditText) findViewById(R.id.editText);
        mButtonTextToSpeech = (Button) findViewById(R.id.buttonTextToSpeech);
        mButtonSpeechToText = (Button) findViewById(R.id.buttonSpeechToText);
        mButtonTTSlanguages = (Button) findViewById(R.id.buttonTTSlang);
        mButtonSTTlanguages = (Button) findViewById(R.id.buttonSTTlang);

        mButtonTextToSpeech.setOnClickListener(this);
        mButtonSpeechToText.setOnClickListener(this);
        mButtonTTSlanguages.setOnClickListener(this);
        mButtonSTTlanguages.setOnClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public void onClick(View view) {
        if (view == mButtonSpeechToText) {
            startSpeechToText();
        }
        else if (view == mButtonTextToSpeech) {
            startTextToSpeech();
        }
        else if (view == mButtonTTSlanguages) {
            getTTSlanguages();
        }
        else if (view == mButtonSTTlanguages) {
            getSTTlanguages();
        }
    }

    private void getSTTlanguages() {
        Intent intent = new Intent(RecognizerIntent.ACTION_GET_LANGUAGE_DETAILS);
        sendOrderedBroadcast(intent, null, mReceiver, null, Activity.RESULT_OK, null, null);
    }
    private void startSpeechToText() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "en-US");
        startActivityForResult(intent, 1);
    }


    private void getTTSlanguages() {
        Toast.makeText(this, "Text To Speech Languages", Toast.LENGTH_SHORT).show();
    }
    private void startTextToSpeech() {
        Toast.makeText(this, "Text To Speech", Toast.LENGTH_SHORT).show();
    }


    private BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Bundle extras = getResultExtras(true);
            assert extras != null;
            if (extras.containsKey(RecognizerIntent.EXTRA_SUPPORTED_LANGUAGES)) {
                ArrayList<String> languages = extras.getStringArrayList(RecognizerIntent.EXTRA_SUPPORTED_LANGUAGES);
                setText(languages);
            }
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                setText(result);
            }
        }
    }

    private void setText(ArrayList<String> lines) {
        StringBuilder builder = new StringBuilder();
        for (String line : lines) builder.append(line+"\r\n");
        mEditText.setText(builder.toString());
    }

}
