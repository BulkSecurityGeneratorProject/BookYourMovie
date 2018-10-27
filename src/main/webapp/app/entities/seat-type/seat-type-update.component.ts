import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { ISeatType } from 'app/shared/model/seat-type.model';
import { SeatTypeService } from './seat-type.service';
import { IScreen } from 'app/shared/model/screen.model';
import { ScreenService } from 'app/entities/screen';

@Component({
    selector: 'jhi-seat-type-update',
    templateUrl: './seat-type-update.component.html'
})
export class SeatTypeUpdateComponent implements OnInit {
    seatType: ISeatType;
    isSaving: boolean;

    screens: IScreen[];

    constructor(
        private jhiAlertService: JhiAlertService,
        private seatTypeService: SeatTypeService,
        private screenService: ScreenService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ seatType }) => {
            this.seatType = seatType;
        });
        this.screenService.query().subscribe(
            (res: HttpResponse<IScreen[]>) => {
                this.screens = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.seatType.id !== undefined) {
            this.subscribeToSaveResponse(this.seatTypeService.update(this.seatType));
        } else {
            this.subscribeToSaveResponse(this.seatTypeService.create(this.seatType));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<ISeatType>>) {
        result.subscribe((res: HttpResponse<ISeatType>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }

    trackScreenById(index: number, item: IScreen) {
        return item.id;
    }
}
