/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { BookYourMovieTestModule } from '../../../test.module';
import { SeatTypeDeleteDialogComponent } from 'app/entities/seat-type/seat-type-delete-dialog.component';
import { SeatTypeService } from 'app/entities/seat-type/seat-type.service';

describe('Component Tests', () => {
    describe('SeatType Management Delete Component', () => {
        let comp: SeatTypeDeleteDialogComponent;
        let fixture: ComponentFixture<SeatTypeDeleteDialogComponent>;
        let service: SeatTypeService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [BookYourMovieTestModule],
                declarations: [SeatTypeDeleteDialogComponent]
            })
                .overrideTemplate(SeatTypeDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(SeatTypeDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(SeatTypeService);
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
