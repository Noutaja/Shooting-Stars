package com.jarvinen.jyri.ss;

enum Actions{
    EXIT_GAME, RESTART_STAGE, START_GAME, RESUME_GAME, RETURN_TO_MAIN_MENU, SHOW_SCORES, HELP, CREDITS, RETRY
}

/**
 * Action a MenuItem does upon activation.
 */
public class MenuAction {
    Credit credit;
    GameController gc;
    Actions action;

    /**
     * @param action The action this MenuAction will do when activated.
     */
    public MenuAction(Actions action){
        gc = GameController.Instance;
        this.action = action;
    }

    /**
     * Does the action it was assigned.
     */
    public void activate(){
        if (action == Actions.START_GAME) {
            gc.credit = new Credit();

        } else if (action == Actions.RESUME_GAME) {
            credit = Credit.Instance;
            credit.menu = null;

        } else if (action == Actions.RESTART_STAGE) {
            credit = Credit.Instance;
            credit.startStage(credit.currentStage);
            credit.menu = null;

        } else if (action == Actions.RETRY) {
            credit = Credit.Instance;
            if (credit != null)
                credit.music.stop();

            gc.credit = new Credit();

        } else if (action == Actions.SHOW_SCORES) {
            MyScreen ms = new MyScreen();
            ms.elements.add(new HighScores());
            gc.setScreen(ms);

        } else if (action == Actions.HELP) {
            MyScreen ms = new MyScreen();
            ms.elements.add(new Help());
            gc.setScreen(ms);

        } else if (action == Actions.RETURN_TO_MAIN_MENU) {
            credit = Credit.Instance;
            if (credit != null)
                credit.music.stop();

            gc.credit = null;

            MyScreen ms = new MyScreen();
            ms.elements.add(new MainMenu());
            gc.setScreen(ms);

        } else if (action == Actions.EXIT_GAME) {
            System.exit(0);

        } else if (action == Actions.CREDITS) {
            MyScreen ms = new MyScreen();
            ms.elements.add(new Credits(100, 120, 350));
            gc.setScreen(ms);
        }
    }
}
