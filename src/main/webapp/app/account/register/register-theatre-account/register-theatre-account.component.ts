import { Component, OnInit, Input, Output } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { ICity } from 'app/shared/model/city.model';
import { CityService } from 'app/entities/city';
import { JhiAlertService } from 'ng-jhipster';
import { Register } from '../register.service';
import { EventEmitter } from '@angular/core';

@Component({
  selector: 'jhi-register-theatre-account',
  templateUrl: './register-theatre-account.component.html'
})
export class RegisterTheatreAccountComponent implements OnInit {

  @Input() disableForm: boolean;
  @Input() user: any;

  @Output() onSuccess = new EventEmitter();
  @Output() onCustomError = new EventEmitter();

  theatre: any;
  cities: any;
  theatreAccount: any;

  constructor(private jhiAlertService: JhiAlertService,
    private cityService: CityService, private registerService: Register) {
  }

  ngOnInit() {
    this.theatre = {};
    this.cityService.getSupportedCities().subscribe(
      (res: HttpResponse<ICity[]>) => {
        this.cities = res.body;
      },
      (res: HttpErrorResponse) => this.onError(res.message)
    );
  }

  private onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }

  register() {
    this.theatreAccount = {
      theatre: this.theatre,
      user: this.user,
      password: this.user.password
    };
    this.registerService.saveTheatreAccount(this.theatreAccount).subscribe(
      () => {
        this.onSuccess.emit(true);
      },
      response => {
        this.onCustomError.emit(response);
      }
    );
  }

}
