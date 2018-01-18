package com.praveen.session7assignment3;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.InputStream;

public class MainActivity extends AppCompatActivity {


    Button btnBrowseGallery;
    Button btnClearImage;
    ImageView selectedImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnBrowseGallery = findViewById(R.id.browseButton);
        btnClearImage = findViewById(R.id.clearButton);
        selectedImage = findViewById(R.id.selectedImage);

        btnBrowseGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                gallery.setType("image/*");

                startActivityForResult(gallery, 1);
            }
        });


        btnClearImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedImage.setImageBitmap(null);
            }
        });

        @Override
        onActivityResult( int requestCode, int resultCode, Intent data){
            super.onActivityResult(requestCode, resultCode, data);

            if (requestCode == 1 && resultCode == RESULT_OK) {
                // Start Try Catch Block To Catch File Not Found Exception.
                try {
                    // Retrieve Selected Image Details
                    Uri selectedImageUri = data.getData();

                    // Open Input Stream And Convert Selected Image to Stream
                    InputStream imageInputStream = getContentResolver().openInputStream(selectedImageUri);

                    // Decode Input Stream object to Image OBject.
                    Bitmap selectedImage = BitmapFactory.decodeStream(imageInputStream);

                    // Set Selected Image to Image View
                    selectedImage.setImageBitmap(selectedImage);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                    // Toast If an error occurred.
                    Toast.makeText(this, "An error occurred while loading the image.", Toast.LENGTH_LONG).show();
                }
            } else
                Toast.makeText(this, "Selection Cancelled by user.", Toast.LENGTH_LONG).show();

        }
    }
}