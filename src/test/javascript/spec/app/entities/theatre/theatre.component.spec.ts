/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { BookYourMovieTestModule } from '../../../test.module';
import { TheatreComponent } from 'app/entities/theatre/theatre.component';
import { TheatreService } from 'app/entities/theatre/theatre.service';
import { Theatre } from 'app/shared/model/theatre.model';

describe('Component Tests', () => {
    describe('Theatre Management Component', () => {
        let comp: TheatreComponent;
        let fixture: ComponentFixture<TheatreComponent>;
        let service: TheatreService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [BookYourMovieTestModule],
                declarations: [TheatreComponent],
                providers: []
            })
                .overrideTemplate(TheatreComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(TheatreComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(TheatreService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new Theatre(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.theatres[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
