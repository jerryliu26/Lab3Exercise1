package th.ac.tu.siit.its333.lab3exercise1;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends ActionBarActivity {

    int cre = 0;         // Credits
    double gp = 0.0;    // Grade points
    double gpa = 0.0;   // Grade point average

    List<String> listCodes;
    List<Integer> listCredits;
    List<String> listGrades;
    String out = new String("List of Courses\n") ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listCodes = new ArrayList<String>();
        listCredits = new ArrayList<Integer>();
        listGrades = new ArrayList<String>();

        recal();

        //Use listCodes.add("ITS333"); to add a new course code
        //Use listCodes.size() to refer to the number of courses in the list
    }

    public void addCourse (View v) {
        Intent i = new Intent(this, CourseActivity.class);
        //startActivity(i);
        startActivityForResult(i, 23);
    }
    public void showCourse (View v) {
        Intent i = new Intent(this, CourseListActivity.class);

        for (int count = 0; count<listCodes.size();count++){
            out+=(listCodes.get(count) + "(");
            out+=(listCredits.get(count) + " Credit(s)) = ");
            out+=(listGrades.get(count)+"\n");
        }

        i.putExtra("toShow", out);
        startActivity(i);
    }
    public void reset (View v) {

        out = "List of Courses\n";
        cre = 0;
        gp = 0.0;
        gpa = 0.0;
        listCodes = new ArrayList<String>();
        listCredits = new ArrayList<Integer>();
        listGrades = new ArrayList<String>();

        recal();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Values from child activity
        if (requestCode == 23) {
            if (resultCode == RESULT_OK) {

                String code = data.getStringExtra("toMainCode");
                int cr = data.getIntExtra("toMainCR", 0);
                String grade = data.getStringExtra("toMainGrade");

                listCodes.add(code);
                listCredits.add(cr);
                listGrades.add(grade);

            recal();

            }
        }
    }

    public void recal()
    {
        for (int i = 0 ; i < listCodes.size() ; i++)
        {
            String grade = listGrades.get(i);
            double g;
            if (grade.equals("A")) {
                g = 4.0;
            } else if (grade.equals("B+")) {
                g = 3.5;
            } else if (grade.equals("B")) {
                g = 3.0;
            } else if (grade.equals("C+")) {
                g = 2.5;
            } else if (grade.equals("C")) {
                g = 2.0;
            } else if (grade.equals("D+")) {
                g = 1.5;
            } else if (grade.equals("D")) {
                g = 1.0;
            } else {
                g = 0.0;
            }
            cre += listCredits.get(i);
            gp += (g * listCredits.get(i));
        }

        gpa = gp/cre;

        TextView tvGP = (TextView) findViewById(R.id.tvGP);
        tvGP.setText(Double.toString(gp));
        TextView tvCR = (TextView) findViewById(R.id.tvCR);
        tvCR.setText(Integer.toString(cre));
        TextView tvGPA = (TextView) findViewById(R.id.tvGPA);
        tvGPA.setText(Double.toString(gpa) );

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
