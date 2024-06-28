import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SearchLawComponent } from './search-law.component';

describe('SearchLawComponent', () => {
  let component: SearchLawComponent;
  let fixture: ComponentFixture<SearchLawComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ SearchLawComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(SearchLawComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
