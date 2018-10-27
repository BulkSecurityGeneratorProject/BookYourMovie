/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { BookYourMovieTestModule } from '../../../test.module';
import { ScreenComponent } from 'app/entities/screen/screen.component';
import { ScreenService } from 'app/entities/screen/screen.service';
import { Screen } from 'app/shared/model/screen.model';

describe('Component Tests', () => {
    describe('Screen Management Component', () => {
        let comp: ScreenComponent;
        let fixture: ComponentFixture<ScreenComponent>;
        let service: ScreenService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [BookYourMovieTestModule],
                declarations: [ScreenComponent],
                providers: []
            })
                .overrideTemplate(ScreenComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(ScreenComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ScreenService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new Screen(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.screens[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
