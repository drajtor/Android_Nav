package com.example.darek.nav_10;

import android.app.Application;

/**
 * Created by 212449139 on 3/1/2017.
 */

public class NavApplication extends Application{

    ColorPainterForTracking colorPainterForTracking = new ColorPainterForTracking();

    public ColorPainterForTracking getColorPainterForTracking(){
        return colorPainterForTracking;
    }
    public void setColorPainterTheme(ColorPainter.Theme theme){
        colorPainterForTracking.setTheme(theme);
    }
}
