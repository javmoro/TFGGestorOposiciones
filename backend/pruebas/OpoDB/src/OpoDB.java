import java.text.SimpleDateFormat;  
 
import Dominio.Departamento;
import Dominio.Epigrafe;
import Dominio.Oposicion;
import Dominio.RelDepEpi;
import Dominio.RelDepEpiPK;

import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;


import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

 



public class OpoDB {
  private static int errorEpigrafe,errorDepartamento,errorOposicion;
    static Connection con;
    public static void main(String [] args) throws SQLException{
        errorEpigrafe =0;
        errorDepartamento =0;
        errorOposicion =0;
        con = DriverManager.getConnection("jdbc:derby://localhost:1527/OpoDB", "usuario", "usuario");
        
        mains();
        System.out.println("error Departamento: "+ errorDepartamento+"\n"
                + "error epigrafe: "+ errorEpigrafe+"\n"
                + "error oposicion: "+ errorOposicion);
    }


 
 static void mains(){
     
    LocalDate date = LocalDate.of(2020, 1, 1);
    String fecha ;
    //System.out.println(fecha);
    
    for(int i = 0 ; date.isBefore(LocalDate.now()); i++){
        if(date.getMonthValue()==2&&date.getDayOfMonth()==5){
            System.out.println("Feliz Cumple maricon");
        }
        fecha = getFecha(date);
        getBoe("https://boe.es/diario_boe/xml.php?id=BOE-S-"+ fecha,fecha);
        date =date.plusDays(1);
    }
 }
 
 public  static void getBoe(String url,String fecha){
    try{
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        Document doc = db.parse(new URL(url).openStream());
        //System.out.println(fecha);
        
        Element e = getElementoUnico("diario", doc.getDocumentElement(),0);
        Element el = getSeccionOposiciones(e);
        if(el==null){
            throw new NullPointerException( "No hay oposiciones en el BOE" );
        }
        NodeList departamentos = el.getElementsByTagName("departamento");
        for(int i = 0 ; i<departamentos.getLength();i++){
            Node ni = departamentos.item(i);
            Element ele = (Element)ni;
            Departamento dep = new Departamento(ele.getAttribute("etq"),ele.getAttribute("nombre"));
            
            anadirDepartamentoBD(dep);
            
            NodeList epigrafes = ele.getElementsByTagName("epigrafe");
            Epigrafe epi = new Epigrafe();
            
            //System.out.println("\t"+ele.getAttribute("nombre"));
            for(int j = 0; j<epigrafes.getLength();j++){
                Epigrafe epig;
                
                Node nj = epigrafes.item(j);
                Element epigrafe = (Element) nj;
                NodeList items = epigrafe.getElementsByTagName("item");
                //System.out.println("\t\t"+epigrafe.getAttribute("nombre"));
                epig = new Epigrafe(epigrafe.getAttribute("nombre"));
                anadirEpigrafeBD(epig);
                RelDepEpi rel = new RelDepEpi(epig.getNombre(),dep.getEtq());
                rel.setDepartamento(dep);
                rel.setEpigrafe(epig);
                anadirREL(rel);
                for(int y = 0 ; y <items.getLength(); y++){
                    Node ny = items.item(y);
                    Element item = (Element) ny;
                    
                    Date f = Date.valueOf(Integer.valueOf(fecha.substring(0, 4))+"-"+Integer.valueOf(fecha.substring(4, 6))+"-"+Integer.valueOf(fecha.substring(6)));
                    //Date f = new Date(Integer.valueOf(fecha.substring(0, 4)),Integer.valueOf(fecha.substring(4, 6)),Integer.valueOf(fecha.substring(6)));
                    Oposicion opo = new Oposicion(item.getAttribute("id"),f,item.getAttribute("control"));
                    opo.setRelDepEpi(rel);
                    anadirOposicionBD(opo);
                    //System.out.println("\t\t\t"+item.getAttribute("id"));
                }
                
            }
        }
        
        
    } catch(Exception e){
       //System.out.println(e.toString()+ "  total");
    }
    
}
public static Element getElementoUnico(String a, Element el,int item){
    NodeList nl = el.getElementsByTagName(a);
    Node n = nl.item(item);
    Element e = (Element) n;
    return e;
}

public static void getOposicion(String urlParte){
    String url = "boe.es"+urlParte;
}
public static Element getSeccionOposiciones(Element e){
    Element el = null;
    NodeList nl = e.getElementsByTagName("seccion");
    for (int i = 0; i<nl.getLength();i++){
        Node n = nl.item(i);
        Element ei = (Element) n;
        if(ei.getAttribute("num").equals("2B")){
            el = ei;
            break;
        }
    }
    return el;
}
private static String getFecha(LocalDate date) {
    
    String fecha = String.valueOf(date.getYear());
    if(date.getMonthValue()<10){
        fecha +=("0"+String.valueOf(date.getMonthValue()));
    } else {
        fecha +=String.valueOf(date.getMonthValue());
    }
    if(date.getDayOfMonth()<10){
        fecha +=("0"+String.valueOf(date.getDayOfMonth()));
    } else {
        fecha +=String.valueOf(date.getDayOfMonth());
    }
    return fecha;
}

