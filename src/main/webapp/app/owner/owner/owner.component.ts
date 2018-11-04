import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'jhi-owner',
  templateUrl: './owner.component.html',
  styles: []
})
export class OwnerComponent implements OnInit {

  constructor() { }

  ngOnInit() {
  }

  addScreen() {
    console.log('Screen to be added');
  }

}
