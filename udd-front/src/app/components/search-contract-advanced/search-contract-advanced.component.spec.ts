import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SearchContractAdvancedComponent } from './search-contract-advanced.component';

describe('SearchContractAdvancedComponent', () => {
  let component: SearchContractAdvancedComponent;
  let fixture: ComponentFixture<SearchContractAdvancedComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ SearchContractAdvancedComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(SearchContractAdvancedComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
