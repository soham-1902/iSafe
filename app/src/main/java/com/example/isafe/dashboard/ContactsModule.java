package com.example.isafe.dashboard;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.example.isafe.R;
import com.example.isafe.databaseHandling.DatabaseHelper;
import com.example.isafe.databaseHandling.ParticipantModel;

import java.util.List;

public class ContactsModule extends AppCompatActivity {

    private Toolbar toolbarContacts;
    private Button addFromContacts, addBtn;

    private StringBuilder name;
    private StringBuilder phoneNumber;

    private ListView addedContactsLv;

    private EditText etNumber, etName;

    private int count = -1;

    public ArrayAdapter<ParticipantModel> customerArrayAdapter;
    public List<ParticipantModel> all;
    public DatabaseHelper databaseHelper = new DatabaseHelper(ContactsModule.this);

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            this.finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts_module);

        toolbarContacts = findViewById(R.id.toolbarContacts);
        addFromContacts = findViewById(R.id.addFromContacts);
        etName = findViewById(R.id.etContactName);
        etNumber = findViewById(R.id.etContactNumber);
        addBtn = findViewById(R.id.addBtn);
        addedContactsLv = findViewById(R.id.addedContactsLv);

        setSupportActionBar(toolbarContacts);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        all = databaseHelper.getAll();
        customerArrayAdapter = new ArrayAdapter<>(ContactsModule.this, R.layout.list_item_by_soham, R.id.contactNameclv, all);
        addedContactsLv.setAdapter(customerArrayAdapter);
        customerArrayAdapter.notifyDataSetChanged();


        addFromContacts.setOnClickListener(v -> {
            Intent pickContact = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
            pickContact.setType(ContactsContract.CommonDataKinds.Phone.CONTENT_TYPE);
            startActivityForResult(pickContact, 1);
        });

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name = new StringBuilder(etName.getText().toString().trim());
                phoneNumber = new StringBuilder(etNumber.getText().toString().trim().replaceAll("\\s+", "").replace("+", ""));

                if (phoneNumber.length() == 10 || phoneNumber.length() == 12) {
                    count += 1;
                    ParticipantModel participantModel = new ParticipantModel(count, name.toString(), phoneNumber.toString());
                    boolean success = databaseHelper.addOne(participantModel);
                    if (success) {


                        all = databaseHelper.getAll();

                        customerArrayAdapter = new ArrayAdapter<>(ContactsModule.this, R.layout.list_item_by_soham, R.id.contactNameclv, all);

                        addedContactsLv.setAdapter(customerArrayAdapter);
                        customerArrayAdapter.notifyDataSetChanged();

                        etName.setText("");
                        etNumber.setText("");


                        Toast.makeText(ContactsModule.this.getApplicationContext(), "Contact added!", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(ContactsModule.this.getApplicationContext(), "Opps! Something went wrong", Toast.LENGTH_SHORT).show();
                    }

                } else {
                    Toast.makeText(ContactsModule.this.getApplicationContext(), "Enter a valid contact number!", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    @SuppressLint("Range")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Uri contactData = data.getData();
        Cursor c = getContentResolver().query(contactData, null, null, null, null);
        if (c.moveToFirst()) {
            etName.setText(c.getString(c.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME)));
            etNumber.setText(c.getString(c.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)));
        }
    }

    public void deleteContact(View v) {
        final int position = addedContactsLv.getPositionForView((View) v.getParent());

        databaseHelper.deleteOne(all, position);

        all.remove(position);

        customerArrayAdapter.notifyDataSetChanged();

    }
}