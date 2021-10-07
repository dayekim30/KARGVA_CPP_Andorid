 package com.ops.adictest;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.ops.adictest.databinding.ActivityMainBinding;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.FileNotFoundException;



 public class MainActivity extends AppCompatActivity {

     private int EXTERNAL_STORAGE_PERMISSION_CODE = 23;
    // Used to load the 'adictest' library on application startup.
    static {
        System.loadLibrary("adictest");
    }

    private ActivityMainBinding binding;

    private static final String File_Name ="example.txt";
    EditText mEditText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Example of a call to a native method
        TextView tv = binding.sampleText;
        tv.setText(stringFromJNI());

        mEditText = findViewById(R.id.edit_text);
    }

    public void loading(View v) throws IOException {
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                EXTERNAL_STORAGE_PERMISSION_CODE);

        String text = mEditText.getText().toString();
        //R
        File folder = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);

        File file = new File(folder, "geeksData.txt");
        writeTextData(file, text);
        mEditText.getText().clear();
        //

       /* FileOutputStream fos = null;
        try{
            fos = openFileOutput(File_Name,MODE_PRIVATE);
            fos.write(text.getBytes());
            TextView tv = (TextView) findViewById(R.id.sample_text);
            tv.setText("saved to " + getFilesDir());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }catch(IOException e){
            e.printStackTrace();
        }finally{
            if(fos!=null){
                fos.close();
            }
        }*/
    }
    private void writeTextData(File file, String data) {
        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = new FileOutputStream(file);
            fileOutputStream.write(data.getBytes());
            Toast.makeText(this, "Done" + file.getAbsolutePath(), Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (fileOutputStream != null) {
                try {
                    fileOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    /**
     * A native method that is implemented by the 'adictest' native library,
     * which is packaged with this application.
     */
    public native String stringFromJNI();
    public native String ReadFile();

    public void writeFile(View view) {
        TextView tv = (TextView)findViewById(R.id.sample_text);
        tv.setText(ReadFile());
    }
}