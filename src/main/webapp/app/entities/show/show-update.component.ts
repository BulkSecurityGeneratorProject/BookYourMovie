import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiAlertService } from 'ng-jhipster';

import { IShow } from 'app/shared/model/show.model';
import { ShowService } from './show.service';
import { IScreen } from 'app/shared/model/screen.model';
import { ScreenService } from 'app/entities/screen';
import { IMovie } from 'app/shared/model/movie.model';
import { MovieService } from 'app/entities/movie';

@Component({
    selector: 'jhi-show-update',
    templateUrl: './show-update.component.html'
})
export class ShowUpdateComponent implements OnInit {
    show: IShow;
    isSaving: boolean;

    screens: IScreen[];

    movies: IMovie[];
    time: string;

    constructor(
        private jhiAlertService: JhiAlertService,
        private showService: ShowService,
        private screenService: ScreenService,
        private movieService: MovieService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ show }) => {
            this.show = show;
            this.time = this.show.time != null ? this.show.time.format(DATE_TIME_FORMAT) : null;
        });
        this.screenService.query().subscribe(
            (res: HttpResponse<IScreen[]>) => {
                this.screens = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.movieService.query().subscribe(
            (res: HttpResponse<IMovie[]>) => {
                this.movies = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        this.show.time = this.time != null ? moment(this.time, DATE_TIME_FORMAT) : null;
        if (this.show.id !== undefined) {
            this.subscribeToSaveResponse(this.showService.update(this.show));
        } else {
            this.subscribeToSaveResponse(this.showService.create(this.show));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IShow>>) {
        result.subscribe((res: HttpResponse<IShow>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackMovieById(index: number, item: IMovie) {
        return item.id;
    }
}
