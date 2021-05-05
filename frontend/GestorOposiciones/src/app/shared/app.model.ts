export interface Departamento{
    etq:String,
    nombre:String
}
export interface Epigrafe{
    nombre:String
}
export interface RelDepEpiPK{
    etqdep:String,
    nombreep:String
}
export interface RelDepEpi{
    departamento:Departamento,
    epigrafe:Epigrafe,
    relDepEpiPK: RelDepEpiPK
}

export interface Oposicion {
    control: String,
    fecha: String,
    id: String,
    relDepEpi: RelDepEpi
}


export enum Tipo{
    SUCCESS = "success",
    ERROR = "danger",
    WARNING= "warning",
    INFO="info"
}
export class Mensaje{
    readonly texto: String;
    readonly type : Tipo;
    readonly mostrar : boolean;
    constructor(mensaje?:String, type?:Tipo, mostrar?:boolean){
        this.texto = mensaje || "";
        this.type = type || Tipo.INFO;
        this.mostrar = mostrar || false;
    }
}