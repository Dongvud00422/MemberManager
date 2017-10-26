/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MemberManager.view;

import MemberManager.entity.Member;
import MemberManager.model.MemberModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author dongvu
 */
public class MemberListView {

    private JFrame frame;
    private JScrollPane scrollpane;
    private DefaultTableModel tableModel;
    private JTable table;
    private JLabel lblPage;
    private JButton btnNext, btnPre;
    private static int page = 1;

    public void showListMember() {
        frame = new JFrame("Member List");
        frame.setSize(1200, 250);
        frame.setLocationRelativeTo(null);

        tableModel = loadContentTable(page);
        table = new JTable();
        lblPage = new JLabel(String.valueOf(page));
        btnNext = new JButton("Next");
        btnPre = new JButton("Previous");

        btnPre.setBounds(500, 195, 80, 30);
        lblPage.setBounds(590, 195, 30, 30);
        btnNext.setBounds(610, 195, 80, 30);
        btnNext.addActionListener(new NextHandle());
        btnPre.addActionListener(new PreveousHandle());
        btnPre.setEnabled(false);

        table.setModel(tableModel);
        scrollpane = new JScrollPane(table);
        scrollpane.setBounds(10, 10, 1180, 185);
        frame.add(scrollpane);
        frame.add(btnNext);
        frame.add(btnPre);
        frame.add(lblPage);
        frame.setLayout(null);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    private class NextHandle implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            System.out.println("Page: " + ++page);
           
            tableModel = loadContentTable(++page);
            table.setModel(tableModel);
            scrollpane.add(table);
            frame.add(scrollpane);
            btnPre.setEnabled(true);
            lblPage.setText(String.valueOf(page));
            System.out.println("PageAfterNext: " + page);
        }

    }

    private class PreveousHandle implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

            if (page >= 2) {
                System.out.println("Page: " + page);
                tableModel = loadContentTable(--page);
                table.setModel(tableModel);
                scrollpane.add(table);
                frame.add(scrollpane);
                lblPage.setText(String.valueOf(page));
                            System.out.println("PageAfterPre: "+ page);
            } else {
                btnPre.setEnabled(false);
            }
        }
    }

    private DefaultTableModel loadContentTable(int p) {
        DefaultTableModel tModel = new DefaultTableModel();
        MemberModel memberModel = new MemberModel();

        if (memberModel.getData(p) != null) {
            ArrayList<Member> memberList = memberModel.getData(p);
            Long[] id = new Long[memberList.size()];
            String[] username = new String[memberList.size()];
            String[] fullname = new String[memberList.size()];
            String[] email = new String[memberList.size()];
            String[] password = new String[memberList.size()];
            String[] birthday = new String[memberList.size()];
            Integer[] gender = new Integer[memberList.size()];
            String[] avatar = new String[memberList.size()];
            Integer[] status = new Integer[memberList.size()];

            for (int i = 0; i < memberList.size(); i++) {
                id[i] = memberList.get(i).getId();
                username[i] = memberList.get(i).getUsername();
                fullname[i] = memberList.get(i).getFullname();
                email[i] = memberList.get(i).getEmail();
                password[i] = memberList.get(i).getPassword();
                birthday[i] = memberList.get(i).getBirthday();
                gender[i] = memberList.get(i).getGender();
                avatar[i] = memberList.get(i).getAvatar();
                status[i] = memberList.get(i).getStatus();
            }

            tModel.addColumn("Id", id);
            tModel.addColumn("Username", username);
            tModel.addColumn("Fullname", fullname);
            tModel.addColumn("Email", email);
            tModel.addColumn("Password", password);
            tModel.addColumn("Birthday", birthday);
            tModel.addColumn("Gender", gender);
            tModel.addColumn("Avatar", avatar);
            tModel.addColumn("Status", status);
        }
        return tModel;

    }
}
