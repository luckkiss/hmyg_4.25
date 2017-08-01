package com.zzy.common.widget.gestureimageview;

public interface RomateAnimationListener {

	void onRomate(float rotation, boolean needScale, float scale,
                  float x, float y);

	void onComplete(boolean needScale);
}
