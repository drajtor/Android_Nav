package com.example.darek.nav_10;

/**
 * Created by 212449139 on 2/28/2017.
 */

public class ColorPainter {

    enum ButtonType {START_BUTTON, PAUSE_BUTTON, STOP_BUTTON, TRACK_BILLABLE_BUTTON, TRACK_NON_BILLABLE_BUTTON};
    enum Theme {BLUE, RED, GREEN, COLOR_ACTIVE};

    protected static final int BLUE_LIGHT = 0xFF33B5E5;
    protected static final int BLUE = 0xFF0099CC;

    protected static final int GREEN = 0xFF99CC00;
    protected static final int ORANGE = 0xFFFFAA33;
    protected static final int RED = 0xFFFF3300;

    protected static final int GREEN_DARK = 0xFF77b300;
    protected static final int ORANGE_DARK = 0xFFFFAA33;
    protected static final int RED_DARK = 0xFFEE0000;
    protected static final int GREY_DARK = 0xFF5A5956;

    protected static int BackgroundColor;
    protected static int ButtonColor;
    protected static int ButtonPushedColor;

    protected static Theme theme;

    public ColorPainter(){
        setTheme (Theme.GREEN);

        switch (theme){
            case BLUE:
                BackgroundColor = BLUE;
                ButtonColor = BackgroundColor;
                ButtonPushedColor = BLUE_LIGHT;
                break;
            case RED:
                BackgroundColor = RED_DARK;
                ButtonColor = BackgroundColor;
                ButtonPushedColor = RED;
                break;
            case GREEN:
                BackgroundColor = GREEN_DARK;
                ButtonColor = BackgroundColor;
                ButtonPushedColor = GREEN;
                break;
            case COLOR_ACTIVE:
            default:
                BackgroundColor = BLUE;
                ButtonColor = BackgroundColor;
                ButtonPushedColor = BLUE_LIGHT;
                break;
        }
    }

    public void setTheme (Theme theme){
        this.theme = theme;
    }

    public int getBackgroundColor() {
        return BackgroundColor;
    }

    public int getButtonPushedColor(){
            return ButtonPushedColor;
    }

    public int getButtonColor(){
        return ButtonColor;
    }

}
