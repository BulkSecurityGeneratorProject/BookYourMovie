import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IScreen } from 'app/shared/model/screen.model';
import { Principal } from 'app/core';
import { ScreenService } from './screen.service';

@Component({
    selector: 'jhi-screen',
    templateUrl: './screen.component.html'
})
export class ScreenComponent implements OnInit, OnDestroy {
    screens: IScreen[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private screenService: ScreenService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {}

    loadAll() {
        this.screenService.query().subscribe(
            (res: HttpResponse<IScreen[]>) => {
                this.screens = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    ngOnInit() {
        this.loadAll();
        this.principal.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInScreens();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IScreen) {
        return item.id;
    }

    registerChangeInScreens() {
        this.eventSubscriber = this.eventManager.subscribe('screenListModification', response => this.loadAll());
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
