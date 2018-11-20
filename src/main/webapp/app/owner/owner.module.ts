import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { OwnerRoutingModule } from './owner-routing.module';
import { OwnerComponent } from './owner/owner.component';
import { BookYourMovieScreenModule } from './screen/screen.module';

@NgModule({
  imports: [
    CommonModule,
    OwnerRoutingModule,
    BookYourMovieScreenModule
  ],
  declarations: [OwnerComponent]
})
export class OwnerModule { }
