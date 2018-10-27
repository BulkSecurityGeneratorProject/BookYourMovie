import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { BookYourMovieSharedModule } from 'app/shared';
import {
    SeatTypeComponent,
    SeatTypeDetailComponent,
    SeatTypeUpdateComponent,
    SeatTypeDeletePopupComponent,
    SeatTypeDeleteDialogComponent,
    seatTypeRoute,
    seatTypePopupRoute
} from './';

const ENTITY_STATES = [...seatTypeRoute, ...seatTypePopupRoute];

@NgModule({
    imports: [BookYourMovieSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        SeatTypeComponent,
        SeatTypeDetailComponent,
        SeatTypeUpdateComponent,
        SeatTypeDeleteDialogComponent,
        SeatTypeDeletePopupComponent
    ],
    entryComponents: [SeatTypeComponent, SeatTypeUpdateComponent, SeatTypeDeleteDialogComponent, SeatTypeDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class BookYourMovieSeatTypeModule {}
