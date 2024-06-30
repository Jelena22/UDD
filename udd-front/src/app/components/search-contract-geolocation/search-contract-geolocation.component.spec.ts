import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SearchContractGeolocationComponent } from './search-contract-geolocation.component';

describe('SearchContractGeolocationComponent', () => {
  let component: SearchContractGeolocationComponent;
  let fixture: ComponentFixture<SearchContractGeolocationComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ SearchContractGeolocationComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(SearchContractGeolocationComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
