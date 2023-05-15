package sg.edu.rp.c346.id21023028.l04_reservation;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.google.android.material.textfield.TextInputEditText;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    TextInputEditText ipName;
    TextInputEditText ipPax;
    TextInputEditText ipMobile;
    CheckBox cbSmoking;
    ToggleButton tbDate;
    ToggleButton tbTime;
    TimePicker tp;
    DatePicker dp;
    Button btnReserve;
    Button btnReset;
    TextView txtDisplayData;


    //methods to use
    private void reset(){
        ipName.setText("");
        ipPax.setText("");
        ipMobile.setText("");
        cbSmoking.setChecked(false);
        tbDate.setChecked(false);
        tbTime.setChecked(false);
        tp.setCurrentHour(19);
        tp.setCurrentMinute(30);
        dp.updateDate(2020,5,1);
        tp.setVisibility(View.GONE);
        dp.setVisibility(View.GONE);
        txtDisplayData.setVisibility(View.GONE);
    }
    private boolean dateCheck(int day, int month, int year){
        boolean result = true;
        // Get the current date
        Calendar calendar = Calendar.getInstance();
        Date currentDate = calendar.getTime();

        // Set the input date
        Calendar inputCalendar = Calendar.getInstance();
        inputCalendar.set(year, month, day);
        Date inputDate = inputCalendar.getTime();

        // Compare the dates
        int comparison = currentDate.compareTo(inputDate);
        if (comparison<0){
            result = false;
        }
        return result;
    }
    private boolean checkValid(String name,String pax, String mobile, int day, int month, int year){
        boolean valid = true;
        if (name.isEmpty() || pax.isEmpty() || mobile.isEmpty() || mobile.length()!=8
                || pax.equals("0") || dateCheck(day, month, year)){ //if any blank/invalid values
            if (name.isEmpty()){
                ipName.setError("Name cannot be empty");
            }
            if (pax.isEmpty()){
                ipPax.setError("Size cannot be empty");
            }
            if (mobile.isEmpty()){
                ipMobile.setError("Mobile cannot be empty");
            }
            if (mobile.length()!=8){
                ipMobile.setError("Mobile must be 8 digit");
            }
            if (pax.equals("0")){
                ipPax.setError("Size cannot be 0!");
            }
            if (dateCheck(day, month, year)){
                Toast.makeText(MainActivity.this,"Invalid Date",Toast.LENGTH_LONG)
                        .show();
            }
            valid = false;
        }
        return valid;
    }
    private String smoking(boolean ans){
        String result = "Yes";
        if (!ans){
            result = "No";
        }
        return result;
    }
    private String monthName(int month){
        String[] monthNames = new String[] {
                "January", "February", "March", "April", "May", "June", "July",
                "August", "September", "October", "November", "December"
        };
        return  monthNames[month];
    }
    private String timeFormat(int currentHour, int currentMinute){
        SimpleDateFormat dateFormat = new SimpleDateFormat("h:mm a", Locale.getDefault());
        String formattedTime = dateFormat.format(new Date(0, 0, 0, currentHour,
                currentMinute));
        return formattedTime;
    }
    private void reserve(){

        //get values
        String name = ipName.getText().toString();
        String pax = ipPax.getText().toString();
        String mobile = ipMobile.getText().toString();
        //date or time values (this values cannot be blanked as they have default values
        int day = dp.getDayOfMonth();
        int month = dp.getMonth();
        int year = dp.getYear();
        int hour = tp.getCurrentHour();
        int minute = tp.getCurrentMinute();

        //check if values are valid and create the output msg
        if (checkValid(name, pax, mobile, day, month, year)){ //if values are ok, don't need else since checkValid method will handle it
            String dateJoined = String.format("%s %s %s",day, monthName(month),year);
            String msg = String.format("%15s %-20s" +
                    "\n\n%15s %-20s" +
                    "\n\n%15s %-20s" +
                    "\n\n%15s %-20s" +
                    "\n\n%15s %-20s" +
                    "\n\n%15s %-20s"
                    , "Name:",name,"Mobile Number:",mobile,"Size of group:",pax,"Smoking:"
                    ,smoking(cbSmoking.isChecked()),"Date:",dateJoined,"Time:",
                    timeFormat(hour, minute));
            //display the error
            txtDisplayData.setVisibility(View.VISIBLE);
            txtDisplayData.setText(msg);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //link views to variable
        ipName = findViewById(R.id.ipName);
        ipPax = findViewById(R.id.ipPax);
        ipMobile = findViewById(R.id.ipMobile);
        cbSmoking = findViewById(R.id.cbSmoking);
        tbDate = findViewById(R.id.tbDate);
        tbTime = findViewById(R.id.tbTime);
        tp = findViewById(R.id.tp);
        dp = findViewById(R.id.dp);
        btnReserve = findViewById(R.id.btnReserve);
        btnReset = findViewById(R.id.btnReset);
        txtDisplayData = findViewById(R.id.txtDisplayData);
        reset(); //make views to default values

//create listeners

    //create button click listeners

        //Create reset button listener
        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reset(); //make values for all fields reset
            }
        });

        //Create Reserve button listener
        btnReserve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reserve();
            }
        });

    //create togglebutton click listener

        //tb for time
        tbTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int hour = tp.getCurrentHour();
                int minute = tp.getCurrentMinute();
                if (tbTime.isChecked()){ //make the timepicker appear or disappear
                    tp.setVisibility(View.VISIBLE);
                }
                else{
                    tp.setVisibility(View.GONE);
                }
                tbTime.setText(timeFormat(hour, minute));
            }
        });
        //tb for date
        tbDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tbDate.isChecked()){ //make the timepicker appear or disappear
                    dp.setVisibility(View.VISIBLE);
                }
                else{
                    dp.setVisibility(View.GONE);
                }
            }
        });
    //create tp listeners

        //tp listener
        tp.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
                if (hourOfDay<8 || hourOfDay>=21){ //if hours are not within acceptable range
                    if (hourOfDay<8){
                        tp.setCurrentHour(8); //set to 8am
                    }
                    else{
                        tp.setCurrentHour(20); //set to 8pm
                    }
                }
                tbTime.setText(timeFormat(hourOfDay, minute));
            }
        });
    }
}