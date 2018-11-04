import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { OwnerRoutingModule } from './owner-routing.module';
import { OwnerComponent } from './owner/owner.component';

@NgModule({
  imports: [
    CommonModule,
    OwnerRoutingModule
  ],
  declarations: [OwnerComponent]
})
export class OwnerModule { }
