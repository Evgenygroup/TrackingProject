import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, ParamMap} from '@angular/router';
import {CustomerService} from '../../../service/customer-service/customer.service';
import {Shipment} from '../../../model/shipment/shipment';
import {switchMap} from 'rxjs/operators';

@Component({
  selector: 'app-customer-shipments',
  templateUrl: './customer-shipments.component.html',
  styleUrls: ['./customer-shipments.component.scss']
})

export class CustomerShipmentsComponent implements OnInit {
  shipments: Shipment[];
  customerId: string;

  constructor(private customerService: CustomerService, private route: ActivatedRoute) {
  }

  ngOnInit() {
    // Observable in Observable - valid solution but can be done in other way - pls see below
    /*this.route.paramMap.subscribe(params => {
      this.customerService.getCustomerShipments(Number(params.get('customerId'))).subscribe(c => {
        this.shipments = c;
        this.customerId = params.get('customerId');
      })
    });*/

    this.route.paramMap.pipe(
      switchMap((params: ParamMap) => {
          this.customerId = params.get('customerId');
          return this.customerService.getCustomerShipments(Number(this.customerId));
        }
      )
    ).subscribe( (shipments: Shipment[]) => this.shipments = shipments);
  }
}
