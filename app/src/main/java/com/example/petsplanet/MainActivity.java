package com.example.petsplanet;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


public class MainActivity extends AppCompatActivity {
    Button btn_add, btn_view, image_btn;
    Bitmap selectedImage;
    private static final int PICK_IMAGE_REQUEST = 1;

    EditText et_name, editText,editTextText2;
    ListView lv_StudentList;
    ArrayAdapter studentArrayAdapter;
    DataBaseHelper dataBaseHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // on create, give value
        btn_add = findViewById(R.id.btn_add);
        image_btn = findViewById(R.id.image_btn);
        btn_view = findViewById(R.id.btn_view);
        editText= findViewById(R.id.editText);
        editTextText2= findViewById(R.id.editTextText2);

        et_name = findViewById(R.id.et_name);
        lv_StudentList = findViewById(R.id.lv_StudentList);
        dataBaseHelper = new DataBaseHelper(MainActivity.this);
        ShowPostOnListView(dataBaseHelper);


        //Listeners:
        image_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery();
            }
        });

        btn_view.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v){
                dataBaseHelper = new DataBaseHelper(MainActivity.this);
                ShowPostOnListView(dataBaseHelper);
                //Toast.makeText(MainActivity.this, everyone.toString(),Toast.LENGTH_SHORT).show();
            }
        });


        btn_add.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // create model
                Post post;
                try {

                    post = new Post( selectedImage,
                            et_name.getText().toString(),-1
                    );
                    Toast.makeText(MainActivity.this,
                            post.toString(), Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    Toast.makeText(MainActivity.this, "Enter Valid input",
                            Toast.LENGTH_SHORT).show();
                    post = new Post(null, "ERROR",-1);
                }
                DataBaseHelper dataBaseHelper = new
                        DataBaseHelper(MainActivity.this);
                if(dataBaseHelper.addOne(post))
                    Toast.makeText(MainActivity.this, "SUCCESS= ",
                            Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(MainActivity.this, "FAIL= ",
                            Toast.LENGTH_SHORT).show();
                ShowPostOnListView(dataBaseHelper);
            }
        });



        lv_StudentList.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id){
                String st=editText.getText().toString();
                if(st.equals("delete")){
                    Post clickedStudent= (Post) parent.getItemAtPosition(position);
                    dataBaseHelper.DeleteOne(clickedStudent);
                    ShowPostOnListView(dataBaseHelper);
                    Toast.makeText(MainActivity.this,"deleted "+ clickedStudent.toString(), Toast.LENGTH_SHORT).show();}

                if(st.equals("edit")){
                    if(st.equals("edit")){
                        Post clickedStudent= (Post) parent.getItemAtPosition(position);
                        String st2=editTextText2.getText().toString();

                        dataBaseHelper.editOne(clickedStudent,st2);
                        ShowPostOnListView(dataBaseHelper);
                        Toast.makeText(MainActivity.this,"edited "+ clickedStudent.toString(), Toast.LENGTH_SHORT).show();}}

            }
        });
    }

    private void ShowPostOnListView(DataBaseHelper dataBaseHelper) {
        studentArrayAdapter = new
                ArrayAdapter<Post>(MainActivity.this,
                android.R.layout.simple_list_item_1, dataBaseHelper.getEveryone());
        lv_StudentList.setAdapter(studentArrayAdapter);
    }

    private void openGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null) {
            selectedImage = BitmapFactory.decodeFile(data.getData().getPath());
        }
    }





}