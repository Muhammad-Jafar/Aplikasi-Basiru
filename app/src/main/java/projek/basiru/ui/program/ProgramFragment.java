package projek.basiru.ui.program;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import projek.basiru.R;
import projek.basiru.databinding.FragmentProfilBinding;
import projek.basiru.databinding.FragmentProgramBinding;
import projek.basiru.login;

public class ProgramFragment extends Fragment {

    private @NonNull FragmentProfilBinding binding;


    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
//        ProgramViewModel homeViewModel = new ViewModelProvider(this).get(ProgramViewModel.class);
//        binding = FragmentProgramBinding.inflate(inflater, container, false);
//        View root = binding.getRoot();

//        final TextView textView = binding.fragmentProgram;
//        homeViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);

        View view = inflater.inflate(R.layout.fragment_program, container, false);
        binding = FragmentProfilBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}