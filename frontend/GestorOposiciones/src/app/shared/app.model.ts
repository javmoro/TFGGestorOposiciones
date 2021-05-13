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

export class Oposicion {
    readonly control: String;
    fecha: Date;
    id: String;
    urlxml:String;
    urlpdf:String;
    titulo:String;
    relDepEpi: RelDepEpi;
    constructor(_control? : String, fecha? : String){
        this.id = _control || "";
    }
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