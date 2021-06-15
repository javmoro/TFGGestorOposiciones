import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { ClienteApiRestService } from '../shared/cliente-api-rest.service';
import { DataService } from '../shared/data.service';
import { Oposicion, Departamento, RelDepEpi, RelDepEpiPK, Mensaje, Tipo, } from '../shared/app.model';
import { ModalController } from '@ionic/angular';
import { DetalleOposicionComponent } from 'src/app/componentes/detalle-oposicion/detalle-oposicion.component';
import { GlobalConstants } from 'src/app/shared/global-constants';
@Component({
  selector: 'app-oposiciones',
  templateUrl: './oposiciones.component.html',
  styleUrls: ['./oposiciones.component.scss'],
})
export class OposicionesComponent implements OnInit {
  fecha1: String;
  fecha2: String;
  departamento: String;
  epigrafe: String;
  estado: String;

  oposiciones: Oposicion[];
  fechaActual: String;
  href: String;

  idEp: String;
  idDep: String;
  fecha: String;

  pagina: number;
  search: String;
  constructor(private modalCtrl: ModalController, private router: Router, private ruta: ActivatedRoute, private clienteApiRestService: ClienteApiRestService, private datos: DataService) {
    this.oposiciones = [];
    this.fechaActual = "";
    this.fecha1 = "";
    this.fecha2 = "";
    this.departamento = "";
    this.epigrafe = "";
    this.estado = "Resolución";
    this.pagina = 1;
    this.href = this.router.url;
  }

  ngOnInit() {
    var dateActual = new Date();
    this.fechaActual = dateActual.toISOString().substr(0, 10);
    this.ruta.paramMap.subscribe(
      params => {
        this.pagina = 1;
        this.idEp = params.get('idEpi');
        this.idDep = params.get('idDep');
        this.fecha = params.get('fecha');
      },
      err => console.log("Error al leer id para editar: " + err)
    );
    this.ruta.queryParams
      .subscribe(params => {
        this.resetear();
        console.log(params);
        this.search = params.busqueda;
        this.fecha1 = params.desde;
        this.fecha2 = params.hasta;
        this.epigrafe = params.epigrafe;
        this.departamento = params.departamento;
        this.estado = params.estado;
        this.cargarOposiciones();

      }
      );
  }
  async _abrirDetalle(id: string, titulo: string, nombreEp: string, nombreDep: string, urlPdf: string) {
    const modal = await this.modalCtrl.create({
      component: DetalleOposicionComponent,
      componentProps: {
        "id": id,
        "titulo": titulo,
        "nombreEp": nombreEp,
        "nombreDep": nombreDep,
        "urlPdf": urlPdf
      }
    })
    return await modal.present();

  }
  cargarOposiciones() {
    if (this.idDep != null || this.idEp != null) {
      this.getOposicionesParEpiDep()
    }
    else {
      if (this.search != null || this.epigrafe != null || this.departamento != null || this.fecha1 != null || this.fecha2 != null || this.estado != null) {
        this.getOposicionesBusquedaAvanzada();
      } else {
        this.getOposiciones();

      }
    }
  }

  getOposiciones() {
    console.log(this.oposiciones.length+"AAAAAAAAAAAAAAAAAA");

    this.clienteApiRestService.getOposiciones(this.pagina).subscribe(
      resp => {
        if (resp.status < 400) {
          this.pagina++;

          this.oposiciones.push(...resp.body);
        }
        else {
          console.log("Error al recoger las oposiciones " + resp.status);
        }
      },
      err => {
        console.log("Error al recoger las oposiciones" + err.message)
        throw err;
      }
    )
  }

  getOposicionesBusquedaAvanzada() {
    this.clienteApiRestService.busquedaAvanzada(this.search, this.departamento, this.epigrafe, this.fecha1, this.fecha2, this.estado, this.pagina).subscribe(
      resp => {
        if (resp.status < 400) {
          this.pagina++;
          this.oposiciones.push(...resp.body);
        }
        else {
          console.log("Error al recoger las oposiciones busqueda" + resp.status);
        }
      },
      err => {
        console.log("Error al recoger las oposiciones busqueda" + err.message)
        throw err;
      }
    )
  }

  getOposicionesParEpiDep() {
    this.clienteApiRestService.getOposicionesFrom(this.idDep, this.idEp, this.pagina).subscribe(
      resp => {
        if (resp.status < 400) {
          this.pagina++;
          this.oposiciones.push(...resp.body);
        }
        else {
          console.log("Error al recoger las oposiciones par Epigrafe Departamento" + resp.status);
        }
      },
      err => {
        console.log("Error al recoger las oposiciones par Epigrafe Departamento" + err.message)
        throw err;
      }
    )
  }

  cargarMasOposiciones(event) {
    console.log('Cargando siguientes..')
    setTimeout(() => {
      this.cargarOposiciones();
      event.target.complete();
    }, 1000);
  }

  getFecha(fecha: String): String {
    if (fecha == null)
      return "";
    var f = fecha.substring(0, 10)
    return f;
  }

  resetear() {
    console.log("resetear");

    this.oposiciones = [];
    this.fecha1 = "";
    this.fecha2 = "";
    this.search = "";
    this.departamento = "";
    this.epigrafe = "";
    this.estado = "Resolución";
    this.pagina = 1;
    this.href = this.router.url;
  }

  buscar() {
    var busc = this.search;
    if (this.departamento == null) {
      this.departamento = "";
    }
    if (this.estado == null) {
      this.estado = "";
    }
    if (this.epigrafe == null) {
      this.epigrafe = "";
    }
    if (busc == null)
      busc = "";
    var f1 = this.getFecha(this.fecha1);
    var f2 = this.getFecha(this.fecha2);
    this.href = this.href.substring(0, this.href.lastIndexOf("/oposiciones/"));
    this.router.navigateByUrl(this.href + '/oposiciones/search?busqueda=' + busc + '&departamento=' + this.departamento + '&epigrafe=' + this.epigrafe + '&desde=' + f1 + '&hasta=' + f2 + '&estado=' + this.estado);
  }

  buscarEvent(q: string) {
    var busc = q;
    if (q == null)
      busc = ""
    var f1 = this.getFecha(this.fecha1);
    var f2 = this.getFecha(this.fecha2);

    //this.resetear();
    console.log(f1);
    this.href = this.href.substring(0, this.href.lastIndexOf("/oposiciones/"));
    this.router.navigateByUrl(this.href + '/oposiciones/search?busqueda=' + busc + '&desde=' + f1 + '&hasta=' + f2);
  }

  
}