    private static void anadirDepartamentoBD(Departamento dep) {
        PreparedStatement st;
        try {
        st = con.prepareStatement("insert into DEPARTAMENTO(ETQ,NOMBRE)values(?,?)");
        
        st.setString(1, dep.getEtq());
        st.setString(2, dep.getNombre());
        st.executeUpdate();
        } catch (SQLException ex) {
            errorDepartamento++;
            System.out.println(dep.getEtq());
        }
    }
    private static void anadirREL(RelDepEpi rel) {
        PreparedStatement st;
        try {
        st = con.prepareStatement("insert into REL_DEP_EPI(NOMBREEP,ETQDEP)values(?,?)");
        
        st.setString(1, rel.getEpigrafe().getNombre());
        st.setString(2, rel.getDepartamento().getEtq());
        st.executeUpdate();
        } catch (Exception ex) {
            errorDepartamento++;
            //System.out.println(etq+"\t\t\t\t\t\t\t"+nombre+"AAAAAAAAAAAAAAAA");
        }
    }
    private static void anadirOposicionBD(Oposicion opo) {
        PreparedStatement st;
        try {
        st = con.prepareStatement("insert into OPOSICION(ID,FECHA,CONTROL,NOMBREEP,ETQDEP)values(?,?,?,?,?)");
        st.setString(1, opo.getId());
        st.setDate(2, opo.getFecha());
        st.setString(3, opo.getControl());
        st.setString(4, opo.getRelDepEpi().getEpigrafe().getNombre());
        st.setString(5, opo.getRelDepEpi().getDepartamento().getEtq());
        st.executeUpdate();
        } catch (Exception ex) {
           errorOposicion++;
           //System.out.println(ex.toString()+ "  oposicion");
        }
    }
    private static void anadirEpigrafeBD(Epigrafe epi) {
        PreparedStatement st;
        try {
        st = con.prepareStatement("insert into EPIGRAFE(NOMBRE)values(?)");
        
        st.setString(1, epi.getNombre());
        st.executeUpdate();
        } catch (Exception ex) {
            System.out.println("\t\t\t\t\t\t\t"+epi.getNombre());
        }
    }



    
 }
