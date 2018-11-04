import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { ownerRoute } from './';

const routes: Routes = [ownerRoute];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class OwnerRoutingModule { }
