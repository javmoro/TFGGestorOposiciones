package dominio;

import dominio.Departamento;
import dominio.Epigrafe;
import dominio.Oposicion;
import dominio.RelDepEpiPK;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2021-03-14T23:07:38")
@StaticMetamodel(RelDepEpi.class)
public class RelDepEpi_ { 

    public static volatile CollectionAttribute<RelDepEpi, Oposicion> oposicionCollection;
    public static volatile SingularAttribute<RelDepEpi, RelDepEpiPK> relDepEpiPK;
    public static volatile SingularAttribute<RelDepEpi, Departamento> departamento;
    public static volatile SingularAttribute<RelDepEpi, Epigrafe> epigrafe;

}