import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { BookYourMovieSharedModule } from 'app/shared';
import {
    ScreenComponent,
    ScreenDetailComponent,
    ScreenUpdateComponent,
    ScreenDeletePopupComponent,
    ScreenDeleteDialogComponent,
    screenRoute,
    screenPopupRoute
} from './';

const ENTITY_STATES = [...screenRoute, ...screenPopupRoute];

@NgModule({
    imports: [BookYourMovieSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [ScreenComponent, ScreenDetailComponent, ScreenUpdateComponent, ScreenDeleteDialogComponent, ScreenDeletePopupComponent],
    entryComponents: [ScreenComponent, ScreenUpdateComponent, ScreenDeleteDialogComponent, ScreenDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class BookYourMovieScreenModule {}
