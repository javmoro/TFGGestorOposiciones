import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { ClienteApiRestService } from '../shared/cliente-api-rest.service';
import { DataService } from '../shared/data.service';
import { FormsModule } from '@angular/forms';
import { Oposicion,Departamento,RelDepEpi,RelDepEpiPK, Mensaje, Tipo,} from '../shared/app.model';

@Component({
  selector: 'app-oposiciones',
  templateUrl: './oposiciones.component.html',
  styleUrls: ['./oposiciones.component.scss'],
})
export class OposicionesComponent implements OnInit {
  myDate: String;
  oposiciones: Oposicion[];
  oposicion: Oposicion;
  href:String;
  idEp: String;
  idDep: String;
  fecha: String;
  count: number;
  pagina:number;
  search: String;
  constructor( private router: Router,private ruta: ActivatedRoute,private clienteApiRestService:ClienteApiRestService, private datos: DataService) {
    this.oposiciones = [];
    this.pagina = 0;
    this.href = this.router.url;
  }
  ngOnInit() {
    this.ruta.paramMap.subscribe(
      params => {
        this.search = params.get('search');
        this.idEp = params.get('idEpi');
        this.idDep = params.get('idDep');
        this.fecha = params.get('fecha');
        if(this.idEp!=null&&this.idDep!=null){
          if(this.fecha!=null){
            this.getOposicionesByFrom();
          }else
            this.getOposicionesFrom();
          
        }else{
          console.log("aaaaaaaa");
          if(this.fecha!=null){
            this.getOposicionesFecha();
          }else
            if(this.search!=null)
              this.busquedaOposiciones();
              else{
                this.getOposiciones();
              }
        }
      },
      err => console.log("Error al leer id para editar: " + err)
    )
  }
  busquedaOposiciones(){
    console.log("busqueda");
    this.clienteApiRestService.busquedaOposicionesByWord(this.search,this.pagina).subscribe(
      resp=>{
        if (resp.status < 400) {
          this.pagina++;

          this.oposiciones.push( ...resp.body);
        }
        else{
          this.datos.cambiarMensaje(new Mensaje("Error al acceder a las oposiciones"));  
        }
      },
      err=>{
        this.datos.cambiarMensaje(new Mensaje("Error al acceder a las oposiciones"));
        console.log("Error al traer la lista"+err.message)
        throw err;
      }
    )
  }
  buscarFecha(){
    console.log("secamiob"+this.myDate);
    var date = new Date(this.myDate+'');
    
    var fecha = date.getFullYear()+"-"+(date.getUTCMonth()+1)+"-"+date.getUTCDate();
    if(this.fecha!=null){
      this.href = this.href.substring(0,this.href.lastIndexOf("/fecha/"));
    }
    
    this.router.navigateByUrl(this.href+'/fecha/'+fecha);
  }
  getOposicionesFecha(){
    console.log("aaaa");
    this.clienteApiRestService.getOposicionesByFecha(this.fecha,this.pagina).subscribe(
      resp=>{
        if (resp.status < 400) {
          this.pagina++;

          this.oposiciones.push( ...resp.body);
        }
        else{
          this.datos.cambiarMensaje(new Mensaje("Error al acceder a las oposiciones"));  
        }
      },
      err=>{
        this.datos.cambiarMensaje(new Mensaje("Error al acceder a las oposiciones"));
        console.log("Error al traer la lista"+err.message)
        throw err;
      }
    )
  }
  getCount(num: number){
    this.clienteApiRestService.getSizeOposicion().subscribe(
      resp=>{
        if (resp.status < 400) {
          this.count =Math.ceil(resp.body/ num);
        }
        else{
          this.datos.cambiarMensaje(new Mensaje("Error al acceder a las oposiciones"));  
        }
      },
      err=>{
        this.datos.cambiarMensaje(new Mensaje("Error al acceder a las oposiciones"));
        console.log("Error al traer la lista"+err.message)
        throw err;
      }
    )
  }
  getOposicionesFrom(){
    this.clienteApiRestService.getOposicionesFrom(this.idDep,this.idEp,this.pagina).subscribe(
      resp=>{
        if (resp.status < 400) {
          this.pagina++;
          
          this.oposiciones.push( ...resp.body);
        }
        else{
          this.datos.cambiarMensaje(new Mensaje("Error al acceder a las oposiciones"));  
        }
      },
      err=>{
        this.datos.cambiarMensaje(new Mensaje("Error al acceder a las oposiciones"));
        console.log("Error al traer la lista"+err.message)
        throw err;
      }
    )
  }
  
  getOposicionesByFrom(){
    this.clienteApiRestService.getOposicionesFromByFecha(this.idDep,this.idEp,this.fecha,this.pagina).subscribe(
      resp=>{
        if (resp.status < 400) {
          this.pagina++;
          
          this.oposiciones.push( ...resp.body);
        }
        else{
          this.datos.cambiarMensaje(new Mensaje("Error al acceder a las oposiciones"));  
        }
      },
      err=>{
        this.datos.cambiarMensaje(new Mensaje("Error al acceder a las oposiciones"));
        console.log("Error al traer la lista"+err.message)
        throw err;
      }
    )
  }
  getOposicion(num: String){
    this.clienteApiRestService.getOposicion(num).subscribe(
      resp=>{
        if (resp.status < 400) {
          this.oposicion = resp.body ;
        }
        else{
          this.datos.cambiarMensaje(new Mensaje("Error al acceder a las oposiciones"));  
        }
      },
      err=>{
        this.datos.cambiarMensaje(new Mensaje("Error al acceder a las oposiciones"));
        console.log("Error al traer la lista"+err.message)
        throw err;
      }
    )
  }

  cargarMasOposiciones(event){
    console.log('Cargando siguientes..')
    setTimeout(()=>{
      if(this.idEp!=null&&this.idDep!=null){
        if(this.fecha!=null){
          this.getOposicionesByFrom();
        }else
          this.getOposicionesFrom();
        
      }else{
        console.log("aaaaaaaa");
        if(this.fecha!=null){
          this.getOposicionesFecha();
        }else
          if(this.search!=null)
            this.busquedaOposiciones();
            else{
              this.getOposiciones();
            }
      }
      event.target.complete();
    },1000);
  }

  getOposiciones(){
    
    this.clienteApiRestService.getOposiciones(this.pagina).subscribe(
      resp=>{
        if (resp.status < 400) {
          this.pagina++;
          
          this.oposiciones.push( ...resp.body);
          
          // ||[];a
        }
        else{
          this.datos.cambiarMensaje(new Mensaje("Error al acceder a las oposiciones"));  
        }
      },
      err=>{
        this.datos.cambiarMensaje(new Mensaje("Error al acceder a las oposiciones"));
        console.log("Error al traer la lista"+err.message)
        throw err;
      }
    )
  }
}
