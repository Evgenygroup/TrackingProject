import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CustomerShipmentCreateComponent } from './customer-shipment-create.component';

describe('CustomerShipmentCreateComponent', () => {
  let component: CustomerShipmentCreateComponent;
  let fixture: ComponentFixture<CustomerShipmentCreateComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CustomerShipmentCreateComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CustomerShipmentCreateComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
