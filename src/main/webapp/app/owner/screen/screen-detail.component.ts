import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IScreen } from 'app/shared/model/screen.model';

@Component({
    selector: 'jhi-screen-detail',
    templateUrl: './screen-detail.component.html'
})
export class ScreenDetailComponent implements OnInit {
    screen: IScreen;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ screen }) => {
            this.screen = screen;
        });
    }

    previousState() {
        window.history.back();
    }
}
