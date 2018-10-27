import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { ITheatre } from 'app/shared/model/theatre.model';
import { Principal } from 'app/core';
import { TheatreService } from './theatre.service';

@Component({
    selector: 'jhi-theatre',
    templateUrl: './theatre.component.html'
})
export class TheatreComponent implements OnInit, OnDestroy {
    theatres: ITheatre[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private theatreService: TheatreService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {}

    loadAll() {
        this.theatreService.query().subscribe(
            (res: HttpResponse<ITheatre[]>) => {
                this.theatres = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    ngOnInit() {
        this.loadAll();
        this.principal.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInTheatres();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: ITheatre) {
        return item.id;
    }

    registerChangeInTheatres() {
        this.eventSubscriber = this.eventManager.subscribe('theatreListModification', response => this.loadAll());
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
