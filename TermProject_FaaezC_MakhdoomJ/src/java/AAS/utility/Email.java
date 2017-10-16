/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AAS.utility;

import AAS.controller.AuthDB;
import AAS.model.StudentAppointment;
import AAS.model.User;
import AAS.view.SessionBean;
import java.sql.SQLException;
import java.util.Properties;
import java.util.Random;
import java.util.logging.Level;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.sql.DataSource;

/**
 *
 * @author fchishti-sw
 */
public class Email {

    private DataSource db;
    String host = "smtp.gmail.com";
    final String user = "csadvisement1@gmail.com";//change accordingly  
    final String password = "ppp12345";//change accordingly

    Properties props;
    Session session;

    public Email(DataSource db) {
        this.db = db;

        props = new Properties();
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.port", "587");

    }

    public void send(String text, String to) {
        try {
            session = Session.getInstance(props,
                    new javax.mail.Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(user, password);
                }
            });

            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(user));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
            message.setSubject("CS Advisement");
            message.setText(text);

            Transport.send(message);

            System.out.println("message sent successfully...");

        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    public void sendAuthCode(User student) {
        Random rand = new Random();
        String code = "" + (rand.nextInt(9999999) + 1000000);
        try {
            session = Session.getInstance(props,
                    new javax.mail.Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(user, password);
                }
            });

            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(user));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(student.getEmail()));
            message.setSubject("CS Advisement");
            message.setText("Your verification code is : " + code);

            Transport.send(message);

            System.out.println("message sent successfully...");

        } catch (MessagingException e) {
            e.printStackTrace();
        }

        AuthDB dataBase = new AuthDB(db);
        try {
            dataBase.create(student, Integer.parseInt(code));
        } catch (SQLException ex) {
            java.util.logging.Logger.getLogger(SessionBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void sendDeleteConfirm(StudentAppointment appointment) {
        try {
            session = Session.getInstance(props,
                    new javax.mail.Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(user, password);
                }
            });

            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(user));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(appointment.user.getEmail()));
            message.setSubject("CS Advisement");
            message.setText("Your " + appointment.appointment.getDateTime().dayOfMonth() +
                     appointment.appointment.getDateTime().monthOfYear() +
                    appointment.appointment.getDateTime().year() 
                    + " at "
                    + appointment.appointment.getDateTime().hourOfDay()
                    + appointment.appointment.getDateTime().minuteOfHour()
                    + " appointment has been canceled by " + appointment.appointment.getFacultyFirstname()
                    + " " + appointment.appointment.getFacultyLastname());

            Transport.send(message);

            System.out.println("message sent successfully...");

        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

}
