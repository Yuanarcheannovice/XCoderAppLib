package com.xcoder.lib.widget.refresh;

public class RefreshDragDistanceConverterEg implements RefreshIDragDistanceConverter {

    @Override
    public float convert(float scrollDistance, float refreshDistance) {
        return scrollDistance * 0.5f;
    }
}
