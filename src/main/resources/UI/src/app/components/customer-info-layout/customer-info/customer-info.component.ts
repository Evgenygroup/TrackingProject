import { Component, OnInit } from '@angular/core';
import {Customer} from '../../../model/customer/customer';
import {CustomerService} from '../../../service/customer-service/customer.service';
import {Shipment} from '../../../model/shipment/shipment';
import {Observable} from 'rxjs';
import {switchMap} from 'rxjs/operators';
import {ActivatedRoute, ParamMap, Route} from '@angular/router';

@Component({
  selector: 'app-customer-info',
  templateUrl: './customer-info.component.html',
  styleUrls: ['./customer-info.component.scss']
})
export class CustomerInfoComponent implements OnInit {

  currentCustomer: Customer;
  shipment: Shipment;
  shipments: Observable<Shipment[]>;

  showCustomerEditForm = false;
  display = false;
  showCustomerEdited: boolean = false;

  constructor(private customerService: CustomerService,
              private route: ActivatedRoute) { }

  ngOnInit(): void {
    this.route.paramMap.pipe(
      switchMap((params: ParamMap) => {
          const customerId = +params.get('customerId');
          return this.customerService.getCustomer(customerId);
        }
      )
    ).subscribe( customer => this.currentCustomer = customer);
  }

  onCustomerUpdated(customer: Customer) {
    this.customerService.updateCustomer(customer).subscribe(
      (customer) => {
        this.currentCustomer = customer;
        this.showCustomerEdited = true;
      }
    );
  }

  onShipmentAdded(shipment: Shipment) {
    this.customerService.createShipment(shipment.customerId, shipment).subscribe(
      (shipment) =>  this.shipment = shipment
    );
  }

  toggleCustomerEditForm() {
    this.showCustomerEditForm = !this.showCustomerEditForm;
  }

  toggleShipmentAddForm() {
    this.display = !this.display;
  }
}
