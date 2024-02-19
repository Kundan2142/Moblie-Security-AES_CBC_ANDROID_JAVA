package com.example.encrypta_data;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidAlgorithmParameterException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.cert.CertificateException;

public class MainActivity extends AppCompatActivity {

    private Button button;
    String file = "digest.txt";
    String plaintext;
    private EditText two;

    private TextView three;
    private Button four;
    private Button five;

    private Button six;
    Aes ob=new Aes("kundanJCS232560");

    public MainActivity() throws InvalidAlgorithmParameterException, CertificateException, NoSuchAlgorithmException, KeyStoreException, IOException, NoSuchProviderException {
    }


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button=findViewById(R.id.button);
        two=findViewById(R.id.two);
        three=findViewById(R.id.three);
        four=findViewById(R.id.four);
        five=findViewById(R.id.five);
        six=findViewById(R.id.six);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String s= two.getText().toString();




                try {
                    three.setText((ob.encrypt(s)).toString());
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
                Toast.makeText(MainActivity.this, "Encryption successful", Toast.LENGTH_SHORT).show();

            }
        });

        four.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String s= three.getText().toString();



                try {

                    three.setText(ob.decrypt(s).toString());
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
                Toast.makeText(MainActivity.this, "Decryption successful", Toast.LENGTH_SHORT).show();

            }
        });

        five.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String s= two.getText().toString();
                Digest ob =new Digest();
                String mytext = "";
                try {
                    InputStream inputStream = getAssets().open("input.txt");
                    int size = inputStream.available();
                    byte[] buffer = new byte[size];
                    inputStream.read(buffer);
                    mytext = new String(buffer);
                    plaintext=mytext;
                    inputStream.close();
                } catch (IOException e) {
                }
                String st;
                try {
                    String k=ob.sha256(mytext);
                    st=k;
                    three.setText(k.toString());
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
                Toast.makeText(MainActivity.this, "Signing successful", Toast.LENGTH_SHORT).show();


                String content = st.toString();



                try {
                    // Open an output stream to write to the asset file
                    FileOutputStream fos = openFileOutput(file, Context.MODE_PRIVATE);
                    fos.write(content.getBytes());
                    fos.close();
                }catch (Exception e) {
                    e.printStackTrace();
                }


            }
        });

        six.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String s = three.getText().toString();
                Digest ob = new Digest();
                String mytext = "kundan";
                try {
                    FileInputStream fis = openFileInput(file);
                    int size = fis.available();
                    byte[] buffer = new byte[size];
                    fis.read(buffer);
                    fis.close();
                    mytext = new String(buffer);
                } catch (IOException e) {
                }

                try {
                    String k = String.valueOf(ob.verify(mytext,plaintext));
                    three.setText(k);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
                Toast.makeText(MainActivity.this, "Verification Sucessful", Toast.LENGTH_SHORT).show();
            }
        });




    }
}