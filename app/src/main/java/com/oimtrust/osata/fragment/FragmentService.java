package com.oimtrust.osata.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.oimtrust.osata.R;
import com.oimtrust.osata.activity.ServiceActivity;

public class FragmentService extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
       View serviceRoot = inflater.inflate(R.layout.activity_fragment_service, container, false);

        TextView    addWisataTxt    = (TextView) serviceRoot.findViewById(R.id.tambahLokasi_text_service);
        addWisataTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), ServiceActivity.class));
            }
        });

        return serviceRoot;
    }
}
