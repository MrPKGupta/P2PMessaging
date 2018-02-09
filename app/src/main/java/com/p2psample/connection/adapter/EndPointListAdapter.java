package com.p2psample.connection.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.p2psample.R;
import com.p2psample.nearby.Endpoint;

import java.util.List;

/**
 * Created by Purushottam Gupta on 2/6/2018.
 */

public class EndPointListAdapter extends RecyclerView.Adapter<EndPointListAdapter.DeviceHolder> {
    private List<Endpoint> endPoints;
    private static ConnectionFragmentListener connectionFragmentListener;

    public EndPointListAdapter(List<Endpoint> endPoints, ConnectionFragmentListener listener) {
        this.endPoints = endPoints;
        connectionFragmentListener = listener;
    }

    @Override
    public int getItemCount() {
        return endPoints.size();
    }

    @Override
    public DeviceHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_device, parent, false);
        return new DeviceHolder(view);
    }

    @Override
    public void onBindViewHolder(DeviceHolder holder, int position) {
        Endpoint endpoint = endPoints.get(position);
        holder.tvDevice.setText(endpoint.getName());
    }

    static class DeviceHolder extends RecyclerView.ViewHolder {
        TextView tvDevice;

        DeviceHolder(View view) {
            super(view);
            tvDevice = view.findViewById(R.id.tvDevice);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    connectionFragmentListener.onItemClick(getAdapterPosition());
                }
            });
        }
    }

    public void remove(int position) {
        endPoints.remove(position);
        notifyItemRemoved(position);
    }

    public void add(Endpoint endpoint) {
        endPoints.add(endpoint);
        notifyItemInserted(endPoints.size());
    }

    public interface ConnectionFragmentListener {
        void onItemClick(int position);
    }
}
