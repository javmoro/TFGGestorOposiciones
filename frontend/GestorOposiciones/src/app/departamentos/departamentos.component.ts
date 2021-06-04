import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Departamento, Mensaje } from '../shared/app.model';
import { ClienteApiRestService } from '../shared/cliente-api-rest.service';
import { DataService } from '../shared/data.service';

@Component({
  selector: 'app-departamentos',
  templateUrl: './departamentos.component.html',
  styleUrls: ['./departamentos.component.scss'],
})
export class DepartamentosComponent implements OnInit {
  departamentos: Departamento[];
  pagina: number;
  search: String;
  href: String;
  constructor(private router: Router, private ruta: ActivatedRoute, private clienteApiRestService: ClienteApiRestService, private datos: DataService) {
    this.departamentos = [];
    this.href = this.router.url;
    this.pagina = 1;
  }
  ngOnInit() {
    this.ruta.paramMap.subscribe(
      params => {
        this.search = params.get('search');

        if (this.search != null)
          this.buscarDepartamentos();
        else {
          this.getDepartamentos();
        }

      },
      err => console.log("Error al leer id para editar: " + err)
    )
  }

  buscarEvent(q: string) {
    this.href = this.href.substring(0, this.href.lastIndexOf("/departamentos/"));

    this.router.navigateByUrl(this.href + '/departamentos/search/' + q);
  }

  cargarMasOposiciones(event) {
    console.log('Cargando siguientes..')
    setTimeout(() => {
      if (this.search == null)
        this.getDepartamentos();
      else {
        this.buscarDepartamentos();
      }
      event.target.complete();
    }, 1000);
  }
  buscarDepartamentos() {
    this.clienteApiRestService.busquedaDepartamentosByWord(this.search, this.pagina).subscribe(
      resp => {
        if (resp.status < 400) {
          this.pagina++;

          this.departamentos.push(...resp.body);

          // ||[];
        }
        else {
          this.datos.cambiarMensaje(new Mensaje("Error al acceder a las oposiciones"));
        }
      },
      err => {
        this.datos.cambiarMensaje(new Mensaje("Error al acceder a las oposiciones"));
        console.log("Error al traer la lista" + err.message)
        throw err;
      }
    )
  }
  getDepartamentos() {

    this.clienteApiRestService.getDepartamentos(this.pagina).subscribe(
      resp => {
        if (resp.status < 400) {
          this.pagina++;

          this.departamentos.push(...resp.body);

          // ||[];
        }
        else {
          this.datos.cambiarMensaje(new Mensaje("Error al acceder a las oposiciones"));
        }
      },
      err => {
        this.datos.cambiarMensaje(new Mensaje("Error al acceder a las oposiciones"));
        console.log("Error al traer la lista" + err.message)
        throw err;
      }
    )
  }
}
