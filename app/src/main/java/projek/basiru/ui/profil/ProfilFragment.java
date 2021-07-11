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

import org.jetbrains.annotations.NotNull;

import projek.basiru.R;
import projek.basiru.databinding.FragmentProfilBinding;

public class ProfilFragment extends Fragment
{

    private FragmentProfilBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
//        ProfilViewModel profilViewModel = new ViewModelProvider(this).get(ProfilViewModel.class);
        View view = inflater.inflate(R.layout.fragment_profil, container, false);
        binding = FragmentProfilBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

//        final TextView textView = binding.fragmentProfil;
//        profilViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);


        return view;
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);

    }

    @Override
    public void onDestroyView()
    {
        super.onDestroyView();
        binding = null;
    }

}
