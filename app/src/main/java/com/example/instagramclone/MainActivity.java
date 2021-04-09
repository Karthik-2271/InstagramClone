package com.example.instagramclone;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.SaveCallback;
import com.shashank.sony.fancytoastlib.FancyToast;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private EditText edtName,edtKickSpeed,edtKickPower;
    private TextView txtParseObject;
    private Button btnParseObjects;
    private String objectsParse;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        edtName=(EditText) findViewById(R.id.edtName);
        edtKickSpeed=(EditText) findViewById(R.id.edtKickSpeed);
        edtKickPower=(EditText) findViewById(R.id.edtKickPower);
        txtParseObject=findViewById(R.id.txtParseObjects);
        btnParseObjects=findViewById(R.id.btnParse);
        txtParseObject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ParseQuery<ParseObject> parseQuery=ParseQuery.getQuery("KickBoxer");
                parseQuery.getInBackground("3WsD4bP39P", new GetCallback<ParseObject>() {
                    @Override
                    public void done(ParseObject object, ParseException e) {
                        if(object!=null && e==null){
                            txtParseObject.setText(object.get("name")+"");
                        }
                    }
                });
            }
        });
        btnParseObjects.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ParseQuery<ParseObject> allQueries=ParseQuery.getQuery("KickBoxer");
                allQueries.whereGreaterThanOrEqualTo("kick_power",1000);
                allQueries.setLimit(1);
                allQueries.findInBackground(new FindCallback<ParseObject>() {
                    @Override
                    public void done(List<ParseObject> objects, ParseException e) {
                        if(objects.size()>0 && e==null){
                            objectsParse="";
                            for(ParseObject parseObject:objects) {
                                objectsParse+=parseObject.get("name") + " "+parseObject.get("kick_speed")+"\n";
                            }FancyToast.makeText(MainActivity.this,objectsParse, FancyToast.LENGTH_LONG, FancyToast.SUCCESS, true).show();
                        }else{
                            FancyToast.makeText(MainActivity.this,"Failed to retrieve the data",FancyToast.LENGTH_LONG,FancyToast.ERROR,true).show();
                        }
                    }
                });
            }
        });

    }
    public void buttonIsTapped(View view){
        ParseObject kickBoxer=new ParseObject("KickBoxer");
        kickBoxer.put("name",edtName.getText().toString());
        kickBoxer.put("kick_speed",Integer.parseInt(edtKickSpeed.getText().toString()));
        kickBoxer.put("kick_power",Integer.parseInt(edtKickPower.getText().toString()));
        kickBoxer.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if(e==null){
                    FancyToast.makeText(MainActivity.this,kickBoxer.get("name")+" saved successfully to the Server",FancyToast.LENGTH_LONG,FancyToast.SUCCESS,true).show();
                }else{
                    FancyToast.makeText(MainActivity.this,"Failed to save",FancyToast.LENGTH_LONG,FancyToast.ERROR,true).show();
                }
            }
        });
    }
}