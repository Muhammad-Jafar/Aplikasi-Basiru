//package projek.basiru.ui.dashboard;
//
//import android.content.Context;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ImageView;
//import android.widget.TextView;
//import androidx.annotation.NonNull;
//import androidx.recyclerview.widget.RecyclerView;
//import org.jetbrains.annotations.NotNull;
//import java.util.ArrayList;
//import projek.basiru.R;
//
//public class DashboardAdapter extends RecyclerView.Adapter<DashboardAdapter.MyViewHolder> {
//
//    ArrayList <Datanya> araynya;
//    Context konteks;
//    ImageView gbr;
//    TextView teks1nya, teks2nya;
//
//    public DashboardAdapter(Context konteks, ArrayList araynya)
//    {
//        this.konteks = konteks;
//        this.araynya = araynya;
//    }
//
//    public DashboardAdapter(DashboardViewModel dashboardViewModel) {
//        this.dashboaradViewModel = dashboardViewModel;
//    }
//
//    public static class MyViewHolder extends RecyclerView.ViewHolder {
//        TextView jumlahdonasi;
//        TextView totaldonastur;
//        ImageView gbrpendukung;
//
//        public MyViewHolder (View itemView) {
//            super(itemView);
//            jumlahdonasi = itemView.findViewById(R.id.ketdonasi);
//            totaldonastur = itemView.findViewById(R.id.totaldonasi);
//            gbrpendukung = itemView.findViewById(R.id.gbrpendukung);
//        }
//    }
//
//    @Override
//    public DashboardAdapter.MyViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
//
//        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_layout, parent, false);
//        MyViewHolder myViewHolder = new MyViewHolder(view);
//        return myViewHolder;
//    }
//
//    @Override
//    public void onBindViewHolder(final MyViewHolder holder, final int listPosition) {
//        TextView jumlahdonasi = holder.jumlahdonasi;
//        TextView totaldonatur = holder.totaldonastur;
//        ImageView gbrpendukung = holder.gbrpendukung;
//
//        jumlahdonasi.setText(araynya[name]);
//
////        jumlahdonasi.setText(nameArray.getText());
////        totaldonatur.setText(versionArray.getText());
////        gbrpendukung.setImageResource(Integer.parseInt(gbrpendukung.get(listPosition).getText()));
//    }
//
//    @Override
//    public int getItemCount() {
//        return araynya.size();
//    }
//}
