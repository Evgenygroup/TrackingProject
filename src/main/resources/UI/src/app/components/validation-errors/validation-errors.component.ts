import {Component, Input, OnInit} from '@angular/core';
import {AbstractControl} from "@angular/forms";

@Component({
  selector: 'app-validation-errors',
  templateUrl: './validation-errors.component.html',
  styleUrls: ['./validation-errors.component.scss']
})
export class ValidationErrorsComponent implements OnInit {


  @Input()
  control: AbstractControl;

  constructor() {
  }

  ngOnInit(): void {
  }

  isControlInvalid(): boolean {
    return this.control && this.control.invalid && this.control.touched;
  }



}
