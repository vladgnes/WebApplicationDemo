package controller.student;

import dao.hibernate.DAOHibernateStudent;
import dao.jdbc.DAOGetMark;
import dao.jdbc.DAOLogin;
import dao.jdbc.DAOSetSubject;
import dao.jdbc.DAOShowCourses;
import model.Course;
import model.CourseInfoEntity;
import model.Student;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by vlad on 26.10.16.
 */
public class SubscribeStudentController extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException,java.io.IOException{
        String[] subscribedCourses = request.getParameterValues("subscribeBox");
        HttpSession session = request.getSession(true);
        if(subscribedCourses == null) {
            session.setAttribute("isFirst", false);
            response.sendRedirect("SubscribeCoursePage.jsp");
        }
        else {
            Student student = (Student) session.getAttribute("student");
            DAOHibernateStudent hibernateStudent = new DAOHibernateStudent();
            String firstName = student.getFirstName();
            String lastName = student.getLastName();
            List<CourseInfoEntity> courses = hibernateStudent.getCoursesInfo();
            session.setAttribute("courses",courses);
            student = hibernateStudent.login((String)session.getAttribute("login"),
                    (String)session.getAttribute("password"));
            ArrayList<Integer> marks = new ArrayList<>();
            int javaMark = hibernateStudent.getMark("Java", firstName, lastName);
            marks.add(javaMark);
            int javaScriptMark = hibernateStudent.getMark("JavaScript", firstName, lastName);
            marks.add(javaScriptMark);
            int dataStructuresMark = hibernateStudent.getMark("DataStructures", firstName, lastName);
            marks.add(dataStructuresMark);
            session.setAttribute("marks",marks);
            session.setAttribute("student",student);
            for (String subject: subscribedCourses) {
                hibernateStudent.subcribe(subject, firstName, lastName);
            }
            if(!response.isCommitted()) {
                response.sendRedirect("StudentProfilePage.jsp");
            }
        }
    }
}
