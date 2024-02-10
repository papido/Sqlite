package edu.utem.ftmk.sqlite;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.Vector;


public class StudentAdapter extends RecyclerView.Adapter<StudentViewHolder> {
    private final LayoutInflater layoutInflater;
    private final List<Student> students;

    public StudentAdapter(LayoutInflater layoutInflater, List<Student> students){
        this.layoutInflater = layoutInflater;
        this.students = students;
    }

    @NonNull
    @Override
    public StudentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        return new
                StudentViewHolder(layoutInflater.inflate(R.layout.student_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull StudentViewHolder holder, int position) {
        holder.setStudent(students.get(position));
    }

    @Override
    public int getItemCount() {
        return students.size();
    }
    }

