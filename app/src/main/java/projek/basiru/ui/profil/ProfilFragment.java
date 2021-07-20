package projek.basiru.ui.profil;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import org.jetbrains.annotations.NotNull;

import projek.basiru.R;
import projek.basiru.SessionManager;
import projek.basiru.databinding.FragmentProfilBinding;
import projek.basiru.databinding.FragmentProgramBinding;
import projek.basiru.login;
import projek.basiru.ui.program.ProgramViewModel;

public class ProfilFragment extends Fragment
{

    private FragmentProfilBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_profil, container, false);
        ProfilViewModel profilViewModel = new ViewModelProvider(this).get(ProfilViewModel.class);
        binding = FragmentProfilBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

//        final TextView textView = binding.fragmentProfil;
//        profilViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);


        return root;
    }

    @Override
    public void onDestroyView()
    {
        super.onDestroyView();
        binding = null;
    }

}
