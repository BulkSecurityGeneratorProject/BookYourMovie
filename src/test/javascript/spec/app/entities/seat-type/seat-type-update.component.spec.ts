/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { BookYourMovieTestModule } from '../../../test.module';
import { SeatTypeUpdateComponent } from 'app/entities/seat-type/seat-type-update.component';
import { SeatTypeService } from 'app/entities/seat-type/seat-type.service';
import { SeatType } from 'app/shared/model/seat-type.model';

describe('Component Tests', () => {
    describe('SeatType Management Update Component', () => {
        let comp: SeatTypeUpdateComponent;
        let fixture: ComponentFixture<SeatTypeUpdateComponent>;
        let service: SeatTypeService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [BookYourMovieTestModule],
                declarations: [SeatTypeUpdateComponent]
            })
                .overrideTemplate(SeatTypeUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(SeatTypeUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(SeatTypeService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new SeatType(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.seatType = entity;
                    // WHEN
                    comp.save();
                    tick(); // simulate async

                    // THEN
                    expect(service.update).toHaveBeenCalledWith(entity);
                    expect(comp.isSaving).toEqual(false);
                })
            );

            it(
                'Should call create service on save for new entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new SeatType();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.seatType = entity;
                    // WHEN
                    comp.save();
                    tick(); // simulate async

                    // THEN
                    expect(service.create).toHaveBeenCalledWith(entity);
                    expect(comp.isSaving).toEqual(false);
                })
            );
        });
    });
});
