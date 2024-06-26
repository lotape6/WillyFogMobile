package com.popokis.willyfogmobile;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class MainActivity extends AppCompatActivity {

    protected TextView name;
    protected TextView grade;
    protected ImageView profile;
    protected Button button;
    protected ListView listView;


    public static String[] Universities = {"Universidad de Málaga", "Universidad de Oxford",
            "Universidad de Dinamarca del Sur", "Universidad de Bulgaria"};
//    public static String[] photos = {"http://www.uma.es/media/files/marca_uma.jpg", "http://www.dtc.ox.ac.uk/people/12/muszkiewicz/Oxford_logo.png",
//            "http://2.bp.blogspot.com/-oIcNjSOAjoI/Vkojfh8s0PI/AAAAAAAAAz0/8cMLZkRZvqY/s1600/University-of-Southern-Denmark-new-scholarships.png",
//            "http://3.bp.blogspot.com/-4GHmgptoLYA/UZ5kef7ceDI/AAAAAAAAAjw/-8sFqKxfMW8/s1600/bulgaria.png"};
    public static String[] dates = {"31-01-2016", "24-12-2015", "1-11-2015", "1-11-2015"};
    public static int[] images = {R.drawable.uma, R.drawable.oxford, R.drawable.denmark, R.drawable.bulgaria};


    private final int SELECT_PHOTO = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        String nameStr = null;
        String gradeStr = null;

        if (bundle != null) {
            String[] personalData = (String[]) bundle.get("PERSONAL_DATA");
            nameStr = personalData[0];
            gradeStr = personalData[1];
        }

        name = (TextView) findViewById(R.id.nameTextView);
        grade = (TextView) findViewById(R.id.gradeTextView);
        name.setText(nameStr);
        grade.setText(gradeStr);

        profile = (ImageView) findViewById(R.id.profileImageView);
        profile.setImageResource(R.drawable.willy_profile);

        listView = (ListView) findViewById(R.id.listView);
        listView.setAdapter(new CustomAdapter(this, Universities, images, dates));

        button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                photoPickerIntent.setType("image/*");
                startActivityForResult(photoPickerIntent, SELECT_PHOTO);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);

        switch (requestCode) {
            case SELECT_PHOTO:
                if (resultCode == RESULT_OK) {
                    try {
                        final Uri imageUri = imageReturnedIntent.getData();
                        final InputStream imageStream = getContentResolver().openInputStream(imageUri);
                        final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                        profile.setImageBitmap(selectedImage);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }

                }
        }
    }
}