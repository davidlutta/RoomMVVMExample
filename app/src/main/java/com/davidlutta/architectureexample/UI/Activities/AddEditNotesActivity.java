package com.davidlutta.architectureexample.UI.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Toast;

import com.davidlutta.architectureexample.R;

public class AddEditNotesActivity extends AppCompatActivity {
    public static final String EXTRA_ID =
            "com.davidlutta.architectureexample.UI.Activities.EXTRA_ID";

    public static final String EXTRA_TITLE =
            "com.davidlutta.architectureexample.UI.Activities.EXTRA_TITLE";

    public static final String EXTRA_DESC =
            "com.davidlutta.architectureexample.UI.Activities.EXTRA_DESC";

    public static final String EXTRA_PRIORITY =
            "com.davidlutta.architectureexample.UI.Activities.EXTRA_PRIORITY";

    private EditText titleEditText, descriptionEditText;
    private NumberPicker priorityNumberPicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_notes);

        titleEditText = findViewById(R.id.titleEditText);
        descriptionEditText = findViewById(R.id.descriptionEditText);
        priorityNumberPicker = findViewById(R.id.priorityNumberPicker);

        priorityNumberPicker.setMinValue(1);
        priorityNumberPicker.setMaxValue(10);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);

        Intent intent = getIntent();

        if (intent.hasExtra(EXTRA_ID)){
            setTitle("Edit Note");
            titleEditText.setText(intent.getStringExtra(EXTRA_TITLE));
            descriptionEditText.setText(intent.getStringExtra(EXTRA_DESC));
            priorityNumberPicker.setValue(intent.getIntExtra(EXTRA_PRIORITY,1));
        } else {
            setTitle("Add Note");
        }



    }

    private void saveNote() {
        String title = titleEditText.getText().toString().trim();
        String description = descriptionEditText.getText().toString().trim();
        int priority = priorityNumberPicker.getValue();

        if (title.isEmpty() || description.isEmpty()){
            Toast.makeText(this, "Please insert a title and description", Toast.LENGTH_SHORT).show();
            return;
        }

        Intent data = new Intent();
        data.putExtra(EXTRA_TITLE,title);
        data.putExtra(EXTRA_DESC,description);
        data.putExtra(EXTRA_PRIORITY,priority);

        int id = getIntent().getIntExtra(EXTRA_ID,-1);

        if (id != -1){
            data.putExtra(EXTRA_ID,id);
        }

        setResult(RESULT_OK,data);
        finish();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.add_note_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.saveNote:
                saveNote();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
