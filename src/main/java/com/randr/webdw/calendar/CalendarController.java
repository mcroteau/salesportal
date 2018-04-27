package com.randr.webdw.calendar;
   
import java.io.IOException;

import javax.servlet.ServletException;

import com.randr.webdw.mvcAbstract.AbstractController;

/**
 * User: Administrator
 * Date: Jun 21, 2004
 * Time: 2:13:48 PM
 * @author randr
 * @version $Revision: 1.1 $
 */
public class CalendarController extends AbstractController {
    /**
     * Method doAction.
     * @throws ServletException
     * @throws IOException
     */
    public void doAction() throws ServletException, IOException {
        request.getRequestDispatcher("/jsp/shared/calendar/calendar.jsp").forward(request, response);
    }
}
