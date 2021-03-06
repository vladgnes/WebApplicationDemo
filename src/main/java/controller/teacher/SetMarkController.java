package controller.teacher;

import controller.student.SetImageStudentController;
import dao.hibernate.DAOHibernateTeacher;
import model.Course;
import model.Student;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by vlad on 02.11.16.
 */
public class SetMarkController extends HttpServlet {

    private static Logger logger = Logger.getLogger(SetImageStudentController.class);

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession(true);
        ArrayList<Course> students = (ArrayList<Course>) session.getAttribute("students");
        String subject = (String) session.getAttribute("subject");
        ArrayList<Integer> marksArray = new ArrayList<>();
        for(int i = 0;i < students.size();i++) {
            String firstName = students.get(i).getFirstName();
            String lastName = students.get(i).getLastName();
            try {
                Integer mark = Integer.parseInt(request.getParameter("mark" + i));
                if(mark > 100 || mark <= 0){
                    session.setAttribute("illegalValues",false);
                    response.sendRedirect("TeacherProfilePage.jsp");
                    return;
                }else {
                    setMark(subject,mark,firstName,lastName,marksArray);
                }
            }catch (Exception e){
                logger.info(e);
                if (!response.isCommitted()) {
                    session.setAttribute("illegalValues", false);
                    response.sendRedirect("TeacherProfilePage.jsp");
                    return;
                }
            }
        }
        session.setAttribute("marks",marksArray);
        if(!response.isCommitted()){
            response.sendRedirect("TeacherProfilePage.jsp");
        }

    }
    private void setMark(String subject,Integer mark,String firstName,String lastName,
                         ArrayList<Integer> marksArray){
        marksArray.add(mark);
        DAOHibernateTeacher teacher = new DAOHibernateTeacher();
        teacher.setMark(subject,mark,firstName,lastName);

    }

}
