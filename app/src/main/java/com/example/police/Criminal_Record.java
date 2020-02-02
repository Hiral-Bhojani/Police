package com.example.police;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.Intent;
import android.icu.lang.UProperty;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;

public class Criminal_Record extends AppCompatActivity {
    ImageView imageView2;
    Button Select,Upload;
    EditText Name,Age,Location,Relatives,Sources,Crime;
    StorageReference mstorageref;
    public Uri imguri;
    private StorageTask uploadTask;
    DatabaseReference dbreff;
    Record record;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_criminal__record);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mstorageref= FirebaseStorage.getInstance().getReference("Images");
        dbreff= FirebaseDatabase.getInstance().getReference().child("datas");
        Select=(Button)findViewById(R.id.Select);
        Upload=(Button)findViewById(R.id.Upload);
        imageView2=(ImageView)findViewById(R.id.imageView2);
        Name=(EditText)findViewById(R.id.Enter_Name);
        Age=(EditText)findViewById(R.id.Enter_Age);
        Location=(EditText)findViewById(R.id.Enter_Location);
        Crime=(EditText)findViewById(R.id.Enter_Crime);
        Relatives=(EditText)findViewById(R.id.Enter_Relatives);
        Sources=(EditText)findViewById(R.id.Enter_Sources);
        record=new Record();
        Select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    Filechooser();
            }
        });
        Upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (uploadTask != null && uploadTask.isInProgress()) {
                    Toast.makeText(Criminal_Record.this, "Upload in Progress", Toast.LENGTH_LONG).show();
                } else {
                    Fileuploader();
                }
            }
        });
    }

    private void Fileuploader()
    {
        String imageid;
        imageid=System.currentTimeMillis()+"."+getExtension(imguri);
        record.setName(Name.getText().toString().trim());
        record.setAge(Age.getText().toString().trim());
        record.setCrime(Crime.getText().toString().trim());
        record.setLocation(Location.getText().toString().trim());
        record.setRelatives(Relatives.getText().toString().trim());
        record.setSources(Sources.getText().toString().trim());
        record.setImageid(imageid);
        dbreff.push().setValue(record);
        StorageReference Ref = mstorageref.child(imageid);
        uploadTask=Ref.putFile(imguri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        // Get a URL to the uploaded content
                        //Uri downloadUrl = taskSnapshot.getDownloadUrl();
                        Toast.makeText(Criminal_Record.this, "Data uploaded", Toast.LENGTH_LONG).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        // Handle unsuccessful uploads
                        // ...
                    }
                });
    }

    private void Filechooser()
    {
        Intent intent = new Intent();
        intent.setType("image/'");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,1);
    }

    private String getExtension(Uri uri){
        ContentResolver cr=getContentResolver();
        MimeTypeMap mimeTypeMap=MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(cr.getType(uri));
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id=item.getItemId();

        if(id== android.R.id.home)
        {
            this.finish();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==1 && resultCode==RESULT_OK && data!=null && data.getData()!=null);
        {
            imguri= data.getData();
            imageView2.setImageURI(imguri);
        }
    }
}