package projek.basiru.ui.dashboard;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class DashboardViewModel extends ViewModel {

    String name;
    String version;
    int id_;
    int image;

    public DashboardViewModel(String name, String version, int id_, int image) {
        this.name = name;
        this.version = version;
        this.id_ = id_;
        this.image=image;
    }

    public String getName() { return name; }

    public String getVersion() { return version; }

    public int getImage() { return image; }

    public int getId() { return id_; }


//    private MutableLiveData<String> mText;
//
//    public DashboardViewModel()
//    {
//        mText = new MutableLiveData<>();
//        mText.setValue("This is dashboard fragment");
//    }
//
//    public LiveData<String> getText() { return mText; }
}