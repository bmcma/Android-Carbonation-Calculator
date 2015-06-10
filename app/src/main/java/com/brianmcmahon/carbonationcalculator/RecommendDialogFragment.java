package com.brianmcmahon.carbonationcalculator; /**
 * Created by Brian on 2015-06-09.
 */

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

import static java.lang.String.format;

public class RecommendDialogFragment extends DialogFragment {
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        //message to be displayed in dialog
        String beerStyles = format("%-23s%n%-17s%n%-23s%n%-17s%n%-23s%n%-17s%n%-23s%n%-17s%n%-23s%n%-17s%n%-23s%n%-17s%n%-23s%n%-17s%n%-23s%n%-17s",
                "British Style Ales:", "1.5 - 2.0 volumes",
                "Belgian Ales:", "1.9 - 2.4 volumes ",
                "American Ales and Lager:", "2.2 - 2.7 volumes",
                "Fruit Lambic:", "3.0 - 4.5 volumes ",
                "Porter/Stout:", "1.7 - 2.3 volumes ",
                "European Lagers:", "2.2 - 2.7 volumes ",
                "Lambic:", "2.4 - 2.8 volumes ",
                "German Wheat Beer:", "3.3 - 4.5 volumes");
        //sets the message and title for the dialog
        builder.setMessage(beerStyles)
                .setTitle("Recommended Volumes of \n Co2 by Style")
                .setIcon(R.drawable.foamy_beer)
                //set the return button
                .setPositiveButton("return", new DialogInterface.OnClickListener(){
                        @Override
                        public void onClick(DialogInterface dialog, int id){
                            dialog.dismiss();
            }
        });

        return builder.create();
    }
}
