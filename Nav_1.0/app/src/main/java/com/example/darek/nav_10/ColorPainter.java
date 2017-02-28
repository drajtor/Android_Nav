package com.example.darek.nav_10;

/**
 * Created by 212449139 on 2/28/2017.
 */

public class ColorPainter {

    private static final int BLUE_MARKED_ITEM_COLOR = 0xFF33B5E5;
    private static final int BLUE_ITEMS_COLOR = 0xFF0099CC;

    private static final int GREEN = 0xFF99CC00;
    private static final int ORANGE = 0xFFFFAA33;
    private static final int RED = 0xFFFF4444;

    private static final int GREEN_DARK = 0xFF669900;
    private static final int ORANGE_DARK = 0xFFFF9922;
    private static final int RED_DARK = 0xFFCC0000;
    private static final int GREY_DARK = 0xFF5A5956;


    private FragmentTrackingColorsStructure fragmentTrackingColorsStructure = new FragmentTrackingColorsStructure();

    public ColorPainter(){
        setTheme (Theme.BLUE);
        UpdateColors();
    }

    enum Theme {BLUE,COLOR};
    Theme theme;

    public void setTheme (Theme theme){
        this.theme = theme;
    }

    private void  UpdateColors (){
        setFragmentTrackingColorsStructure();
    }

    private void setFragmentTrackingColorsStructure (){
                switch (theme){
            case BLUE:
                fragmentTrackingColorsStructure.Color_StartButton = BLUE_ITEMS_COLOR;
                fragmentTrackingColorsStructure.Color_PauseButton = BLUE_ITEMS_COLOR;
                fragmentTrackingColorsStructure.Color_StopButton = BLUE_ITEMS_COLOR;
                fragmentTrackingColorsStructure.Color_StartButtonPushed = BLUE_MARKED_ITEM_COLOR;
                fragmentTrackingColorsStructure.Color_PauseButtonPushed = BLUE_MARKED_ITEM_COLOR;
                fragmentTrackingColorsStructure.Color_StopButtonPushed = BLUE_MARKED_ITEM_COLOR;
                fragmentTrackingColorsStructure.Color_Background = BLUE_ITEMS_COLOR;
                break;
            case COLOR:
                fragmentTrackingColorsStructure.Color_StartButton = GREEN;
                fragmentTrackingColorsStructure.Color_PauseButton = ORANGE;
                fragmentTrackingColorsStructure.Color_StopButton = RED_DARK;

                fragmentTrackingColorsStructure.Color_StartButtonPushed = GREEN_DARK;
                fragmentTrackingColorsStructure.Color_PauseButtonPushed = ORANGE_DARK;
                fragmentTrackingColorsStructure.Color_StopButtonPushed = RED_DARK;
                fragmentTrackingColorsStructure.Color_Background = GREY_DARK;
                break;
        }
    }

    FragmentTrackingColorsStructure getFragmentTrackingColors (){
        return fragmentTrackingColorsStructure;
    }

    public class FragmentTrackingColorsStructure {
        int Color_StartButton;
        int Color_PauseButton;
        int Color_StopButton;

        int Color_StartButtonPushed;
        int Color_PauseButtonPushed;
        int Color_StopButtonPushed;

        int Color_Background;
    }
}
