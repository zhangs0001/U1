package com.u1.gocashm.util;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import java.lang.ref.WeakReference;

/**
 * Package name is com.cmcc.numberportable.utils.misc
 * Created by ArcherMind on 05/22/2017.
 */

public class WeakRefHandler extends Handler {
	private WeakReference<Callback> mWeakReference;

	public WeakRefHandler(Callback callback) {
		mWeakReference = new WeakReference<Callback>(callback);
	}

	public WeakRefHandler(Callback callback, Looper looper) {
		super(looper);
		mWeakReference = new WeakReference<Callback>(callback);
	}

	@Override
	public void handleMessage(Message msg) {
		if (mWeakReference != null && mWeakReference.get() != null) {
			Callback callback = mWeakReference.get();
			callback.handleMessage(msg);
		}
	}
}