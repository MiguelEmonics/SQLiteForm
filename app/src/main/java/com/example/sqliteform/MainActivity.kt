package com.example.sqliteform

import android.content.ContentValues
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.BaseColumns
import android.provider.ContactsContract
import android.text.util.Linkify
import android.util.Log
import android.view.View
import android.widget.*
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.sqliteform.database.UserContract

class MainActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {
    companion object {
        val PERMISSIONS_REQUEST_READ_CONTACTS = 100
    }

    lateinit var spinnerStates: Spinner
    lateinit var spinnerCities: Spinner
    lateinit var adapter: MainAdapter
    lateinit var recyclerView: RecyclerView



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
/*
        val loadContacts = findViewById<ImageView>(R.id.loadContacts)
*/
/*
        loadContacts.setOnClickListener { loadContacts() }
*/

        val btn: TextView = findViewById(R.id.submitBtn)
        btn.setOnClickListener {
            submitBtn()
        }

        val readBtn: TextView = findViewById(R.id.readBtn)
        readBtn.setOnClickListener {
            readDB()
        }
/*
        val webURL = findViewById<TextView>(R.id.textLinkify)
        webURL.setText("6183669787");
        Linkify.addLinks(webURL , Linkify.PHONE_NUMBERS);

        val email = findViewById<TextView>(R.id.textLinkifyWeb)
        email.setText("gonzalez@gmail.com");
        Linkify.addLinks(email , Linkify.EMAIL_ADDRESSES);

        val url = findViewById<TextView>(R.id.textLinkifyURL)
        url.setText("www.youtube.com");
        Linkify.addLinks(url , Linkify.WEB_URLS);*/

        spinnerStates = findViewById(R.id.state_spinner)
        ArrayAdapter.createFromResource(this, R.array.states, android.R.layout.simple_spinner_item).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinnerStates.adapter = adapter
        }
        spinnerStates.onItemSelectedListener = this
    }

/*    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == PERMISSIONS_REQUEST_READ_CONTACTS) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                loadContacts()
            } else {
                //  toast("Permission must be granted in order to display contacts information")
            }
        }
    }*/

/*    private fun loadContacts() {
        var builder = StringBuilder()
        val listContacts = findViewById(R.id.listContacts) as TextView
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && checkSelfPermission(Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(arrayOf(Manifest.permission.READ_CONTACTS), PERMISSIONS_REQUEST_READ_CONTACTS)
            //callback onRequestPermissionsResult
        } else {
            builder = getContacts()
            listContacts.text = builder.toString()
        }
    }*/

