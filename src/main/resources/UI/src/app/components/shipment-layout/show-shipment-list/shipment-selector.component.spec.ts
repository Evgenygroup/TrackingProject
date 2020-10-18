import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ShipmentSelectorComponent } from './shipment-selector.component';

describe('ShowShipmentListComponent', () => {
  let component: ShipmentSelectorComponent;
  let fixture: ComponentFixture<ShipmentSelectorComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ShipmentSelectorComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ShipmentSelectorComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
