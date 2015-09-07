package com.booking.controller;

//package by.bsu.famcs.jspservlet.logic;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.DriverManager;
import com.booking.controller.ConfigurationManager;


/**
 * @author Maksym.
 */
public class LoginLogic {
    public static boolean checkLogin(String login, String password) {
        // �������� ������ � ������
        try {
//����������� ����������� ���������� � ����� ������
            String driver = ConfigurationManager.getInstance().getProperty(ConfigurationManager.DATABASE_DRIVER_NAME);
            Class.forName(driver);
            Connection cn = null;
            try {
                String url = ConfigurationManager.getInstance().getProperty(ConfigurationManager.DATABASE_URL);
                cn = DriverManager.getConnection(url);
                PreparedStatement st = null;
                try {
                    st = cn.prepareStatement("SELECT * FROM USERS WHERE LOGIN = ? AND PASSWORD = ?");
                    st.setString(1, login);
                    st.setString(2, password);
                    ResultSet rs = null;
                    try {
                        rs = st.executeQuery();
/* ��������, ���������� �� ������������
� ��������� ������� � ������� */
                        return rs.next();
                    } finally {
                        if (rs != null)
                            rs.close();
                    }
                } finally {
                    if (st != null)
                        st.close();
                }
            } finally {
                if (cn != null)
                    cn.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }
}
