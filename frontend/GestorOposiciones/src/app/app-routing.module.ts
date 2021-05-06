import { NgModule } from '@angular/core';
import { PreloadAllModules, RouterModule, Routes } from '@angular/router';
import { OposicionesComponent } from './oposiciones/oposiciones.component';
import { DepartamentosComponent} from './departamentos/departamentos.component';
import { EpigrafesComponent} from './epigrafes/epigrafes.component';
import { DetalleOposicionComponent } from './detalle-oposicion/detalle-oposicion.component'
const routes: Routes = [
  {path: 'oposiciones', component:OposicionesComponent},
  {path: 'oposiciones/:idOp', component:DetalleOposicionComponent},
  {path: 'epigrafes/:idEpi/departamentos/:idDep/oposiciones', component:OposicionesComponent},
  {path: 'departamentos/:idDep/epigrafes/:idEpi/oposiciones',component:OposicionesComponent},
  {path: 'departamentos', component:DepartamentosComponent},
  {path: 'epigrafes', component:EpigrafesComponent},
  {path: 'departamentos/:idDep/epigrafes',component: EpigrafesComponent},
  {path: 'epigrafes/:idEpi/departamentos', component: DepartamentosComponent},
  {path: '**', redirectTo:'oposiciones', pathMatch:'full'}
  
  
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
