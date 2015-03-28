
package group38.elderlyportal;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import android.app.ListActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

import android.view.*;
import android.widget.*;
import java.util.* ;
import java.util.ArrayList;
import java.util.List;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.app.ListActivity;

public class MyMedicationsActivity extends ListActivity {
    private TextView text; //text above the list

    //IDs for new activities
    public static final int AddAMedicationActivity_ID = 1;
    public static final int EditAMedicationActivity_ID = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_medications);

        //initialize the text before the list
        text = (TextView) findViewById(R.id.mainText);

        //populate the medicine list with random medicines - for debugging
        Medicine med1 = new Medicine ("medicine 1", new GregorianCalendar (2015,3,26,3,1), "1 dose") ;
        Medicine med2 = new Medicine ("medicine 2", new GregorianCalendar (2015,3,26,5,0), "3 doses") ;
        Medicine med3 = new Medicine ("medicine 3", new GregorianCalendar (2015,9,2,12,47), "9 doses") ;
        Medicine med4 = new Medicine ("medicine 4", new GregorianCalendar (2015,3,28,8,9), "4 doses") ;
        Medicine med5 = new Medicine ("medicine 5", new GregorianCalendar (2015,12,28,2,26), "2 doses") ;
        Medicine med6 = new Medicine ("medicine 6", new GregorianCalendar (2015,3,27,7,47), "3 doses") ;
        Medicine med7 = new Medicine ("medicine 7", new GregorianCalendar (2014,3,28,8,9), "10 doses") ;
        Medicine med8 = new Medicine ("medicine 8", new GregorianCalendar (2015,3,28,11,26), "0 doses") ;
        Medicine[] list = new Medicine[] {med1, med2, med3, med4, med5, med6, med7, med8} ;

        // initiate the listadapter to be used for managing the list of medicines
        MedicineListArrayAdapter adapter = new MedicineListArrayAdapter(this, list);

        // assign the list adapter to this class
        setListAdapter(adapter);

        // assign the spinner's listener so it communicates to the list adapter
        final Spinner spinner = (Spinner) findViewById(R.id.spinner) ;
        spinner.setOnItemSelectedListener (new SpinnerListener (adapter)) ;
    }

    /* Spinner listener class that fine tunes the spinner's actions.
       Specifically, when an option of the spinner is selected,
       the spinner tells the list adapter to sort by that string.
    */
    private class SpinnerListener implements AdapterView.OnItemSelectedListener {

        private MedicineListArrayAdapter adapter ; //the adapter

        /* constructor stores a reference to the list adapter
         */
        public SpinnerListener (MedicineListArrayAdapter adapter) {
            super () ;
            this.adapter = adapter ;
        }

        /* When an item is selected, the spinner has the adapter sort by it.
         */
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            Spinner spinner = (Spinner) parent ;
            String selected = spinner.getSelectedItem().toString() ;
            sortBy(selected) ;
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    }

    // when an item of the list is clicked
    @Override
    protected void onListItemClick(ListView list, View view, int position, long id) {
        super.onListItemClick(list, view, position, id);
        Medicine selectedItem = (Medicine) getListView().getItemAtPosition(position);

        text.setText("You clicked " + selectedItem.getName() + " at position " + position);

        Intent intent = new Intent (this, EditAMedicationActivity.class) ;
        intent.putExtra("Medicine", selectedItem) ;
        startActivityForResult (intent, EditAMedicationActivity_ID) ;
    }

    public void addAMedication (View view) {
        Intent intent = new Intent (this, AddAMedicationActivity.class) ;
        startActivityForResult(intent, AddAMedicationActivity_ID) ;
    }

    public void sortBy (String sortBy) {
        Log.v(this.toString(), "sorting by " + sortBy);
        MedicineListArrayAdapter adapter = (MedicineListArrayAdapter) this.getListAdapter();

        if (sortBy.equals("Name")) {
            adapter.sort(new Comparator<Medicine>() {
                @Override
                public int compare(Medicine m1, Medicine m2) {
                    return m1.getName().compareTo(m2.getName());
                }
            });
        }
        else if (sortBy.equals("Date")) {
            adapter.sort(new Comparator<Medicine>() {
                @Override
                public int compare(Medicine m1, Medicine m2) {
                    return m1.getDateOfNextRefill().compareTo(m2.getDateOfNextRefill());
                }
            });
        }
    }

    public void showBy (String showBy) {
        Log.v(this.toString(), "showing by " + showBy);
        MedicineListArrayAdapter adapter = (MedicineListArrayAdapter) this.getListAdapter();
        adapter.showBy(showBy) ;
    }

    public void test (View view) {
        double rand = Math.random() ;
        if (rand < 0.5) showBy("Date") ;
        if (rand > 0.5) showBy("Dosage") ;
    }


}