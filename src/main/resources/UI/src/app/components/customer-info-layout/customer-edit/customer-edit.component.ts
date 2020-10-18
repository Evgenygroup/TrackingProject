import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {FormBuilder, FormGroup} from '@angular/forms';
import {Customer} from '../../../model/customer/customer';

@Component({
  selector: 'app-customer-edit',
  templateUrl: './customer-edit.component.html',
  styleUrls: ['./customer-edit.component.scss']
})
export class CustomerEditComponent implements OnInit {

  customerEditForm: FormGroup;
  visible = true;
  displayed = false;

  public _customer: Customer;

  @Input()
  set customer(customer: Customer) {
    console.log(customer);
    this._customer = customer;
    this.initForm();
  }

  @Output()
  customerUpdated: EventEmitter<Customer> = new EventEmitter<Customer>();

  constructor(private fb: FormBuilder) { }

  ngOnInit(): void {
    this.initForm();
  }

  onSubmit() {
    this.customerUpdated.emit(this.customerEditForm.value);
    console.log(this.customerEditForm.value);
    this.customerEditForm.reset();
  }

  private initForm() {
    console.log(this._customer);
    this.customerEditForm = this.fb.group({
      id: [this._customer? this._customer.id : null],
      name: [this._customer? this._customer.name : null]
    });
  }
}