/* 
import Dominio.Departamento;
import Dominio.Epigrafe;
import Dominio.Oposicion;

import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;


import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

 



public class OpoDB {
  private static int errorEpigrafe,errorDepartamento,errorOposicion;
    static Connection con;
    public static void main(String [] args) throws SQLException{
        errorEpigrafe =0;
        errorDepartamento =0;
        errorOposicion =0;
        con = DriverManager.getConnection("jdbc:derby://localhost:1527/OpoDB", "usuario", "usuario");
        
        mains();
        System.out.println("error Departamento: "+ errorDepartamento+"\n"
                + "error epigrafe: "+ errorEpigrafe+"\n"
                + "error oposicion: "+ errorOposicion);
    }


 
 static void mains(){
     
    LocalDate date = LocalDate.of(2020, 1, 1);
    String fecha ;
    //System.out.println(fecha);
    
    for(int i = 0 ; date.isBefore(LocalDate.now()); i++){
        if(date.getMonthValue()==2&&date.getDayOfMonth()==5){
            System.out.println("Feliz Cumple maricon");
        }
        fecha = getFecha(date);
        getBoe("https://boe.es/diario_boe/xml.php?id=BOE-S-"+ fecha,fecha);
        date =date.plusDays(1);
    }
 }
 
 public  static void getBoe(String url,String fecha){
    try{
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        Document doc = db.parse(new URL(url).openStream());
        //System.out.println(fecha);
        
        Element e = getElementoUnico("diario", doc.getDocumentElement(),0);
        Element el = getSeccionOposiciones(e);
        if(el==null){
            throw new NullPointerException( "No hay oposiciones en el BOE" );
        }
        NodeList departamentos = el.getElementsByTagName("departamento");
        for(int i = 0 ; i<departamentos.getLength();i++){
            Node ni = departamentos.item(i);
            Element ele = (Element)ni;
            Departamento dep = new Departamento(ele.getAttribute("nombre"),ele.getAttribute("etq"));
            
            anadirDepartamentoBD(dep);
            
            NodeList epigrafes = ele.getElementsByTagName("epigrafe");
            Epigrafe epi = new Epigrafe();
            //System.out.println("\t"+ele.getAttribute("nombre"));
            for(int j = 0; j<epigrafes.getLength();j++){
                Epigrafe epig;
                
                Node nj = epigrafes.item(j);
                Element epigrafe = (Element) nj;
                NodeList items = epigrafe.getElementsByTagName("item");
                //System.out.println("\t\t"+epigrafe.getAttribute("nombre"));
                epig = new Epigrafe(epigrafe.getAttribute("nombre"),dep.getEtq());
                anadirEpigrafeBD(epig);
                for(int y = 0 ; y <items.getLength(); y++){
                    Node ny = items.item(y);
                    Element item = (Element) ny;
                    Oposicion opo = new Oposicion(item.getAttribute("id"),fecha,item.getAttribute("control"),epig.getNombre());
                    anadirOposicionBD(opo);
                    //System.out.println("\t\t\t"+item.getAttribute("id"));
                }
                
            }
        }
        
        
    } catch(Exception e){
       // System.out.println(e.toString()+ "  total");
    }
    
}
public static Element getElementoUnico(String a, Element el,int item){
    NodeList nl = el.getElementsByTagName(a);
    Node n = nl.item(item);
    Element e = (Element) n;
    return e;
}

public static void getOposicion(String urlParte){
    String url = "boe.es"+urlParte;
}
public static Element getSeccionOposiciones(Element e){
    Element el = null;
    NodeList nl = e.getElementsByTagName("seccion");
    for (int i = 0; i<nl.getLength();i++){
        Node n = nl.item(i);
        Element ei = (Element) n;
        if(ei.getAttribute("num").equals("2B")){
            el = ei;
            break;
        }
    }
    return el;
}
private static String getFecha(LocalDate date) {
    
    String fecha = String.valueOf(date.getYear());
    if(date.getMonthValue()<10){
        fecha +=("0"+String.valueOf(date.getMonthValue()));
    } else {
        fecha +=String.valueOf(date.getMonthValue());
    }
    if(date.getDayOfMonth()<10){
        fecha +=("0"+String.valueOf(date.getDayOfMonth()));
    } else {
        fecha +=String.valueOf(date.getDayOfMonth());
    }
    return fecha;
}

    private static void anadirDepartamentoBD(Departamento dep) {
        PreparedStatement st;
        try {
        st = con.prepareStatement("insert into DEPARTAMENTO(ETQ,NOMBRE)values(?,?)");
        
        st.setString(1, dep.getEtq());
        st.setString(2, dep.getNombre());
        st.executeUpdate();
        } catch (SQLException ex) {
            errorDepartamento++;
        }
    }
    private static void anadirOposicionBD(Oposicion opo) {
        PreparedStatement st;
        try {
        st = con.prepareStatement("insert into OPOSICION(ID,FECHA,CONTROL,NOMBREEP)values(?,?,?,?)");
        
        st.setString(1, opo.getId());
        st.setString(2, opo.getFecha());
        st.setString(3, opo.getControl());
        st.setString(4, opo.getNombreep());
        st.executeUpdate();
        } catch (SQLException ex) {
           errorOposicion++;
           System.out.println(ex.toString()+ "  oposicion");
        }
    }
    private static void anadirEpigrafeBD(Epigrafe epi) {
        PreparedStatement st;
        try {
        st = con.prepareStatement("insert into EPIGRAFE(NOMBRE,ETQDEP)values(?,?)");
        
        st.setString(1, epi.getNombre());
        st.setString(2, epi.getEtqdep());
        st.executeUpdate();
        } catch (Exception ex) {
           errorEpigrafe++;
        }
    }



    
 }*/