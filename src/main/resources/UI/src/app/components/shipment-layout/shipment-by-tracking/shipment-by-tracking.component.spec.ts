import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ShipmentByTrackingComponent } from './shipment-by-tracking.component';

describe('ShipmentByTrackingComponent', () => {
  let component: ShipmentByTrackingComponent;
  let fixture: ComponentFixture<ShipmentByTrackingComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ShipmentByTrackingComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ShipmentByTrackingComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
