import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { ISeat } from 'app/shared/model/seat.model';
import { Principal } from 'app/core';
import { SeatService } from './seat.service';

@Component({
    selector: 'jhi-seat',
    templateUrl: './seat.component.html'
})
export class SeatComponent implements OnInit, OnDestroy {
    seats: ISeat[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private seatService: SeatService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {}

    loadAll() {
        this.seatService.query().subscribe(
            (res: HttpResponse<ISeat[]>) => {
                this.seats = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    ngOnInit() {
        this.loadAll();
        this.principal.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInSeats();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: ISeat) {
        return item.id;
    }

    registerChangeInSeats() {
        this.eventSubscriber = this.eventManager.subscribe('seatListModification', response => this.loadAll());
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
