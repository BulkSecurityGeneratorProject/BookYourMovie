/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { BookYourMovieTestModule } from '../../../test.module';
import { ScreenDeleteDialogComponent } from 'app/entities/screen/screen-delete-dialog.component';
import { ScreenService } from 'app/entities/screen/screen.service';

describe('Component Tests', () => {
    describe('Screen Management Delete Component', () => {
        let comp: ScreenDeleteDialogComponent;
        let fixture: ComponentFixture<ScreenDeleteDialogComponent>;
        let service: ScreenService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [BookYourMovieTestModule],
                declarations: [ScreenDeleteDialogComponent]
            })
                .overrideTemplate(ScreenDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(ScreenDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ScreenService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('confirmDelete', () => {
            it('Should call delete service on confirmDelete', inject(
                [],
                fakeAsync(() => {
                    // GIVEN
                    spyOn(service, 'delete').and.returnValue(of({}));

                    // WHEN
                    comp.confirmDelete(123);
                    tick();

                    // THEN
                    expect(service.delete).toHaveBeenCalledWith(123);
                    expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
                })
            ));
        });
    });
});
