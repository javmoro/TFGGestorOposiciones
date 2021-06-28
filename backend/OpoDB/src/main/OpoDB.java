package main;

import java.text.SimpleDateFormat;

import dominio.Departamento;
import dominio.Epigrafe;
import dominio.Oposicion;
import dominio.RelDepEpi;
import dominio.RelDepEpiPK;
import dominio.ReferenciaAnterior;
import dominio.ReferenciaAnteriorPK;
import java.io.IOException;

import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class OpoDB {

    private static int errorEpigrafe, errorDepartamento, errorOposicion;
    static Connection con;

    public static void main(String[] args) throws SQLException {
        errorEpigrafe = 0;
        errorDepartamento = 0;
        errorOposicion = 0;
        con = DriverManager.getConnection("jdbc:derby://localhost:1527/OpoDB", "usuario", "usuario");
        LocalDate date;
        if(args.length>1){
            date = LocalDate.of(Integer.valueOf(args[0]), Integer.valueOf(args[1]),Integer.valueOf(args[2]));
        }else{
            date = LocalDate.now();
        }
        date = LocalDate.of(2020, 8,24);
        String fecha;
    //System.out.println(fecha);
        
        for (int i = 0; date.isBefore(LocalDate.now()) || date.equals(LocalDate.now()); i++) {
            if (date.getMonthValue() == 2 && date.getDayOfMonth() == 5) {
                //System.out.println("Feliz Cumple maricon");
            }
            fecha = getFecha(date);
            System.out.println("https://boe.es/diario_boe/xml.php?id=BOE-S-" + fecha);
            getBoe("https://boe.es/diario_boe/xml.php?id=BOE-S-" + fecha, fecha);
            date = date.plusDays(1);
        }
        //System.out.println("error Departamento: "+ errorDepartamento+"\n"+ "error epigrafe: "+ errorEpigrafe+"\n"+ "error oposicion: "+ errorOposicion);
    }

    

    public static void getBoe(String url, String fecha) {
        try {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(new URL(url).openStream());
        //System.out.println(fecha);

            Element e = getElementoUnico("diario", doc.getDocumentElement(), 0);
            if (e == null) {
                throw new NullPointerException("Es domingo");
            }
            Element el = getSeccionOposiciones(e);
            if (el == null) {
                throw new NullPointerException("No hay oposiciones en el BOE");
            }
            NodeList departamentos = el.getElementsByTagName("departamento");
            for (int i = 0; i < departamentos.getLength(); i++) {
                Node ni = departamentos.item(i);
                Element ele = (Element) ni;
                Departamento dep = new Departamento(ele.getAttribute("etq"), ele.getAttribute("nombre"));

                anadirDepartamentoBD(dep);

                NodeList epigrafes = ele.getElementsByTagName("epigrafe");
                Epigrafe epi = new Epigrafe();

                //System.out.println("\t"+ele.getAttribute("nombre"));
                for (int j = 0; j < epigrafes.getLength(); j++) {
                    Epigrafe epig;

                    Node nj = epigrafes.item(j);
                    Element epigrafe = (Element) nj;
                    NodeList items = epigrafe.getElementsByTagName("item");
                    //System.out.println("\t\t"+epigrafe.getAttribute("nombre"));
                    epig = new Epigrafe(epigrafe.getAttribute("nombre"));
                    anadirEpigrafeBD(epig);
                    RelDepEpi rel = new RelDepEpi(epig.getNombre(), dep.getEtq());
                    rel.setDepartamento(dep);
                    rel.setEpigrafe(epig);
                    anadirREL(rel);
                    for (int y = 0; y < items.getLength(); y++) {
                        Node ny = items.item(y);
                        Element item = (Element) ny;
                        String titulo = item.getElementsByTagName("titulo").item(0).getTextContent();
                        String xmlurl = "https://boe.es" + item.getElementsByTagName("urlXml").item(0).getTextContent();
                        String pdfurl = "https://boe.es";
                        pdfurl += getPdfURL(xmlurl);

                        Date f = Date.valueOf(Integer.valueOf(fecha.substring(0, 4)) + "-" + Integer.valueOf(fecha.substring(4, 6)) + "-" + Integer.valueOf(fecha.substring(6)));
                        //Date f = new Date(Integer.valueOf(fecha.substring(0, 4)),Integer.valueOf(fecha.substring(4, 6)),Integer.valueOf(fecha.substring(6)));
                        String idOpo = item.getAttribute("id");
                        Oposicion opo = new Oposicion(idOpo, f, item.getAttribute("control"), pdfurl, xmlurl, titulo.toString());

                        opo.setRelDepEpi(rel);

                        ArrayList<ReferenciaAnterior> referenciasAnteriores = getReferencias(idOpo);

                        if (referenciasAnteriores != null) {

                            opo.setEstado("Resolución");
                            if (referenciasAnteriores.size() > 0) {
                                opo.setEstado("Corrección");
                            }

                            anadirOposicionBD(opo);
                            for (int index = 0; index < referenciasAnteriores.size(); index++) {
                                anadirRelacion(referenciasAnteriores.get(index));
                            }
                        }

                        //System.out.println("\t\t\t"+item.getAttribute("isd"));
                    }

                }
            }

        } catch (Exception e) {
            //System.out.println(e.toString());
        }

    }

    public static ArrayList<ReferenciaAnterior> getReferencias(String idOpo) {
        ArrayList referenciasAnteriores = new ArrayList<>();
        try {
            referenciasAnteriores = new ArrayList<>();
            Element eRefAnt;
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(new URL("https://boe.es/diario_boe/xml.php?id=" + idOpo).openStream());
            Element e = getElementoUnico("analisis", doc.getDocumentElement(), 0);
            if (e == null) {
                throw new NullPointerException("Error Analisis");
            }
            e = getElementoUnico("referencias", e, 0);
            eRefAnt = getElementoUnico("anteriores", e, 0);
            Element el = null;
            NodeList nl = eRefAnt.getElementsByTagName("anterior");

            String texto, palabra, idAnterior;
            for (int i = 0; i < nl.getLength(); i++) {
                Node n = nl.item(i);
                Element ei = (Element) n;
                idAnterior = ei.getAttribute("referencia");
                texto = ei.getElementsByTagName("texto").item(0).getTextContent();
                palabra = ei.getElementsByTagName("palabra").item(0).getTextContent();
                referenciasAnteriores.add(new ReferenciaAnterior(idAnterior, idOpo, palabra, texto));
            }
        } catch (Exception E) {
            return null;
        }
        return referenciasAnteriores;
    }

    public static String getPdfURL(String xml) throws ParserConfigurationException, SAXException, IOException {
        String pdfurl = "";
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        Document doc = db.parse(new URL(xml).openStream());
        Element e = doc.getDocumentElement();
        Element metadatos = (Element) e.getElementsByTagName("metadatos").item(0);
        pdfurl = metadatos.getElementsByTagName("url_pdf").item(0).getTextContent();
        return pdfurl;
    }

    public static Element getElementoUnico(String a, Element el, int item) {
        NodeList nl = el.getElementsByTagName(a);
        Node n = nl.item(item);
        Element e = (Element) n;
        return e;
    }

    public static void getOposicion(String urlParte) {
        String url = "boe.es" + urlParte;
    }

    public static Element getSeccionOposiciones(Element e) {
        Element el = null;
        NodeList nl = e.getElementsByTagName("seccion");
        for (int i = 0; i < nl.getLength(); i++) {
            Node n = nl.item(i);
            Element ei = (Element) n;
            if (ei.getAttribute("num").equals("2B")) {
                el = ei;
                break;
            }
        }
        return el;
    }

    private static String getFecha(LocalDate date) {

        String fecha = String.valueOf(date.getYear());
        if (date.getMonthValue() < 10) {
            fecha += ("0" + String.valueOf(date.getMonthValue()));
        } else {
            fecha += String.valueOf(date.getMonthValue());
        }
        if (date.getDayOfMonth() < 10) {
            fecha += ("0" + String.valueOf(date.getDayOfMonth()));
        } else {
            fecha += String.valueOf(date.getDayOfMonth());
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
            //System.out.println(dep.getEtq());
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

        }
    }

    private static void anadirRelacion(ReferenciaAnterior r) {
        PreparedStatement st;
        try {
            st = con.prepareStatement("insert into REFERENCIA_ANTERIOR(ID_REF_ANTERIOR,ID_REF_POSTERIOR,PALABRA, TEXTO)values(?,?,?,?)");
            st.setString(1, r.getReferenciaAnteriorPK().getIdRefAnterior());
            st.setString(2, r.getReferenciaAnteriorPK().getIdRefPosterior());
            st.setString(3, r.getPalabra());
            st.setString(4, r.getTexto());
            String palabra = r.getPalabra();
            st.executeUpdate();

            if (palabra.equals("DEJA SIN EFECTO") || palabra.equals("ANULA") || palabra.equals("APRUEBA")) {
                if (palabra.equals("DEJA SIN EFECTO") || palabra.equals("ANULA")) {
                    palabra = "Cancelado";
                }

                if (palabra.equals("APRUEBA")) {
                    palabra = "Aprobado";
                }
                st = con.prepareStatement("UPDATE OPOSICION SET ESTADO= ? WHERE id = ?");
                st.setString(1, palabra);
                st.setString(2, r.getReferenciaAnteriorPK().getIdRefAnterior());
                st.executeUpdate();
            }
        } catch (Exception ex) {
            errorOposicion++;
            System.out.println(ex.toString() + "  relaciones");
        }
    }

    private static void anadirOposicionBD(Oposicion opo) {
        PreparedStatement st;
        try {
            st = con.prepareStatement("insert into OPOSICION(ID,FECHA,ESTADO,CONTROL,NOMBREEP,ETQDEP,URLPDF,URLXML,TITULO)values(?,?,?,?,?,?,?,?,?)");
            st.setString(1, opo.getId());
            st.setDate(2, (Date) opo.getFecha());
            st.setString(3, opo.getEstado());
            st.setString(4, opo.getControl());
            st.setString(5, opo.getRelDepEpi().getEpigrafe().getNombre());
            st.setString(6, opo.getRelDepEpi().getDepartamento().getEtq());
            st.setString(7, opo.getUrlpdf());
            st.setString(8, opo.getUrlxml());
            st.setString(9, opo.getTitulo());

            st.executeUpdate();
        } catch (Exception ex) {
            errorOposicion++;
            System.out.println(ex.toString() + "  oposicion");
        }
    }

    private static void anadirEpigrafeBD(Epigrafe epi) {
        PreparedStatement st;
        try {
            st = con.prepareStatement("insert into EPIGRAFE(NOMBRE)values(?)");

            st.setString(1, epi.getNombre());
            st.executeUpdate();
        } catch (Exception ex) {
            //System.out.println("\t\t\t\t\t\t\t"+epi.getNombre());
        }
    }

}
