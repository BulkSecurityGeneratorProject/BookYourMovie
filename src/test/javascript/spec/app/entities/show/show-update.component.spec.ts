/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { BookYourMovieTestModule } from '../../../test.module';
import { ShowUpdateComponent } from 'app/entities/show/show-update.component';
import { ShowService } from 'app/entities/show/show.service';
import { Show } from 'app/shared/model/show.model';

describe('Component Tests', () => {
    describe('Show Management Update Component', () => {
        let comp: ShowUpdateComponent;
        let fixture: ComponentFixture<ShowUpdateComponent>;
        let service: ShowService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [BookYourMovieTestModule],
                declarations: [ShowUpdateComponent]
            })
                .overrideTemplate(ShowUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(ShowUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ShowService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new Show(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.show = entity;
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
                    const entity = new Show();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.show = entity;
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
