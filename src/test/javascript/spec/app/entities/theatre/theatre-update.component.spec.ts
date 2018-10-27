/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { BookYourMovieTestModule } from '../../../test.module';
import { TheatreUpdateComponent } from 'app/entities/theatre/theatre-update.component';
import { TheatreService } from 'app/entities/theatre/theatre.service';
import { Theatre } from 'app/shared/model/theatre.model';

describe('Component Tests', () => {
    describe('Theatre Management Update Component', () => {
        let comp: TheatreUpdateComponent;
        let fixture: ComponentFixture<TheatreUpdateComponent>;
        let service: TheatreService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [BookYourMovieTestModule],
                declarations: [TheatreUpdateComponent]
            })
                .overrideTemplate(TheatreUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(TheatreUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(TheatreService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new Theatre(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.theatre = entity;
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
                    const entity = new Theatre();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.theatre = entity;
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
