package com.example.darek.nav_10;

/**
 * Created by 212449139 on 2/28/2017.
 */

public class ColorPainter {

    enum ButtonType {START_BUTTON, PAUSE_BUTTON, STOP_BUTTON, TRACK_BILLABLE_BUTTON, TRACK_NON_BILLABLE_BUTTON};

    private int BackgroundColorByState = RED;
    private int ButtonPushedColorByState = RED;

    private static final int BLUE_MARKED_ITEM_COLOR = 0xFF33B5E5;
    private static final int BLUE_ITEMS_COLOR = 0xFF0099CC;

    private static final int GREEN = 0xFF99CC00;
    private static final int ORANGE = 0xFFFFAA33;
    private static final int RED = 0xFFFF4444;

    private static final int GREEN_DARK = 0xFF669900;
    private static final int ORANGE_DARK = 0xFFe67a00;
    private static final int RED_DARK = 0xFFCC0000;
    private static final int GREY_DARK = 0xFF5A5956;

    private FragmentTrackingColorsStructure fragmentTrackingColorsStructure = new FragmentTrackingColorsStructure();

    public ColorPainter(){
        setTheme (Theme.COLOR);
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
                fragmentTrackingColorsStructure.Color_StartButton = GREEN_DARK;
                fragmentTrackingColorsStructure.Color_PauseButton = BLUE_ITEMS_COLOR;
                fragmentTrackingColorsStructure.Color_StopButton = RED_DARK;

                fragmentTrackingColorsStructure.Color_StartButtonPushed = GREEN;
                fragmentTrackingColorsStructure.Color_PauseButtonPushed = BLUE_MARKED_ITEM_COLOR;
                fragmentTrackingColorsStructure.Color_StopButtonPushed = RED_DARK;
                fragmentTrackingColorsStructure.Color_Background = GREY_DARK;
                break;
        }
    }

    public FragmentTrackingColorsStructure getFragmentTrackingColors (){
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

    public int UpdateBackgroundColor (TrackHandler.State state){
        switch (state) {
            case START:
                BackgroundColorByState = fragmentTrackingColorsStructure.Color_StartButton;
                ButtonPushedColorByState = fragmentTrackingColorsStructure.Color_StartButtonPushed;
                return BackgroundColorByState;
            case PAUSE:
                BackgroundColorByState = fragmentTrackingColorsStructure.Color_PauseButton;
                ButtonPushedColorByState = fragmentTrackingColorsStructure.Color_PauseButtonPushed;
                return BackgroundColorByState;
            case STOP:
                BackgroundColorByState = fragmentTrackingColorsStructure.Color_StopButton;
                ButtonPushedColorByState = fragmentTrackingColorsStructure.Color_StopButtonPushed;
                return BackgroundColorByState;
        }
        return 0;
    }

    public int getBackgroundColorByState (){
        return BackgroundColorByState;
    }

    public int getButtonPushedColorByState(){
        return ButtonPushedColorByState;
    }

    public int UpdateButtonColor (TrackHandler.State state, TrackHandler.TrackType trackType, ButtonType buttonType){
        int color = 0;

        switch (buttonType){
            case START_BUTTON:
            case PAUSE_BUTTON:
            case STOP_BUTTON:
                color = StateButtonColor(state,trackType,buttonType);
                break;
            case TRACK_BILLABLE_BUTTON:
            case TRACK_NON_BILLABLE_BUTTON:
                color = TrackButtonColor(state,trackType,buttonType);
                break;
        }
        return color;
    }

    private int StateButtonColor (TrackHandler.State state, TrackHandler.TrackType trackType,ButtonType buttonType){
        switch (buttonType) {
            case START_BUTTON:
                switch (state) {
                    case START:
                        return fragmentTrackingColorsStructure.Color_StartButtonPushed;
                    case PAUSE:
                        return fragmentTrackingColorsStructure.Color_StartButton;
                    case STOP:
                        return fragmentTrackingColorsStructure.Color_StartButton;
                }
                break;
            case PAUSE_BUTTON:
                switch (state) {
                    case START:
                        return fragmentTrackingColorsStructure.Color_PauseButton;
                    case PAUSE:
                        return fragmentTrackingColorsStructure.Color_PauseButtonPushed;
                    case STOP:
                        return fragmentTrackingColorsStructure.Color_PauseButton;
                }
                break;
            case STOP_BUTTON:
                switch (state) {
                    case START:
                        return fragmentTrackingColorsStructure.Color_StopButton;
                    case PAUSE:
                        return fragmentTrackingColorsStructure.Color_StopButton;
                    case STOP:
                        return fragmentTrackingColorsStructure.Color_StopButtonPushed;
                }
                break;
        }
        return 0;
    }

    private int TrackButtonColor (TrackHandler.State state, TrackHandler.TrackType trackType,ButtonType buttonType){
        int ButtonColor = 0;
        int ButtonColorPushed = 0;

        switch (state){
            case START:
                ButtonColor = fragmentTrackingColorsStructure.Color_StartButton;
                ButtonColorPushed = fragmentTrackingColorsStructure.Color_StartButtonPushed;
                break;
            case PAUSE:
                ButtonColor = fragmentTrackingColorsStructure.Color_PauseButton;
                ButtonColorPushed = fragmentTrackingColorsStructure.Color_PauseButtonPushed;
                break;
            case STOP:
                ButtonColor = fragmentTrackingColorsStructure.Color_StopButton;
                ButtonColorPushed = fragmentTrackingColorsStructure.Color_StopButtonPushed;
                break;
        }
        switch (buttonType){
            case TRACK_BILLABLE_BUTTON:
                if (trackType == TrackHandler.TrackType.TRACK_BILLABLE)
                    return ButtonColorPushed;
                else{
                    return ButtonColor;
                }
            case TRACK_NON_BILLABLE_BUTTON:
                if (trackType == TrackHandler.TrackType.TRACK_NON_BILLABLE)
                    return ButtonColorPushed;
                else{
                    return ButtonColor;
                }
        }
    return 0;
    }
}
