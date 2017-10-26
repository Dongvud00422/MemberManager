/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MemberManager.model;

import MemberManager.entity.Member;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/**
 *
 * @author dongvu
 */
public class MemberModel {

    public ArrayList<Member> getData(int page) {
        BufferedReader buffer = null;
        ArrayList<Member> memberList = null;
        try {
            URL url = new URL("http://youtube-api-challenger.appspot.com/xml/members?page=" + String.valueOf(page) + "&limit=10");
            URLConnection urlConn = url.openConnection();
            buffer = new BufferedReader(new InputStreamReader(urlConn.getInputStream()));

            String line = null;
            while ((line = buffer.readLine()) != null) {
                try {
//                    System.out.println(line);
                    Document document = loadXMLFromString(line);
                    document.getDocumentElement().normalize();
                    NodeList nList = document.getElementsByTagName("Member");
                    memberList = new ArrayList<>();
                    for (int i = 0; i < nList.getLength(); i++) {
                        Node nNode = nList.item(i);

                        Element element = (Element) nNode;
                        Member member = new Member();
                        member.setId(Long.parseLong(element.getAttribute("id")));
                        member.setUsername(element.getElementsByTagName("UserName").item(0).getTextContent());
                        member.setFullname(element.getElementsByTagName("FullName").item(0).getTextContent());
                        member.setEmail(element.getElementsByTagName("Email").item(0).getTextContent());
                        member.setPassword(element.getElementsByTagName("Password").item(0).getTextContent());
                        member.setBirthday(element.getElementsByTagName("Birthday").item(0).getTextContent());
                        member.setGender(Integer.parseInt(element.getElementsByTagName("Gender").item(0).getTextContent()));
                        member.setAvatar(element.getElementsByTagName("Avatar").item(0).getTextContent());
                        member.setStatus(Integer.parseInt(element.getElementsByTagName("Status").item(0).getTextContent()));

                        memberList.add(member);
                    }
                } catch (ParserConfigurationException | SAXException ex) {
                    System.out.println("parser|SAX: " + ex.getMessage());
                    return null;
                }

            }

        } catch (MalformedURLException ex) {
            System.out.println("MailformedURLEx: " + ex.getMessage());
        } catch (IOException ex) {
            System.out.println("ioEx: " + ex.getMessage());
        } finally {
            if (buffer != null) {
                try {
                    buffer.close();
                } catch (IOException ex) {
                    Logger.getLogger(MemberModel.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return memberList;

    }

    public static Document loadXMLFromString(String xml) throws ParserConfigurationException, SAXException, IOException {
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = dbFactory.newDocumentBuilder();
        InputSource is = new InputSource(new StringReader(xml));
        return builder.parse(is);
    }

}
