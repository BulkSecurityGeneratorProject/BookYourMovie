import { NgModule } from '@angular/core';

import { BookYourMovieSharedLibsModule, JhiAlertComponent, JhiAlertErrorComponent } from './';

@NgModule({
    imports: [BookYourMovieSharedLibsModule],
    declarations: [JhiAlertComponent, JhiAlertErrorComponent],
    exports: [BookYourMovieSharedLibsModule, JhiAlertComponent, JhiAlertErrorComponent]
})
export class BookYourMovieSharedCommonModule {}
