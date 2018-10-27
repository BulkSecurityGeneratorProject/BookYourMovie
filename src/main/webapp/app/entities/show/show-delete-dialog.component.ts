import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IShow } from 'app/shared/model/show.model';
import { ShowService } from './show.service';

@Component({
    selector: 'jhi-show-delete-dialog',
    templateUrl: './show-delete-dialog.component.html'
})
export class ShowDeleteDialogComponent {
    show: IShow;

    constructor(private showService: ShowService, public activeModal: NgbActiveModal, private eventManager: JhiEventManager) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.showService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'showListModification',
                content: 'Deleted an show'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-show-delete-popup',
    template: ''
})
export class ShowDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ show }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(ShowDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
                this.ngbModalRef.componentInstance.show = show;
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
