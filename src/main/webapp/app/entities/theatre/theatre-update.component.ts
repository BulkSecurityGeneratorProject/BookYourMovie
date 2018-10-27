import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { ITheatre } from 'app/shared/model/theatre.model';
import { TheatreService } from './theatre.service';
import { ICity } from 'app/shared/model/city.model';
import { CityService } from 'app/entities/city';

@Component({
    selector: 'jhi-theatre-update',
    templateUrl: './theatre-update.component.html'
})
export class TheatreUpdateComponent implements OnInit {
    theatre: ITheatre;
    isSaving: boolean;

    cities: ICity[];

    constructor(
        private jhiAlertService: JhiAlertService,
        private theatreService: TheatreService,
        private cityService: CityService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ theatre }) => {
            this.theatre = theatre;
        });
        this.cityService.query().subscribe(
            (res: HttpResponse<ICity[]>) => {
                this.cities = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.theatre.id !== undefined) {
            this.subscribeToSaveResponse(this.theatreService.update(this.theatre));
        } else {
            this.subscribeToSaveResponse(this.theatreService.create(this.theatre));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<ITheatre>>) {
        result.subscribe((res: HttpResponse<ITheatre>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackCityById(index: number, item: ICity) {
        return item.id;
    }
}
