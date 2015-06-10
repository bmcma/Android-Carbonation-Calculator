package com.brianmcmahon.carbonationcalculator;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import java.text.DecimalFormat;

/*
formula sourced from http://byo.com/grains/item/164-balancing-your-draft-system-advanced-brewing
 */


public class MainActivity extends FragmentActivity {

    DecimalFormat df = new DecimalFormat("#.#");
    private double volumeCo2;
    private double temp;
    private static final double value1 = -16.6999;
    private static final double value2 = 0.0101059;
    private static final double value3 = 0.00116512;
    private static final double value4 = 0.173354;
    private static final double value5 = 4.24267;
    private static final double value6 = 0.0684226;
    private EditText tempInput;
    private EditText co2Input;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    //worker thread to run the calculations in the background while dealing with the view in the UI thread
    private class Conversion extends AsyncTask<Object, Object, Double>{
        //sets up the values needed for the calculation
        @Override
        protected void onPreExecute(){
            tempInput = (EditText) findViewById(R.id.tempEditText);
            String input1 = tempInput.getText().toString();
            //display an error and prompt to enter if the EditText is empty
            if(input1.compareTo("") == 0){
                tempInput.setError("Please enter the temperature to carbonate at!");
                return;
            }else{
                temp = Double.parseDouble(input1);
            }
            //display an error and prompt to enter if the EditText is empty
            co2Input = (EditText) findViewById(R.id.co2EditText);
            String input2 = co2Input.getText().toString();
            if(input2.compareTo("") == 0){
                co2Input.setError("Please enter the desired volume of co2! \nIf you are unsure see the " +
                        "recommendations below.");
                //return;
            }else{
                volumeCo2 = Double.parseDouble(input2);
            }
        }
        //runs the calculation for PSI in a worker thread
        @Override
        protected Double doInBackground(Object... objects) {

            double psi;
            psi = (value1 - (value2 * temp) + (value3 * Math.pow(temp, 2))
                    + (value4 * temp * volumeCo2) + (value5 * volumeCo2)
                    - (value6 * Math.pow(volumeCo2, 2)));
            return psi;
        }
        //passes the result of the calculation to the TextView in the UI thread
        @Override
        protected void onPostExecute(Double psi){
            //does not display result for psi unless user has entered the required values
            TextView psiResult;
            if(tempInput.getText().toString().compareTo("") == 0 || co2Input.getText().toString().compareTo("") == 0){
                psiResult = (TextView) findViewById(R.id.psiTextView);
                psiResult.setText("");
            }else {
                psiResult = (TextView) findViewById(R.id.psiTextView);
                psiResult.setText("The required PSI is " + df.format(psi));
            }
        }
    }

    //click listener to perform calculation
    public void convert(View view){
        new Conversion().execute();
    }
    //worker thread to show alert dialog
    private class showDialog extends AsyncTask<Object, Object, Fragment>{

        @Override
        protected Fragment doInBackground(Object... objects) {
            RecommendDialogFragment dialog = new RecommendDialogFragment();
            dialog.show(getSupportFragmentManager(), "styles");
            return null;
        }
    }
    //click listener to display the alert dialog
    public void recommend(View view){
        //shows the dialog listing the recommended carbonation levels
       new showDialog().execute();
    }

}
