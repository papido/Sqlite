package edu.utem.ftmk.sqlite;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.Toast;

import java.util.Calendar;
import java.util.List;
import java.util.Vector;

import edu.utem.ftmk.sqlite.databinding.ActivityStudentMainBinding;
import edu.utem.ftmk.sqlite.sqliteDatabase.DatabaseAttendance;

public class StudentMainActivity extends AppCompatActivity {

    private ActivityStudentMainBinding binding;
    private Student student;
    private StudentAdapter adapter;
    private DatePickerDialog datePicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityStudentMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.edtBirthdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);
                int mHour = cldr.get(Calendar.HOUR_OF_DAY);
                int mMinute = cldr.get(Calendar.MINUTE);
                String strDay = "";
                datePicker = new DatePickerDialog(StudentMainActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        binding.edtBirthdate.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                    } }, year, month, day);
                datePicker.show();
            }
        });
        binding.fabAdd.setOnClickListener(this::fnInsertStudent);
        binding.fabDelete.setOnClickListener(this::fnDeleteStudent);
    }

    @Override
    protected void onStart() {
        super.onStart();
        DatabaseAttendance databaseAttendance = new DatabaseAttendance(StudentMainActivity.this);
        List<Student> everyone =  databaseAttendance.fnGetAllStudents();
        adapter = new StudentAdapter(getLayoutInflater(),everyone);
        binding.rcvStud.setAdapter(adapter);
        binding.rcvStud.setLayoutManager(new LinearLayoutManager(this));
    }

    private void fnInsertStudent(View view) {
        String fullname = binding.edtFullName.getText().toString();
        String studNo = binding.edtStudentNumber.getText().toString();
        String email = binding.edtEmail.getText().toString();
        String birth = binding.edtBirthdate.getText().toString();
        String gender = "";
        String state = binding.spnState.getSelectedItem().toString();
        if(binding.rbMale.isChecked())
            gender = binding.rbMale.getText().toString();
        else if(binding.rbFemale.isChecked())
            gender = binding.rbFemale.getText().toString();
        try {
            student = new Student(fullname,studNo,email,gender,birth, state);
            DatabaseAttendance databaseAttendance = new DatabaseAttendance(StudentMainActivity.this);
            databaseAttendance.fnInsertStudent(student);
            databaseAttendance.fnUpdateExpenses(student);
            List<Student> everyone =  databaseAttendance.fnGetAllStudents();
            adapter = new StudentAdapter(getLayoutInflater(),everyone);
            binding.rcvStud.setAdapter(adapter);
            binding.rcvStud.setLayoutManager(new LinearLayoutManager(this));
            Toast.makeText(StudentMainActivity.this, "Successfully created/updated Student attendance", Toast.LENGTH_SHORT).show();
        }
        catch (Exception e){
            Toast.makeText(StudentMainActivity.this, "Error creating Student attendance", Toast.LENGTH_SHORT).show();
        }
    }

    private void fnDeleteStudent(View view) {
        String fullname = binding.edtFullName.getText().toString();
        String studNo = binding.edtStudentNumber.getText().toString();
        String email = binding.edtEmail.getText().toString();
        String birth = binding.edtBirthdate.getText().toString();
        String gender = "";
        String state = binding.spnState.getSelectedItem().toString();
        if(binding.rbMale.isChecked())
            gender = binding.rbMale.getText().toString();
        else if(binding.rbFemale.isChecked())
            gender = binding.rbFemale.getText().toString();
        student = new Student(fullname,studNo,email,gender,birth, state);
        DatabaseAttendance databaseAttendance = new DatabaseAttendance(StudentMainActivity.this);
        databaseAttendance.fnDeleteExpenses(student);
        List<Student> everyone =  databaseAttendance.fnGetAllStudents();
        adapter = new StudentAdapter(getLayoutInflater(),everyone);
        binding.rcvStud.setAdapter(adapter);
        binding.rcvStud.setLayoutManager(new LinearLayoutManager(this));
        Toast.makeText(StudentMainActivity.this, "Successfully deleted Student attendance", Toast.LENGTH_SHORT).show();
    }
}