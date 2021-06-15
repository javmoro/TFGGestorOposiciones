import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';
import { IonicModule } from '@ionic/angular';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { OposicionesComponent } from './oposiciones.component';
describe('OposicionesComponent', () => {
  let component: OposicionesComponent;
  let fixture: ComponentFixture<OposicionesComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      
      declarations: [ OposicionesComponent ],
      imports: [IonicModule.forRoot(),RouterTestingModule,HttpClientTestingModule]
    }).compileComponents();

    fixture = TestBed.createComponent(OposicionesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
    component.fechaActual = "";
    component.fecha1 = "";
    component.fecha2 = "";
    component.search = "";
    component.departamento = "";
    component.epigrafe = "";
    component.estado = "Resolución";
    component.pagina = 1;
    
  }));

  it('should create', () => {
    expect(component).toBeTruthy();
  });
/*
  it('getOposiciones', () => {
    expect(component).toBeTruthy();
    
    expect(component.oposiciones.length).not.toEqual(0);
  });
  it('getOposicionesFecha', () => {
    expect(component).toBeTruthy();
    //(this.search, this.departamento, this.epigrafe, this.fecha1, this.fecha2, this.estado, this.pagina)
    component.fecha1="2015-06-08";
    var date = new Date();
    date.setFullYear(2015,6,8);
    
    component.getOposicionesBusquedaAvanzada();
    !expect(component.oposiciones[0].id).toEqual("aa");
  });*/
});
