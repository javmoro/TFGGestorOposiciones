import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse, HttpHeaders } from '@angular/common/http';
import { Oposicion,Epigrafe, Departamento, RelDepEpi } from './app.model';

import { BehaviorSubject, Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ClienteApiRestService {
  private headersEmpty = new HttpHeaders({
    'Content-Type': 'application/json',
    Authorization: ''
  })

  private headers = new BehaviorSubject(this.headersEmpty);

  headersObs = this.headers.asObservable();
  
  private static readonly BASE_URI = 'http://localhost:8080/GestorOposiciones/webresources';
  constructor(private http: HttpClient) { } // inyectamos el modulo HttpClientModule

  getAllOposiciones() : Observable<HttpResponse<Oposicion[]>>{
    let url = ClienteApiRestService.BASE_URI + '/oposicion';
    return this.http.get<Oposicion[]>(url, {observe: 'response', headers: this.headers.value});
  }
  getOposiciones(pagina: Number): Observable<HttpResponse<Oposicion[]>>{
    console.log('get oposiciones'+pagina);
    let url = ClienteApiRestService.BASE_URI + '/oposicion?page='+pagina;
    return this.http.get<Oposicion[]>(url, {observe: 'response', headers: this.headers.value});
  }

  
  getOposicionesByFecha(fecha:String,pagina:Number):Observable<HttpResponse<Oposicion[]>>{
    console.log('get oposiciones'+pagina);
    let url = ClienteApiRestService.BASE_URI + '/oposicion?fecha='+fecha+'&page='+pagina;
    return this.http.get<Oposicion[]>(url, {observe: 'response', headers: this.headers.value});
  }
  getOposicion(id: String): Observable<HttpResponse<Oposicion>>{
    let url = ClienteApiRestService.BASE_URI + '/oposicion/'+id;
    return this.http.get<Oposicion>(url, {observe : 'response', headers: this.headers.value});
  }
  getSizeOposicion(): Observable<HttpResponse<number>>{
    let url =  ClienteApiRestService.BASE_URI + '/oposicion/count';
    return this.http.get<number>(url, {observe : 'response',headers: this.headers.value});
  }
  getAllDepartamentos() : Observable<HttpResponse<Departamento[]>>{
    let url = ClienteApiRestService.BASE_URI + '/departamento';
    return this.http.get<Departamento[]>(url, {observe: 'response', headers: this.headers.value});
  }
  getDepartamento(etqdep: String): Observable<HttpResponse<Departamento>>{
    let url = ClienteApiRestService.BASE_URI + '/departamento/'+etqdep;
    return this.http.get<Departamento>(url, {observe : 'response', headers: this.headers.value});
  }
  getDepartamentos(pagina:Number) : Observable<HttpResponse<Departamento[]>>{
    let url = ClienteApiRestService.BASE_URI + '/departamento?page='+pagina;
    return this.http.get<Departamento[]>(url, {observe: 'response', headers: this.headers.value});
  }
  getAllEpigrafes() : Observable<HttpResponse<Epigrafe[]>>{
    let url = ClienteApiRestService.BASE_URI + '/epigrafe';
    return this.http.get<Epigrafe[]>(url, {observe: 'response', headers: this.headers.value});
  }
  getEpigrafes(pagina:Number) : Observable<HttpResponse<Epigrafe[]>>{
    let url = ClienteApiRestService.BASE_URI + '/epigrafe?page='+pagina;
    return this.http.get<Epigrafe[]>(url, {observe: 'response', headers: this.headers.value});
  }

  getEpigrafe(nombreep: String): Observable<HttpResponse<Epigrafe>>{
    let url = ClienteApiRestService.BASE_URI + '/epigrafe/'+nombreep;
    return this.http.get<Epigrafe>(url, {observe : 'response', headers: this.headers.value});
  }
  getEpigrafesFromDep(etqdep:String, pagina:Number):Observable<HttpResponse<RelDepEpi[]>>{
    let url = ClienteApiRestService.BASE_URI + '/departamento/'+etqdep+'/epigrafes?page='+pagina;
    return this.http.get<RelDepEpi[]>(url, {observe : 'response', headers: this.headers.value});
  }
  getDepartamentosFromEp(nombreep:String,pagina:Number):Observable<HttpResponse<RelDepEpi[]>>{
    let url = ClienteApiRestService.BASE_URI + '/epigrafe/'+nombreep+'/departamentos?page='+pagina;
    return this.http.get<RelDepEpi[]>(url, {observe : 'response', headers: this.headers.value});
  }
  getOposicionesFrom(etqdep:String,nombreep:String,pagina:Number):Observable<HttpResponse<Oposicion[]>>{
    let url = ClienteApiRestService.BASE_URI + '/epigrafe/'+nombreep+'/departamentos/'+etqdep+'/oposiciones?page='+pagina;
    return this.http.get<Oposicion[]>(url, {observe : 'response', headers: this.headers.value});
  }
  getOposicionesFromByFecha(etqdep:String,nombreep:String,fecha:String,pagina:Number):Observable<HttpResponse<Oposicion[]>>{
    console.log('get oposiciones'+pagina);
    let url = ClienteApiRestService.BASE_URI + '/epigrafe/'+nombreep+'/departamentos/'+etqdep+'/oposiciones?fecha='+fecha+'&page='+pagina;
    return this.http.get<Oposicion[]>(url, {observe: 'response', headers: this.headers.value});
  }
  busquedaOposicionesByWord(busqueda:String,pagina:Number):Observable<HttpResponse<Oposicion[]>>{
    console.log('/oposicion/search/'+busqueda+'&page='+pagina);

    let url = ClienteApiRestService.BASE_URI + '/oposicion/search/'+busqueda+'?page='+pagina;
    return this.http.get<Oposicion[]>(url, {observe: 'response', headers: this.headers.value});
  }
}