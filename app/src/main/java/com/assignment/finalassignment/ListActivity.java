package com.assignment.finalassignment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.ColorSpace;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.firebase.ui.database.FirebaseRecyclerAdapter;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.firebase.ui.database.SnapshotParser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public class ListActivity extends AppCompatActivity {

    private LinearLayoutManager manager;
    private FirebaseRecyclerAdapter adapter;

    RecyclerView rView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        rView = findViewById(R.id.recycleView);
        manager = new LinearLayoutManager(this);
        rView.setLayoutManager(manager);
        rView.setHasFixedSize(true);
        fetch();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView x;
        public TextView y;
        public TextView timestamp;

        public ViewHolder(View itemView) {
            super(itemView);
            x = itemView.findViewById(R.id.Valuex);
            y = itemView.findViewById(R.id.Valuey);
            timestamp = itemView.findViewById(R.id.Valuep);
        }

        public void setX(String xVal) {
            this.x.setText(xVal);
        }


        public void setY(String yVal) {
            this.y.setText(yVal);
        }

        public void setTimestamp(String tVal) {
            this.timestamp.setText(tVal);
        }
    }

    private void fetch() {
        Query query = FirebaseDatabase.getInstance().getReference("points").child("save_points");

        FirebaseRecyclerOptions<Model> options =
                new FirebaseRecyclerOptions.Builder<Model>()
                        .setQuery(query, new SnapshotParser<Model>() {
                            @NonNull
                            @Override
                            public Model parseSnapshot(@NonNull DataSnapshot snapshot) {
                                System.out.print("sadnaskd");
                                return new Model(snapshot.child("x").getValue().toString(),
                                        snapshot.child("y").getValue().toString(),
                                        snapshot.child("timestamp").getValue().toString());
                            }
                        })
                        .build();

        adapter = new FirebaseRecyclerAdapter<Model, ViewHolder>(options) {
            @Override
            public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.list_item, parent, false);

                return new ViewHolder(view);
            }


            @Override
            protected void onBindViewHolder(ViewHolder holder, final int position, Model model) {
                holder.setX(model.getX());
                holder.setY(model.getY());
                holder.setTimestamp(model.getTimeStamp());

            }

        };
        rView.setAdapter(adapter);
    }
}
