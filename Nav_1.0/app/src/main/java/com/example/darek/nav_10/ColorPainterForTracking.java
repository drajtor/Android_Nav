package com.example.darek.nav_10;

/**
 * Created by 212449139 on 3/21/2017.
 */

public class ColorPainterForTracking extends ColorPainter {

    enum Mode {ONE_COLOR, WOLAN_MODE};

    protected static Mode mode;
    private FragmentTrackingColorsStructure fragmentTrackingColorsStructure = new FragmentTrackingColorsStructure();


    public ColorPainterForTracking (){
        super();
        setMode(Mode.ONE_COLOR);
        setFragmentTrackingColorsStructure();
    }

    public void setMode (Mode mode){
        this.mode =mode;
    }

    public Mode getMode (){
        return mode;
    }

    private void setFragmentTrackingColorsStructure (){
        switch (mode){
            case ONE_COLOR:
                fragmentTrackingColorsStructure.Color_StartButton = super.ButtonColor;
                fragmentTrackingColorsStructure.Color_PauseButton = super.ButtonColor;
                fragmentTrackingColorsStructure.Color_StopButton = super.ButtonColor;

                fragmentTrackingColorsStructure.Color_StartButtonPushed = super.ButtonPushedColor;
                fragmentTrackingColorsStructure.Color_PauseButtonPushed = super.ButtonPushedColor;
                fragmentTrackingColorsStructure.Color_StopButtonPushed = super.ButtonPushedColor;
                fragmentTrackingColorsStructure.Color_Background = super.BackgroundColor;
                break;
            case WOLAN_MODE:
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

    public int getBackgroundColorByState(TrackHandler.State state){
        switch (state) {
            case START:
                super.BackgroundColor = fragmentTrackingColorsStructure.Color_StartButton;
                super.ButtonPushedColor = fragmentTrackingColorsStructure.Color_StartButtonPushed;
                super.ButtonColor = fragmentTrackingColorsStructure.Color_StartButton;
                return BackgroundColor;
            case PAUSE:
                super.BackgroundColor = fragmentTrackingColorsStructure.Color_PauseButton;
                super.ButtonPushedColor = fragmentTrackingColorsStructure.Color_PauseButtonPushed;
                super.ButtonColor = fragmentTrackingColorsStructure.Color_PauseButton;
                return BackgroundColor;
            case STOP:
                super.BackgroundColor = fragmentTrackingColorsStructure.Color_StopButton;
                super.ButtonPushedColor = fragmentTrackingColorsStructure.Color_StopButtonPushed;
                super.ButtonColor = fragmentTrackingColorsStructure.Color_StopButton;
                return super.BackgroundColor;
        }
        return 0;
    }

    public int UpdateButtonColor (TrackHandler.State state, TrackHandler.TrackType trackType, ButtonType buttonType){
        int color = 0;

        switch (buttonType){
            case START_BUTTON:
            case PAUSE_BUTTON:
            case STOP_BUTTON:
                color = StateButtonColor(state,buttonType);
                break;
            case TRACK_BILLABLE_BUTTON:
            case TRACK_NON_BILLABLE_BUTTON:
                color = TrackButtonColor(state,trackType,buttonType);
                break;
        }
        return color;
    }

    private int StateButtonColor (TrackHandler.State state, ButtonType buttonType){

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

            ButtonColor = fragmentTrackingColorsStructure.Color_Background;
            ButtonColorPushed = super.ButtonPushedColor;

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

    private class FragmentTrackingColorsStructure {
        int Color_StartButton;
        int Color_PauseButton;
        int Color_StopButton;

        int Color_StartButtonPushed;
        int Color_PauseButtonPushed;
        int Color_StopButtonPushed;

        int Color_Background;
    }
}
