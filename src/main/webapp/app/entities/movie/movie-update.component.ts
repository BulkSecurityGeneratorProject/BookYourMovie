import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { IMovie } from 'app/shared/model/movie.model';
import { MovieService } from './movie.service';

@Component({
    selector: 'jhi-movie-update',
    templateUrl: './movie-update.component.html'
})
export class MovieUpdateComponent implements OnInit {
    movie: IMovie;
    isSaving: boolean;

    constructor(private movieService: MovieService, private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ movie }) => {
            this.movie = movie;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.movie.id !== undefined) {
            this.subscribeToSaveResponse(this.movieService.update(this.movie));
        } else {
            this.subscribeToSaveResponse(this.movieService.create(this.movie));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IMovie>>) {
        result.subscribe((res: HttpResponse<IMovie>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }
}
