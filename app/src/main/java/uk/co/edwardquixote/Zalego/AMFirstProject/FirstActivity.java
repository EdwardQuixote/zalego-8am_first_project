package uk.co.edwardquixote.Zalego.AMFirstProject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import uk.co.edwardquixote.Zalego.AMFirstProject.Misc.AppConstants;

public class FirstActivity extends AppCompatActivity {

    private EditText edFirstName;
    private EditText edMiddleName;
    private EditText edLastName;
    private EditText edEmailAddress;
    private EditText edPhoneNumber;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);


        //  Here, inside onCreate() we'll initialize our UI Components. In our UI we have EditTexts and Buttons; we'll initialize them here
        //  This initialization is more of creating a link between the XML UI component with a Java object that we'll use in our Java source code

        edFirstName = (EditText) this.findViewById(R.id.edFirstName);
        edMiddleName = (EditText) this.findViewById(R.id.edMiddleName);
        edLastName = (EditText) this.findViewById(R.id.edLastName);
        edEmailAddress = (EditText) this.findViewById(R.id.edEmailAddress);
        edPhoneNumber = (EditText) this.findViewById(R.id.edPhoneNumber);

        Button btnSave = (Button) this.findViewById(R.id.btnSave);
        Button btnCancel = (Button) this.findViewById(R.id.btnCancel);
        btnSave.setOnClickListener(clkFirstActivity);
        btnCancel.setOnClickListener(clkFirstActivity);

    }

    /**
     * In this method, we'll create an EXPLICIT INTENT to start Second Activity.
     *
     * Called In:
     *          - clkFirstActivity.onClick();
     */
    private void codeToStartSecondActivity() {

        //  Below We get values from our EditTexts, i.e. the values that the User has typed in each of the respective fields.
        //  We use the method "getText()" to get these values, but the returned value is of type "Editable".
        //  It's easier for us to deal with String than Editable data types, so we call "toString()" on the Editable returned to convert it to String.

        String sFirstName = edFirstName.getText().toString();
        String sMiddleName = edMiddleName.getText().toString();
        String sLastName = edLastName.getText().toString();
        String sEmailAddress = edEmailAddress.getText().toString();
        String sPhoneNumber = edPhoneNumber.getText().toString();


        //  To create an Intent, we use the class Intent; we have to import it the first time we use it in a class.
        //  As for Explicit Intents they take in two parameters:
                //  1. The creator component of the Intent, i.e. the android component that created the Intent, in this case First Activity.
                //  2. The receiver component fo the Intent, i.e. the android component that will receive and fulfil the Intent, in this case the Second Activity.
        //  In Java Source code, to reference Android components, we use the Java class names, e.g. FirstActivity and SecondActivity.
        //  So we use this Intent as a way to navigate from First Activity to Second Activity.

        //  We want to pass the values we got earlier to the Second Activity. We will use the Intent's "putExtra()" method to set this values onto the Intent.
        //  When the Intent gets executed and Second Activity started, it'll receive this data.
        //  Notice that the keys of our Key-value pairs are made up of our Package Name; this is to ensure the keys are very unique.
        //  For the last two "putExtra()" lines we have declared and created the keys as constants in a different class that specifically holds constants, then called those variables here.
        //  This helps us reduce risk of errors and keep our code clean and easily manageable; we could do the same for the rest - I just wanted you to understand.

        //  Finally we call "startActivity()" and pass to it our Explicit Intent, to start the Activity component specified by the Intent.

        Intent inStartSecondActivity = new Intent(FirstActivity.this, SecondActivity.class);
        inStartSecondActivity.putExtra("uk.co.edwardquixote.Zalego.AMFirstProject.EXTRA_FIRST_NAME", sFirstName);
        inStartSecondActivity.putExtra("uk.co.edwardquixote.Zalego.AMFirstProject.EXTRA_MIDDLE_NAME", sMiddleName);
        inStartSecondActivity.putExtra("uk.co.edwardquixote.Zalego.AMFirstProject.EXTRA_LAST_NAME", sLastName);
        inStartSecondActivity.putExtra(AppConstants.EXTRA_EMAIL_ADDRESS, sEmailAddress);
        inStartSecondActivity.putExtra(AppConstants.EXTRA_PHONE_NUMBER, sPhoneNumber);
        this.startActivity(inStartSecondActivity);

    }

    /**
     * In this method, we'll create an IMPLICIT INTENT to Send an Email.
     *
     * Called In:
     *          - clkFirstActivity.onClick();
     */
    private void codeToSendEmailToSavedEmailAddress() {

        //  Below We get values from our EditTexts, i.e. the values that the User has typed in each of the respective fields.
        //  We use the method "getText()" to get these values, but the returned value is of type "Editable".
        //  It's easier for us to deal with String than Editable data types, so we call "toString()" on the Editable returned to convert it to String.
        //  These values will help make up the Email we're going to send

        String sFirstName = edFirstName.getText().toString();
        String sMiddleName = edMiddleName.getText().toString();
        String sLastName = edLastName.getText().toString();
        String sEmailAddress = edEmailAddress.getText().toString();
        String sPhoneNumber = edPhoneNumber.getText().toString();


        //  We then create some variables that we'll use in the Intent Extras

        String[] toEmailAddressesArray = {sEmailAddress};
        String sEmailSubject = "Hello World";
        String sEmailBody = "Hello " + sFirstName + ",\n\n"
                + "We hope you're well.\n\n"
                + "Welcome to our new App! Your registration details are as follows:\n\n"
                + "Name: " + sFirstName + " " + sMiddleName + " " + sLastName + "\n\n"
                + "Email Address: " + sEmailAddress + "\n\n"
                + "Phone Number: " + sPhoneNumber + "\n\n"
                + "All the best. Bye!";


        //  To create an Intent, we use the class Intent; we have to import it the first time we use it in a class.
        //  As for Implici Intents they take in only one parameter:
        //      1. The Action we want handled
        //  In Java Source code, this Action is specified as a String.
        //  Some of these Strings are created in the Intent class as Actions, so we can simply call them, e.g. Intent.ACTION_SENDTO
        //  Since we're a dealing with an Action known to the Android System, the keys of the Intent Extras are also defined as constants in the Intent class, e.g. Intent.EXTRA_EMAIl

        Intent inStartSendEmailActivity = new Intent(Intent.ACTION_SENDTO);
        inStartSendEmailActivity.setType("*/*");
        inStartSendEmailActivity.putExtra(Intent.EXTRA_EMAIL, toEmailAddressesArray);
        inStartSendEmailActivity.putExtra(Intent.EXTRA_SUBJECT, sEmailSubject);
        inStartSendEmailActivity.putExtra(Intent.EXTRA_TEXT, sEmailBody);

        //  Now we make sure that the user's device has an App with a component that can handle our Action, by calling the "resolveActivity()" method of our Intent object

        if (inStartSendEmailActivity.resolveActivity(getPackageManager()) != null) {
            startActivity(inStartSendEmailActivity);
        }

    }

    /**
     * In this method, we'll close down this Activity.
     *
     * Called In:
     *          - clkFirstActivity.onClick();
     */
    private void codeToCloseFirstActivity() {

        //  To close an Activity, we just call the "finish()" method on the class.

        this.finish();

    }


    /**
     * This is an OnClickListener interface.
     * An interface acts as a bridge for communication between different Java classes, i.e. used to pass data, actions between classes, using declared methods that must be overriden.
     * The whole purpose of this Interface is to respond to clicks on the views/viewgroups on this Activity.
     * Notice how it's not effective until we assign it to the actual View that we want to respond to clicks; in our case, the two Buttons in this Activity.
     * Also, notice that inside this interface, we have a method called "onClick()"; this method must be overriden by this Interface.
     * Inside the "onClick()" method, we manually check on which view has been clicked using a Switch - Case statement.
     *
     * Implemented In:
     *          - (Override) this.onCreate();
     */
    private View.OnClickListener clkFirstActivity = new View.OnClickListener() {

        @Override
        public void onClick(View view) {

            switch (view.getId()) {

                case R.id.btnSave:

                    codeToStartSecondActivity();

                    codeToSendEmailToSavedEmailAddress();

                    break;

                case R.id.btnCancel:

                    codeToCloseFirstActivity();

                    break;

            }

        }

    };


    @Override
    protected void onStart() {
        super.onStart();
    }


    @Override
    protected void onResume() {
        super.onResume();

    }


    @Override
    protected void onPause() {
        super.onPause();

    }


    @Override
    protected void onStop() {
        super.onStop();

    }


    @Override
    protected void onRestart() {
        super.onRestart();

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

}
