import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ShipmentTrackingListComponent } from './shipment-tracking-list.component';

describe('ShipmentTrackingListComponent', () => {
  let component: ShipmentTrackingListComponent;
  let fixture: ComponentFixture<ShipmentTrackingListComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ShipmentTrackingListComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ShipmentTrackingListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
