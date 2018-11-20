import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { BookYourMovieCityModule } from './city/city.module';
import { BookYourMovieTheatreModule } from './theatre/theatre.module';
import { BookYourMovieBookingModule } from './booking/booking.module';
import { BookYourMovieScreenModule } from './screen/screen.module';
import { BookYourMovieMovieModule } from './movie/movie.module';
import { BookYourMovieSeatTypeModule } from './seat-type/seat-type.module';
import { BookYourMovieSeatModule } from './seat/seat.module';
import { BookYourMovieShowModule } from './show/show.module';
import { BookYourMovieRowModule } from './row/row.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    // prettier-ignore
    imports: [
        BookYourMovieCityModule,
        BookYourMovieTheatreModule,
        BookYourMovieBookingModule,
        BookYourMovieScreenModule,
        BookYourMovieMovieModule,
        BookYourMovieSeatTypeModule,
        BookYourMovieSeatModule,
        BookYourMovieShowModule,
        BookYourMovieRowModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class BookYourMovieEntityModule {}