/*    private fun getContacts(): StringBuilder {
        val builder = StringBuilder()
        val resolver: ContentResolver = contentResolver;
        val cursor = resolver.query(ContactsContract.Contacts.CONTENT_URI, null, null, null,null)

        if (cursor != null) {
            if (cursor.count > 0) {
                while (cursor.moveToNext()) {
                    val id = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID))
                    val name = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME))
                    val phoneNumber = (cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER))).toInt()

                    if (phoneNumber > 0) {
                        val cursorPhone = contentResolver.query( ContactsContract.CommonDataKinds.Phone.CONTENT_URI,null, ContactsContract.CommonDataKinds.Phone.CONTACT_ID + "=?", arrayOf(id), null)
                        if (cursorPhone != null) {
                            if(cursorPhone.count > 0) {
                                while (cursorPhone.moveToNext()) {
                                    val phoneNumValue = cursorPhone.getString(cursorPhone.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER))
                                    builder.append("Contact: ").append(name).append(", Phone Number: ").append(phoneNumValue).append("\n\n")
                                    Log.e("Name ===>",phoneNumValue);
                                }
                            }
                        }
                        if (cursorPhone != null) {
                            cursorPhone.close()
                        }
                    }
                }
            } else {
                //   toast("No contacts available!")
            }
        }
        if (cursor != null) {
            cursor.close()
        }
        return builder
    }*/

    fun submitBtn() {
        val usernameEditText = findViewById(R.id.username_editText) as EditText
        val passwordEditText = findViewById(R.id.password_editText) as EditText
        val emailEditText = findViewById(R.id.email_editText) as EditText
        val phoneEditText = findViewById(R.id.phone_editText) as EditText
        val addressEditText = findViewById(R.id.address_editText) as EditText
        val stateText = findViewById<Spinner>(R.id.state_spinner)
        val citySpinner = findViewById<Spinner>(R.id.city_spinner)

        val dbHelper = UserContract.UserContractDBHelper(this)
        val db = dbHelper.writableDatabase

        val values = ContentValues().apply {
            put(UserContract.UserEntry.COLUMN_NAME_USERNAME, usernameEditText.text.toString())
            put(UserContract.UserEntry.COLUMN_NAME_PASSWORD, passwordEditText.text.toString())
            put(UserContract.UserEntry.COLUMN_NAME_EMAIL, emailEditText.text.toString())
            put(UserContract.UserEntry.COLUMN_NAME_PHONE, phoneEditText.text.toString())
            put(UserContract.UserEntry.COLUMN_NAME_ADDRESS, addressEditText.text.toString())
            put(UserContract.UserEntry.COLUMN_NAME_STATE, stateText.selectedItem.toString())
            put(UserContract.UserEntry.COLUMN_NAME_CITY, citySpinner.selectedItem.toString())
        }

        val newEntry = db?.insert(UserContract.UserEntry.TABLE_NAME, null, values)
        Log.d("NEW INPUT ID", "${newEntry}")
    }

    fun readDB() {
        val dbHelper = UserContract.UserContractDBHelper(this)
        val dbData = dbHelper.readableDatabase
        val projection = arrayOf(BaseColumns._ID,
            UserContract.UserEntry.COLUMN_NAME_USERNAME,
            UserContract.UserEntry.COLUMN_NAME_PASSWORD,
            UserContract.UserEntry.COLUMN_NAME_EMAIL,
            UserContract.UserEntry.COLUMN_NAME_PHONE,
            UserContract.UserEntry.COLUMN_NAME_ADDRESS,
            UserContract.UserEntry.COLUMN_NAME_CITY,
            UserContract.UserEntry.COLUMN_NAME_STATE)

        val cursor = dbData.query(UserContract.UserEntry.TABLE_NAME, projection, null, null, null, null, null)

        val data = mutableListOf<User>()
        with(cursor) {
            while (moveToNext()) {
                val username = getString(getColumnIndexOrThrow(UserContract.UserEntry.COLUMN_NAME_USERNAME))
                data.add(username)
            }
        }
        cursor.close()
        Log.d("DATA FROM DATABASE", data.toString())
        adapter = MainAdapter()
        recyclerView = findViewById(R.id.recyclerview)
        recyclerView.layoutManager = GridLayoutManager(applicationContext, 2)
        recyclerView.adapter = adapter
        adapter.setUserList(data)
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        //Log.d("pinner selected value", "${parent?.getItemAtPosition(position)}")
        spinnerCities = findViewById(R.id.city_spinner)
        when (position) {
            0 -> ArrayAdapter.createFromResource(this, R.array.state1, android.R.layout.simple_spinner_item).also { adapter ->
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                spinnerCities.adapter = adapter
            }
            1 -> ArrayAdapter.createFromResource(this, R.array.state2, android.R.layout.simple_spinner_item).also { adapter ->
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                spinnerCities.adapter = adapter
            }
            2 -> ArrayAdapter.createFromResource(this, R.array.state3, android.R.layout.simple_spinner_item).also { adapter ->
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                spinnerCities.adapter = adapter
            }
            3 -> ArrayAdapter.createFromResource(this, R.array.state4, android.R.layout.simple_spinner_item).also { adapter ->
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                spinnerCities.adapter = adapter
            }
        }
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        TODO("Not yet implemented")
    }
}