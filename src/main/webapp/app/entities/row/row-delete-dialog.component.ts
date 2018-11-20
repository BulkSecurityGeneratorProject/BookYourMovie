import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IRow } from 'app/shared/model/row.model';
import { RowService } from './row.service';

@Component({
    selector: 'jhi-row-delete-dialog',
    templateUrl: './row-delete-dialog.component.html'
})
export class RowDeleteDialogComponent {
    row: IRow;

    constructor(private rowService: RowService, public activeModal: NgbActiveModal, private eventManager: JhiEventManager) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.rowService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'rowListModification',
                content: 'Deleted an row'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-row-delete-popup',
    template: ''
})
export class RowDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ row }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(RowDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
                this.ngbModalRef.componentInstance.row = row;
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
