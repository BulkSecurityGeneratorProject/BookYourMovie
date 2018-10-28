import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { BookYourMovieSharedModule } from 'app/shared';
import { BookYourMovieAdminModule } from 'app/admin/admin.module';
import {
    TheatreComponent,
    TheatreDetailComponent,
    TheatreUpdateComponent,
    TheatreDeletePopupComponent,
    TheatreDeleteDialogComponent,
    theatreRoute,
    theatrePopupRoute
} from './';

const ENTITY_STATES = [...theatreRoute, ...theatrePopupRoute];

@NgModule({
    imports: [BookYourMovieSharedModule, BookYourMovieAdminModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        TheatreComponent,
        TheatreDetailComponent,
        TheatreUpdateComponent,
        TheatreDeleteDialogComponent,
        TheatreDeletePopupComponent
    ],
    entryComponents: [TheatreComponent, TheatreUpdateComponent, TheatreDeleteDialogComponent, TheatreDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class BookYourMovieTheatreModule {}
