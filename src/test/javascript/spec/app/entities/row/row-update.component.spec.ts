/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { BookYourMovieTestModule } from '../../../test.module';
import { RowUpdateComponent } from 'app/entities/row/row-update.component';
import { RowService } from 'app/entities/row/row.service';
import { Row } from 'app/shared/model/row.model';

describe('Component Tests', () => {
    describe('Row Management Update Component', () => {
        let comp: RowUpdateComponent;
        let fixture: ComponentFixture<RowUpdateComponent>;
        let service: RowService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [BookYourMovieTestModule],
                declarations: [RowUpdateComponent]
            })
                .overrideTemplate(RowUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(RowUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(RowService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new Row(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.row = entity;
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
                    const entity = new Row();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.row = entity;
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
