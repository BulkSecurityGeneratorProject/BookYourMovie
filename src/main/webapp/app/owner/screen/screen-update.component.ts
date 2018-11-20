import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { IScreen } from 'app/shared/model/screen.model';
import { ScreenService } from './screen.service';
import { ITheatre } from 'app/shared/model/theatre.model';
import { TheatreService } from 'app/entities/theatre';
import { Status } from 'app/shared/model//seat.model';

@Component({
    selector: 'jhi-screen-update',
    templateUrl: './screen-update.component.html'
})
export class ScreenUpdateComponent implements OnInit {
    screen: IScreen;
    isSaving: boolean;
    rows: any[] = [];
    numberOfSeatsInARow = 50;
    numberOfRows = 30;

    theatres: ITheatre[];

    constructor(
        private jhiAlertService: JhiAlertService,
        private screenService: ScreenService,
        private theatreService: TheatreService,
        private activatedRoute: ActivatedRoute
    ) { }

    ngOnInit() {
        this.isSaving = false;
        this.initializeRows();
        this.activatedRoute.data.subscribe(({ screen }) => {
            this.screen = screen;
        });
        this.theatreService.query().subscribe(
            (res: HttpResponse<ITheatre[]>) => {
                this.theatres = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    initializeRows() {
        const seats: any[] = [];
        const seat: any = {
            'status': Status.SPACE,
            'seatNumber': 0
        };
        for (let i = 0; i < this.numberOfSeatsInARow; i++) {
            seats.push(seat);
        }
        const row: any = {
            'seats': seats
        };
        for (let i = 0; i < this.numberOfRows; i++) {
            this.rows.push(row);
        }
        console.log(this.rows);
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.screen.id !== undefined) {
            this.subscribeToSaveResponse(this.screenService.update(this.screen));
        } else {
            this.subscribeToSaveResponse(this.screenService.create(this.screen));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IScreen>>) {
        result.subscribe((res: HttpResponse<IScreen>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackTheatreById(index: number, item: ITheatre) {
        return item.id;
    }
}
