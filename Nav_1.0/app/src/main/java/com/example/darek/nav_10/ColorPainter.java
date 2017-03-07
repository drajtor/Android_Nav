package com.example.darek.nav_10;

/**
 * Created by 212449139 on 2/28/2017.
 */

public class ColorPainter {

    enum ButtonType {START_BUTTON, PAUSE_BUTTON, STOP_BUTTON, TRACK_BILLABLE_BUTTON, TRACK_NON_BILLABLE_BUTTON};

    private int BackgroundColor = BLUE;
    private int ButtonColor = BackgroundColor;
    private int ButtonPushedColor = BLUE_LIGHT;

    private static final int BLUE_LIGHT = 0xFF33B5E5;
    private static final int BLUE = 0xFF0099CC;

    private static final int GREEN = 0xFF99CC00;
    private static final int ORANGE = 0xFFFFAA33;
    private static final int RED = 0xFFFF3300;

    private static final int GREEN_DARK = 0xFF77b300;
    private static final int ORANGE_DARK = 0xFFFFAA33;
    private static final int RED_DARK = 0xFFEE0000;
    private static final int GREY_DARK = 0xFF5A5956;

    private FragmentTrackingColorsStructure fragmentTrackingColorsStructure = new FragmentTrackingColorsStructure();

    public ColorPainter(){
        setTheme (Theme.COLOR_ACTIVE);
        setMode (Mode.WOLAN_MODE);
        UpdateColors();
    }

    enum Mode {ONE_COLOR, ACTIVE_COLOR, WOLAN_MODE};
    enum Theme {BLUE, RED, GREEN, COLOR_ACTIVE};
    Theme theme;
    Mode mode;

    public void setTheme (Theme theme){
        this.theme = theme;
    }
    public void setMode (Mode mode){
        this.mode =mode;
    }

    public Mode getMode (){
        return mode;
    }

    private void  UpdateColors (){
        setFragmentTrackingColorsStructure();
    }

    private void setFragmentTrackingColorsStructure (){
        switch (theme){
            case BLUE:
                fragmentTrackingColorsStructure.Color_StartButton = BLUE;
                fragmentTrackingColorsStructure.Color_PauseButton = BLUE;
                fragmentTrackingColorsStructure.Color_StopButton = BLUE;

                fragmentTrackingColorsStructure.Color_StartButtonPushed = BLUE_LIGHT;
                fragmentTrackingColorsStructure.Color_PauseButtonPushed = BLUE_LIGHT;
                fragmentTrackingColorsStructure.Color_StopButtonPushed = BLUE_LIGHT;
                fragmentTrackingColorsStructure.Color_Background = BLUE;

                BackgroundColor = BLUE;
                ButtonColor = BackgroundColor;
                ButtonPushedColor = BLUE_LIGHT;
                break;

            case RED:
                fragmentTrackingColorsStructure.Color_StartButton = RED_DARK;
                fragmentTrackingColorsStructure.Color_PauseButton = RED_DARK;
                fragmentTrackingColorsStructure.Color_StopButton = RED_DARK;

                fragmentTrackingColorsStructure.Color_StartButtonPushed = RED;
                fragmentTrackingColorsStructure.Color_PauseButtonPushed = RED;
                fragmentTrackingColorsStructure.Color_StopButtonPushed = RED;
                fragmentTrackingColorsStructure.Color_Background = RED_DARK;

                BackgroundColor = RED_DARK;
                ButtonColor = BackgroundColor;
                ButtonPushedColor = RED;
                break;

            case GREEN:
                fragmentTrackingColorsStructure.Color_StartButton = GREEN_DARK;
                fragmentTrackingColorsStructure.Color_PauseButton = GREEN_DARK;
                fragmentTrackingColorsStructure.Color_StopButton = GREEN_DARK;

                fragmentTrackingColorsStructure.Color_StartButtonPushed = GREEN;
                fragmentTrackingColorsStructure.Color_PauseButtonPushed = GREEN;
                fragmentTrackingColorsStructure.Color_StopButtonPushed = GREEN;
                fragmentTrackingColorsStructure.Color_Background = GREEN_DARK;

                BackgroundColor = GREEN_DARK;
                ButtonColor = BackgroundColor;
                ButtonPushedColor = GREEN;
                break;
            case COLOR_ACTIVE:
                fragmentTrackingColorsStructure.Color_StartButton = GREEN_DARK;
                fragmentTrackingColorsStructure.Color_PauseButton = ORANGE_DARK;
                fragmentTrackingColorsStructure.Color_StopButton = RED_DARK;

                fragmentTrackingColorsStructure.Color_StartButtonPushed = GREEN;
                fragmentTrackingColorsStructure.Color_PauseButtonPushed = ORANGE;
                fragmentTrackingColorsStructure.Color_StopButtonPushed = RED_DARK;
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

    public int getBackgroundColorByState(TrackHandler.State state){
        if (mode == Mode.WOLAN_MODE){
            BackgroundColor = fragmentTrackingColorsStructure.Color_Background;
//            ButtonPushedColor = BLUE_LIGHT;//TODO
            return fragmentTrackingColorsStructure.Color_Background;
        }
        switch (state) {
            case START:
                BackgroundColor = fragmentTrackingColorsStructure.Color_StartButton;
                ButtonPushedColor = fragmentTrackingColorsStructure.Color_StartButtonPushed;
                ButtonColor = fragmentTrackingColorsStructure.Color_StartButton;
                return BackgroundColor;
            case PAUSE:
                BackgroundColor = fragmentTrackingColorsStructure.Color_PauseButton;
                ButtonPushedColor = fragmentTrackingColorsStructure.Color_PauseButtonPushed;
                ButtonColor = fragmentTrackingColorsStructure.Color_PauseButton;
                return BackgroundColor;
            case STOP:
                BackgroundColor = fragmentTrackingColorsStructure.Color_StopButton;
                ButtonPushedColor = fragmentTrackingColorsStructure.Color_StopButtonPushed;
                ButtonColor = fragmentTrackingColorsStructure.Color_StopButton;
                return BackgroundColor;
        }
        return 0;
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
                    case STOP:
                        return fragmentTrackingColorsStructure.Color_StartButton;
                }
                break;
            case PAUSE_BUTTON:
                switch (state) {
                    case START:
                    case STOP:
                        return fragmentTrackingColorsStructure.Color_PauseButton;
                    case PAUSE:
                        return fragmentTrackingColorsStructure.Color_PauseButtonPushed;
                }
                break;
            case STOP_BUTTON:
                switch (state) {
                    case START:
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

        if (mode != Mode.WOLAN_MODE){
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
        }else{
            ButtonColor = fragmentTrackingColorsStructure.Color_Background;
            ButtonColorPushed = ButtonPushedColor;
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
