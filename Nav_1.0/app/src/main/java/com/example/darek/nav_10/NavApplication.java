package com.example.darek.nav_10;

import android.app.Application;

/**
 * Created by 212449139 on 3/1/2017.
 */

public class NavApplication extends Application{

    ColorPainter colorPainter = new ColorPainter();

    public ColorPainter getColorPainter (){
        return colorPainter;
    }
    public void setColorPainterTheme(ColorPainter.Theme theme){
        colorPainter.setTheme(theme);
    }
}
