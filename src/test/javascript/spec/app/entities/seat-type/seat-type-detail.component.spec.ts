/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { BookYourMovieTestModule } from '../../../test.module';
import { SeatTypeDetailComponent } from 'app/entities/seat-type/seat-type-detail.component';
import { SeatType } from 'app/shared/model/seat-type.model';

describe('Component Tests', () => {
    describe('SeatType Management Detail Component', () => {
        let comp: SeatTypeDetailComponent;
        let fixture: ComponentFixture<SeatTypeDetailComponent>;
        const route = ({ data: of({ seatType: new SeatType(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [BookYourMovieTestModule],
                declarations: [SeatTypeDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(SeatTypeDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(SeatTypeDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.seatType).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
