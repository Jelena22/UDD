import { Component } from '@angular/core';
import { SearchByGeolocationDTO, SearchResultGovernment, SearchService } from 'src/app/service/search.service';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-search-contract-geolocation',
  templateUrl: './search-contract-geolocation.component.html',
  styleUrls: ['./search-contract-geolocation.component.css']
})
export class SearchContractGeolocationComponent {

  searchResultGovernment: SearchResultGovernment | null = null;
  totalPages: number = 0;
  totalElements: number = 0;
  currentPage: number = 0;
  pageSize: number = 4;
  city: string = '';
  distanceInKm: number = 0;

  constructor(private searchService: SearchService) {

    }

  onSearch(): void {
    if (this.city === '' && this.distanceInKm === 0) {
      Swal.fire({
        icon: 'warning',
        title: 'Warning',
        text: 'Please enter keywords to search.',
      });
      return;
    }
    const query: SearchByGeolocationDTO = {
      city: this.city,
      distanceInKm: this.distanceInKm
    };
    const isFirstSearch = !this.searchResultGovernment;
    this.searchService.searchContractByGeolocation(query, this.currentPage, this.pageSize).subscribe(
      result => {
        this.searchResultGovernment = result;
        console.log(result);
        this.totalPages = result.totalPages;
        console.log(this.totalPages);
        this.totalElements = this.searchResultGovernment.totalElements;
        if (isFirstSearch) {
          Swal.fire({
            icon: 'success',
            title: 'Search Complete!',
            text: 'Results have been successfully retrieved.',
          });
        }
      },
      error => {
        Swal.fire({
          icon: 'error',
          title: 'Search Failed',
          text: 'There was an error processing your search.',
        });
        console.error('Search failed', error);
      }
    );
  }

  onPageChange(page: number): void {
    if (page >= 0 && page < this.totalPages) {
      console.log(this.totalPages);
      this.currentPage = page;
      this.onSearch();
    }
  }

}
