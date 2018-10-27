import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IShow } from 'app/shared/model/show.model';
import { Principal } from 'app/core';
import { ShowService } from './show.service';

@Component({
    selector: 'jhi-show',
    templateUrl: './show.component.html'
})
export class ShowComponent implements OnInit, OnDestroy {
    shows: IShow[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private showService: ShowService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {}

    loadAll() {
        this.showService.query().subscribe(
            (res: HttpResponse<IShow[]>) => {
                this.shows = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    ngOnInit() {
        this.loadAll();
        this.principal.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInShows();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IShow) {
        return item.id;
    }

    registerChangeInShows() {
        this.eventSubscriber = this.eventManager.subscribe('showListModification', response => this.loadAll());
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
