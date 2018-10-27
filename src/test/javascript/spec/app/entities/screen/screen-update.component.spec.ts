/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { BookYourMovieTestModule } from '../../../test.module';
import { ScreenUpdateComponent } from 'app/entities/screen/screen-update.component';
import { ScreenService } from 'app/entities/screen/screen.service';
import { Screen } from 'app/shared/model/screen.model';

describe('Component Tests', () => {
    describe('Screen Management Update Component', () => {
        let comp: ScreenUpdateComponent;
        let fixture: ComponentFixture<ScreenUpdateComponent>;
        let service: ScreenService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [BookYourMovieTestModule],
                declarations: [ScreenUpdateComponent]
            })
                .overrideTemplate(ScreenUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(ScreenUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ScreenService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new Screen(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.screen = entity;
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
                    const entity = new Screen();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.screen = entity;
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
