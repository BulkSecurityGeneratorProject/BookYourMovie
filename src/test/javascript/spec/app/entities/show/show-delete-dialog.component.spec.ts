/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { BookYourMovieTestModule } from '../../../test.module';
import { ShowDeleteDialogComponent } from 'app/entities/show/show-delete-dialog.component';
import { ShowService } from 'app/entities/show/show.service';

describe('Component Tests', () => {
    describe('Show Management Delete Component', () => {
        let comp: ShowDeleteDialogComponent;
        let fixture: ComponentFixture<ShowDeleteDialogComponent>;
        let service: ShowService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [BookYourMovieTestModule],
                declarations: [ShowDeleteDialogComponent]
            })
                .overrideTemplate(ShowDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(ShowDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ShowService);
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
