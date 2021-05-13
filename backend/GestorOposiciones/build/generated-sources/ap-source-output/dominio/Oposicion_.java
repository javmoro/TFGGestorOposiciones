package dominio;

import dominio.RelDepEpi;
import java.sql.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2021-05-08T19:07:45")
@StaticMetamodel(Oposicion.class)
public class Oposicion_ { 

    public static volatile SingularAttribute<Oposicion, Date> fecha;
    public static volatile SingularAttribute<Oposicion, String> urlpdf;
    public static volatile SingularAttribute<Oposicion, RelDepEpi> relDepEpi;
    public static volatile SingularAttribute<Oposicion, String> urlxml;
    public static volatile SingularAttribute<Oposicion, String> titulo;
    public static volatile SingularAttribute<Oposicion, String> control;
    public static volatile SingularAttribute<Oposicion, String> id;

}