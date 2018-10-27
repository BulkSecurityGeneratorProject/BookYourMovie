/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { BookYourMovieTestModule } from '../../../test.module';
import { SeatTypeComponent } from 'app/entities/seat-type/seat-type.component';
import { SeatTypeService } from 'app/entities/seat-type/seat-type.service';
import { SeatType } from 'app/shared/model/seat-type.model';

describe('Component Tests', () => {
    describe('SeatType Management Component', () => {
        let comp: SeatTypeComponent;
        let fixture: ComponentFixture<SeatTypeComponent>;
        let service: SeatTypeService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [BookYourMovieTestModule],
                declarations: [SeatTypeComponent],
                providers: []
            })
                .overrideTemplate(SeatTypeComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(SeatTypeComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(SeatTypeService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new SeatType(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.seatTypes[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
