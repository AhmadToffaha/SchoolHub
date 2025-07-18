package com.example.schoolhub.Student;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.schoolhub.R;
import com.example.schoolhub.Registration.LoginActivity;

import org.json.JSONException;

public class StudentSettingsFragment extends Fragment {

    private TextView tvStudentName, tvGrade, tvAge, tvId;
    private Button btnAboutUs, btnLogout;



    Bundle bundle = new Bundle();

    private  int studentId ;

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_student_settings, container, false);
        if (getArguments() != null) {
            studentId = getArguments().getInt("student_id", -1);
        } else {
            studentId = -1; // fallback
        }
        tvStudentName = view.findViewById(R.id.tvStudentName);
        tvGrade = view.findViewById(R.id.tvGrade);
        tvAge = view.findViewById(R.id.tvAge);
        tvId = view.findViewById(R.id.tvStudentID);
        btnAboutUs = view.findViewById(R.id.btnAboutUs);
        btnLogout = view.findViewById(R.id.btnLogout);

        fetchStudentInfo();

        btnAboutUs.setOnClickListener(v -> {
            requireActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.studentFragmentContainer, new AboutUsFragment())
                    .addToBackStack(null)
                    .commit();
        });

        btnLogout.setOnClickListener(v -> {
            Toast.makeText(getContext(), "Logged out", Toast.LENGTH_SHORT).show();


        });

        return view;
    }

    private void fetchStudentInfo() {
        String url = LoginActivity.baseUrl+"get_student_profile.php?student_id=" + studentId;

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                response -> {
                    try {
                        String name = response.getString("name");
                        String grade = response.getString("grade");
                        String age = response.getString("age");
                        String id = response.getString("id");

                        tvStudentName.setText(name);
                        tvGrade.setText(grade);
                        tvAge.setText( age);
                        tvId.setText(id);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                },
                error -> Toast.makeText(getContext(), "Failed to load student info", Toast.LENGTH_SHORT).show()
        );

        Volley.newRequestQueue(requireContext()).add(request);
    }
}
