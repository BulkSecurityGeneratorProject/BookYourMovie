import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ISeatType } from 'app/shared/model/seat-type.model';
import { SeatTypeService } from './seat-type.service';

@Component({
    selector: 'jhi-seat-type-delete-dialog',
    templateUrl: './seat-type-delete-dialog.component.html'
})
export class SeatTypeDeleteDialogComponent {
    seatType: ISeatType;

    constructor(private seatTypeService: SeatTypeService, public activeModal: NgbActiveModal, private eventManager: JhiEventManager) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.seatTypeService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'seatTypeListModification',
                content: 'Deleted an seatType'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-seat-type-delete-popup',
    template: ''
})
export class SeatTypeDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ seatType }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(SeatTypeDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
                this.ngbModalRef.componentInstance.seatType = seatType;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate([{ outlets: { popup: null } }], { replaceUrl: true, queryParamsHandling: 'merge' });
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate([{ outlets: { popup: null } }], { replaceUrl: true, queryParamsHandling: 'merge' });
                        this.ngbModalRef = null;
                    }
                );
            }, 0);
        });
    }

    ngOnDestroy() {
        this.ngbModalRef = null;
    }
}
