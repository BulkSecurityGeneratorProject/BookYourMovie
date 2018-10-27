/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { BookYourMovieTestModule } from '../../../test.module';
import { ShowComponent } from 'app/entities/show/show.component';
import { ShowService } from 'app/entities/show/show.service';
import { Show } from 'app/shared/model/show.model';

describe('Component Tests', () => {
    describe('Show Management Component', () => {
        let comp: ShowComponent;
        let fixture: ComponentFixture<ShowComponent>;
        let service: ShowService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [BookYourMovieTestModule],
                declarations: [ShowComponent],
                providers: []
            })
                .overrideTemplate(ShowComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(ShowComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ShowService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new Show(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.shows[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
