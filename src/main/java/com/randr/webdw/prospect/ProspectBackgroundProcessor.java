package com.randr.webdw.prospect;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Timer;

import javax.servlet.ServletException;

import com.randr.webdw.AppSettings;
import com.randr.webdw.mvcAbstract.AbstractController;

/**
 */
public class ProspectBackgroundProcessor extends AbstractController {

    /**
     * Method doAction.
     * @throws ServletException
     * @throws IOException
     */
    public void doAction() throws ServletException, IOException {
    }

    /**
     * Method init.
     */
    public void init() {
        if (!AppSettings.isLoaded()) {
            AppSettings.createConfigDocument(getServletContext().getRealPath("/"));
        }

        if (AppSettings.getParm("SALES_ACTION_EMAIL_ENABLED").equals("YES")) {

            int startAfterMilliseconds = 10 * 60 * 1000; //ten minutes
            int repeatEveryMilliseconds = new BigDecimal(AppSettings.getParm("SALES_ACTION_EMAIL_SCHEDULE_MINUTES")).intValue() * 60 * 1000;

            Timer timer = new Timer(false);
            timer.scheduleAtFixedRate(new ProspectTimerTask(), startAfterMilliseconds, repeatEveryMilliseconds);
        }
    }
}
