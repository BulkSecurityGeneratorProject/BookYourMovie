import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { BookYourMovieSharedModule } from 'app/shared';
import {
    RowComponent,
    RowDetailComponent,
    RowUpdateComponent,
    RowDeletePopupComponent,
    RowDeleteDialogComponent,
    rowRoute,
    rowPopupRoute
} from './';

const ENTITY_STATES = [...rowRoute, ...rowPopupRoute];

@NgModule({
    imports: [BookYourMovieSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [RowComponent, RowDetailComponent, RowUpdateComponent, RowDeleteDialogComponent, RowDeletePopupComponent],
    entryComponents: [RowComponent, RowUpdateComponent, RowDeleteDialogComponent, RowDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class BookYourMovieRowModule {}
