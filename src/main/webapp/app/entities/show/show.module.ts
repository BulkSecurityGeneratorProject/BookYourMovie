import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { BookYourMovieSharedModule } from 'app/shared';
import {
    ShowComponent,
    ShowDetailComponent,
    ShowUpdateComponent,
    ShowDeletePopupComponent,
    ShowDeleteDialogComponent,
    showRoute,
    showPopupRoute
} from './';

const ENTITY_STATES = [...showRoute, ...showPopupRoute];

@NgModule({
    imports: [BookYourMovieSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [ShowComponent, ShowDetailComponent, ShowUpdateComponent, ShowDeleteDialogComponent, ShowDeletePopupComponent],
    entryComponents: [ShowComponent, ShowUpdateComponent, ShowDeleteDialogComponent, ShowDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class BookYourMovieShowModule {}
