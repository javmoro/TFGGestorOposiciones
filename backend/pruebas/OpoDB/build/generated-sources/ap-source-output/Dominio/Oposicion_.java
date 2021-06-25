package Dominio;

import Dominio.ReferenciaAnterior;
import Dominio.RelDepEpi;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2021-06-21T12:12:38")
@StaticMetamodel(Oposicion.class)
public class Oposicion_ { 

    public static volatile SingularAttribute<Oposicion, Date> fecha;
    public static volatile SingularAttribute<Oposicion, String> estado;
    public static volatile SingularAttribute<Oposicion, String> urlpdf;
    public static volatile SingularAttribute<Oposicion, RelDepEpi> relDepEpi;
    public static volatile CollectionAttribute<Oposicion, ReferenciaAnterior> referenciaAnteriorCollection;
    public static volatile SingularAttribute<Oposicion, String> urlxml;
    public static volatile SingularAttribute<Oposicion, String> titulo;
    public static volatile SingularAttribute<Oposicion, String> control;
    public static volatile SingularAttribute<Oposicion, String> id;
    public static volatile CollectionAttribute<Oposicion, ReferenciaAnterior> referenciaAnteriorCollection1;

}