package com.neopixl.pixlui.intern;

import android.content.Context;

public interface FontStyleView {
    boolean setCustomFont(Context ctx, String font);
    void setPaintFlags(int flags);
    int getPaintFlags();
}
