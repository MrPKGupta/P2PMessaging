package com.p2psample.util;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by Purushottam Gupta on 2/6/2018.
 */

public class ActivityUtils {
    private ActivityUtils() {
        //Private Constructor
    }

    public static void addFragmentToActivity(@NonNull FragmentManager fragmentManager,
            @NonNull Fragment fragment, int frameId) {
        checkNotNull(fragmentManager);
        checkNotNull(fragment);
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(frameId, fragment);
        transaction.commit();
    }
}
