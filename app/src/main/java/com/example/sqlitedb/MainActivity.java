package com.example.sqlitedb;

import androidx.appcompat.app.AppCompatActivity;


import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity
{
    private Database database;

    EditText e1,e2;
    Button button,button1,button2,button3,button4;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        database = new Database(this);

        e1 = findViewById(R.id.roll);
        e2 = findViewById(R.id.name);

        button = findViewById(R.id.insert);
        button1 = findViewById(R.id.fetch);
        button2 = findViewById(R.id.update);
        button3 = findViewById(R.id.delete);

        button4 = findViewById(R.id.getParticular);
        listView = findViewById(R.id.listView);

        //insert code
        button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                if(e1.getText().toString().isEmpty())
                {
                     e1.setError("Please enter a roll no");
                }
                else
                    {
                        String roll = e1.getText().toString();
                        String name = e2.getText().toString();

                        //send data to class security
                        Security security = new Security();
                        security.setRoll(Integer.parseInt(roll));
                        security.setName(name);

                        long l= database.insert(security);

                        if(l>0)
                        {
                            Toast.makeText(MainActivity.this,"Data Insert Successfully",Toast.LENGTH_SHORT).show();
                        }

                        else
                        {
                            Toast.makeText(MainActivity.this,"Data Insert Unsuccessfully",Toast.LENGTH_SHORT).show();
                        }
                    }


            }
        });


        //read/fetch the code
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
               List<Security> list = database.fetch();

               if(list.size()>0)
               {
                  // ArrayAdapter<Security> arrayAdapter = new ArrayAdapter(MainActivity.this,android.R.layout.simple_list_item_1,list);

                   CustomItems customItems = new CustomItems(list);
                   listView.setAdapter(customItems);

                   Toast.makeText(MainActivity.this,"DATA LOADED SUCCESSFULLY",Toast.LENGTH_SHORT).show();
               }
               else
                   {
                       Toast.makeText(MainActivity.this,"DATA LOADING FAILED",Toast.LENGTH_SHORT).show();
                   }
            }
        });


        //update the code
        button2.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                String roll = e1.getText().toString();
                Security security = database.getParticular(Integer.parseInt(roll)); //hold the old values

                if(security!=null)
                {
                    String roll1 = e1.getText().toString();
                    String name1 = e2.getText().toString(); //new values will be stored in it
                    security.setName(name1); // new values are passed

                    long l =database.update(security);
                    if(l>0)
                    {
                        Toast.makeText(MainActivity.this,"RECORD UPDATED SUCCESSFULLY",Toast.LENGTH_SHORT).show();
                    }
                    else
                        {
                            Toast.makeText(MainActivity.this,"RECORD UPDATE FAILED",Toast.LENGTH_SHORT).show();
                        }
                }

                else
                {
                    Toast.makeText(MainActivity.this,"RECORD DOES NOT EXIST",Toast.LENGTH_SHORT).show();
                }

            }
        });

        //delete the code
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
              String roll = e1.getText().toString();
              Security security=database.getParticular(Integer.parseInt(roll));

              if(security!=null)
              {
                  long l =database.delete(Integer.parseInt(roll));
                  if(l>0)
                  {
                      Toast.makeText(MainActivity.this,"RECORD DELETED SUCCESSFULLY",Toast.LENGTH_SHORT).show();
                  }
                  else
                  {
                      Toast.makeText(MainActivity.this,"RECORD DELETION FAILED",Toast.LENGTH_SHORT).show();
                  }
              }

              else
              {
                  Toast.makeText(MainActivity.this,"RECORD NOT EXIST",Toast.LENGTH_SHORT).show();
              }
            }
        });

        //get particular record
        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                String roll = e1.getText().toString();

                //if data available, it will return security obj
                Security security=database.getParticular(Integer.parseInt(roll));
                if(security!=null)
                {
                    String roll1 = String.valueOf(security.getRoll());
                    String name1 = security.getName();

                    e1.setText(roll1);
                    e2.setText(name1);
                    Toast.makeText(MainActivity.this,"RECORD DISPLAYED SUCCESSFULLY",Toast.LENGTH_SHORT).show();
                }

                else
                {
                    Toast.makeText(MainActivity.this,"RECORD DOES NOT EXIST",Toast.LENGTH_SHORT).show();
                }

            }
        });

    }
}
