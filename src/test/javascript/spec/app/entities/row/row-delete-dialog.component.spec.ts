/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { BookYourMovieTestModule } from '../../../test.module';
import { RowDeleteDialogComponent } from 'app/entities/row/row-delete-dialog.component';
import { RowService } from 'app/entities/row/row.service';

describe('Component Tests', () => {
    describe('Row Management Delete Component', () => {
        let comp: RowDeleteDialogComponent;
        let fixture: ComponentFixture<RowDeleteDialogComponent>;
        let service: RowService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [BookYourMovieTestModule],
                declarations: [RowDeleteDialogComponent]
            })
                .overrideTemplate(RowDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(RowDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(RowService);
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
