package Dominio;

import Dominio.Oposicion;
import Dominio.ReferenciaAnteriorPK;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2021-06-21T12:12:38")
@StaticMetamodel(ReferenciaAnterior.class)
public class ReferenciaAnterior_ { 

    public static volatile SingularAttribute<ReferenciaAnterior, String> texto;
    public static volatile SingularAttribute<ReferenciaAnterior, Oposicion> oposicion1;
    public static volatile SingularAttribute<ReferenciaAnterior, String> palabra;
    public static volatile SingularAttribute<ReferenciaAnterior, ReferenciaAnteriorPK> referenciaAnteriorPK;
    public static volatile SingularAttribute<ReferenciaAnterior, Oposicion> oposicion;

}