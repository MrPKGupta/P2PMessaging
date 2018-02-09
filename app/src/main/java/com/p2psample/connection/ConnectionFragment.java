package com.p2psample.connection;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.p2psample.R;
import com.p2psample.connection.adapter.EndPointListAdapter;
import com.p2psample.messaging.MessagingActivity;
import com.p2psample.nearby.Endpoint;
import com.p2psample.nearby.NearBySource;

import java.util.ArrayList;
import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by Purushottam Gupta on 2/5/2018.
 */

public class ConnectionFragment extends Fragment implements ConnectionContract.View, View.OnClickListener,
        EndPointListAdapter.ConnectionFragmentListener {
    private ConnectionContract.Presenter presenter;
    private EndPointListAdapter endPointListAdapter;
    private List<Endpoint> endPoints = new ArrayList<>();

    private Button btnFind;
    private Button btnShare;
    private TextView tvLog;
    private String endPointName;

    public static ConnectionFragment newInstance() {
        return new ConnectionFragment();
    }

    @Override
    public void setPresenter(@NonNull ConnectionContract.Presenter presenter) {
        this.presenter = checkNotNull(presenter);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_connection, container, false);

        btnFind = root.findViewById(R.id.btnFind);
        btnShare = root.findViewById(R.id.btnShare);
        tvLog = root.findViewById(R.id.tvLog);

        RecyclerView rvDevices = root.findViewById(R.id.rvDevice);
        rvDevices.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvDevices.setHasFixedSize(true);

        if (endPointListAdapter == null) {
            endPointListAdapter = new EndPointListAdapter(endPoints, this);
        }
        rvDevices.setAdapter(endPointListAdapter);

        btnFind.setOnClickListener(this);
        btnShare.setOnClickListener(this);

        return root;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnFind:
                disableControl();
                presenter.findConnection(getContext().getPackageName());
                break;
            case R.id.btnShare:
                disableControl();
                //getEndPointName();
                presenter.shareConnection(endPointName, getContext().getPackageName());
                break;
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        presenter.start();
    }

    @Override
    public void onStop() {
        super.onStop();
        presenter.stop();
    }

    private void getEndPointName() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Device name");
        final EditText input = new EditText(getContext());
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        input.setLayoutParams(lp);
        builder.setView(input);
        builder.setPositiveButton(getContext().getString(R.string.ok),
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        endPointName = input.getText().toString().trim();
                    }
                })
                .create();
        builder.show();
    }

    private void disableControl() {
        btnFind.setEnabled(false);
        btnShare.setEnabled(false);
    }

    @Override
    public void addEndPoint(Endpoint endpoint) {
        tvLog.append("EndPoint found " + endpoint.getName());
        endPointListAdapter.add(endpoint);
    }

    @Override
    public void removeEndPoint(String endPointId) {
        //endPointListAdapter.remove(position);
        tvLog.append("EndPoint lost " + endPointId);
    }

    @Override
    public void onItemClick(int position) {
        NearBySource.getInstance().requestConnection(endPoints.get(position));
    }

    @Override
    public void startMessaging() {
        Intent intent = new Intent(getActivity(), MessagingActivity.class);
        startActivity(intent);
    }
}
