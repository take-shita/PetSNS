package com.example.petsns.ui.route;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.petsns.MyApplication;
import com.example.petsns.R;
import com.example.petsns.ui.snstop.TestPost;
import com.example.petsns.ui.snstop.TestPostAdapter;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class FavoriteRouteAdapter extends RecyclerView.Adapter<FavoriteRouteAdapter.RouteViewHolder>{
    private List<FavoriteRoute> routes;
    private Context context;
    private List<QueryDocumentSnapshot> data;
    private RouteViewModel viewModel;
    private FragmentActivity fragment;
    private View routeView;
    private Dialog dialog;

    public FavoriteRouteAdapter(Context context, FragmentActivity fragment, View view, Dialog dialog) {
        this.data =new ArrayList<>();
        this.context = context;
        this.fragment=fragment;
        this.routeView=view;
        this.dialog=dialog;
    }

    public void setRoutes(List<FavoriteRoute> routes) {
        this.routes = routes;
        notifyDataSetChanged();
    }

    private String formattimestamp(Timestamp timestamp) {
        // ここで適切なフォーマットに変換する処理を実装
        // 例: SimpleDateFormatを使用して文字列に変換する
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        return sdf.format(new Date(timestamp.getSeconds() * 1000));
    }

    @NonNull
    @Override
    public RouteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_favorite_route, parent, false);

        MyApplication myApplication = (MyApplication) fragment.getApplication();
        if (myApplication != null) {
            viewModel = myApplication.getSharedRouteViewModel();
        } else {
            // エラーハンドリング
        }
        return new RouteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FavoriteRouteAdapter.RouteViewHolder holder, int position) {
        int adapterPosition = holder.getAdapterPosition();

        FavoriteRoute route=routes.get(adapterPosition);
        FirebaseFirestore db=FirebaseFirestore.getInstance();
        String routeName=route.getName();
        String documentId=route.getDocumentId();
        holder.txtRouteName.setText(routeName);
        holder.btnSelected.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewModel.TrueFavoriteCheck();
                viewModel.setOrigin(route.getOrigin());
                viewModel.setPoint1(route.getPoint1());
                viewModel.setPoint2(route.getPoint2());
                viewModel.setPoint3(route.getPoint3());
                Navigation.findNavController(routeView).navigate(R.id.action_navigation_route_to_navigation_route_view);
                dialog.dismiss();
            }
        });

        holder.btnDeleted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.collection("routeFavorite").document(documentId)
                        .delete()
                        .addOnSuccessListener(aVoid -> {
                            Toast.makeText(context, "ルートが削除されました", Toast.LENGTH_SHORT).show();
                            dialog.dismiss();
                        })
                        .addOnFailureListener(e -> {
                            // 削除失敗時の処理
                            // 例: エラーメッセージを表示
                        });
            }
        });
    }
    public int getItemCount() { return routes != null ? routes.size() : 0; }
    public class RouteViewHolder extends RecyclerView.ViewHolder {
        TextView txtRouteName;
        Button btnSelected;
        Button btnDeleted;
        public RouteViewHolder(@NonNull View itemView) {
            super(itemView);
            txtRouteName = itemView.findViewById(R.id.route_name);
            btnSelected=itemView.findViewById(R.id.selected_btn);
            btnDeleted=itemView.findViewById(R.id.deleted_btn);
        }
    }
}
