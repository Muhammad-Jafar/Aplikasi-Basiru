package projek.basiru.ui.dashboard;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import projek.basiru.R;
import projek.basiru.databinding.FragmentDashboardBinding;


public class DashboardFragment extends Fragment
{
    private FragmentDashboardBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_dashboard, container, false);
        DashboardViewModel dashboardViewModel = new ViewModelProvider(this).get(DashboardViewModel.class);
        binding = FragmentDashboardBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

//        final TextView totaldonasi = binding.jumlahterkumpul;
//        dashboardViewModel.getText().observe(getViewLifecycleOwner(), totaldonasi::setText);
        return root;
    }

    @Override
    public void onDestroyView()
    {
        super.onDestroyView();
         binding.getRoot();
    }
}
