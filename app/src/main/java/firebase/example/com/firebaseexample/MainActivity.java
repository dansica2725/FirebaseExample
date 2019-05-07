package firebase.example.com.firebaseexample;

import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import static android.webkit.ConsoleMessage.MessageLevel.LOG;

public class MainActivity extends AppCompatActivity {

    Button add;
    Button get;
    Button del;
    EditText nameText;
    ListView nameListView;

    String id;
    String sid;
    String nid;

    Names nameId;

    DatabaseReference databaseName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        databaseName = FirebaseDatabase.getInstance().getReference("names");



        add = findViewById(R.id.buttonAdd);
        get = findViewById(R.id.buttonGet);
        del = findViewById(R.id.buttonDel);
;       nameText = findViewById(R.id.editTextName);
        nameListView = findViewById(R.id.nameListView);

    }
    public void Add(View v) {
        addName();
    }
    public void Get(View v) {

    }
//    public void Del(View v) {
//        deleteName(nameId);
//    }
//
//    private void deleteName(Names nameId) {
//        DatabaseReference delName = FirebaseDatabase.getInstance().getReference("names").child(nameId.toString());
//
//        delName.removeValue();
//    }

    private void addName() {
        String name = nameText.getText().toString().trim();

        //Check if the nameText has a value in it.
        if (!TextUtils.isEmpty(name)) {

            //Making a unique ID or key.
            id = databaseName.push().getKey();

            //Creating a names object.
            Names names = new Names(id, name);

            //Adding the object to the Firebase.
            databaseName.child(id).setValue(names);

            //Make TextView empty.
            nameText.setText("");

            Toast.makeText(this, "Name added", Toast.LENGTH_LONG).show();

        }
        else {
            Toast.makeText(this, "Enter a Name", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();

        databaseName.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                //Make ArrayList to store the data
                ArrayList<String> namesList = new ArrayList<>();

                //Clear namesList so that there will not be multiple similar data.
                namesList.clear();

                //For loop to get the Children from database
                for (DataSnapshot nameSnapshot : dataSnapshot.getChildren()) {
                    //Make a names object to store the (name) and (nameId) so that we can call it later.
                    Names names = nameSnapshot.getValue(Names.class);

                    //Add the name to the ArrayList namesList.
                    if (names != null) {
                        namesList.add(names.name);
                    }
                }

                //Adding the namesList to the nameListView.
                ListAdapter listAdapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1, namesList);
                nameListView.setAdapter(listAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}

